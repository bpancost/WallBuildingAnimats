package com.pancost.wallbuildingsimulation.predator;

class PredatorNeuralNetworkMotorNeuron {
    private double nodeValue, valuePB, valuePL, valuePR;

    PredatorNeuralNetworkMotorNeuron(){
        nodeValue = 0;
    }

    public void evaluate(){
        nodeValue = valuePB + valuePL + valuePR;
    }

    public void getPB(double valuePB){
        this.valuePB = valuePB;
    }
    public void getPL(double valuePL){
        this.valuePL = valuePL;
    }
    public void getPR(double valuePR){
        this.valuePR = valuePR;
    }

    public double getNodeValue(){
        return nodeValue;
    }
    public void setNodeValue(double newNodeValue){
        this.nodeValue = newNodeValue;
    }
}
