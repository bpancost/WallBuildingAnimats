package com.pancost.wallBuildingSimulation;

import com.pancost.wallbuildingsimulation.prey.PreyNeuralNetwork;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import sim.util.Int2D;

public class Prey extends SimplePortrayal2D implements Steppable{
    
    private static final long serialVersionUID = 1L;
    private Stoppable stop;
    private static int NORTH = 0;
    private static int EAST = 1;
    private static int SOUTH = 2;
    private static int WEST = 3;
    private int orientation = 0;//This seems a bit like a hack (random would be better)
    private PreyNeuralNetwork nn;
    private LinkedList<String> lastFourMoves = new LinkedList<>();

    public Prey() {
        nn = new PreyNeuralNetwork();
    }

    public Prey(double[] nnDirectEncoding){
        nn = new PreyNeuralNetwork(nnDirectEncoding);
    }
	
    @Override
    public void step(SimState state) {
        final WallBuilding wb = (WallBuilding)state;

        Int2D location = wb.prey.getObjectLocation(this);

        if((nn.getH() > WallBuilding.HUNGER_DEATH) && WallBuilding.DO_HUNGER_DEATH){			
            wb.prey.remove(this);
            this.unSchedule();
        }else if(orientation == NORTH){
            nn.setCB(wb.preyOdor.get(location.x,location.y)-1);
            nn.setCL(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y-1))-.9);
            nn.setCR(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y-1))-.9);

            nn.setFB(wb.foodOdor.get(location.x,location.y));
            nn.setFL(wb.foodOdor.get(wb.foodOdor.stx(location.x-1),wb.foodOdor.stx(location.y-1)));
            nn.setFR(wb.foodOdor.get(wb.foodOdor.stx(location.x+1),wb.foodOdor.stx(location.y-1)));

            nn.setPB(wb.predatorOdor.get(location.x,location.y));
            nn.setPL(wb.predatorOdor.get(wb.predatorOdor.stx(location.x-1),wb.predatorOdor.stx(location.y-1)));
            nn.setPR(wb.predatorOdor.get(wb.predatorOdor.stx(location.x+1),wb.predatorOdor.stx(location.y-1)));

            nn.setTF(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y+1))));
            nn.setTB(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y-1))));
            nn.setTL(touchSensorModification(wb.block.get(wb.block.stx(location.x-1), location.y)));
            nn.setTR(touchSensorModification(wb.block.get(wb.block.stx(location.x+1), location.y)));

            nn.evaluate();

            double MF = nn.getMF();
            double MB = nn.getMB();
            double ML = nn.getML();
            double MR = nn.getMR();
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
                switch(wb.random.nextInt(4)){
                    case 0: {
                        this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                        addThisMove("1");
                        break;
                    }
                    case 1: {
                        this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                        this.orientation = Prey.SOUTH;
                        addThisMove("2");
                        break;
                    }
                    case 2: {
                        this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                        this.orientation = Prey.WEST;
                        addThisMove("3");
                        break;
                    }
                    case 3: {
                        this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                        this.orientation = Prey.EAST;
                        addThisMove("4");
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                addThisMove("1");
            }else if(MB > ML && MB > MR){//move back
                this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                this.orientation = Prey.SOUTH;
                addThisMove("2");
            }else if(ML > MR){//move left
                this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                this.orientation = Prey.WEST;
                addThisMove("3");
            }else{//move right
                this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                this.orientation = Prey.EAST;
                addThisMove("4");
            }
        }else if(orientation == EAST) {
            nn.setCB(wb.preyOdor.get(location.x,location.y)-1);
            nn.setCL(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y-1))-.9);
            nn.setCR(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y+1))-.9);

            nn.setFB(wb.foodOdor.get(location.x,location.y));
            nn.setFL(wb.foodOdor.get(wb.foodOdor.stx(location.x+1),wb.foodOdor.stx(location.y-1)));
            nn.setFR(wb.foodOdor.get(wb.foodOdor.stx(location.x+1),wb.foodOdor.stx(location.y+1)));

            nn.setPB(wb.predatorOdor.get(location.x,location.y));
            nn.setPL(wb.predatorOdor.get(wb.predatorOdor.stx(location.x+1),wb.predatorOdor.stx(location.y-1)));
            nn.setPR(wb.predatorOdor.get(wb.predatorOdor.stx(location.x+1),wb.predatorOdor.stx(location.y+1)));

            nn.setTF(touchSensorModification(wb.block.get(wb.block.stx(location.x+1), location.y)));
            nn.setTB(touchSensorModification(wb.block.get(wb.block.stx(location.x-1), location.y)));
            nn.setTL(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y+1))));
            nn.setTR(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y-1))));

            nn.evaluate();

            double MF = nn.getMF();
            double MB = nn.getMB();
            double ML = nn.getML();
            double MR = nn.getMR();
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
                switch(wb.random.nextInt(4)) {
                    case 0: {
                        this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                        addThisMove("1");
                        break;
                    }
                    case 1: {
                        this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                        this.orientation = Prey.WEST;
                        addThisMove("2");
                        break;
                    }
                    case 2: {
                        this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                        this.orientation = Prey.NORTH;
                        addThisMove("3");
                        break;
                    }
                    case 3: {
                        this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                        this.orientation = Prey.SOUTH;
                        addThisMove("4");
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                addThisMove("1");
            }else if(MB > ML && MB > MR){//move back
                this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                this.orientation = Prey.WEST;
                addThisMove("2");
            }else if(ML > MR){//move left
                this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                this.orientation = Prey.NORTH;
                addThisMove("3");
            }else{//move right
                this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                this.orientation = Prey.SOUTH;
                addThisMove("4");
            }
        }else if(orientation == SOUTH){
            nn.setCB(wb.preyOdor.get(location.x,location.y)-1);
            nn.setCL(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y+1))-.9);
            nn.setCR(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y+1))-.9);

            nn.setFB(wb.foodOdor.get(location.x,location.y));
            nn.setFL(wb.foodOdor.get(wb.foodOdor.stx(location.x-1),wb.foodOdor.stx(location.y+1)));
            nn.setFR(wb.foodOdor.get(wb.foodOdor.stx(location.x+1),wb.foodOdor.stx(location.y+1)));

            nn.setPB(wb.predatorOdor.get(location.x,location.y));
            nn.setPL(wb.predatorOdor.get(wb.predatorOdor.stx(location.x-1),wb.predatorOdor.stx(location.y+1)));
            nn.setPR(wb.predatorOdor.get(wb.predatorOdor.stx(location.x+1),wb.predatorOdor.stx(location.y+1)));

            nn.setTF(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y-1))));
            nn.setTB(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y+1))));
            nn.setTL(touchSensorModification(wb.block.get(wb.block.stx(location.x+1), location.y)));
            nn.setTR(touchSensorModification(wb.block.get(wb.block.stx(location.x-1), location.y)));

            nn.evaluate();
            double MF = nn.getMF();
            double MB = nn.getMB();
            double ML = nn.getML();
            double MR = nn.getMR();
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
                switch(wb.random.nextInt(4)){
                    case 0: {
                        this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                        addThisMove("1");
                        break;
                    }
                    case 1: {
                        this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                        this.orientation = Prey.NORTH;
                        addThisMove("2");
                        break;
                    }
                    case 2: {
                        this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                        this.orientation = Prey.EAST;
                        addThisMove("3");
                        break;
                    }
                    case 3: {
                        this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                        this.orientation = Prey.WEST;
                        addThisMove("4");
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                addThisMove("1");
            }else if(MB > ML && MB > MR){//move back
                this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                this.orientation = Prey.NORTH;
                addThisMove("2");
            }else if(ML > MR){//move left
                this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                this.orientation = Prey.EAST;
                addThisMove("3");
            }else{//move right
                this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                this.orientation = Prey.WEST;
                addThisMove("4");
            }
        }else if(orientation == WEST) {
            nn.setCB(wb.preyOdor.get(location.x,location.y)-1);
            nn.setCL(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y-1))-.9);
            nn.setCR(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y+1))-.9);

            nn.setFB(wb.foodOdor.get(location.x,location.y));
            nn.setFL(wb.foodOdor.get(wb.foodOdor.stx(location.x-1),wb.foodOdor.stx(location.y-1)));
            nn.setFR(wb.foodOdor.get(wb.foodOdor.stx(location.x-1),wb.foodOdor.stx(location.y+1)));

            nn.setPB(wb.predatorOdor.get(location.x,location.y));
            nn.setPL(wb.predatorOdor.get(wb.predatorOdor.stx(location.x-1),wb.predatorOdor.stx(location.y-1)));
            nn.setPR(wb.predatorOdor.get(wb.predatorOdor.stx(location.x-1),wb.predatorOdor.stx(location.y+1)));

            nn.setTF(touchSensorModification(wb.block.get(wb.block.stx(location.x-1), location.y)));
            nn.setTB(touchSensorModification(wb.block.get(wb.block.stx(location.x+1), location.y)));
            nn.setTL(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y-1))));
            nn.setTR(touchSensorModification(wb.block.get(location.x,wb.block.sty(location.y+1))));

            nn.evaluate();
            double MF = nn.getMF();
            double MB = nn.getMB();
            double ML = nn.getML();
            double MR = nn.getMR();//west
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()) {//only need this once I think?
                switch(wb.random.nextInt(4)){
                    case 0: {
                        this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
                        addThisMove("1");
                        break;
                    }
                    case 1: {
                        this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                        this.orientation = Prey.EAST;
                        addThisMove("2");
                        break;
                    }
                    case 2: {
                        this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                        this.orientation = Prey.SOUTH;
                        addThisMove("3");
                        break;
                    }
                    case 3: {
                        this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                        this.orientation = Prey.NORTH;
                        addThisMove("4");
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                this.preyMovement(wb,location.x-1,location.x-2, location.y, location.y);
            }else if(MB > ML && MB > MR){//move back
                this.preyMovement(wb,location.x+1,location.x+2, location.y, location.y);
                this.orientation = Prey.EAST;
            }else if(ML > MR){//move left
                this.preyMovement(wb,location.x,location.x, location.y+1, location.y+2);
                this.orientation = Prey.SOUTH;
            }else{//move right
                this.preyMovement(wb,location.x,location.x, location.y-1, location.y-2);
                this.orientation = Prey.NORTH;
            }
        }
    }
	
    //x1 is one ahead, x2 is two ahead
    //y1 is one ahead, y2 is two ahead
    //for most cases, either x1=x2 or y1=y2!!!
    public void preyMovement(WallBuilding wb, int x1, int x2, int y1, int y2){
        int b1 = wb.block.get(wb.block.stx(x1), wb.block.sty(y1));//one away
        int b2 = wb.block.get(wb.block.stx(x2), wb.block.sty(y2));//two away
        if(b1 == 0){//if there are no blocks, we can move normally
            if(wb.food.getObjectsAtLocation(x1, y1) == null){//if there is no food in that space
                wb.prey.setObjectLocation(this, wb.prey.stx(x1),wb.prey.sty(y1));
            }
        }else if(b1 == 1){//if there is one block
            if(b2 == 0){//if the space after is empty, we can move too!
                if(wb.food.getObjectsAtLocation(x2, y2) == null){//if there is no food in that space
                    wb.block.set(wb.block.stx(x2), wb.block.sty(y2), b2+1);
                    wb.block.set(wb.block.stx(x1), wb.block.sty(y1), b1-1);
                    wb.prey.setObjectLocation(this, wb.prey.stx(x1),wb.prey.sty(y1));
                }
            }else if(b2 < 3){//there is space to move the block
                wb.block.set(wb.block.stx(x2), wb.block.sty(y2), b2+1);
                wb.block.set(wb.block.stx(x1), wb.block.sty(y1), b1-1);
            }
        }else{//if there are 2 or 3 blocks
            if(b2 < 3){//there is space to move the block
                if(wb.food.getObjectsAtLocation(x2, y2) == null){//if there is no food in that space
                    wb.block.set(wb.block.stx(x2), wb.block.sty(y2), b2+1);
                    wb.block.set(wb.block.stx(x1), wb.block.sty(y1), b1-1);
                }
            }
        }
    }
	
    private void addThisMove(String move){
        lastFourMoves.addFirst(move);
        if(lastFourMoves.size() == 5){
            lastFourMoves.removeLast();
        }
    }
	
    private boolean isFrustrated(){
        if(lastFourMoves.size() == 4){
            String move1 = lastFourMoves.get(3);
            String move2 = lastFourMoves.get(2);
            String move3 = lastFourMoves.get(1);
            String move4 = lastFourMoves.get(0);
            
            if(move1 == null || move2 == null || move3 == null || move4 == null) {
                return false;
            }
            
            if(move1.equals(move2) && move2.equals(move3) && move3.equals(move4)){
                return true;
            }
        }
        return false;
    }
	
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
        graphics.setColor(Color.blue);

        int x = (int)(info.draw.x - info.draw.width / 2.0);
        int y = (int)(info.draw.y - info.draw.height / 2.0);
        int width = (int)(info.draw.width);
        int height = (int)(info.draw.height);
        graphics.fillOval(x,y,width, height);
    }
	
    public boolean canMate(){
        if(nn.getMate() == 1){
            return true;
        }else{
            return false;
        }
    }

    private int touchSensorModification(int blocks){
        if(blocks == 0){
            return 0;
        }else{
            return 4-blocks;
        }
    }
	
    public double[] getNNDirectEncoding(){
        return nn.getNNDirectEncoding();
    }

    public void resetHunger(){
        nn.resetH();
    }
    public void resetMating(){
        nn.resetM();
    }

    public void setStoppable(Stoppable stop){
        this.stop = stop;
    }
    public void unSchedule(){
        stop.stop();
    }

    //inspector stuff
    public int getOrientation(){
        return orientation;
    }
    public double getMF(){
        return nn.getMF();
    }
    public double getML(){
        return nn.getML();
    }
    public double getMR(){
        return nn.getMR();
    }
    public double getMB(){
        return nn.getMB();
    }
    public double getHunger(){
        return nn.getH();
    }
}
