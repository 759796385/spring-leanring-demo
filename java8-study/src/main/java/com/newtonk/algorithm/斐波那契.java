package com.newtonk.algorithm;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/11/4
 */
public class 斐波那契 {
	public static void main(String[] args) {
		System.out.println(jump(3));
	}

	/**
	 * 递归性能差，同个值要重复计算多次。 O（n）解法
	 */
	public static int fibonacci(int n ){
		int[] array = new int[]{0,1};
		if (n < 2) {
			return array[n];
		}
		int one = 	0;
		int two = 1;
		int result = 0;
		for (int i = 0; i < n; i++) {
			result = one+two;
			two = one;
			one = result;
		}
		return result;
	}

	/**
	 * 青蛙跳楼梯
	 * 青蛙跳n阶，一次跳一阶或两阶
	 * 简化成 f(n) = f(n-1)+f(n-1)
	 */
	public static int jump(int n){
		if (n == 1) {
			return 1;
		}
		if (n == 2) {
			return 2;
		}
		return jump(n - 1) + jump(n - 2);
	}
}
