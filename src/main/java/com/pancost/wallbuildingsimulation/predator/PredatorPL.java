package com.pancost.wallbuildingsimulation.predator;

class PredatorPL extends PredatorNeuralNetworkSensorNeuron{
	
    PredatorPL(double weightMF,
               double weightMB,
               double weightML,
               double weightMR){	
        this.setWeightMF(weightMF);
        this.setWeightMB(weightMB);
        this.setWeightML(weightML);
        this.setWeightMR(weightMR);
    }

    @Override
    public void evaluate(PredatorNeuralNetworkMotorNeuron MF,
                         PredatorNeuralNetworkMotorNeuron MB,
                         PredatorNeuralNetworkMotorNeuron ML,
                         PredatorNeuralNetworkMotorNeuron MR){
        MF.getPL(this.getNodeValue()*this.getWeightMF());
        MB.getPL(this.getNodeValue()*this.getWeightMB());
        ML.getPL(this.getNodeValue()*this.getWeightML());
        MR.getPL(this.getNodeValue()*this.getWeightMR());
    }
}
