package ssamodel;

import java.util.Random;

import matrixUtils.ArgMax;
import matrixUtils.Argmin;
import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;
import matrixUtils.SortArray;
import plotUtils.ResultPlot;
import testFunctionUtils.Functions;
public class SSAExecutor {

	// producer�����˿�ռ��
	private float pPercent;
	private MatrixCreateUtils mc;
	private MatOpt m;
	private Random rand;

	private double fMin;
	private double[] bestX;
	private double[] Convergence_curve;

	public SSAExecutor() {
		// TODO Auto-generated constructor stub
		this.mc = new MatrixCreateUtils();
		this.m = new MatOpt();
		this.rand = new Random();
	}

	public void paramsInit() {
		this.pPercent = 0.2f;
	}

	/**
	 * ��һ��������ݸ������½�����޽�
	 * @param array ����������
	 * @param lb �½�
	 * @param ub �Ͻ�
	 * @return ��ԭʼ�����޽�֮������
	 */
	private double[] clipping(double[] array, double[] lb, double[] ub) {
		int len = array.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = array[i];
			if (result[i] < lb[i]) {
				result[i] = lb[i];
			}else if (result[i] > ub[i]) {
				result[i] = ub[i];
			}
		}
		return result;
	}

	/**
	 * 
	 * @param pop ��Ⱥ
	 * @param M ����������
	 * @param c ����ϵ��
	 * @param d ����ϵ��
	 * @param dim ��ȸ����ά��
	 * @param functionName ��ѡ���Ժ���
	 * @return 
	 */
	public double[] mainProcess(int pop, int M, double c, double d, int dim) {
		this.paramsInit();
		// producer�˿ڹ�ģ
		int pNum = Math.round(this.pPercent*pop);
		System.out.println("�˿ڹ�ģpNum: "+pNum);
		// ������
		double[] lb= mc.scalaMulti(c, mc.ones(dim));
		double[] ub= mc.scalaMulti(d, mc.ones(dim));
		System.out.print("��ռ����ޣ� ");
		m.printVector(lb);
		System.out.print("��ռ����ޣ� ");
		m.printVector(ub);

		// ������ȸλ��
		double[][] X = mc.zeros(pop, dim);
		// ��Ӧ��ֵ��ʼΪ0
		double[] fitness = mc.zeros(pop);
		// ��ʼ����ȸ���������Ӧ��
		for (int i = 0; i < pop; i++) {
			for (int j = 0; j < X[0].length; j++) {
				// ���ȷֲ������X~U(lb, ub)
				X[i][j] = lb[j]+(ub[j] - lb[j]) * Math.random();
			}
			fitness[i] = Functions.rosenbrock(X[i]);
		}
//		System.out.println("X �ĳ�ʼֵΪ��");
//		m.printMatrix(X);
		System.out.print("��ʼ��Ӧ��Ϊfitness: ");
		m.printVector(fitness);
		/**
		 *  �����Ⱥ����Ӧ�Ⱦ��󣬳�ʼֵΪ��ʼ��Ⱥ
		 */
		double[]pFit = m.copyVector(fitness);
		double[][] pX = m.copyMatric(X);
		/**
		 *  ȫ��������Ӧֵ��Producer������������ˮƽȡ���ڶԸ�����Ӧ��ֵ������
		 */
		double fMin = m.min(fitness);
		System.out.println("��ǰ������Ӧ�ȣ�"+fMin);
		Argmin min = new Argmin(fitness);
		// fMin��Ӧ��ȫ������λ�õı�����Ϣ
		int bestI = min.getIndex();
		double[] bestX = X[bestI];
		System.out.printf("����λ��%d��������Ϣ", bestI);
		m.printVector(bestX);
		// ��������
		double[] ConvergenceCurve = mc.zeros(M);

		SortArray sa = null;
		ArgMax am = null;
		/**
		 * ������Ӧ�������ĸ����ԭ����
		 */
		int[] index;
		double fMax;
		int fMaxIndex;
		double[] worst;

		double r2;  // Ԥ��ֵ
		// ��������
		for (int i = 0; i < M; i++) {
			System.out.printf("iter: %d/%d\n", i, M);
			// ���ȶ���ȸ��Ӧ������ȡ������
			sa = new SortArray(pFit);
			index = sa.getSortIndex();
			
			// �����Ӧ�ȼ����±�
			am = new ArgMax(pFit);
			fMax = am.getResult();
			fMaxIndex = am.getIndex();
			// �����Ӧ��
			worst = X[fMaxIndex];
			/* ================================================
			 * ==============update Producer===================
			 * ================================================
			 */
			// ����Producer
			// Ԥ��ֵ
			double r1, Q;
			r2 = rand.nextGaussian();
			// Ԥ��ֵ��С˵��û�в�ʳ�߳���
			if (r2 < 0.8) {
				// ����producer
				for (int j = 0; j < pNum; j++) {
					r1 = rand.nextGaussian();
					
					for (int k = 0; k < worst.length; k++) {
						X[index[j]][k] = pX[index[j]][k]*Math.exp(-k/(r1*M));
					}
					X[index[j]] = clipping(X[index[j]], lb, ub);
					fitness[index[j]] = Functions.rosenbrock(X[index[j]]);
				}
			}else{
				for (int j = 0; j < pNum; j++) {
					Q = rand.nextGaussian();
					for (int k = 0; k < worst.length; k++) {
						X[index[j]][k] = Q+pX[index[j]][k];
					}
					X[index[j]] = this.clipping(X[index[j]], lb, ub);
					
					fitness[index[j]] = Functions.rosenbrock(X[index[j]]);
				}
			}
			/* ================================================
			 * =============update scrounger===================
			 * ================================================
			 */
			// 
			// bestII
			Argmin II = new Argmin(fitness);
			int bestII = II.getIndex();
			// bestXX
			double[] bestXX = X[bestII];
			int si;
			int[] A;
			// scrounger��λ�ø���
			for (int j = 0; j < pop - pNum; j++) {
				// si = j+pNum;
				si = j + pNum;
				// A = (int)( rand(1, dim));
				double[] A0 = mc.randGaussian(dim);  // ��ʱ��������
				A = new int[dim];
				for (int j2 = 0; j2 < A0.length; j2++) {
					A[j2] = (int)(2*A[j2]) * 2 - 1;
				}
				if (si > pop/2) {
					Q = rand.nextGaussian();
					for (int k = 0; k < dim; k++) {
						X[index[si]][k] = Q*Math.exp(worst[k] - pX[index[si]][k]/(si*si));
//						X[index[si]][k] = Q*Math.exp(worst[k] - pX[index[si]][k]/Math.sqrt(si));
					}
				}else {
					double temp = m.vectorMultiDot(m.abs(m.vectorSub(pX[index[si]], bestXX)), m.vectorDiv(m.scalaMulti(A, m.vectorMultiDot(A, A)), 1, "left"));
					X[index[si]] = m.scalaAdd(X[index[si]], temp);
				}
				X[index[si]] = clipping(X[index[si]], lb, ub);
				fitness[index[si]] = Functions.rosenbrock(X[index[si]]);
			}
			
			/* ================================================
			 * ========update who was aware of danger==========
			 * ================================================
			 */
			int[] arrc = mc.seriesInt(index.length);
			arrc = m.shuffle(arrc);
			int[] b = m.getSubArray(index, arrc);
			for (int j = 0; j < b.length; j++) {
				if (pFit[index[b[j]]] > fMin){
					X[index[b[j]]] = m.vectorAdd(bestX, m.vectorMulti(mc.randGaussian(dim), m.abs(m.vectorSub(pX[index[b[j]]], bestX))));
				}else {
					double[] temp = m.vectorDiv(m.vectorSub(m.abs(pX[index[b[j]]]), worst), pFit[index[b[j]]]+Math.pow(10, 50)-fMax, "right");
					X[index[b[j]]] = m.vectorAdd(pX[index[b[j]]], m.scalaMulti(temp, (2*rand.nextGaussian()-1)));
				}
				X[index[b[j]]] = this.clipping(X[index[b[j]]], lb, ub);
				fitness[index[b[j]]] = Functions.rosenbrock(X[index[b[j]]]);
			}
			
			for (int j = 0; j < pop; j++) {
				if (fitness[j] < pFit[j]) {
					pFit[j] = fitness[j];
					pX[j] = X[j];
				}
				if(pFit[j] < fMin) {
					fMin = pFit[j];
					bestX = pX[j];
				}
			}
			
			ConvergenceCurve[i] = fMin;
			System.out.println(fMin);
		}
		
		this.fMin = fMin;
		this.bestX = bestX;
		new ResultPlot(ConvergenceCurve);
		m.printMatrix(pX);
		return bestX;
	}

	public double getfMin() {
		return fMin;
	}

	public double[] getBestX() {
		return bestX;
	}

	public double[] getConvergence_curve() {
		return Convergence_curve;
	}
	
	private double getDistance(double[] position1, double[] position2) {
		double result = 0;
		for (int i = 0; i < position1.length; i++) {
			result += (position1[i]-position2[i])*(position1[i]-position2[i]);
		}
		return result;
	}
	
	private double[][] getDistanceMatrix(double[][] coordinates){
		int len = coordinates.length;
		double[][] result = new double[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < i; j++) {
				result[i][j] = this.getDistance(coordinates[i], coordinates[j]);
				result[j][i] = result[i][j];
			}
		}
		return result;
	}
	
	// �������Ĺ�һ����ÿһ�н��й�һ��
	private double[][] getNormalizedDistance(double[][] distance) {
		int len = distance.length;
		double[][] result = new double[len][len];
		double tempSum=0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len && j != i; j++) {
				tempSum += distance[i][j];
			}

			for (int j = 0; j < len && j != i; j++) {
				result[i][j] = distance[i][j]/tempSum;
			}
		}
		return result;
	}
}
