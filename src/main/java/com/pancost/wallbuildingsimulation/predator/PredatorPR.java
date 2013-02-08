package com.pancost.wallbuildingsimulation.predator;

class PredatorPR extends PredatorNeuralNetworkSensorNeuron{
	
    PredatorPR(double weightMF,
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
                         MF.getPR(this.getNodeValue()*this.getWeightMF());
                         MB.getPR(this.getNodeValue()*this.getWeightMB());
                         ML.getPR(this.getNodeValue()*this.getWeightML());
                         MR.getPR(this.getNodeValue()*this.getWeightMR());
    }
}
