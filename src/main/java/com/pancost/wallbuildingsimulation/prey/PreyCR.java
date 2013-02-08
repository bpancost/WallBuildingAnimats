package com.pancost.wallbuildingsimulation.prey;

class PreyCR extends PreyGatedSensorNeuron {
    PreyCR(double weightMF,
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
        MF.getCR(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
        MB.getCR(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
        ML.getCR(this.getNodeValue()*this.getWeightML()*this.getGateML());
        MR.getCR(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
    }
}
