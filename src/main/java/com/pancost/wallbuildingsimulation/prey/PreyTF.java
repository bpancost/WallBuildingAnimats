package com.pancost.wallbuildingsimulation.prey;

class PreyTF extends PreyDirectSensorNeuron {
    PreyTF(double weightMF,
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
        MF.getTF(this.getNodeValue()*this.getWeightMF());
        MB.getTF(this.getNodeValue()*this.getWeightMB());
        ML.getTF(this.getNodeValue()*this.getWeightML());
        MR.getTF(this.getNodeValue()*this.getWeightMR());
    }
}
