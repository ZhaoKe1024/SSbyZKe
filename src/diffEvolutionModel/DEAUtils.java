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

	/**
	 * 变异操作
	 * @param X 群体矩阵
	 * @param bestX 最优个体向量
	 * @param F 变异率
	 * @param mutationStrategy 变异策略选择
	 * @return 变异后的群体
	 */
	static double[][] mutation(double[][] X, double[] bestX, double F, int mutationStrategy) {
		// TODO Auto-generated method stub
		// 矩阵创建与操作对象
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		
		int NP = X.length;  // 个体数
		int Dim = X[0].length;  // 个体向量的维度
		// 临时变量
		int nrandI = 5;  // 变异基因个数（即个体变异的向量的维度数）
		int[] r = new int[nrandI];
		int[] equalr = null;
		int equali=0;
		int equalall=0;
		double[][] V = new double[NP][Dim];
		// 每一个个体进行变异操作
		for (int i = 0; i < NP; i++) {
//			System.out.printf("变异： %d/%d\n", i, NP);
			r = mc.randi(nrandI, 1, NP);  // 初始化一个1行NP列的随机整数向量
//			m.printVector(r);
			equalr = new int[nrandI];  // 记录向量r每个维度元素值，在该向量中的出现次数
			// 逐个搜索
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
				// V(i,:)=X(r(1),:)+F*(X(r(2),:)-X(r(3),:));
				double[] temp1 = m.vectorSub(X[r[1]], X[r[2]]);
				V[i] = m.vectorAdd(X[r[0]], m.scalaMulti(temp1, F));
				double[] temp2 = m.vectorSub(X[r[3]], X[r[4]]);
				V[i] = m.vectorAdd(V[i], m.scalaMulti(temp2, F));
				break;

			case 2:
				//             V(i,:)=bestX+F*(X(r(1),:)-X(r(2),:));
				V[i] = m.vectorAdd(bestX, m.vectorSub(X[r[0]], X[r[1]]));
				break;
				
			case 3:
				// V(i,:)=X(i,:)+F*(bestX-X(i,:))+F*(X(r(1),:)-X(r(2),:));
				V[i] = X[i];
				V[i] = m.vectorAdd(V[i], m.scalaMulti(m.vectorSub(bestX, X[i]), F));
				V[i] = m.vectorAdd(V[i], m.scalaMulti(m.vectorSub(X[r[0]], X[r[1]]), F));
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected mutationStrategy value: " + mutationStrategy);
			}
		}

		return V;
	}

	/**
	 * 	差分进化算法的交叉操作
	 * @param X 本次迭代的原始种群
	 * @param V 本次迭代的变异 种群
	 * @param CR 交叉率
	 * @param crossStrategy
	 * @return 交叉结果种群
	 */
	static double[][] crossover(double[][] X, double[][] V, double CR, int crossStrategy) {
		int NP = X.length;
		int Dim = X[0].length;
		double[][] U = new double[NP][Dim];

		Random rand = new Random();
		double k;
		switch (crossStrategy) {
			case 1:
				int jRand=0;  // 随机整数确保不会出现完全复制
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
					L=0;
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
