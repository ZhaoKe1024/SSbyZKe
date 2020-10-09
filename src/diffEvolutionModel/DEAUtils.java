package diffEvolutionModel;

import java.util.Random;

import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;

public class DEAUtils {

	public DEAUtils() {
		// TODO Auto-generated constructor stub
	}

	static double[] select() {
		
		return null;
	}

	static double[][] mutation(double[][] X, double[] bestX, double F, int mutationStrategy) {
		// TODO Auto-generated method stub
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		int NP = X.length;
		int Dim = X[0].length;
		int nrandI = 5;
		int[] r = new int[nrandI];
		int[] equalr = null;
		int equali=0;
		int equalall=0;
		double[][] V = new double[NP][Dim];

		for (int i = 0; i < NP; i++) {
//			System.out.printf("变异： %d/%d\n", i, NP);
			r = mc.randi(nrandI, 1, NP);  // 初始化一个1行NP列的随机整数向量
//			m.printVector(r);
			equalr = new int[nrandI];
			for (int j = 0; j < nrandI; j++) {
				equalr[j] = 0;
				for (int k = 0; k < nrandI; k++) {  // 统计一下向量每一个维度的数值出现的次数
					if (r[k] == r[j]) {
						equalr[j]++;
					}
				}
			}
			
			equali = 0;
			for (int j = 0; j < nrandI; j++) {  // 
				if (r[j] == i) {
					equali++;
				}
			}
			
			equalall = m.sum(equalr)+equali;
//			System.out.println("i="+i+", 相同元素有"+(equalall-nrandI)+"个");
			while (equalall > nrandI) {
				r = mc.randi(nrandI, 1, NP);
//				m.printVector(r);
				
				equalr = new int[nrandI];
				for (int j = 0; j < nrandI; j++) {
					equalr[j] = 0;
					for (int k = 0; k < nrandI; k++) {
						if (r[k] == r[j]) {
							equalr[j]++;
						}
					}
				}
				
				equali = 0;
				for (int j = 0; j < nrandI; j++) {
					if (r[j] == i) {
						equali++;
					}
				}
				
				equalall = m.sum(equalr)+equali;
//				System.out.println("相同元素有"+(equalall-5)+"个");
			}
			
			switch (mutationStrategy) {
			case 1:
				double[] temp = m.vectorSub(X[r[1]], X[r[2]]);
				V[i] = m.vectorAdd(X[r[0]], m.scalaMulti(temp, F));
				break;

			default:
				throw new IllegalArgumentException("Unexpected mutationStrategy value: " + mutationStrategy);
			}
		}
	
		return V;
	}

	static double[][] crossover(double[][] X, double[][] V, double CR, int crossStrategy) {
		int NP = X.length;
		int Dim = X[0].length;
		double[][] U = new double[NP][Dim];

		Random rand = new Random();
		double k;
		switch (crossStrategy) {
			case 1:
				int jRand=0;
				for (int i = 0; i < NP; i++) {
					jRand = rand.nextInt(Dim);
					for (int j = 0; j < Dim; j++) {
						k = rand.nextDouble();
						if (k <= CR || j == jRand) {
							U[i][j] = V[i][j];
						}else {
							U[i][j] = X[i][j];
						}
					}
				}
				break;

			case 2:
				int L = 0;
				int j;
				for (int i = 0; i < NP; i++) {
					j = rand.nextInt(Dim);
					U[i] = X[i];
					k = rand.nextDouble();
					while (k < CR && L < Dim) {
						U[i][j] = V[i][j];
						j++;
						if (j > Dim) {
							j=1;
						}
						L++;
					}
				}
				break;

			default:
				throw new IllegalArgumentException("Unexpected crossStrategy value: " + crossStrategy);
		}
		return U;
	}
}
