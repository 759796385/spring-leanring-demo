package com.newtonk.stream;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/4/29 0029
 */
public class StreamTest {
    public static void main(String[] args) {
        StreamTest a = new StreamTest();
        a.reduceTest();

    }
    //Stream主要分中间操作和结果操作
    public void a(){
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add("2");
        Stream<Object> stream =list.stream();
        stream.forEach(System.out::println);
    }

    public void filterTest(){
        //中间操作
        List<Integer> list = new ArrayList<>();
        for (int i =0 ;i<8;i++) {
            list.add(i);
        }
        list.stream().filter((item) -> item> 4).forEach(System.out::print);
    }

    public void sortTest(){
        //中间操作
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i =0 ;i<8;i++) {
            list.add(random.nextInt(100));
        }

        //sort聚合操作会返回一个按自然排序的stream
        list.stream().sorted().forEach(System.out::println);
        //也可自行实现一个逆向排序
        list.stream().sorted((left,right) -> right - left).forEach(System.out::println);
    }

    void mapTest(){
        //中间操作
        List<String> list = new ArrayList<>();
        for (int i =77 ;i<77+8;i++) {
            list.add( "" +(char)i);
        }
        //map操作能够根据提供的函数转换每个对象。新生成的Stream只包含转换生成的元素
        list.stream().map(String::toLowerCase).sorted().forEach(System.out::println);
    }

    void matchTest(){
        //用 自定义函数来匹配集合中的元素是否匹配，返回boolean
        List<String> list = new ArrayList<>();
        for (int i =77 ;i<77+8;i++) {
            list.add( "" +(char)i);
        }
        boolean startWithOne = list.stream().allMatch((item) -> item.startsWith("n"));
        System.out.println(startWithOne); //false

        boolean startWithAll = list.stream().anyMatch((item) -> item.startsWith("N"));
        System.out.println(startWithAll);// true

        boolean nonMatch = list.stream().noneMatch((item) -> item.startsWith("J"));
        System.out.println(nonMatch);   //true
    }

    void countTest(){
        //对stream结果操作。返回long
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i =0 ;i<8;i++) {
            list.add(random.nextInt(100));
        }
        long gt50 = list.stream().filter((item) -> item>50).count();
        System.out.println(gt50);
    }

    void reduceTest(){
        //结果操作，根据提供函数对结果进行汇聚操作，结果是Optional函数持有
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i =0 ;i<8;i++) {
            list.add(random.nextInt(100));
        }
        System.out.println(list);
        //将结果大于50的数用#连接起来
        //reduce方法接受一个函数，这个函数有两个参数，第一个参数是上次函数执行的返回值（也称为中间结果）
        // ，第二个参数是stream中的元素，这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数。
        Optional<Integer> reduced = list.stream().filter((item) -> item>50 ).sorted().reduce((sum,item) -> sum+item);
        reduced.ifPresent(System.out::println);


        //reduce提供一个初始值，在此基础上进行操作
        List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        System.out.println("ints sum is:" + ints.stream().reduce(100, (sum, item) -> sum + item));

    }


    //并发流
    void bingfaTest(){
        //上面的流都是串行得到结果的，流也可以并行计算得到。
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        //串行排序
//        long t0 = System.nanoTime();
//
//        long count = values.stream().sorted().count();
//        System.out.println(count);
//
//        long t1 = System.nanoTime();
//
//        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
//        System.out.println(String.format("sequential sort took: %d ms", millis)); //sequential sort took: 625 ms
        //并行排序
        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis)); //parallel sort took: 401 ms

        /* 串行的stream性能比直接的代码实现foreach要慢。 并行多核机器能比代码实现快 */
    }

    /* Map接口没有直接提供stream（）方法，但是 */
    void hashmapTest(){
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

//        map.forEach((k,v) -> {
//            System.out.println(k);
//            System.out.println(v);
//        });
        /*
        * if (map.get(key) != null) {
         *     V oldValue = map.get(key);
         *     V newValue = remappingFunction.apply(key, oldValue);
         *     if (newValue != null)
         *         map.put(key, newValue);
         *     else
         *         map.remove(key);
         * }
         * */
        //给这个key换一个新的值
        System.out.println(map.get(3)); //val3
        map.computeIfPresent(3,(k,v) -> k+v);
        System.out.println(map.get(3)); //3val3

        System.out.println(map.get(4)); //val4
        map.computeIfPresent(4,(k,v) -> null);
        System.out.println(map.containsKey(4)); //false

        map.computeIfAbsent(24,num -> "val"+ num);
        map.containsKey(24); //true

        map.computeIfAbsent(4, num -> "bam");
        System.out.println(map.get(4)); //bam


        //map能够移除匹配的entry
        map.remove(4,"sasa");   //移除不成功
        System.out.println(map.get(4));
        map.remove(4,"bam");
        System.out.println(map.get(4));//null

        System.out.println(map.get(42));
        System.out.println(map.getOrDefault(42,"没找到"));

        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));             // val9val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);     //      val9concat
    }
}
