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
	 * �������
	 * @param X Ⱥ�����
	 * @param bestX ���Ÿ�������
	 * @param F ������
	 * @param mutationStrategy �������ѡ��
	 * @return ������Ⱥ��
	 */
	static double[][] mutation(double[][] X, double[] bestX, double F, int mutationStrategy) {
		// TODO Auto-generated method stub
		// ���󴴽����������
		MatrixCreateUtils mc = new MatrixCreateUtils();
		MatOpt m = new MatOpt();
		
		int NP = X.length;  // ������
		int Dim = X[0].length;  // ����������ά��
		// ��ʱ����
		int nrandI = 5;  // ����������������������������ά������
		int[] r = new int[nrandI];
		int[] equalr = null;
		int equali=0;
		int equalall=0;
		double[][] V = new double[NP][Dim];
		// ÿһ��������б������
		for (int i = 0; i < NP; i++) {
//			System.out.printf("���죺 %d/%d\n", i, NP);
			r = mc.randi(nrandI, 1, NP);  // ��ʼ��һ��1��NP�е������������
//			m.printVector(r);
			equalr = new int[nrandI];  // ��¼����rÿ��ά��Ԫ��ֵ���ڸ������еĳ��ִ���
			// �������
			for (int j = 0; j < nrandI; j++) {
				equalr[j] = 0;
				for (int k = 0; k < nrandI; k++) {  // ͳ��һ������ÿһ��ά�ȵ���ֵ���ֵĴ���
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
//			System.out.println("i="+i+", ��ͬԪ����"+(equalall-nrandI)+"��");
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
//				System.out.println("��ͬԪ����"+(equalall-5)+"��");
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
	 * 	��ֽ����㷨�Ľ������
	 * @param X ���ε�����ԭʼ��Ⱥ
	 * @param V ���ε����ı��� ��Ⱥ
	 * @param CR ������
	 * @param crossStrategy
	 * @return ��������Ⱥ
	 */
	static double[][] crossover(double[][] X, double[][] V, double CR, int crossStrategy) {
		int NP = X.length;
		int Dim = X[0].length;
		double[][] U = new double[NP][Dim];

		Random rand = new Random();
		double k;
		switch (crossStrategy) {
			case 1:
				int jRand=0;  // �������ȷ�����������ȫ����
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
