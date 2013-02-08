package com.pancost.wallbuildingsimulation.predator;

class PredatorPB  extends PredatorNeuralNetworkSensorNeuron{
	
	public PredatorPB(double weightMF,
						double weightMB,
						double weightML,
						double weightMR){	
		this.setWeightMF(weightMF);
		this.setWeightMB(weightMB);
		this.setWeightML(weightML);
		this.setWeightMR(weightMR);
	}
	
	public void evaluate(PredatorNeuralNetworkMotorNeuron MF,
							PredatorNeuralNetworkMotorNeuron MB,
							PredatorNeuralNetworkMotorNeuron ML,
							PredatorNeuralNetworkMotorNeuron MR){
		MF.getPB(this.getNodeValue()*this.getWeightMF());
		MB.getPB(this.getNodeValue()*this.getWeightMB());
		ML.getPB(this.getNodeValue()*this.getWeightML());
		MR.getPB(this.getNodeValue()*this.getWeightMR());
	}
}
