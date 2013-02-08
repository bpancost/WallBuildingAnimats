package com.pancost.wallbuildingsimulation.prey;

abstract class PreyGatedSensorNeuron {
    private double weightMF, weightMB, weightML, weightMR,
                   gateMF, gateMB, gateML, gateMR, nodeValue;

    public abstract void evaluate(PreyMotorNeuron MF,
                                  PreyMotorNeuron MB,
                                  PreyMotorNeuron ML,
                                  PreyMotorNeuron MR);

    public double getNodeValue(){
        return nodeValue;
    }
    public void setNodeValue(double newNodeValue){
        this.nodeValue = newNodeValue;
    }

    public double getWeightMF(){
        return weightMF;
    }
    public void setWeightMF(double weightMF){
        this.weightMF = weightMF;
    }
    public double getGateMF(){
        return gateMF;
    }
    public void setGateMF(double gateMF){
        this.gateMF = gateMF;
    }

    public double getWeightMB(){
        return weightMB;
    }
    public void setWeightMB(double weightMB){
        this.weightMB = weightMB;
    }
    public double getGateMB(){
        return gateMB;
    }
    public void setGateMB(double gateMB){
        this.gateMB = gateMB;
    }

    public double getWeightML(){
        return weightML;
    }
    public void setWeightML(double weightML){
        this.weightML = weightML;
    }
    public double getGateML(){
        return gateML;
    }
    public void setGateML(double gateML){
        this.gateML = gateML;
    }

    public double getWeightMR(){
        return weightMR;
    }
    public void setWeightMR(double weightMR){
        this.weightMR = weightMR;
    }
    public double getGateMR(){
        return gateMR;
    }
    public void setGateMR(double gateMR){
        this.gateMR = gateMR;
    }
}
