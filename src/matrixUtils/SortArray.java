package matrixUtils;

import java.util.Arrays;

/**
 * 对一维double数组进行排序并获取其排序前的索引
 * @author zhaoke
 *
 */
public class SortArray {

	private double[] array;
	private int[] sortIndex;

	/**
	 * 对一维double数组进行排序并获取其排序前的索引，返回一个排序后的深拷贝对象及其索引
	 * @param array
	 */
	public SortArray(double[] array) {
		// TODO Auto-generated constructor stub
		this.array=array;
		this.sortIndex = new int[array.length];
		for (int i = 0; i < sortIndex.length; i++) {
			sortIndex[i] = i;
		}
		this.bubbleSort(this.array, sortIndex);
	}

	
	
	public double[] getArray() {
		return array;
	}



	public int[] getSortIndex() {
		return sortIndex;
	}



	public void bubbleSort(double[] array2, int[] index){
//		System.out.println("排序前");
//		System.out.println(Arrays.toString(array2));
//		System.out.println(Arrays.toString(index));
		bubbleSort(array2, index, true);
//		System.out.println(Arrays.toString(array2));
//		System.out.println(Arrays.toString(index));
	}

	private void swap(double[] array2, int[] index, int i, int j){
		array2[i] -= array2[j];
		array2[j] += array2[i];
		array2[i] = array2[j] - array2[i];
		
		index[i] -= index[j];
		index[j] += index[i];
		index[i] = index[j] - index[i];
	}
	public void print(double[] array2, int[] index) {
		for (int i = 0; i < array2.length; i++) {
			System.out.print(array2[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < array2.length; i++) {
			System.out.print(index[i] + " ");
		}
		System.out.println();
	}
	public void bubbleSort(double[] array2, int[] index,  boolean asc) {
//		System.out.println("冒泡排序 ( "+ (asc? "升": "降") + "序)");
		boolean exchange=true;
		for(int i=1;i<array2.length&&exchange;i++) {
			exchange = false;
			for(int j=0;j<array2.length-i;j++) {
				if(asc?array2[j]>array2[j+1] : array2[j]< array2[j+1]) {
					swap(array2, index, j, j+1);
					exchange=true;
				}
			}
		}
	}
	
	public void quickSort(double[] keys) {
		quickSort(keys, 0, keys.length-1);
	}
	private void quickSort(double[] keys, int begin, int end) {
		if (begin >= 0 && begin < keys.length && end >= 0 && end < keys.length && begin < end) {
			int i = begin, j = end;
			double vot = keys[i];
			int votIndex = i;
			while (i != j) {
				while(i < j && keys[j] >= vot)j--;
				if(i < j) {
					keys[i] = keys[j];
//					votIndex++;
					sortIndex[j] =i;
					i++;
				}
				while(i < j && keys[i] <= vot)  i++;
				if(i < j) {
					keys[j] = keys[i];
					sortIndex[i] = j;
					j--;
				}
			}
			keys[i] = vot;
			sortIndex[votIndex] = i;
//			sortIndex[i] = votIndex;
//			System.out.println(begin+".."+end+", vot="+vot+" ");
//			SortMethod.print(keys);
			System.out.println(Arrays.toString(keys));
			System.out.println(Arrays.toString(sortIndex));
			quickSort(keys, begin, j-1);
			quickSort(keys, i+1, end);
		}
	}
}
