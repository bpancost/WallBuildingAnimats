package com.pancost.wallbuildingsimulation.prey;

class PreyFB extends PreyGatedSensorNeuron {
	public PreyFB(double weightMF,
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
		MF.getFB(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
		MB.getFB(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
		ML.getFB(this.getNodeValue()*this.getWeightML()*this.getGateML());
		MR.getFB(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
	}
}
