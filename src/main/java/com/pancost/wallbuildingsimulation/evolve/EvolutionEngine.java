package com.pancost.wallbuildingsimulation.evolve;

import java.util.*;

public class EvolutionEngine {
    private static double mutationRate = .05;

    /**
     * 
     * @param parent1Encoding
     * @param parent2Encoding
     * @return
     * @author Brandon Pancost
     * @since  1.0
     */
    public static double[] evolve(double[] parent1Encoding, double[] parent2Encoding){
        Random r = new Random();
        r.setSeed(new Date().getTime());
        int crossoverLocation = r.nextInt(60);
        double[] childEncoding = new double[60];
        System.arraycopy(parent1Encoding, 0, childEncoding, 0, crossoverLocation);
        System.arraycopy(parent2Encoding, crossoverLocation, childEncoding, crossoverLocation, 60 - crossoverLocation);
        for(int k = 0; k < 60; k++){
            if(r.nextDouble() <= mutationRate){
                if(r.nextDouble() <= .5){
                    childEncoding[k] = r.nextDouble();
                }else{
                    childEncoding[k] = -r.nextDouble();
                }
            }
        }
        return childEncoding;
    }
}
