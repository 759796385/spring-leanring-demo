package com.newtonk.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Optional<Integer> t =nums.stream().filter(Objects::nonNull).findAny();
        System.out.println(t.get());

        Map<String, Object> wheelProperties = new HashMap<>();
        wheelProperties.put("HasType.PROPERTY", "wheel");
        wheelProperties.put("HasModel.PROPERTY", "15C");
        wheelProperties.put("HasPrice.PROPERTY", 100L);

        Map<String, Object> doorProperties = new HashMap<>();
        doorProperties.put("HasType.PROPERTY", "door");
        doorProperties.put("HasModel.PROPERTY", "Lambo");
        doorProperties.put("HasPrice.PROPERTY", 300L);
        List<Map<String, Object>> a = Arrays.asList(wheelProperties, doorProperties);
        Optional<List<Map<String, Object>>> result =Stream.of(a).filter(Objects::nonNull).findAny();
        System.out.println(result.get());
    }
}
