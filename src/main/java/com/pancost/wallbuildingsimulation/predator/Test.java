package com.pancost.wallbuildingsimulation.predator;

public class Test {

    static double valuePB = 1;
    static double valuePL = 5;
    static double valuePR = 3;

    public static void main(String[] args) {
        PredatorNeuralNetwork pnn = new PredatorNeuralNetwork();
        pnn.setPBValue(valuePB);
        pnn.setPLValue(valuePL);
        pnn.setPRValue(valuePR);
        pnn.evaluate();
        System.out.println("PB was: " + valuePB);
        System.out.println("PL was: " + valuePL);
        System.out.println("PR was: " + valuePR);
        System.out.println("MF is: " + pnn.getMFValue());
        System.out.println("MB is: " + pnn.getMBValue());
        System.out.println("ML is: " + pnn.getMLValue());
        System.out.print("MR is: " + pnn.getMRValue());
    }

}
