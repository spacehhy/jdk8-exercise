package com.hhy.jdk8.stream2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 7.流(Stream)源码分析
 */
public class StreamTest3 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        /*
         * list.stream() -> StreamSupport.stream(spliterator(), false);
         * 返回一个串行流,将当前集合作为流的源
         * 当spliterator()不能返回一个spliterator[分割迭代器]要么是[IMMUTABLE]不可变的,要么是[CONCURRENT]并行的,
         * 或者是[late-binding]延迟绑定的其中一个时,该方法应该被重写
         * 默认的实现会创建一个串行流,会从集合的spliterator里创建一个串行流,返回的就是一个针对的是当前集合中元素的串行流
         * Collection#spliterator():
         * 针对于集合中的元素创建一个分割迭代器(spliterator),实现应该对于spliterator所返回来的特性值进行文档化的说明,并记录下来
         * 这些特性并不要求去报告,如果spliterator报告了一个spliterator#SIZED[固定大小的]并且集合不包含任何元素.
         * 默认事项应该被子类重写,重写之后返回一个高效的分割迭代器,为了保留期望的流的steam()方法和parallelStream()方法延迟行为
         * spliterator分割迭代器要么拥有IMMUTABLE[不可变的]要么是CONCURRENT[并发的]流才是late-binding[延迟加载的],
         * 如果上面都无法实现,那么重写的类应该去描述spliterator文档化的绑定策略和结构上的阻碍,而且必须重写stream()和
         * parallelStream()方法来创建流,并且使用spliterator的Supplier来创建.
         * 这些要求确保了由stream()方法及parallelStream()方法生成的流,它会最终反映出集合的内容,从终止流操作开始发起的时候,就能
         * 反应出流的内容
         * 默认的实现会创建一个延迟绑定的spliterator,是从集合的iterator迭代器创建一个延迟绑定的分割迭代器,所创建出来的分割迭代器
         * 会继承集合迭代器的快速失败[fail-fast]属性,所创建的spliterator会拥有spliterator#SIZED这样的固定大小的特性值
         * 所创建的spliterator还会额外的增加一个SubSized[子大小]特性(分割迭代器进行分割之后,会生成若干个块,每个块的大小是确定的,
         * 每个块的大小不会发生变化,称之为子大小[SubSized])
         * 如果分割迭代器没有cover任何元素[即是空的],那么额外的特性值的报告,除了SIZED和SubSized特性之外,其他特性并不会帮助客户端
         * 去控制或者简化一些计算,然而可以有助于不可变的,空的分割迭代器实例的一个共享的使用,spliterator实例请参见emptySpliterator
         * 对于空的集合来说,可以帮助客户端确定spliterator是否没有元素
         */

        System.out.println("-------------------");

        //map() 不添加终止操作forEach(System.out::println) 流将不会进行计算 [惰性求值,延迟求值]
        //map()方法中间操作为何不输出任何结果?
        //map()方法本身仅仅就是返回了StatelessOp这样一个对象,而StatelessOp的构造方法就是完成了一些成员变量的赋值
        //而opWrapSink方法根本没有执行,因为只有真正调用它时才会执行,所以不管中间有多少操作,操作本身返回诸如StatelessOp这样的对象
        //它里面的方法都是不会得到执行的,既然都不会执行,显然map()方法不会输出
        list.stream().map(item -> item + "_abc").forEach(System.out::println);

        //流遇到终止操作才会真正的被触发,真正的得到执行和计算
        //forEach 两种实现 Head in ReferencePipeline.forEach()[针对流源的简化操作] 和
        //ReferencePipeline.forEach()[不是针对流源调用forEach,中间有任何一个中间操作,紧接着调用forEach方法]
        //public void forEach(Consumer<? super P_OUT> action) {
        //    evaluate(ForEachOps.makeRef(action, false));
        //}

        //源码分析 从"stream()"开始  stream() forEach()
        //1.分析入口1: stream()
        Stream<String> stream = list.stream();
        //2.forEach() 实现有两个 Head in ReferencePipeline.forEach() 和 ReferencePipeline.forEach()
        //List<String> list = Arrays.asList("hello", "world", "hello world","welcome","person","student");
        //list.stream().parallel().forEach(System.out::println);//非顺序执行 每次结果执行顺序位置
        //list.stream().forEach(System.out::println);//Head in ReferencePipeline.forEach()
        //list.stream().map(item -> item).forEach(System.out::println);//ReferencePipeline.forEach()

        //IteratorSpliterator.forEachRemaining(Consumer<? super T> action)处打断点,发现程序不走该方法
        //System.out.println(list.getClass()); //java.util.Arrays$ArrayList
        //list.stream().forEach(System.out::println);

        // stream() -> StreamSupport.stream(spliterator(), false); ->
        // default Spliterator<E> spliterator() {return Spliterators.spliterator(this, 0);} ->
        // ArrayList in Arrays #spliterator() ->
        // Spliterators#spliterator return new ArraySpliterator<>(Objects.requireNonNull(array),additionalCharacteristics);
        //

        //动态调试 111 222处打断点
        //Stream<String> stream = list.stream();

        System.out.println("1111");

        Stream<String> stream1 = stream.map(item -> item + "_abc");

        System.out.println("2222");

        stream1.forEach(System.out::println);

        System.out.println("------");

        //获取流对象,没有中间操作,直接返回TerminalOp终止操作
        list.stream().forEach(System.out::println);

        System.out.println("------");

        //Iterator 将传统命名式遍历转化为函数式遍历 单纯遍历效率更高
        list.forEach(System.out::println);
    }
}
