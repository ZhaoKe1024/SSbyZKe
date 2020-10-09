package matrixUtils;

import java.util.Arrays;

import ssamodel.SSAExecutor;
import testFunctionUtils.Functions;

public class SSAExample1 {

	private int searchAgentsNo;
	private int maxIteration;
	private Functions fun;

	public SSAExample1() {
		// TODO Auto-generated constructor stub
		this.init();
	}

	public void init() {
		this.searchAgentsNo = 50;
		this.maxIteration = 1000;
//		this.fun = new Functions("GoldsteinPrice");
//		this.fun = new Functions("Ackleys");
		this.fun = new Functions("sphere");
		SSAExecutor ssa = new SSAExecutor();
		double[] result = ssa.mainProcess(searchAgentsNo, maxIteration, fun.getLb(), fun.getUb(), fun.getDimension());
		new MatOpt().printVector(result);
	}

	public void setSearchAgentsNo(int searchAgentsNo) {
		this.searchAgentsNo = searchAgentsNo;
	}

	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}
	
	public void setFun(Functions fun) {
		this.fun = fun;
	}

	public Functions getFun() {
		return fun;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SSAExample1 ssae = new SSAExample1();
		System.out.println("½âµÄÎ¬¶È£º"+ssae.searchAgentsNo);
	}

}
