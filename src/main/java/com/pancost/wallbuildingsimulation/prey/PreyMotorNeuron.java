package com.pancost.wallbuildingsimulation.prey;

class PreyMotorNeuron {
    private double nodeValue, valueFL, valueFR, valueFB, valueCL, valueCR, valueCB,
                   valuePB, valuePL, valuePR, valueTL, valueTR, valueTF, valueTB;
	
    PreyMotorNeuron(){
        nodeValue = 0;
    }

    public void evaluate(){
        nodeValue = valueFB + valueFL + valueFR +
                    valueCL + valueCR + valueCB +
                    valuePB + valuePL + valuePR +
                    valueTF + valueTB + valueTL + valueTR;
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

    public void getFB(double valueFB){
        this.valueFB = valueFB;
    }
    public void getFL(double valueFL){
        this.valueFL = valueFL;
    }
    public void getFR(double valueFR){
        this.valueFR = valueFR;
    }

    public void getCB(double valueCB){
        this.valueCB = valueCB;
    }
    public void getCL(double valueCL){
        this.valueCL = valueCL;
    }
    public void getCR(double valueCR){
        this.valueCR = valueCR;
    }

    public void getTF(double valueTF){
        this.valueTF = valueTF;
    }
    public void getTB(double valueTB){
        this.valueTB = valueTB;
    }
    public void getTL(double valueTL){
        this.valueTL = valueTL;
    }
    public void getTR(double valueTR){
        this.valueTR = valueTR;
    }

    public double getNodeValue(){
        return nodeValue;
    }
    public void setNodeValue(double newNodeValue){
        this.nodeValue = newNodeValue;
    }
}
