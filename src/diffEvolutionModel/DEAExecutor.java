package diffEvolutionModel;

import matrixUtils.Argmin;
import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;
import plotUtils.ResultPlot;
import testFunctionUtils.Functions;

/**
 * 差分进化算法differential evolution algorithm
 * @author zhaoke
 *	差分进化算法也是一种群体适应进化算法，
 *1. 产生服从均匀分布的初始群题作为初始解，群题有n个个体，每个个体是一个有m个维度的向量
 *2. 每次迭代中通过变异，交叉产生新解，新界和旧解选择适应度更好的一个作为新解
 *3. 达到一定迭代次数结束搜寻，返回最优解
 */
public class DEAExecutor {

	// 最大迭代次数，进化代数
	private int maxGen;
	private int Gen;
	// 搜索的上下界
	private double Xmax;
	private double Xmin;
	// 个体维数与种群规模
	private int Dim;
	private int NP;
	// 缩放因子与交叉概率
	private double F;
	private double CR;
	// 测试方程选择，变异策略选择，交叉策略选择
	private int index;
	private int mutationStrategy;
	private int crossStrategy;
	
	/**
	 * 构造函数，初始化算法参数
	 */
	public DEAExecutor() {
		// TODO Auto-generated constructor stub
		this.init();
	}
	private void init() {
		this.maxGen=620;
		this.Gen=1;

		this.Xmax=30;
		this.Xmin=-30;

		this.Dim=30;
		this.NP=100;

		this.F=0.5;
		this.CR=0.3;

		this.index=3;  // 但是这个暂时没用到
		this.mutationStrategy=2;
		this.crossStrategy=1;
	}

	/**
	 * 返回进化后的最优解
	 * @return
	 */
	private double[] exec() {
		/*
		 *矩阵创建对象
		 *矩阵操作对象
		 *测试函数
		 *求最小值及其索引的对象 
		 */
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		Functions testFun = new Functions("rosenBrock");
		Argmin min = null;
		// 初始化
		double[][] X = m.matrixLinearScalaOpt(mc.randUniformMatrix(NP, Dim), (Xmax-Xmin), Xmin);
		System.out.println("初始化种群：");
		m.printMatrixOmitted(X);
		System.out.println("初始化种群end\n===========================\n");
		/* 迭代循环
		 * 初始化迭代中使用的参数
		 */
		double[] fitnessX = new double[NP];  // 每一个个体的适应度，NP个个体
		double fitnessbestX;  // 最佳适应度，最大还是最小视具体问题而定
		int indexbestX;  // 最佳适应度对应的数组下标
		double[] bestX = new double[Dim];  // 最优解，即最优适应度对应的个体，解有Dim个维度
		double[][] V = new double[NP][Dim];  // 变异群体
		double[][] U = new double[NP][Dim];  // 交叉产生的群体
		double[] fitnessU = new double[NP];  // 交叉产生群体的适应度，NP个个体
		double[] bestfitness = new double[maxGen];  // 每次迭代所产生的最佳适应度，用于画图用，一共maxGen个
		for (Gen=0; Gen < maxGen; Gen++) {
			// 计算种群每个个体的适应度
			for (int i = 0; i < NP; i++) {
				fitnessX[i] = Functions.rosenbrock(X[i]);
			}
			// 取其最小值与索引，从而得到当前最优解
			min = new Argmin(fitnessX);
			fitnessbestX = min.getResult();
			indexbestX = min.getIndex();
			bestX = X[indexbestX];

			// 变异操作 
			V = DEAUtils.mutation(X, bestX, F, mutationStrategy);
			// 交叉操作
			U = DEAUtils.crossover(X, V, CR, crossStrategy);
			// 选择
			for (int i = 1; i <NP; i++) {
				fitnessU[i] = Functions.rosenbrock(U[i]);
				if (fitnessU[i] <= fitnessX[i]) {
					X[i] = U[i];
					fitnessX[i] = fitnessU[i];
					if (fitnessU[i] < fitnessbestX) {
						bestX = U[i];
						fitnessbestX = fitnessU[i];
					}
				}
			}
			System.out.printf("第 %d 代\n", Gen);
			m.printVectorOmitted(bestX);  // 在控制台打印向量
			System.out.printf("当前最优解 %f, \n", fitnessbestX);
			bestfitness[Gen] = fitnessbestX;
		}

		return bestX;
		// 绘图
//		m.printVector(bestfitness);
//		System.out.println("结果Ｘ：");
//		m.printMatrix(X);
//		new ResultPlot(m.subArray(bestfitness, 0, bestfitness.length));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DEAExecutor exe = new DEAExecutor();
		exe.exec();
	}

}
