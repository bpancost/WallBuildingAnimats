package com.pancost.wallbuildingsimulation.prey;

final class PreyHungerControlNeuron{
    double nodeValue, gateMF, gateMB, gateML, gateMR;

    PreyHungerControlNeuron(double gateMF,
                            double gateMB,
                            double gateML,
                            double gateMR){	
        this.setGateMF(gateMF);
        this.setGateMB(gateMB);
        this.setGateML(gateML);
        this.setGateMR(gateMR);
    }

    public void evaluate(PreyGatedSensorNeuron FB,
                         PreyGatedSensorNeuron FL,
                         PreyGatedSensorNeuron FR){
        FB.setGateMF(this.gateMF*getNodeValue());
        FB.setGateMB(this.gateMB*getNodeValue());
        FB.setGateML(this.gateML*getNodeValue());
        FB.setGateMR(this.gateMR*getNodeValue());

        FL.setGateMF(this.gateMF*getNodeValue());
        FL.setGateMB(this.gateMB*getNodeValue());
        FL.setGateML(this.gateML*getNodeValue());
        FL.setGateMR(this.gateMR*getNodeValue());

        FR.setGateMF(this.gateMF*getNodeValue());
        FR.setGateMB(this.gateMB*getNodeValue());
        FR.setGateML(this.gateML*getNodeValue());
        FR.setGateMR(this.gateMR*getNodeValue());
        this.nodeValue += .04;
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
        this.nodeValue = 0;
    }
    public double getNodeValue(){
        return nodeValue;
    }
}
