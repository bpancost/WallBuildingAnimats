package com.pancost.wallbuildingsimulation.prey;

final class PreyMatingControlNeuron {
    double nodeValue, gateMF, gateMB, gateML, gateMR;
    int downTime = 36;//50 is death
	
    PreyMatingControlNeuron(double gateMF,
                            double gateMB,
                            double gateML,
                            double gateMR){	
        this.setGateMF(gateMF);
        this.setGateMB(gateMB);
        this.setGateML(gateML);
        this.setGateMR(gateMR);
    }
	
    public void evaluate(PreyGatedSensorNeuron CB,
                         PreyGatedSensorNeuron CL,
                         PreyGatedSensorNeuron CR,
                         PreyMatingMotorNeuron mate){
        CB.setGateMF(this.gateMF*getNodeValue());
        CB.setGateMB(this.gateMB*getNodeValue());
        CB.setGateML(this.gateML*getNodeValue());
        CB.setGateMR(this.gateMR*getNodeValue());

        CL.setGateMF(this.gateMF*getNodeValue());
        CL.setGateMB(this.gateMB*getNodeValue());
        CL.setGateML(this.gateML*getNodeValue());
        CL.setGateMR(this.gateMR*getNodeValue());

        CR.setGateMF(this.gateMF*getNodeValue());
        CR.setGateMB(this.gateMB*getNodeValue());
        CR.setGateML(this.gateML*getNodeValue());
        CR.setGateMR(this.gateMR*getNodeValue());

        if(canMate()){
            mate.setValue(1);
        }else{
            mate.setValue(0);
        }
        if(downTime > 36){//50 is death
            this.nodeValue += .04;
        }else{
            downTime++;
        }
    }
	
    public void setGateMF(double gateMF){
        this.gateMF = gateMF;
    }
    public double getGateMF(){
        return gateMF;
    }

    public void setGateMB(double gateMB){
        this.gateMB = gateMB;
    }
    public double getGateMB(){
        return gateMB;
    }

    public void setGateML(double gateML){
        this.gateML = gateML;
    }
    public double getGateML(){
        return gateML;
    }

    public void setGateMR(double gateMR){
        this.gateMR = gateMR;
    }
    public double getGateMR(){
        return gateMR;
    }

    public void resetValue(){
        nodeValue = 0;
        downTime = 0;
    }
    public double getNodeValue(){
        return nodeValue;
    }
    public boolean canMate(){
        if(this.getNodeValue() > .75){//.75 originally
            return true;
        }
        return false;
    }
}
