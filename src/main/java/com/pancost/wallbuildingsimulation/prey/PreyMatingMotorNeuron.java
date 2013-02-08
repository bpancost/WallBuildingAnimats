package com.pancost.wallbuildingsimulation.prey;

class PreyMatingMotorNeuron {
	double nodeValue;
	
	public PreyMatingMotorNeuron(){
		nodeValue = 0;
	}
	
	public double getNodeValue(){
		return nodeValue;
	}
	public void resetNodeValue(){
		nodeValue = 0;
	}
	
	public void setValue(double newNodeValue){
		nodeValue = newNodeValue;
	}
}
