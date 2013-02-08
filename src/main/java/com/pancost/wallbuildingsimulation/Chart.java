package com.pancost.wallBuildingSimulation;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.*;

public class Chart implements Steppable {

	public Chart() {
		super();
	}

	public void step(SimState state) {
		final WallBuilding wb = (WallBuilding)state;
		
		int zeros = 0;
		int ones = 0;
		int twos = 0;
		int threes = 0;		
		for(int x = 0; x < WallBuilding.GRID_WIDTH; x++){
			for(int y = 0; y < WallBuilding.GRID_HEIGHT; y++){
				int blockSize = wb.block.get(x,y);
				if(blockSize == 0){
					zeros++;
				}else if(blockSize == 1){
					ones++;
				}else if(blockSize == 2){
					twos++;
				}else{
					threes++;
				}
			}
		}
		
		int blocksAroundFood = 0;
		
		Bag allFood = wb.food.allObjects;
		for (int i = 0; i < allFood.size(); i++){
			Int2D foodLocation = wb.food.getObjectLocation(allFood.get(i));
			for(int x = foodLocation.x-5; x <= foodLocation.x+5; x++){
				for(int y = foodLocation.y-5; y <= foodLocation.y+5; y++){
					blocksAroundFood += wb.block.get(wb.block.stx(x),wb.block.sty(y));
				}
			}
		}
		
		int blocksAroundPredators = 0;
		Bag allPredators = wb.predator.allObjects;
		for (int i = 0; i < allPredators.size(); i++){
			Int2D predatorLocation = wb.predator.getObjectLocation(allPredators.get(i));
			for(int x = predatorLocation.x-1; x <= predatorLocation.x+1; x++){
				for(int y = predatorLocation.y-1; y <= predatorLocation.y+1; y++){
					blocksAroundPredators += wb.block.get(wb.block.stx(x),wb.block.sty(y));
				}
			}
		}
		
		wb.blocksNearPredators.add(wb.schedule.getSteps(), blocksAroundPredators);
		wb.blocksNearFood.add(wb.schedule.getSteps(), blocksAroundFood);
		wb.population.add(wb.schedule.getSteps(), wb.prey.getAllObjects().size());
		wb.blockZeroHeightChart.add(wb.schedule.getSteps(), zeros);
		wb.blockOneHeightChart.add(wb.schedule.getSteps(), ones);
		wb.blockTwoHeightChart.add(wb.schedule.getSteps(), twos);
		wb.blockThreeHeightChart.add(wb.schedule.getSteps(), threes);
	}

}
