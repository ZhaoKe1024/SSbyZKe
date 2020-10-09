package diffEvolutionModel;

import matrixUtils.Argmin;
import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;
import testFunctionUtils.Functions;

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
	// 测试方程索引，变异策略，交叉策略
	private int index;
	private int mutationStrategy;
	private int crossStrategy;
	
	public DEAExecutor() {
		// TODO Auto-generated constructor stub
		this.init();
	}
	private void init() {
		this.maxGen=700;
		this.Gen=1;

		this.Xmax=30;
		this.Xmin=-30;

		this.Dim=20;
		this.NP=100;

		this.F=0.5;
		this.CR=0.3;

		this.index=3;
		this.mutationStrategy=1;
		this.crossStrategy=1;
	}
	
	private void exec() {
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		Functions testFun = new Functions("rosenBrock");
		Argmin min = null;
		// 初始化
		double[][] X = m.matrixLinearScalaOpt(mc.randUniformMatrix(NP, Dim), (Xmax-Xmin), Xmin);
//		System.out.println("初始化种群：");
//		m.printMatrixOmitted(X);
		// 迭代循环
		double[] fitnessX = new double[NP];
		double fitnessbestX;
		int indexbestX;
		double[] bestX = new double[Dim];
		double[][] V = new double[NP][Dim];
		double[][] U = new double[NP][Dim];
		double[] fitnessU = new double[NP];
		double[] bestfitness = new double[maxGen];
		for (Gen=1; Gen < maxGen; Gen++) {
			// 计算种群每个个体的适应度
			for (int i = 0; i < NP; i++) {
				fitnessX[i] = testFun.rosenbrock(X[i]);
			}
			// 取其最小值与索引，从而得到当前最优解
			min = new Argmin(fitnessX);
			fitnessbestX = min.getResult();
			indexbestX = min.getIndex();
			bestX = X[indexbestX];
			
			// 变异交叉
			V = DEAUtils.mutation(X, bestX, F, mutationStrategy);
			U = DEAUtils.crossover(X, V, CR, crossStrategy);
			// 选择
			for (int i = 1; i <NP; i++) {
				fitnessU[i] = testFun.rosenbrock(U[i]);
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
			m.printVectorOmitted(bestX);
			System.out.printf("当前最优解 %f, \n", fitnessbestX);
			bestfitness[Gen] = fitnessbestX;
		}
		// 绘图
//		m.printVector(bestfitness);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DEAExecutor exe = new DEAExecutor();
		exe.exec();
	}

}
