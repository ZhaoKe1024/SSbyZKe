package testFunctionUtils;

import java.util.ArrayList;
import java.lang.Math;

import java.util.Iterator;
import java.util.Random;

import matrixUtils.MatOpt;

public class Functions {

	private double lb;
	private double ub;
	private int dimension;
	
//	private String functionName;
	
	private MatOpt m;
	private Random rand;
	
	public Functions(String functionName) {
		// TODO Auto-generated constructor stub
		this.m = new MatOpt();
//		this.functionName = functionName;
		switch (functionName) {
		case "sphere":{
			this.lb = -100;
			this.ub = 100;
			this.dimension = 30;
			break;
		}
		case "GoldsteinPrice":{
			this.lb = -2;
			this.ub = 2;
			this.dimension = 2;
			break;
		}
		case "Ackleys":{
			this.lb = -5;
			this.ub = 5;
			this.dimension = 2;
			break;
		}

		case "rosenBrock":{
			this.lb = Double.MIN_VALUE;
			this.ub = Double.MAX_VALUE;
			this.dimension = 20;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + functionName);
		}
		
		
	}
	
	public void init() {
		
	}

	public static double Ackleys(double[] params) {
//		System.out.println("Ackleys");
		double x = params[0];
		double y = params[1];
		return -20*Math.exp(-0.2*Math.sqrt(0.5*(x*x+y*y)))-Math.exp(0.5*(Math.cos(2*Math.PI*x)+Math.cos(2*Math.PI*y)))+20+Math.E;
	}

//	public double sphere(ArrayList<Double> params) {
//		double result = 0;
//		Iterator<Double> it = params.iterator();
//		while (it.hasNext()) {
//			Double double1 = (Double) it.next();
//			result += (double1-1)*(double1+1)+2;
////			result += (double1)*(double1);
//		}
//		return result;
//	}
	
	public static double camel(double[] params) throws Exception {
		if (params.length > 2) {
			throw new Exception("Camel函数的自变量维度不能超出2");
		}
		double xx = params[0];
		double yy = params[1];
		return (4-2.1*xx*xx+xx*xx*xx*xx/3)*xx*xx + xx*yy+(-4+4*yy*yy)*yy*yy;
	}
	
	public static double sphere(double[] params) {
		double result = 0;
		for (int i = 0; i < params.length; i++) {
			result = (params[i]-1)*(params[i]+1)+9;
		}
		return result;
	}

	public double SchwefelsProblem22(ArrayList<Double> params) {
		double sumItem = 0;
		double prodItem = 1;
		Iterator<Double> it = params.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			if (double1<0) {
				double1 = -double1;
			}
			sumItem += double1;
			prodItem *= double1;
		}
		return sumItem+prodItem;
	}


	
	public double function3(ArrayList<Double> params) {
		double result = 0;
		int len = params.size();
		double temp;
		for (int i = 0; i < len; i++) {
			temp = m.sum(params.subList(0, i+1));
			result += temp*temp;
		}
		return result;
	}
	
	public double function4(ArrayList<Double> params) {
		double result = Math.abs(params.get(0));
		double elem = 0;
		int len = params.size();
		for (int i = 1; i < len; i++) {
			elem = params.get(i);
			if (elem<0) {
				elem = -elem;
			}
			if (elem > result) {
				result = elem;
			}
		}
		
		return result;
	}
	

	
	public double rosenbrock(ArrayList<Double> params) {
		int len = params.size();
		double result = 0;
		ArrayList<Double> item1 = m.square(m.arraySub(params.subList(1, len), m.square(params.subList(0, len-1))));
		result += 100*m.sum(item1);
		result += m.sum(m.square(params.subList(0, len-1)));
		return result;
	}
	
	public double rosenbrock(double[] params) {
		int len = params.length;
		double result = 0;
		double[] item1 = m.square(m.vectorSub(m.subArray(params, 1, len), m.square(m.subArray(params, 0, len-1))));
		result += 100*m.sum(item1);
		result += m.sum(m.square(m.scalaSubtract(m.subArray(params, 0, len-1), 1)));
		return result;
	}
	
	public static void main(String[] args) {
		Functions f = new Functions("rosenBrock");
		double[] x = new double[20];
		for (int i = 0; i < x.length; i++) {
			x[i] = 1;
		}
		System.out.println(f.rosenbrock(x));
	}
	
	public double function6(ArrayList<Double> params) {
		double result = 0;
		Iterator<Double> it = params.iterator();
		double temp;
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			temp = double1+0.5;
			if (temp < 0) {
				temp = -temp;
			}
			result += temp*temp;
		}
		return result;
	}
	
	public static double GoldsteinPrice(double[] parms) {
		double x = parms[0];
		double y = parms[1];
		return (1+(x+y+1)*(x+y+1)*(19-14*x+3*x*x-14*y+6*x*y+3*y*y))*(30+(2*x-3*y)*(2*x-3*y)*(18-32*x+12*x*x+48*y-36*x*y+27*y*y));
	}

	public double getLb() {
		return lb;
	}

	public double getUb() {
		return ub;
	}

	public int getDimension() {
		return dimension;
	}

	
	
}
