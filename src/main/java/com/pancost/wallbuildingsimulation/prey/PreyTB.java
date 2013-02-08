package com.pancost.wallbuildingsimulation.prey;

class PreyTB extends PreyDirectSensorNeuron {
    PreyTB(double weightMF,
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
        MF.getTB(this.getNodeValue()*this.getWeightMF());
        MB.getTB(this.getNodeValue()*this.getWeightMB());
        ML.getTB(this.getNodeValue()*this.getWeightML());
        MR.getTB(this.getNodeValue()*this.getWeightMR());
    }
}
