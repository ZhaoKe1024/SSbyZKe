package diffEvolutionModel;

import matrixUtils.Argmin;
import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;
import testFunctionUtils.Functions;

public class DEAExecutor {

	// ��������������������
	private int maxGen;
	private int Gen;
	// ���������½�
	private double Xmax;
	private double Xmin;
	// ����ά������Ⱥ��ģ
	private int Dim;
	private int NP;
	// ���������뽻�����
	private double F;
	private double CR;
	// ���Է���������������ԣ��������
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
		// ��ʼ��
		double[][] X = m.matrixLinearScalaOpt(mc.randUniformMatrix(NP, Dim), (Xmax-Xmin), Xmin);
//		System.out.println("��ʼ����Ⱥ��");
//		m.printMatrixOmitted(X);
		// ����ѭ��
		double[] fitnessX = new double[NP];
		double fitnessbestX;
		int indexbestX;
		double[] bestX = new double[Dim];
		double[][] V = new double[NP][Dim];
		double[][] U = new double[NP][Dim];
		double[] fitnessU = new double[NP];
		double[] bestfitness = new double[maxGen];
		for (Gen=1; Gen < maxGen; Gen++) {
			// ������Ⱥÿ���������Ӧ��
			for (int i = 0; i < NP; i++) {
				fitnessX[i] = testFun.rosenbrock(X[i]);
			}
			// ȡ����Сֵ���������Ӷ��õ���ǰ���Ž�
			min = new Argmin(fitnessX);
			fitnessbestX = min.getResult();
			indexbestX = min.getIndex();
			bestX = X[indexbestX];
			
			// ���콻��
			V = DEAUtils.mutation(X, bestX, F, mutationStrategy);
			U = DEAUtils.crossover(X, V, CR, crossStrategy);
			// ѡ��
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
			System.out.printf("�� %d ��\n", Gen);
			m.printVectorOmitted(bestX);
			System.out.printf("��ǰ���Ž� %f, \n", fitnessbestX);
			bestfitness[Gen] = fitnessbestX;
		}
		// ��ͼ
//		m.printVector(bestfitness);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DEAExecutor exe = new DEAExecutor();
		exe.exec();
	}

}
