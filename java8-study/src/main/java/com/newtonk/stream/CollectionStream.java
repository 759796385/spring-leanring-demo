package com.newtonk.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/30 0030
 */
public class CollectionStream {
    public static void main(String[] args) {
        CollectionStream a =new CollectionStream();
        a.reduceTest();
    }

    public void reduceTest(){
        List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        System.out.println("ints sum is:" + ints.stream().reduce((sum, item) -> sum + item).get());

        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        List<Integer> numsWithoutNull = nums.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
