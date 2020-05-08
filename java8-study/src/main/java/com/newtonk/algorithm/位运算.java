package com.newtonk.algorithm;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/11/4
 */
public class 位运算 {
	public static void main(String[] args) {
		System.out.println(oneNum(15));
	}

	/**
	 * 求数字二进制一的数量
	 */
	public static int oneNum(int n){
		int flag = 1;
		int count = 0;
		while(flag<= n){
			if((n & flag) ==flag){
				count ++;
			}
			flag = flag<<1;
		}
		return count;
	}
}
