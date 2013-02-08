package com.pancost.wallbuildingsimulation.predator;
//Control class for the predator neural network
public class PredatorNeuralNetwork {
	
	PredatorPB PB;
	PredatorPL PL;
	PredatorPR PR;
	PredatorNeuralNetworkMotorNeuron MF, MB, ML, MR;
	
	//get weights in the following order PB(MF,MB,ML,MR),PL(MF,MB,ML,MR),PR(MF,MB,ML,MR)
	//this is hard-coded because this is the predator neural network.
	public PredatorNeuralNetwork(){
		PB = new PredatorPB(-.45,1.5,0,0);//-.4 for the first
		PL = new PredatorPL(.75,-.25,1,0);
		PR = new PredatorPR(.75,-.25,0,1);
		MF = new PredatorNeuralNetworkMotorNeuron();
		MB = new PredatorNeuralNetworkMotorNeuron();
		ML = new PredatorNeuralNetworkMotorNeuron();
		MR = new PredatorNeuralNetworkMotorNeuron();
	}
	
	public void evaluate(){
		PB.evaluate(MF, MB, ML, MR);
		PL.evaluate(MF, MB, ML, MR);
		PR.evaluate(MF, MB, ML, MR);
		MF.evaluate();
		MB.evaluate();
		ML.evaluate();
		MR.evaluate();
	}
	
	public double getMFValue(){
		return MF.getNodeValue();
	}
	public double getMBValue(){
		return MB.getNodeValue();
	}
	public double getMLValue(){
		return ML.getNodeValue();
	}
	public double getMRValue(){
		return MR.getNodeValue();
	}
	
	public void setPBValue(double valuePB){
		PB.setNodeValue(valuePB);
	}
	public void setPLValue(double valuePL){
		PL.setNodeValue(valuePL);
	}
	public void setPRValue(double valuePR){
		PR.setNodeValue(valuePR);
	}
}
