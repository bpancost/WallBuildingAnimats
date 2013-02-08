package com.pancost.wallBuildingSimulation;

import com.pancost.wallbuildingsimulation.evolve.EvolutionEngine;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Mating implements Steppable {
	
    private static final long serialVersionUID = 1L;

    public Mating() {
        super();
    }

    @Override
    public void step(SimState state) {//2 prey in same state with mating motor neuron firing
        final WallBuilding wb = (WallBuilding)state;

        if((wb.prey.allObjects.size() < WallBuilding.PREY_CAP) && WallBuilding.DO_PREY_CAP){//this is a bit harsh, but I need it! AND BROKEN
            for(int x = 0; x < WallBuilding.GRID_WIDTH; x++){
                for(int y = 0; y < WallBuilding.GRID_HEIGHT; y++){ 
                    if(wb.prey.numObjectsAtLocation(x,y) >1){
                        Bag preyAtLocation = wb.prey.getObjectsAtLocation(x,y);
                        Bag matePrey = new Bag();
                        for(int i = 0; i < preyAtLocation.size(); i++){//make a bag of those who WANT to mate
                            if(((Prey)preyAtLocation.get(i)).canMate()){
                                    matePrey.add(preyAtLocation.get(i));
                            }
                        }
                        for(int i = 0; i+1 < matePrey.size(); i += 2){
                            Prey parent1 = (Prey)matePrey.get(i);
                            Prey parent2 = (Prey)matePrey.get(i+1);
                            double[] childEncoding = EvolutionEngine.evolve(parent1.getNNDirectEncoding(), parent2.getNNDirectEncoding());
                            Prey child = new Prey(childEncoding);
                            wb.prey.setObjectLocation(child, x, y);
                            child.setStoppable(wb.schedule.scheduleRepeating(wb.schedule.getSteps()+2, 1, child, 2));
                            parent1.resetMating();
                            parent2.resetMating();
                        }
                    }
                }
            }
        }
    }
}
