package com.pancost.wallBuildingSimulation;

import sim.engine.SimState;
import sim.display.*;
import sim.portrayal.grid.*;
import sim.util.gui.*;
import java.awt.*;
import javax.swing.JFrame;
import org.jfree.chart.*;

public class WallBuildingWithUI extends GUIState {
    public Display2D display;
    public JFrame displayFrame;
    
    FastValueGridPortrayal2D foodOdorPortrayal = new FastValueGridPortrayal2D("Food Odor");
    FastValueGridPortrayal2D predatorOdorPortrayal = new FastValueGridPortrayal2D("Predator Odor");
    FastValueGridPortrayal2D preyOdorPortrayal = new FastValueGridPortrayal2D("Prey Odor");
    FastValueGridPortrayal2D blockPortrayal = new FastValueGridPortrayal2D("Blocks");
    SparseGridPortrayal2D foodPortrayal = new SparseGridPortrayal2D();
    SparseGridPortrayal2D predatorPortrayal = new SparseGridPortrayal2D();
    SparseGridPortrayal2D preyPortrayal = new SparseGridPortrayal2D();
         
    public static void main(String[] args){
        WallBuildingWithUI antsForage = new WallBuildingWithUI();
        Console c = new Console(antsForage);
        c.setVisible(true);
    }
    
    public WallBuildingWithUI(){
    	super(new WallBuilding(System.currentTimeMillis()));//to change
    }
    public WallBuildingWithUI(SimState state) {
    	super(state);
    }
    
   // public String getName(){
   // 	return "Wall Builders";
   // }
    
    public void setupPortrayals(){
        WallBuilding wb = (WallBuilding)state;
        
        foodOdorPortrayal.setField(wb.foodOdor);
        foodOdorPortrayal.setMap(new SimpleColorMap(0,1/*WallBuilding.INITIAL_FOOD/2*/,new Color(0,0,0,0),new Color(0,255,0,128)));
        
        predatorOdorPortrayal.setField(wb.predatorOdor);
        predatorOdorPortrayal.setMap(new SimpleColorMap(0,1/*WallBuilding.INITIAL_PREDATORS/7*/,new Color(0,0,0,0), new Color(255,0,0,128)));
        
        preyOdorPortrayal.setField(wb.preyOdor);
        preyOdorPortrayal.setMap(new SimpleColorMap(0,1/*WallBuilding.INITIAL_PREY/10*/,new Color(0,0,0,0), new Color(0,0,255,128)));
        
        blockPortrayal.setField(wb.block);
        blockPortrayal.setMap(new SimpleColorMap(0,3, new Color(0,0,0,0), new Color(128,64,64,255)));//brown?
        
        foodPortrayal.setField(wb.food);
        predatorPortrayal.setField(wb.predator);
        preyPortrayal.setField(wb.prey);
                
        // reschedule the displayer
        display.reset();

        // redraw the display
        display.repaint();
    }
    
    @Override
    public void start(){
        super.start();  // set up everything but replacing the display
        // set up our portrayals
        setupPortrayals();
    }
            
    @Override
    public void load(SimState state){
        super.load(state);
        // we now have new grids.  Set up the portrayals to reflect that
        setupPortrayals();
    }

    @Override
    public void init(Controller c){
        super.init(c);
        
        // Make the Display2D.  We'll have it display stuff later.
        display = new Display2D(400,400,this, 1l); // at 400x400, we've got 4x4 per array position
        displayFrame = display.createFrame();
        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);

        // attach the portrayals from bottom to top
        display.attach(foodOdorPortrayal,"Food Odor");
        display.attach(predatorOdorPortrayal,"Predator Odor");
        display.attach(preyOdorPortrayal,"Prey Odor");
        display.attach(blockPortrayal,"Blocks");
        display.attach(foodPortrayal,"Food");
        display.attach(predatorPortrayal,"Predators");
        display.attach(preyPortrayal,"Prey");
        
        // specify the backdrop color  -- what gets painted behind the displays
        display.setBackdrop(Color.white);
        
        addChartPanel(c);
    }
        
    public void quit(){
        super.quit();
        
        // disposing the displayFrame automatically calls quit() on the display,
        // so we don't need to do so ourselves here.
        if (displayFrame!=null) displayFrame.dispose();
        displayFrame = null;  // let gc
        display = null;       // let gc
    }
    
    void addChartPanel( Controller c )
    {
        // ask the model to create the chart for us
        ChartPanel chartPanel1 = new ChartPanel(((WallBuilding)state).createChart1());
        ChartPanel chartPanel2 = new ChartPanel(((WallBuilding)state).createChart2());
        ChartPanel chartPanel3 = new ChartPanel(((WallBuilding)state).createChart3());

        // create the chart frame
        JFrame chartFrame1 = new JFrame();
        chartFrame1.setResizable(true);
        chartFrame1.getContentPane().setLayout(new BorderLayout());
        chartFrame1.getContentPane().add(chartPanel1, BorderLayout.CENTER);
        chartFrame1.setTitle("Live Block Height Statistics");
        chartFrame1.pack();
        
        JFrame chartFrame2 = new JFrame();
        chartFrame2.setResizable(true);
        chartFrame2.getContentPane().setLayout(new BorderLayout());
        chartFrame2.getContentPane().add(chartPanel2, BorderLayout.CENTER);
        chartFrame2.setTitle("Live Block Location Statistics");
        chartFrame2.pack();
        
        JFrame chartFrame3 = new JFrame();
        chartFrame3.setResizable(true);
        chartFrame3.getContentPane().setLayout(new BorderLayout());
        chartFrame3.getContentPane().add(chartPanel3, BorderLayout.CENTER);
        chartFrame3.setTitle("Live Prey Population Statistics");
        chartFrame3.pack();

        // register the chartFrame so it appears in the "Display" list
        c.registerFrame(chartFrame1);
        c.registerFrame(chartFrame2);
        c.registerFrame(chartFrame3);

        // make the frame visible
        chartFrame1.setVisible(true);
        chartFrame2.setVisible(true);
        chartFrame3.setVisible(true);
    }
}
    
    
    
    
