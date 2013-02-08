package com.pancost.wallbuildingsimulation.prey;

//M is mating neuron
//H is hunger neuron
//F* are food sensor neurons
//C* are prey creature sensor neurons
//P* are predator sensor neurons
//T* are block touch sensor neurons
//M* are movement motor neurons
//Mate is the mating motor neuron

//F gates F* neurons, M gates C* neurons and directly affects Mate

//F*, C* can be grouped as one class (both have gated connections) but must be abstract *done?*
//P*, T* can be grouped as one class (both have direct connections) but must be abstract *done?*
//M* can be grouped and as one class and used directly for all M* *done?*
//M, H, Mate must be implemented separately

//Order of evaluation (and why not order entered too?)
//M, H, FL, FR, FB, CL, CR, CB, PL, PR, PB, TL, TR, TF, TB, ML, MR, MF, MB, Mate
public class PreyNeuralNetwork {
	
	private double[] nnDirectEncoding = {1,1,1,1, 1,1,1,1,
										-0.5,1.5,0,0, 0.75,-0.25,1,0, 0.75,-0.25,0,1,
										-0.1,0.3,0,0, 0.15,-.05,0.2,0, 0.15,-.05,0,0.2,
										1.5,-0.5,0,0, -0.25,0.75,0,1, -0.25,0.75,1,0,
										0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
										//size 60, weights given below
	
	private PreyMatingControlNeuron M;
	private PreyHungerControlNeuron H;
	
	private PreyFL FL;
	private PreyFR FR;
	private PreyFB FB;
	private PreyCL CL;
	private PreyCR CR;
	private PreyCB CB;
	private PreyPL PL;
	private PreyPR PR;
	private PreyPB PB;
	private PreyTL TL;
	private PreyTR TR;
	private PreyTF TF;
	private PreyTB TB;
	
	private PreyMotorNeuron ML, MR, MF, MB;
	private PreyMatingMotorNeuron mate;
	
	//default implementation of this neural network
	public PreyNeuralNetwork(){
		//By default, the control neurons gate with a weight of 1.
		M = new PreyMatingControlNeuron(1,1,1,1);//MF, MB, ML, MR
		H = new PreyHungerControlNeuron(1,1,1,1);
		
		//By default, the prey go towards food mostly line of sight (no tricks here).
		FB = new PreyFB(-0.4,1.5,0,0);//MF, MB, ML, MR   -.5 for first
		FL = new PreyFL(0.75,-0.25,1.1,0);
		FR = new PreyFR(0.75,-0.25,0,1.1);
		
		//By default, we have the prey go towards each other at a lesser rate than food,
		//but in a similar fashion.
		CB = new PreyCB(-0.1,0.3,0,0);//MF, MB, ML, MR
		CL = new PreyCL(0.15,-.05,0.2,0);
		CR = new PreyCR(0.15,-.05,0,0.2);
		
		//By default, we have the prey run away from predators as though it were the opposite
		//of a food source.
		PB = new PreyPB(1.5,-0.4,0,0);//MF, MB, ML, MR  -.5 for second
		PL = new PreyPL(-0.25,0.75,0,1);
		PR = new PreyPR(-0.25,0.75,1,0);
		
		//By default the block touch sensors are useless (these weights MUST be evolved).
		TF = new PreyTF(0,0,0,0);//MF, MB, ML, MR
		TB = new PreyTB(0,0,0,0);
		TL = new PreyTL(0,0,0,0);
		TR = new PreyTR(0,0,0,0);
		
		//Motor neurons are easy to set up :]
		MF = new PreyMotorNeuron();
		MB = new PreyMotorNeuron();
		ML = new PreyMotorNeuron();
		MR = new PreyMotorNeuron();
		mate = new PreyMatingMotorNeuron();
	}
	
	//assign weights via a byte array
	public PreyNeuralNetwork(double[] nnDirectEncoding){//continue to set this up
		this.nnDirectEncoding = nnDirectEncoding;
		
		M = new PreyMatingControlNeuron(nnDirectEncoding[0],nnDirectEncoding[1],nnDirectEncoding[2],nnDirectEncoding[3]);//MF, MB, ML, MR
		H = new PreyHungerControlNeuron(nnDirectEncoding[4],nnDirectEncoding[5],nnDirectEncoding[6],nnDirectEncoding[7]);
		
		//By default, the prey go towards food mostly line of sight (no tricks here).
		FB = new PreyFB(nnDirectEncoding[8],nnDirectEncoding[9],nnDirectEncoding[10],nnDirectEncoding[11]);//MF, MB, ML, MR
		FL = new PreyFL(nnDirectEncoding[12],nnDirectEncoding[13],nnDirectEncoding[14],nnDirectEncoding[15]);
		FR = new PreyFR(nnDirectEncoding[16],nnDirectEncoding[17],nnDirectEncoding[18],nnDirectEncoding[19]);
		
		//By default, we have the prey go towards each other at a lesser rate than food,
		//but in a similar fashion.
		CB = new PreyCB(nnDirectEncoding[20],nnDirectEncoding[21],nnDirectEncoding[22],nnDirectEncoding[23]);//MF, MB, ML, MR
		CL = new PreyCL(nnDirectEncoding[24],nnDirectEncoding[25],nnDirectEncoding[26],nnDirectEncoding[27]);
		CR = new PreyCR(nnDirectEncoding[28],nnDirectEncoding[29],nnDirectEncoding[30],nnDirectEncoding[31]);
		
		//By default, we have the prey run away from predators as though it were the opposite
		//of a food source.
		PB = new PreyPB(nnDirectEncoding[32],nnDirectEncoding[33],nnDirectEncoding[34],nnDirectEncoding[35]);//MF, MB, ML, MR
		PL = new PreyPL(nnDirectEncoding[36],nnDirectEncoding[37],nnDirectEncoding[38],nnDirectEncoding[39]);
		PR = new PreyPR(nnDirectEncoding[40],nnDirectEncoding[41],nnDirectEncoding[42],nnDirectEncoding[43]);
		
		//By default the block touch sensors are useless (these weights MUST be evolved).
		TF = new PreyTF(nnDirectEncoding[44],nnDirectEncoding[45],nnDirectEncoding[46],nnDirectEncoding[47]);//MF, MB, ML, MR
		TB = new PreyTB(nnDirectEncoding[48],nnDirectEncoding[49],nnDirectEncoding[50],nnDirectEncoding[51]);
		TL = new PreyTL(nnDirectEncoding[52],nnDirectEncoding[53],nnDirectEncoding[54],nnDirectEncoding[55]);
		TR = new PreyTR(nnDirectEncoding[56],nnDirectEncoding[57],nnDirectEncoding[58],nnDirectEncoding[59]);
		
		//Motor neurons are easy to set up :]
		MF = new PreyMotorNeuron();
		MB = new PreyMotorNeuron();
		ML = new PreyMotorNeuron();
		MR = new PreyMotorNeuron();
		mate = new PreyMatingMotorNeuron();
	}
	
	//M, H, FL, FR, FB, CL, CR, CB, PL, PR, PB, TL, TR, TF, TB
	//evaluate the prey neural network
	public void evaluate(){
		M.evaluate(CB, CL, CR, mate);
		H.evaluate(FB, FL, FR);
		FL.evaluate(MF, MB, ML, MR);
		FR.evaluate(MF, MB, ML, MR);
		FB.evaluate(MF, MB, ML, MR);
		CL.evaluate(MF, MB, ML, MR);
		CR.evaluate(MF, MB, ML, MR);
		CB.evaluate(MF, MB, ML, MR);
		PL.evaluate(MF, MB, ML, MR);
		PR.evaluate(MF, MB, ML, MR);
		PB.evaluate(MF, MB, ML, MR);
		TL.evaluate(MF, MB, ML, MR);
		TR.evaluate(MF, MB, ML, MR);
		TF.evaluate(MF, MB, ML, MR);
		TB.evaluate(MF, MB, ML, MR);
		ML.evaluate();
		MR.evaluate();
		MF.evaluate();
		MB.evaluate();
	}
	
	public double getMF(){
		return MF.getNodeValue();
	}
	public double getMB(){
		return MB.getNodeValue();
	}
	public double getML(){
		return ML.getNodeValue();
	}
	public double getMR(){
		return MR.getNodeValue();
	}
	
	public double getMate(){
		return mate.getNodeValue();
	}
	
	public void setFL(double valueFL){
		FL.setNodeValue(valueFL);
	}
	public void setFR(double valueFR){
		FR.setNodeValue(valueFR);
	}
	public void setFB(double valueFB){
		FB.setNodeValue(valueFB);
	}
	
	public void setCL(double valueCL){
		CL.setNodeValue(valueCL);
	}
	public void setCR(double valueCR){
		CR.setNodeValue(valueCR);
	}
	public void setCB(double valueCB){
		CB.setNodeValue(valueCB);
	}
	
	public void setPL(double valuePL){
		PL.setNodeValue(valuePL);
	}
	public void setPR(double valuePR){
		PR.setNodeValue(valuePR);
	}
	public void setPB(double valuePB){
		PB.setNodeValue(valuePB);
	}
	
	public void setTL(double valueTL){
		TL.setNodeValue(valueTL);
	}
	public void setTR(double valueTR){
		TR.setNodeValue(valueTR);
	}
	public void setTF(double valueTF){
		TF.setNodeValue(valueTF);
	}
	public void setTB(double valueTB){
		TB.setNodeValue(valueTB);
	}
	
	public void resetM(){
		M.resetValue();
	}
	public void resetH(){
		H.resetValue();
	}
	public double getH(){
		return H.getNodeValue();
	}
	
	public double[] getNNDirectEncoding(){
		return nnDirectEncoding;
	}
}
