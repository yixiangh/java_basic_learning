package com.example.javaLambda;

import com.example.entity.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的 Iterator。
 * 原始版本的 Iterator，用户只能显式地一个一个遍历元素并对其执行某些操作；高级版本的 Stream，用户只要给出需要对其包含的元素执行什么
 * 操作。
 * 比如 “过滤掉长度大于 10 的字符串”、“获取每个字符串的首字母”等，Stream 会隐式地在内部进行遍历，做出相应的数据转换。
 *
 * ##  Stream 就如同一个迭代器（Iterator），单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。
 * ##  但是Stream 的另外一大特点是，数据源本身可以是无限的。
 *
 * 而和迭代器又不同的是，Stream 可以并行化操作，迭代器只能命令式地、串行化操作。顾名思义，当使用串行方式去遍历时，每个 item 读完后
 * 再读下一个 item。
 * 而使用并行去遍历时，数据会被分成多个段，其中每一个都在不同的线程中处理，然后将结果一起输出。
 * Stream 的并行操作依赖于 Java7 中引入的 Fork/Join 框架（JSR166y）来拆分任务和加速处理过程。
 *
 * // 流的操作类型分为两种：
 * Intermediate：一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，
 * 然后返回一个新的流，交给下一个操作使用。 * 这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
 *
 * Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
 * Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 (副作用)side effect。
 *
 * #还有一种操作被称为 short-circuiting。用以指：
 * 对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
 * 对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
 *
 * 当把一个数据结构包装成 Stream 后，就要开始对里面的元素进行各类操作了。
 * 常见的操作可以归类如下。
 * Intermediate：
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 *
 * Terminal：
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 *
 * Short-circuiting：
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 * @Author: HYX
 * @Date: 2020/7/23 16:27
 */
public class StreamTest {

    public static void main(String[] args) {
        objectStream();
    }

    public static void objectStream()
    {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentList.add(new Student(i,"张"+i,i+10,"n"));
        }
        List<Student> collect = studentList.stream().filter(student -> student.getId() % 2 == 0).collect(Collectors.toList());
        long count = studentList.stream().count();
        System.out.println(count);

    }



}
