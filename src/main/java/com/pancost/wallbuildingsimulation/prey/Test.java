package com.pancost.wallbuildingsimulation.prey;

public class Test {
	
	static double valueFL = 0;
	static double valueFR = 1;
	static double valueFB = 3;
	static double valueCL = 1;
	static double valueCR = 3;
	static double valueCB = 1;
	static double valuePL = 1;
	static double valuePR = 0;
	static double valuePB = 3;
	static double valueTF = 1;
	static double valueTB = 2;
	static double valueTL = 3;
	static double valueTR = 4;
	
	public static void main(String[] args) {
		PreyNeuralNetwork pnn = new PreyNeuralNetwork();
		pnn.setFL(valueFL);
		pnn.setFR(valueFR);
		pnn.setFB(valueFB);
		pnn.setCL(valueCL);
		pnn.setCR(valueCR);
		pnn.setCB(valueCB);
		pnn.setPL(valuePL);
		pnn.setPR(valuePR);
		pnn.setPB(valuePB);
		pnn.setTF(valueTF);
		pnn.setTB(valueTB);
		pnn.setTL(valueTL);
		pnn.setTR(valueTR);
		
		for(int i = 0; i < 50; i++){
			pnn.evaluate();
		}

		System.out.println("MF is: " + pnn.getMF());
		System.out.println("MB is: " + pnn.getMB());
		System.out.println("ML is: " + pnn.getML());
		System.out.println("MR is: " + pnn.getMR());
		System.out.print("Mate is:" + pnn.getMate());
	}

}
