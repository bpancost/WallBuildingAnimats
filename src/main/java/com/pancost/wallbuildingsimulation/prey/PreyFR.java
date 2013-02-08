package com.pancost.wallbuildingsimulation.prey;

class PreyFR extends PreyGatedSensorNeuron {
	public PreyFR(double weightMF,
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
		MF.getFR(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
		MB.getFR(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
		ML.getFR(this.getNodeValue()*this.getWeightML()*this.getGateML());
		MR.getFR(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
	}
}
