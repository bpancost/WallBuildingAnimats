package com.pancost.wallbuildingsimulation.prey;

class PreyPL extends PreyDirectSensorNeuron {
    PreyPL(double weightMF,
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
        MF.getPL(this.getNodeValue()*this.getWeightMF());
        MB.getPL(this.getNodeValue()*this.getWeightMB());
        ML.getPL(this.getNodeValue()*this.getWeightML());
        MR.getPL(this.getNodeValue()*this.getWeightMR());
    }
}
