package matrixUtils;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MatOpt {

	public double[] abs(double[] vector) {
		double[] result = new double[vector.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Math.abs(vector[i]);
		}
		return result;
	}

	public ArrayList<Double> arraySub(List<Double> params1, List<Double> params2){
		ArrayList<Double> result = new ArrayList<Double>();
		int len = params1.size();
		for (int i = 0; i < len; i++) {
			result.add(params1.get(i) - params2.get(i));
		}
		return result;
	}

	public double[] copyVector(double[] vector) {
		double[] v1 = new double[vector.length];
		for (int i = 0; i < v1.length; i++) {
			v1[i] = vector[i];
		}
		return v1;
	}
	
	public double[][] copyMatric(double[][] matrix){
		double[][] m1 = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			m1[i] = this.copyVector(matrix[i]);
		}
		return m1;
	}
	
	public double max(double[] array) {
		double result = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > result) {
				result = array[i];
			}
		}
		return result;
	}
	
	public double min(double[] array) {
		double result = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < result) {
				result = array[i];
			}
		}
		return result;
	}
	
	public double[] min(double[][] matrix) {
		double[] result = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			result[i] = matrix[i][0];
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] < result[i]) {
					result[i] = matrix[i][j];
				}
			}
		}
		return result;
	}

	public ArrayList<Double> arrayToList(double[] array){
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}
	
	public ArrayList<Integer> arrayToList(int[] array){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}
	// ˳���ת����
	public double[] listToArrayDouble(ArrayList<Double> list) {
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
	// ˳���ת����
	public int[] listToArrayInteger(ArrayList<Integer> list) {
		int[] array = new int[list.size()];
		int index = 0;
		Iterator<Integer>it = list.iterator();
		while (it.hasNext()) {
			Integer double1 = (Integer) it.next();
			array[index] = double1;
			index++;
		}
		return array;
	}
	
	/**
	 * �����������ֵ����Ԫ��
	 * @param array
	 * @return ���Һ������
	 */
	public int[] shuffle(int[] array) {
		ArrayList<Integer> list = this.arrayToList(array);
		Collections.shuffle(list);
		return this.listToArrayInteger(list);
	}
	
	// ������Ƭ
	public double[] getSubArray(double[] array, int leftIndex, int rightIndex) {
		double[] subArray = new double[rightIndex-leftIndex];
		for (int i = leftIndex; i < rightIndex; i++) {
			subArray[i-leftIndex] = array[i];
		}
		return subArray;
	}
	
	public int[] getSubArray(int[] array, int leftIndex, int rightIndex) {
		int[] subArray = new int[rightIndex-leftIndex];
		for (int i = leftIndex; i < rightIndex; i++) {
			subArray[i-leftIndex] = array[i];
		}
		return subArray;
	}
	
	/**
	 * �������array���±�Ϊindexs[i]�Ĳ���Ԫ��
	 * @param array ԭ����
	 * @param indexs ��������
	 * @return ԭ��������������Ԫ��Ϊ��������ЩԪ��
	 */
	public int[] getSubArray(int[] array, int[] indexs) {
		int len = indexs.length;
		int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			result[i] = array[indexs[i]];
		}
		return result;
	}
	
