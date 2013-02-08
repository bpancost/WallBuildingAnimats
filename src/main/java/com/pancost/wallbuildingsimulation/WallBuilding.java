package com.pancost.wallBuildingSimulation;

import ec.util.*;
import sim.engine.*;
import sim.field.grid.*;
import sim.util.*;
import org.jfree.data.xy.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;

public class WallBuilding extends SimState {
	
	//TODO Tweak
	//TODO Cleanup
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3717059111261911846L;
	public static final int INITIAL_PREDATORS = 15;//15 is optimal
	public static final int INITIAL_PREY = 150;//150 is optimal
	public static final int INITIAL_FOOD = 30;//10 is optimal
	public static final int INITIAL_BLOCKS = 10000;//1500
    public static final int GRID_HEIGHT = 200;
    public static final int GRID_WIDTH = 200;
    public static final int PREY_CAP = 5000;//500 is optimal
    public static final boolean DO_PREY_CAP = true;//broken, must be true
    public static final int HUNGER_DEATH = 10;
    public static final boolean DO_HUNGER_DEATH = true;
	
    public XYSeries population = new XYSeries("Prey Population");
    public XYSeries blocksNearFood = new XYSeries("Blocks Near Food");
    public XYSeries blocksNearPredators = new XYSeries("Blocks Near Predators");
    //public XYSeries blocksNearPrey = new XYSeries("Blocks Near Prey");
    public XYSeries blockZeroHeightChart = new XYSeries("Block Zero Height Chart");
    public XYSeries blockOneHeightChart = new XYSeries("Block One Height Chart");
    public XYSeries blockTwoHeightChart = new XYSeries("Block Two Height Chart");
    public XYSeries blockThreeHeightChart = new XYSeries("Block Three Height Chart");
    
    public IntGrid2D block = new IntGrid2D(GRID_WIDTH, GRID_HEIGHT);
    public DoubleGrid2D foodOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
    public DoubleGrid2D predatorOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
    public DoubleGrid2D preyOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
    public SparseGrid2D food = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
    public SparseGrid2D predator = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
    public SparseGrid2D prey = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
	
	public WallBuilding(MersenneTwisterFast random, Schedule schedule) {
		super(random, schedule);
	}
	
	public WallBuilding(long seed){ 
		super(new MersenneTwisterFast(seed), new Schedule());
    }
	
