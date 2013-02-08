package com.pancost.wallBuildingSimulation;

import java.awt.Color;
import java.awt.Graphics2D;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import sim.util.Bag;
import sim.util.Int2D;

public class Food extends SimplePortrayal2D implements Steppable {
    private static final long serialVersionUID = 1L;

    public Food() {
        super();
    }

    @Override
    public void step(SimState state) {
        final WallBuilding wb = (WallBuilding)state;
        Bag allFood = wb.food.allObjects;
        for(int i = 0; i < allFood.size(); i++){
            Int2D foodLocation = wb.food.getObjectLocation((Food)allFood.get(i));
            Bag feedPrey = new Bag();
            Bag feedPreyR = wb.prey.getObjectsAtLocation(wb.prey.stx(foodLocation.x+1),foodLocation.y);
            Bag feedPreyL = wb.prey.getObjectsAtLocation(wb.prey.stx(foodLocation.x-1),foodLocation.y);
            Bag feedPreyF = wb.prey.getObjectsAtLocation(foodLocation.x,wb.prey.sty(foodLocation.y+1));
            Bag feedPreyB = wb.prey.getObjectsAtLocation(foodLocation.x,wb.prey.sty(foodLocation.y-1));//a bag of all prey to be fed at this food resource
            if(feedPreyR != null){
                feedPrey.addAll(feedPreyR);
            }
            if(feedPreyL != null){
                feedPrey.addAll(feedPreyL);
            }
            if(feedPreyF != null){
                feedPrey.addAll(feedPreyF);
            }
            if(feedPreyB != null){
                feedPrey.addAll(feedPreyB);
            }
            for(int j = 0; j < feedPrey.size(); j++){
                    ((Prey)feedPrey.get(j)).resetHunger();
            }
        }
    }
	
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
        graphics.setColor(Color.green);

        int x = (int)(info.draw.x - info.draw.width / 2.0);
        int y = (int)(info.draw.y - info.draw.height / 2.0);
        int width = (int)(info.draw.width);
        int height = (int)(info.draw.height);
        graphics.fillOval(x,y,width, height);
    }
}