//	public static void main(String[] args) {
//		MatOpt m = new MatOpt();
//		int[] array = {12, 34 , 564, 7 , 46 ,76, 987, 7,54 ,3 ,36 ,9};
//		int[] index = {1, 2, 3, 4 ,5 ,6 ,7 , 8, 9, 10};
//		int[] get1 = m.getSubArray(array, index);
//		System.out.println(Arrays.toString(get1));
//		index = m.shuffle(index);
//		int[] get2 = m.getSubArray(array, index);
//		System.out.println(Arrays.toString(get2));
//	}
	
	/**
	 * @param M ������ľ���
	 * @param coef ϵ��������
	 * @param constant ���������
	 * @return ����M��ÿ��Ԫ�ض�����ϵ��coef��Ȼ����ϳ�����constant
	 */
	public double[][] matrixLinearScalaOpt(double[][] M, double coef, double constant){
		int row = M.length;
		int column = M[0].length;
		double[][] result = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				result[i][j] = coef*M[i][j] + constant;
			}
		}
		return result;
	}
	
	public ArrayList<Double> square(List<Double> params){
		ArrayList<Double> result = new ArrayList<Double>();
		Iterator<Double>it = params.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			result.add(double1*double1);
		}
		return result;
	}

	public ArrayList<Double> scalaMulti(ArrayList<Double> params, double factor){
		ArrayList<Double> result = new ArrayList<Double>();
		Iterator<Double>it = params.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			result.add(factor*double1);
		}
		return result;
	}
	
	/**
	 *	 ����ÿһ��ά�ȵ�ֵ��ȥͬһ������ֵ
	 * @param params ��������
	 * @param factor ��������
	 * @return ������ȥ�����Ľ��params[i] - factor;
	 */
	public double[] scalaSubtract(double[] params, double factor){
		int len = params.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = params[i] - factor;
		}
		return result;
	}

	/**
	 * mode��ʾ��������߻����ұ�
	 * @param vector
	 * @param scala
	 * @param mode
	 * @return
	 */
	public double[] vectorDiv(double[] vector, double scala, String mode) {
		double[] result = new double[vector.length];
		if (mode.equals("left")) {
			for (int i = 0; i < result.length; i++) {
				result[i] = scala/vector[i];
			}
		}else if (mode.equals("right")) {
			for (int i = 0; i < result.length; i++) {
				result[i] = vector[i]/scala;
			}
		}
		return result;
	}
	
	public ArrayList<Double> scalaAdd(ArrayList<Double> params, double item){
		ArrayList<Double> result = new ArrayList<Double>();
		Iterator<Double>it = params.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			result.add(item+double1);
		}
		return result;
	}

	public void setSubArray(double[] array, int begin, int end, double[] values) {
		for (int i = begin; i < end; i++) {
			array[i] = values[i];
		}
	}
	public void setSubArray(double[] array, int[] index, double[] values) {
		
		int len = index.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i]= values[index[i]];
		}
	}
	
	public void printVector(double[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.printf("%.3f, ",vector[i]);
		}
		System.out.println();
	}
	
	public void printVector(int[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.printf("dim%d: %d, ", i, vector[i]);
		}
		System.out.println();
	}
	public void printVectorOmitted(double[] vector) {
		int len = vector.length;
		System.out.print("len="+len+", ");
		System.out.printf("%.2f  %.2f  ...  %.2f  ...  %.2f\n", vector[0], vector[1], vector[10], vector[len-1]);
	}
	
	/**
	 * 	���Եش�ӡ����
	 * @param matrix
	 */
	public void printMatrix(double[][] matrix) {
		int len = matrix.length;
		int[] indexs = {0, 1, 2, 3, len-3, len-2, len-1};
		for (int i = 0; i < indexs.length; i++) {
			this.printVector(matrix[indexs[i]]);
		}
	}
	
	public void printMatrixOmitted(double[][] matrix) {
		System.out.println("------print matrix--row="+matrix.length+"----");
		this.printVectorOmitted(matrix[0]);
		this.printVectorOmitted(matrix[1]);
		System.out.println("...  ...  ...  ...");
		this.printVectorOmitted(matrix[matrix.length-1]);
	}
	
	/**
	 * 
	 * @param params
	 * @param item
	 * @return ����ÿ��Ԫ�ؼ��ϳ���item
	 */
	public double[] scalaAdd(double[] params, double item){
		int len = params.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = params[i] + item;
		}
		return result;
	}

	/**
	 * result[i] = factor*params[i];
	 * @param params
	 * @param factor
	 * @return ��������factor*params[i]
	 */
	public double[] scalaMulti(double[] params, double factor){
		int len = params.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = factor*params[i];
		}
		return result;
	}
	public double[] scalaMulti(int[] params, double factor){
		int len = params.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = factor*params[i];
		}
		return result;
	}
	

	/**
	 * 	��˫����double��������ÿһ��Ԫ�ص�ƽ��ֵ����
	 * @param array ԭ����
	 * @return ����ƽ��ֵ
	 */
	public double[] square(double[] array) {
		double[]result = new double[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i]*array[i];
		}
		return result;
	}

	/**
	 * 	ȡ����array��begin��endλ��
	 * @param array ԭ����
	 * @param begin ƫ����
	 * @param end ��ֹλ���н�
	 * @return ������array[begin+i]
	 */
	public double[] subArray(double[] array, int begin, int end) {
		double[] result = new double[end-begin];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[begin+i];
//			result[i] = array[i];
		}
		return result;
	}

	public int sum(int[] array) {
		int result = 0;
		for (int i = 0; i < array.length; i++) {
			result+=array[i];
		}
		return result;
	}

	/**
	 *	 ��double�������Ԫ��ֵ֮��
	 * @param array ��������
	 * @return ��ͽ��
	 */
	public double sum(double[] array) {
		double result = 0;
		for (int i = 0; i < array.length; i++) {
			result+=array[i];
		}
		return result;
	}
	
	public double sum(List<Double> list) {
		double result = 0;
		Iterator<Double>it = list.iterator();
		while (it.hasNext()) {
			Double double1 = (Double) it.next();
			result += double1;
		}
		return result;
	}
	
	/**
	 * result[i] = v1[i] + v2[i];
	 * @return ������ӦԪ�����v1[i] + v2[i];
	 */
	public double[] vectorAdd(double[] v1, double[] v2) {
		int len = v1.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = v1[i] + v2[i];
		}
		return result;
	}
	/**
	 * result[i] = v1[i] - v2[i]
	 * @param v1 �������
	 * @param v2 �����ұ�
	 * @return v1[i] - v2[i]
	 */
	public double[] vectorSub(double[] v1, double[] v2) {
		int len = v1.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = v1[i] - v2[i];
		}
		return result;
	}
	
	/**
	 * 
	 * @return ������ӦԪ�����
	 */
	public double[] vectorMulti(double[] v1, double[] v2) {
		double[] result = new double[v1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = v1[i] * v2[i];
		}
		return result;
	}
	
	/**
	 * 
	 * @return �������ڻ�
	 */
	public double vectorMultiDot(double[] v1, double[] v2) {
		int len = v1.length;
		double result = 0;
		for (int i = 0; i < len; i++) {
			result += v1[i] * v2[i];
		}
		return result;
	}
	
	/**
	 * 
	 * @return ����������ӦԪ�����
	 */
	public double vectorMultiDot(int[] v1, int[] v2) {
		int len = v1.length;
		double result = 0;
		for (int i = 0; i < len; i++) {
			result += v1[i] * v2[i];
		}
		return result;
	}

	/**

	 * @return ����������ӦԪ�����
	 */
	public double[] vectorDivid(double[] v1, double[] v2) {
		double[] result = new double[v1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = v1[i] / v2[i];
		}
		return result;
	}
	
}