	public void start(){
		super.start();
		
		block = new IntGrid2D(GRID_WIDTH, GRID_HEIGHT);
		foodOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
	    predatorOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
	    preyOdor = new DoubleGrid2D(GRID_WIDTH, GRID_HEIGHT, 0);
	    food = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
	    predator = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
	    prey = new SparseGrid2D(GRID_WIDTH, GRID_HEIGHT);
	    
	    population.clear();
	    blocksNearFood.clear();
	    blocksNearPredators.clear();
	    population.clear();
	    blockZeroHeightChart.clear();
	    blockOneHeightChart.clear();
	    blockTwoHeightChart.clear();
	    blockThreeHeightChart.clear();
	    
	    for(int foodCount = 0; foodCount < INITIAL_FOOD; foodCount++){
	    	boolean notPlaced = true;
	    	Food f = new Food();
	    	while(notPlaced){
	    		int x = this.random.nextInt(GRID_WIDTH);
	    		int y = this.random.nextInt(GRID_HEIGHT);
	    		if(food.getObjectsAtLocation(x,y) == null){
	    			food.setObjectLocation(f,x,y);
	    			schedule.scheduleRepeating(Schedule.EPOCH, 3, f);
	    			notPlaced = false;
	    		}
	    	}
	    }
	    for(int predatorCount = 0; predatorCount < INITIAL_PREDATORS; predatorCount++){
	    	boolean notPlaced = true;
	    	Predator p = new Predator();
	    	while(notPlaced){
	    		int x = this.random.nextInt(GRID_WIDTH);
	    		int y = this.random.nextInt(GRID_HEIGHT);
	    		if(food.getObjectsAtLocation(x,y) == null){
	    			predator.setObjectLocation(p, x, y);
	    			schedule.scheduleRepeating(Schedule.EPOCH, 2, p);
	    			notPlaced = false;
	    		}
	    	}
	    }
	    for(int preyCount = 0; preyCount < INITIAL_PREY; preyCount++){
	    	boolean notPlaced = true;
	    	Prey p = new Prey();
	    	while(notPlaced){
	    		int x = this.random.nextInt(GRID_WIDTH);
	    		int y = this.random.nextInt(GRID_HEIGHT);
	    		if(food.getObjectsAtLocation(x,y) == null){
	    			prey.setObjectLocation(p, x, y);
	    			p.setStoppable(schedule.scheduleRepeating(Schedule.EPOCH, 1, p, 2));
	    			notPlaced = false;
	    		}
	    	}
	    }
	    for(int blockCount = 0; blockCount < INITIAL_BLOCKS; blockCount++){
	    	boolean notPlaced = true;
	    	while(notPlaced){
	    		int x = this.random.nextInt(GRID_WIDTH);
	    		int y = this.random.nextInt(GRID_HEIGHT);
	    		if(food.getObjectsAtLocation(x,y) == null){
	    			if((predator.getObjectsAtLocation(x,y) == null) &&
	    					(prey.getObjectsAtLocation(x,y) == null)){
	    				block.set(x,y, block.get(x,y)+1);
	    				notPlaced = false;
	    			}
	    		}
	    	}
	    }
	    
	    Mating m = new Mating();
	    schedule.scheduleRepeating(Schedule.EPOCH,4,m,2);
	    
	    Chart c = new Chart();
	    schedule.scheduleRepeating(Schedule.EPOCH,5,c,2);
	    
	    OdorUpdate o = new OdorUpdate();
	    schedule.scheduleRepeating(Schedule.EPOCH,6,o,1);//odor update must always be last in the scheduler
	    
	    Bag allFood = food.allObjects;
	    for(int i = 0; i < allFood.size(); i++){
			Int2D foodLocation = food.getObjectLocation((Food)allFood.get(i));
			for(int x = foodLocation.x-15; x <= foodLocation.x+15; x++){
				for(int y = foodLocation.y-15; y <= foodLocation.y+15; y++){
					//double value = Math.max(Math.abs(foodLocation.y-y), Math.abs(foodLocation.x-x));
					double value = Math.abs(foodLocation.y - y) + Math.abs(foodLocation.x - x);
					foodOdor.set(foodOdor.stx(x),foodOdor.sty(y), Math.max((1-(value/15)), foodOdor.get(foodOdor.stx(x),foodOdor.sty(y))));
					//foodOdor.set(foodOdor.stx(x),foodOdor.sty(y), (1-(value/30))*2 + foodOdor.get(foodOdor.stx(x),foodOdor.sty(y)));
				}
			}			
		}
	}
	
	JFreeChart createChart1(){
		XYSeriesCollection xySeries = new XYSeriesCollection();
		//xySeries.addSeries(blockZeroHeightChart);
		xySeries.addSeries(blockOneHeightChart);
		xySeries.addSeries(blockTwoHeightChart);
		xySeries.addSeries(blockThreeHeightChart);
		xySeries.setIntervalWidth(10);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Block Heights Over Time",
				"Time Step",
				"Number of Blocks",
				xySeries,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);

        XYPlot plot = chart.getXYPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setFixedDimension(50);
        //ValueAxis yAxis = plot.getRangeAxis();
        //XYItemRenderer renderer = plot.getRenderer();
        //renderer.setSeriesPaint(0, Color.red);
        return chart;
    }
	
	JFreeChart createChart2(){
		XYSeriesCollection xySeries = new XYSeriesCollection();
		xySeries.addSeries(blocksNearFood);
		xySeries.addSeries(blocksNearPredators);
		xySeries.setIntervalWidth(10);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Block Near Food and Predators",
				"Time Step",
				"Number of Blocks",
				xySeries,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);

        XYPlot plot = chart.getXYPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setFixedDimension(50);
        return chart;
	}
	
	JFreeChart createChart3(){
		XYSeriesCollection xySeries = new XYSeriesCollection();
		xySeries.addSeries(population);
		xySeries.setIntervalWidth(10);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Population Over Time",
				"Time Step",
				"Number of Prey",
				xySeries,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);

        XYPlot plot = chart.getXYPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setFixedDimension(50);
        return chart;
	}
	
}
