package com.pancost.wallbuildingsimulation.prey;

class PreyCB extends PreyGatedSensorNeuron {
    PreyCB(double weightMF,
           double weightMB,
           double weightML,
           double weightMR){	
        this.setWeightMF(weightMF);
        this.setWeightMB(weightMB);
        this.setWeightML(weightML);
        this.setWeightMR(weightMR);
    }

    @Override
    public void evaluate(PreyMotorNeuron MF,
                         PreyMotorNeuron MB,
                         PreyMotorNeuron ML,
                         PreyMotorNeuron MR){
        MF.getCB(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
        MB.getCB(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
        ML.getCB(this.getNodeValue()*this.getWeightML()*this.getGateML());
        MR.getCB(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
    }
}
