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

	// producer的总人口占比
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
	 * 对一个数组根据给定上下界进行限界
	 * @param array 待处理数组
	 * @param lb 下界
	 * @param ub 上届
	 * @return 将原始数组限界之后数组
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
	 * @param pop 种群
	 * @param M 最大迭代次数
	 * @param c 下限系数
	 * @param d 上限系数
	 * @param dim 麻雀数据维度
	 * @param functionName 所选测试函数
	 * @return 
	 */
	public double[] mainProcess(int pop, int M, double c, double d, int dim) {
		this.paramsInit();
		// producer人口规模
		int pNum = Math.round(this.pPercent*pop);
		System.out.println("人口规模pNum: "+pNum);
		// 上下限
		double[] lb= mc.scalaMulti(c, mc.ones(dim));
		double[] ub= mc.scalaMulti(d, mc.ones(dim));
		System.out.print("解空间下限： ");
		m.printVector(lb);
		System.out.print("解空间上限： ");
		m.printVector(ub);

		// 代表麻雀位置
		double[][] X = mc.zeros(pop, dim);
		// 适应度值初始为0
		double[] fitness = mc.zeros(pop);
		// 初始化麻雀的坐标和适应度
		for (int i = 0; i < pop; i++) {
			for (int j = 0; j < X[0].length; j++) {
				// 均匀分布随机数X~U(lb, ub)
				X[i][j] = lb[j]+(ub[j] - lb[j]) * Math.random();
			}
			fitness[i] = Functions.rosenbrock(X[i]);
		}
//		System.out.println("X 的初始值为：");
//		m.printMatrix(X);
		System.out.print("初始适应度为fitness: ");
		m.printVector(fitness);
		/**
		 *  最佳种群与适应度矩阵，初始值为初始种群
		 */
		double[]pFit = m.copyVector(fitness);
		double[][] pX = m.copyMatric(X);
		/**
		 *  全局最优适应值，Producer、的能量储备水平取决于对个人适应度值的评估
		 */
		double fMin = m.min(fitness);
		System.out.println("当前最优适应度："+fMin);
		Argmin min = new Argmin(fitness);
		// fMin对应的全局最优位置的变量信息
		int bestI = min.getIndex();
		double[] bestX = X[bestI];
		System.out.printf("最优位置%d，变量信息", bestI);
		m.printVector(bestX);
		// 收敛曲线
		double[] ConvergenceCurve = mc.zeros(M);

		SortArray sa = null;
		ArgMax am = null;
		/**
		 * 根据适应度排序后的个体的原索引
		 */
		int[] index;
		double fMax;
		int fMaxIndex;
		double[] worst;

		double r2;  // 预警值
		// 迭代更新
		for (int i = 0; i < M; i++) {
			System.out.printf("iter: %d/%d\n", i, M);
			// 首先对麻雀适应度排序取得索引
			sa = new SortArray(pFit);
			index = sa.getSortIndex();
			
			// 最大适应度及其下标
			am = new ArgMax(pFit);
			fMax = am.getResult();
			fMaxIndex = am.getIndex();
			// 最差适应度
			worst = X[fMaxIndex];
			/* ================================================
			 * ==============update Producer===================
			 * ================================================
			 */
			// 更新Producer
			// 预警值
			double r1, Q;
			r2 = rand.nextGaussian();
			// 预警值较小说明没有捕食者出现
			if (r2 < 0.8) {
				// 遍历producer
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
			// scrounger的位置更新
			for (int j = 0; j < pop - pNum; j++) {
				// si = j+pNum;
				si = j + pNum;
				// A = (int)( rand(1, dim));
				double[] A0 = mc.randGaussian(dim);  // 临时变量而已
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
	
	// 距离矩阵的归一化，每一行进行归一化
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
