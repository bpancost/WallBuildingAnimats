package com.pancost.wallbuildingsimulation.prey;

class PreyCL extends PreyGatedSensorNeuron {
    PreyCL(double weightMF,
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
        MF.getCL(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
        MB.getCL(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
        ML.getCL(this.getNodeValue()*this.getWeightML()*this.getGateML());
        MR.getCL(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
    }
}
