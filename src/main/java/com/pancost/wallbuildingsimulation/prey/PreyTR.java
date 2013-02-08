package com.pancost.wallbuildingsimulation.prey;

class PreyTR extends PreyDirectSensorNeuron {
	public PreyTR(double weightMF,
					double weightMB,
					double weightML,
					double weightMR){	
		this.setWeightMF(weightMF);
		this.setWeightMB(weightMB);
		this.setWeightML(weightML);
		this.setWeightMR(weightMR);
	}

	public void evaluate(PreyMotorNeuron MF,
							PreyMotorNeuron MB,
							PreyMotorNeuron ML,
							PreyMotorNeuron MR){
		MF.getTR(this.getNodeValue()*this.getWeightMF());
		MB.getTR(this.getNodeValue()*this.getWeightMB());
		ML.getTR(this.getNodeValue()*this.getWeightML());
		MR.getTR(this.getNodeValue()*this.getWeightMR());
	}
}
