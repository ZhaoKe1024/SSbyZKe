package diffEvolutionModel;

import matrixUtils.Argmin;
import matrixUtils.MatOpt;
import matrixUtils.MatrixCreateUtils;
import plotUtils.ResultPlot;
import testFunctionUtils.Functions;

/**
 * ��ֽ����㷨differential evolution algorithm
 * @author zhaoke
 *	��ֽ����㷨Ҳ��һ��Ⱥ����Ӧ�����㷨��
 *1. �������Ӿ��ȷֲ��ĳ�ʼȺ����Ϊ��ʼ�⣬Ⱥ����n�����壬ÿ��������һ����m��ά�ȵ�����
 *2. ÿ�ε�����ͨ�����죬��������½⣬�½�;ɽ�ѡ����Ӧ�ȸ��õ�һ����Ϊ�½�
 *3. �ﵽһ����������������Ѱ���������Ž�
 */
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
	// ���Է���ѡ�񣬱������ѡ�񣬽������ѡ��
	private int index;
	private int mutationStrategy;
	private int crossStrategy;
	
	/**
	 * ���캯������ʼ���㷨����
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

		this.index=3;  // ���������ʱû�õ�
		this.mutationStrategy=2;
		this.crossStrategy=1;
	}

	/**
	 * ���ؽ���������Ž�
	 * @return
	 */
	private double[] exec() {
		/*
		 *���󴴽�����
		 *�����������
		 *���Ժ���
		 *����Сֵ���������Ķ��� 
		 */
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		Functions testFun = new Functions("rosenBrock");
		Argmin min = null;
		// ��ʼ��
		double[][] X = m.matrixLinearScalaOpt(mc.randUniformMatrix(NP, Dim), (Xmax-Xmin), Xmin);
		System.out.println("��ʼ����Ⱥ��");
		m.printMatrixOmitted(X);
		System.out.println("��ʼ����Ⱥend\n===========================\n");
		/* ����ѭ��
		 * ��ʼ��������ʹ�õĲ���
		 */
		double[] fitnessX = new double[NP];  // ÿһ���������Ӧ�ȣ�NP������
		double fitnessbestX;  // �����Ӧ�ȣ��������С�Ӿ����������
		int indexbestX;  // �����Ӧ�ȶ�Ӧ�������±�
		double[] bestX = new double[Dim];  // ���Ž⣬��������Ӧ�ȶ�Ӧ�ĸ��壬����Dim��ά��
		double[][] V = new double[NP][Dim];  // ����Ⱥ��
		double[][] U = new double[NP][Dim];  // ���������Ⱥ��
		double[] fitnessU = new double[NP];  // �������Ⱥ�����Ӧ�ȣ�NP������
		double[] bestfitness = new double[maxGen];  // ÿ�ε����������������Ӧ�ȣ����ڻ�ͼ�ã�һ��maxGen��
		for (Gen=0; Gen < maxGen; Gen++) {
			// ������Ⱥÿ���������Ӧ��
			for (int i = 0; i < NP; i++) {
				fitnessX[i] = Functions.rosenbrock(X[i]);
			}
			// ȡ����Сֵ���������Ӷ��õ���ǰ���Ž�
			min = new Argmin(fitnessX);
			fitnessbestX = min.getResult();
			indexbestX = min.getIndex();
			bestX = X[indexbestX];

			// ������� 
			V = DEAUtils.mutation(X, bestX, F, mutationStrategy);
			// �������
			U = DEAUtils.crossover(X, V, CR, crossStrategy);
			// ѡ��
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
			System.out.printf("�� %d ��\n", Gen);
			m.printVectorOmitted(bestX);  // �ڿ���̨��ӡ����
			System.out.printf("��ǰ���Ž� %f, \n", fitnessbestX);
			bestfitness[Gen] = fitnessbestX;
		}

		return bestX;
		// ��ͼ
//		m.printVector(bestfitness);
//		System.out.println("����أ�");
//		m.printMatrix(X);
//		new ResultPlot(m.subArray(bestfitness, 0, bestfitness.length));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DEAExecutor exe = new DEAExecutor();
		exe.exec();
	}

}
