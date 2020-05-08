package com.newtonk.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.collect.Lists;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/11/4
 */
public class 旋转数组 {
	public static void main(String[] args) {
		int[] array = new int[]{3,4,5,11,22,33,44,0,1,2};
		array = reverseArray(array);
		System.out.println("sa");
	}

	public static int[] reverseArray(int[] array){
		if(array.length == 0){
			return array;
		}
		int l = 0;
		int r = array.length-1;
		while(array[l]>= array[r]){
			if (r - l == 1) {
				break;
			}
			int mid = (l + r) / 2;
			if(array[mid]>=array[r]){
				l = mid;
			}else{
				r = mid;
			}
		}
		//挪位置
		int[] result = new int[array.length];
		int i = 0;
		for (;r <=array.length-1;r++ ){
			result[i++] = array[r];
		}
		for(int h = 0;h<=l;h++){
			result[i++] = array[h];
		}
		return result;
	}
}
