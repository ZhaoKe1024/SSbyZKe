package matrixUtils;

/**
 * get the minimum and its index
 * ͨ���ö�����Ի�ȡһ��һά�������Сֵ��������
 * @author zhaoke
 *
 */
public class Argmin {

	private double result;
	private int index;
	public Argmin(double[] array) {
		// TODO Auto-generated constructor stub
		result = array[0];
		index = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] < result) {
				result = array[i];
				index = i;
			}
		}
	}
	public double getResult() {
		return result;
	}
	public int getIndex() {
		return index;
	}
	

}
