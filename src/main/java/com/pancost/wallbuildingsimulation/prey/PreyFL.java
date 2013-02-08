package com.pancost.wallbuildingsimulation.prey;

class PreyFL extends PreyGatedSensorNeuron {
    PreyFL(double weightMF,
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
        MF.getFL(this.getNodeValue()*this.getWeightMF()*this.getGateMF());
        MB.getFL(this.getNodeValue()*this.getWeightMB()*this.getGateMB());
        ML.getFL(this.getNodeValue()*this.getWeightML()*this.getGateML());
        MR.getFL(this.getNodeValue()*this.getWeightMR()*this.getGateMR());
    }
}
