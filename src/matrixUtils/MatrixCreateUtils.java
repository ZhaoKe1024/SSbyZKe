package matrixUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class MatrixCreateUtils {

	public double[][] scalaMulti(int scala, double[][] matrix) {
		int row = matrix.length;
		int column = matrix[0].length;
		double[][] result = new double[row][column];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				result[i][j] = scala*matrix[i][j];
			}
		}
		return result;
	}
	
	public double[] scalaMulti(double scala, double[] vector) {
		double[] result = new double[vector.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = scala*vector[i];
		}
		return result;
	}
	
	public double[] scalaAdd(double scala, double[] vector) {
	double result[] = new double[vector.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = scala+ vector[i];
		}
		return result;
	}
	
	public double[] zeros(int len) {
		return new double[len]; 
	}
	
	public double[] ones(int len) {
		double[] vector = new double[len];
		for (int i = 0; i < vector.length; i++) {
			vector[i] = 1;
		}
		return vector;
	}
	
	/**
	 * generate 2-dim double all-ones array by row and column
	 * @param row
	 * @param column
	 * @return
	 */
	public double[][] ones(int row, int column){
		double[][] matrix = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				matrix[i][j] = 1;
			}
		}
		return matrix;
	}
	
	/**
	 * generate 2-dims double zeros array by row and column
	 * @param row
	 * @param column
	 * @return
	 */
	public double[][] zeros(int row, int column){
		return new double[row][column];
	}

	public ArrayList<Double> arrayToList(double[] array){
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}
	
	// 顺序表转数组
	public double[] listToArray(ArrayList<Double> list) {
		double[] array = new double[list.size()];
		int index = 0;
		Iterator<Double>it = list.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			array[index] = double1;
			index++;
		}
		return array;
	}
	
	/**
	 * 生成len长度范围[lb, ub]的随机数组
	 * @param len 数组长度
	 * @param lb 下限
	 * @param ub 上限
	 * @return 随机整数数组
	 */
	public int[] randi(int len, int lb, int ub) {
		int[] result = new int[len];
		Random rand = new Random();
		for (int i = 0; i < len; i++) {
			result[i] = lb + rand.nextInt(ub-lb);
		}
		return result;
	}

	/**
	 * generate random numbers obey to Gaussian distribution
	 * @param len
	 * @return
	 */
	public double[] randGaussian(int len) {
		Random rand = new Random();
		double[] result = new double[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = rand.nextGaussian();
		}
		return result;
	}
	
	/**
	 * generate uniform distribution random numbers
	 * @param len
	 * @return
	 */
	public double[] randUniform(int len) {
		double[] result = new double[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = Math.random();
		}
		return result;
	}
	
	/**
	 * generate uniform distribution random numbers
	 * @param len
	 * @return
	 */
	public double[][] randUniformMatrix(int row, int column) {
		double[][] result = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				result[i][j] = Math.random();
			}
		}
		return result;
	}

	
	public int[] seriesInt(int len) {
		int[] array = new int[len];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		return array;
	}
	
	public double[] seriesDouble(int len) {
		double[] array = new double[len];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		return array;
	}
	
}
