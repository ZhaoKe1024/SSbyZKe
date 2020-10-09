package matrixUtils;
/**
 * get the maximum and its index
 * 通过该对象可以获取一个一维数组的最大值及其索引
 * @author zhaoke
 *
 */
public class ArgMax {

	private double result;
	private int index;
	
	public ArgMax(double[] array) {
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		result = array[0];
		index = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > result) {
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
