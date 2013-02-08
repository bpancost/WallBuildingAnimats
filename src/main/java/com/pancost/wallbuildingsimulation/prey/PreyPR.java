package com.pancost.wallbuildingsimulation.prey;

class PreyPR extends PreyDirectSensorNeuron {
    PreyPR(double weightMF,
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
        MF.getPR(this.getNodeValue()*this.getWeightMF());
        MB.getPR(this.getNodeValue()*this.getWeightMB());
        ML.getPR(this.getNodeValue()*this.getWeightML());
        MR.getPR(this.getNodeValue()*this.getWeightMR());
    }
}
