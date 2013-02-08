package com.pancost.wallbuildingsimulation.prey;

class PreyTL extends PreyDirectSensorNeuron {
	public PreyTL(double weightMF,
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
		MF.getTL(this.getNodeValue()*this.getWeightMF());
		MB.getTL(this.getNodeValue()*this.getWeightMB());
		ML.getTL(this.getNodeValue()*this.getWeightML());
		MR.getTL(this.getNodeValue()*this.getWeightMR());
	}
}
