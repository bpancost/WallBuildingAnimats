package com.pancost.wallbuildingsimulation.evolve;

public class Test {
	private static double[] nnDirectEncoding = {1,1,1,1, 1,1,1,1,
			-0.5,1.5,0,0, 0.75,-0.25,1,0, 0.75,-0.25,0,1,
			-0.1,0.3,0,0, 0.15,-.05,0.2,0, 0.15,-.05,0,0.2,
			1.5,-0.5,0,0, -0.25,0.75,0,1, -0.25,0.75,1,0,
			0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
	
	public static void main(String[] args) {
		double[] childEncoding = EvolutionEngine.evolve(nnDirectEncoding, nnDirectEncoding);
		for(int i = 0; i < 1000; i++){
			childEncoding = EvolutionEngine.evolve(childEncoding, nnDirectEncoding);
		}
		for(int i = 0; i < 60; i++){
			System.out.println(nnDirectEncoding[i] + "  " + childEncoding[i]);
		}
	}

}
