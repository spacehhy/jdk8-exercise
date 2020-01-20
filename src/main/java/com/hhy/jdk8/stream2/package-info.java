package com.hhy.jdk8.stream2;

/**
 * Collect:[收集器]
 * 1.collect: 收集器
 * 2.Collector作为collect方法的参数
 * 3.public interface Collector<T, A, R>
 *   Collector本身是一个接口,它是一个可变的汇聚操作,将输入元素累积到一个可变的结果容器中;
 *   它会在所有元素都处理完毕后,将累积的结果转换为一个最终的表示(这是一个可选操作[finisher]);
 *   它支持串行与并行两种方法执行。[设计线程切换,并不是说并行一定快于串行]
 * 4.Collectors本身提供了关于Collector的常见汇聚实现; Collectors本身实际上是一个工厂。
 * 5.为了确保串行与并行操作结果的等价性,Collector函数需要满足两个条件: identity(同一性)与associativity(结合性)
 * 6.同一性 identity
 *   {@code a} must  be equivalent to {@code combiner.apply(a, supplier.get())}.
 *   a == combiner.apply(a, supplier.get())
 *   a: 某一个线程执行分支得到的一个部分结果
 *   (List<String> list1, List<String> list2) -> {list1.addAll(list2);return list1;}
 *   list2为一个空的结果容器,所以等式成立
 *   结合性 associativity
 *   确保串行返回的结果与并行返回的结果一致
 *   java8存在多种汇聚操作, collectors#reducing/Stream#reduce
 *   reduce: 汇聚; collect: 收集器;用法上类似,本质上有很大区别
 *   reduce要求不可变性,处理的对象是不可变的,每一次要生成新的结果
 *   collect是可变行为,可变结果容器;reduce并行时可能发生错乱
 * 7.函数式编程最大的特点: 表示做什么,而不是如何做。
 *
 *
 *
 * 例: 假如combiner函数,有四个线程同时去执行,那么就会生成4个部分结果
 * 1,2,3,4
 * 1,2 -> 5   [新结果容器:  1和2中所有元素添加到新创建集合5(新结果容器);最后返回新创建集合]
 * 5,3 -> 6
 * 6,4 -> 7
 *
 * 再假如 1,2 -> 1 [折叠:  2所有元素添加到1当中;最后返回结果容器1]
 *
 * Collector由四个函数组成:
 * [提供器]supplier: 用于提供可变结果容器
 * [累加器]accumulator: 用于往可变结果容器不断的累加流当中迭代遍历的每一个元素
 * [结合器]combiner: 用于线程并发,把多个部分结果合并成最终结果
 * [完成器]finisher: 可选操作,不提供jdk也会提供默认的;将累积的中间结果转换成最终表示
 * <li>creation of a new result container (supplier)</li>
 * <li>incorporating a new data element into a result container (accumulator)</li>
 * <li>combining two result containers into one (combiner)</li>
 * <li>performing an optional final transform on the container (finisher)</li>
 *
 * Collectors also have a set of characteristics
 * Collectors还有一个特征集合
 * CONCURRENT 并发  UNORDERED 无序  IDENTITY_FINISH 同一性[可进行强制类型转换]
 * CONCURRENT与UNORDERED必须一起使用;CONCURRENT对于有序的容器比如list是无法使用的
 *
 *
 *
 * <p>A sequential implementation of a reduction using a collector would
 * create a single result container using the supplier function, and invoke the
 * accumulator function once for each input element.  A parallel implementation
 * would partition the input, create a result container for each partition,
 * accumulate the contents of each partition into a sub-result for that partition,
 * and then use the combiner function to merge the sub-results into a combined
 * result.
 * [串行创建一个唯一结果容器累加每一个元素,并行将输入分区,每一个分区一个结果容器,每个分区的结果容器包含一个子结果,最后合并所有子结果返回一个结果]
 * ↓↓↓↓↓↓↓
 *
 * 例: 假如combiner函数,有四个线程同时去执行,那么就会生成4个部分结果
 * 1,2,3,4
 * 1,2 -> 5   [新结果容器:  1和2中所有元素添加到新创建集合5(新结果容器);最后返回新创建集合]
 * 5,3 -> 6
 * 6,4 -> 7
 *
 * 再假如 1,2 -> 1 [折叠:  2所有元素添加到1当中;最后返回结果容器1]
 *
 * 为了保证串行并行结果一致,需要满足两个约束: identity(同一性)、associativity(关联性,结合性)
 *
 * Collectors工厂类
 * characteristics: 几种类型
 * CH_CONCURRENT_ID: [CONCURRENT,UNORDERED,IDENTITY_FINISH];
 * CH_CONCURRENT_NOID: [CONCURRENT,UNORDERED] 并发,无序
 * CH_ID: [IDENTITY_FINISH]  中间结果类型与最终结果类型一致，强制转换中间结果类型为最终结果类型，不执行finisher方法
 * CH_UNORDERED_ID: [UNORDERED,IDENTITY_FINISH]
 * CH_NOID: []
 *
 * 对于Collectors静态工厂类来说,其实现一共分为两种情况:
 *
 * 1.通过CollectorImpl来实现。
 * 2.通过reducing方法来实现；reducing本身又是CollectorImpl来实现的。
 *
 * mapping方法,将收集器映射转换类型
 * collectingAndThen 收集后转换成不可变列表;为了执行finisher方法,先去除特性集合中的IDENTITY_FINISH。
 *
 *
 *
 *
 */