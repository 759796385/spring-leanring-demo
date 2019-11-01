package com.newtonk.algorithm;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/10/23
 */
public class sqrt {
	public static void main(String[] args) {
		//已知 sqrt (2)约等于 1.414，要求不用数学库，求 sqrt (2)精确到小数点后 10 位。
		//x^2 = 2 求x精确到十位
		//思维：二分法，从1.4 到1.5之间，不断二分，直到高低位差值小于十位
		double cha = 0.0000000001;
		double high = 1.5;
		double low = 1.4;
		double mid = (low+high)/2;

		while (high - low > cha) {
			if (mid * mid < 2) {
				low = mid;
			}
			else {
				high = mid;
			}
			mid = (low+high)/2;
		}

		System.out.println(mid);
	}
}
