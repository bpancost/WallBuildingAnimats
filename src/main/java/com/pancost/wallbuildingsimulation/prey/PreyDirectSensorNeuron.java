package com.pancost.wallbuildingsimulation.prey;

abstract class PreyDirectSensorNeuron {
	private double weightMF, weightMB, weightML, weightMR, nodeValue;
	
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
	
	public double getWeightMB(){
		return weightMB;
	}
	public void setWeightMB(double weightMB){
		this.weightMB = weightMB;
	}
	
	public double getWeightML(){
		return weightML;
	}
	public void setWeightML(double weightML){
		this.weightML = weightML;
	}
	
	public double getWeightMR(){
		return weightMR;
	}
	public void setWeightMR(double weightMR){
		this.weightMR = weightMR;
	}
}
