package com.pancost.wallBuildingSimulation;

import com.pancost.wallbuildingsimulation.predator.PredatorNeuralNetwork;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import sim.util.Bag;
import sim.util.Int2D;

public class Predator extends SimplePortrayal2D implements Steppable{

    private static final long serialVersionUID = 1L;
    private static int NORTH = 0;
    private static int EAST = 1;
    private static int SOUTH = 2;
    private static int WEST = 3;
    private int orientation = 0;//This seems a bit like a hack (random would be better)
    private PredatorNeuralNetwork nn;
    private LinkedList<String> lastFourMoves = new LinkedList<>();

    public Predator() {
        nn = new PredatorNeuralNetwork();
    }

    @Override
    public void step(SimState state) {
        final WallBuilding wb = (WallBuilding)state;

        Int2D location = wb.predator.getObjectLocation(this);

        if(orientation == NORTH){
            nn.setPBValue(wb.preyOdor.get(location.x,location.y));
            nn.setPLValue(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y-1)));
            nn.setPRValue(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y-1)));

            nn.evaluate();

            double MF = nn.getMFValue();
            double MB = nn.getMBValue();
            double ML = nn.getMLValue();
            double MR = nn.getMRValue();

            if((MF == 0.0 && MB == 0.0 && ML == 0.0 && MR == 0.0)||isFrustrated()) {//only need this once I think?
                switch(wb.random.nextInt(4)) {
                    case 0: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2) {
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                            addThisMove("1");
                        }
                        break;
                    }
                    case 1: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                            this.orientation = Predator.SOUTH;
                            addThisMove("2");
                        }
                        break;
                    }
                    case 2: {
                        if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                            this.orientation = Predator.WEST;
                            addThisMove("3");
                        }
                        break;
                    }
                    case 3: {
                        if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                            this.orientation = Predator.EAST;
                            addThisMove("4");
                        }
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                    addThisMove("1");
                }
            }else if(MB > ML && MB > MR){//move back
                if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                    this.orientation = Predator.SOUTH;
                    addThisMove("2");
                }
            }else if(ML > MR){//move left
                if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                    this.orientation = Predator.WEST;
                    addThisMove("3");
                }
            }else{//move right
                if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                    this.orientation = Predator.EAST;
                    addThisMove("4");
                }
            }
        }else if(orientation == EAST){
            nn.setPBValue(wb.preyOdor.get(location.x,location.y));
            nn.setPLValue(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y-1)));
            nn.setPRValue(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y+1)));

            nn.evaluate();

            double MF = nn.getMFValue();
            double MB = nn.getMBValue();
            double ML = nn.getMLValue();
            double MR = nn.getMRValue();
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
                switch(wb.random.nextInt(4)){
                    case 0: {
                        if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                            addThisMove("1");
                        }
                        break;
                    }
                    case 1: {
                        if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                            this.orientation = Predator.WEST;
                            addThisMove("2");
                        }
                        break;
                    }
                    case 2: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                            this.orientation = Predator.NORTH;
                            addThisMove("3");
                            }
                        break;
                    }
                    case 3: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                            this.orientation = Predator.SOUTH;
                            addThisMove("4");
                        }
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                    addThisMove("1");
                }
            }else if(MB > ML && MB > MR){//move back
                if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                    this.orientation = Predator.WEST;
                    addThisMove("2");
                }
            }else if(ML > MR){//move left
                if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                    this.orientation = Predator.NORTH;
                    addThisMove("3");
                }
            }else{//move right
                if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                    this.orientation = Predator.SOUTH;
                    addThisMove("4");
                }
            }
        }else if(orientation == SOUTH){
            nn.setPBValue(wb.preyOdor.get(location.x,location.y));
            nn.setPLValue(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y+1)));
            nn.setPRValue(wb.preyOdor.get(wb.preyOdor.stx(location.x+1),wb.preyOdor.sty(location.y+1)));

            nn.evaluate();
            double MF = nn.getMFValue();
            double MB = nn.getMBValue();
            double ML = nn.getMLValue();
            double MR = nn.getMRValue();
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
            switch(wb.random.nextInt(4)){
                case 0: {
                    if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2) {
                        wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                        addThisMove("1");
                    }
                    break;
                }
                case 1: {
                    if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2) {
                        wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                        this.orientation = Predator.NORTH;
                        addThisMove("2");
                    }
                    break;
                }
                case 2: {
                    if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2) {
                        wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                        this.orientation = Predator.EAST;
                        addThisMove("3");
                    }
                    break;
                }
                case 3: {
                    if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2) {
                        wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                        this.orientation = Predator.WEST;
                        addThisMove("4");
                    }
                    break;
                }
            }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                    addThisMove("1");
                }
            }else if(MB > ML && MB > MR){//move back
                if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                    this.orientation = Predator.NORTH;
                    addThisMove("2");
                }
            }else if(ML > MR){//move left
                if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                    this.orientation = Predator.EAST;
                    addThisMove("3");
                }
            }else{//move right
                if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                    this.orientation = Predator.WEST;
                    addThisMove("4");
                }
            }
        }else if(orientation == WEST){
            nn.setPBValue(wb.preyOdor.get(location.x,location.y));
            nn.setPLValue(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y-1)));
            nn.setPRValue(wb.preyOdor.get(wb.preyOdor.stx(location.x-1),wb.preyOdor.sty(location.y+1)));

            nn.evaluate();
            double MF = nn.getMFValue();
            double MB = nn.getMBValue();
            double ML = nn.getMLValue();
            double MR = nn.getMRValue();//west
            if((MF == 0 && MB == 0 && ML == 0 && MR == 0)||isFrustrated()){//only need this once I think?
                switch(wb.random.nextInt(4)){
                    case 0: {
                        if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                            addThisMove("1");
                        }
                        break;
                    }
                    case 1: {
                        if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                            wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                            this.orientation = Predator.EAST;
                            addThisMove("2");
                        }
                        break;
                    }
                    case 2: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                            this.orientation = Predator.SOUTH;
                            addThisMove("3");
                        }
                        break;
                    }
                    case 3: {
                        if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                            wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                            this.orientation = Predator.NORTH;
                            addThisMove("4");
                        }
                        break;
                    }
                }
            }else if(MF > MB && MF > ML && MF > MR){//move forward
                if(wb.block.get(wb.predator.stx(location.x-1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x-1),location.y);
                    addThisMove("1");
                }
            }else if(MB > ML && MB > MR){//move back
                if(wb.block.get(wb.predator.stx(location.x+1), location.y) < 2){
                    wb.predator.setObjectLocation(this, wb.predator.stx(location.x+1),location.y);
                    this.orientation = Predator.EAST;
                    addThisMove("2");
                }
            }else if(ML > MR){//move left
                if(wb.block.get(location.x, wb.predator.sty(location.y+1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y+1));
                    this.orientation = Predator.SOUTH;
                    addThisMove("3");
                }
            }else{//move right
                if(wb.block.get(location.x, wb.predator.sty(location.y-1)) < 2){
                    wb.predator.setObjectLocation(this, location.x,wb.predator.sty(location.y-1));
                    this.orientation = Predator.NORTH;
                    addThisMove("4");
                }
            }
        }

        //now for prey eating if there is one!
        Bag preyHere = wb.prey.getObjectsAtLocation(location.x, location.y);
        if(preyHere != null){//if there are prey here
            while(preyHere.size() > 0){//this is a gross hack!!!!!!!!!!!!!!!!!!!!
                int randomPrey = wb.random.nextInt(preyHere.size());
                ((Prey)preyHere.get(randomPrey)).unSchedule();
                wb.prey.remove(preyHere.get(randomPrey));//randomly "eat" one
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
            if(move1 == null || move2 == null || move3 == null || move4 == null) {//not enough information
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
        graphics.setColor(Color.red);

        int x = (int)(info.draw.x - info.draw.width / 2.0);
        int y = (int)(info.draw.y - info.draw.height / 2.0);
        int width = (int)(info.draw.width);
        int height = (int)(info.draw.height);
        graphics.fillOval(x,y,width, height);
    }
	
//	inspector stuff
    public int getOrientation(){
        return orientation;
    }
    public double getMF(){
        return nn.getMFValue();
    }
    public double getML(){
        return nn.getMLValue();
    }
    public double getMR(){
        return nn.getMRValue();
    }
    public double getMB(){
        return nn.getMBValue();
    }
}
