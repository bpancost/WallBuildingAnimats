package com.pancost.wallbuildingsimulation.prey;

class PreyPB extends PreyDirectSensorNeuron {
    PreyPB(double weightMF,
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
        MF.getPB(this.getNodeValue()*this.getWeightMF());
        MB.getPB(this.getNodeValue()*this.getWeightMB());
        ML.getPB(this.getNodeValue()*this.getWeightML());
        MR.getPB(this.getNodeValue()*this.getWeightMR());
    }
}
