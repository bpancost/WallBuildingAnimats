package com.pancost.wallBuildingSimulation;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;
import sim.util.Int2D;

public class OdorUpdate implements Steppable {
    
    private static final long serialVersionUID = 1L;

    public OdorUpdate() {
        super();
    }

    @Override
    public void step(SimState state) {
        final WallBuilding wb = (WallBuilding)state;
        wb.preyOdor.multiply(0);
        wb.predatorOdor.multiply(0);
        Bag prey = wb.prey.allObjects;
        Bag predators = wb.predator.allObjects;
        for(int i = 0; i < prey.size(); i++){
            Int2D preyLocation = wb.prey.getObjectLocation((Prey)prey.get(i));
            for(int x = preyLocation.x-10; x <= preyLocation.x+10; x++){
                for(int y = preyLocation.y-10; y <= preyLocation.y+10; y++){
                    double value = Math.max(Math.abs(preyLocation.y-y), Math.abs(preyLocation.x-x));
                    //double value = Math.abs(preyLocation.y - y) + Math.abs(preyLocation.x - x);
                    wb.preyOdor.set(wb.preyOdor.stx(x),wb.preyOdor.sty(y), Math.max((1-(value/10)), wb.preyOdor.get(wb.preyOdor.stx(x),wb.preyOdor.sty(y))));
                    //wb.preyOdor.set(wb.preyOdor.stx(x),wb.preyOdor.sty(y), (1-(value/10)) + wb.preyOdor.get(wb.preyOdor.stx(x),wb.preyOdor.sty(y)));
                }
            }
        }
        for(int i = 0; i < predators.size(); i++){
            Int2D predatorLocation = wb.predator.getObjectLocation((Predator)predators.get(i));
            for(int x = predatorLocation.x-10; x <= predatorLocation.x+10; x++){
                for(int y = predatorLocation.y-10; y <= predatorLocation.y+10; y++){
                    double value = Math.max(Math.abs(predatorLocation.y-y), Math.abs(predatorLocation.x-x));
                    //double value = Math.abs(predatorLocation.y - y) + Math.abs(predatorLocation.x - x);
                    wb.predatorOdor.set(wb.predatorOdor.stx(x),wb.predatorOdor.sty(y), Math.max((1-(value/10)), wb.predatorOdor.get(wb.predatorOdor.stx(x),wb.predatorOdor.sty(y))));
                    //wb.predatorOdor.set(wb.predatorOdor.stx(x),wb.predatorOdor.sty(y), (1-(value/10)) + wb.predatorOdor.get(wb.predatorOdor.stx(x),wb.predatorOdor.sty(y)));
                }
            }
        }
    }

}
