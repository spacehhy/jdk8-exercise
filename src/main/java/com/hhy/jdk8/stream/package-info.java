package com.hhy.jdk8.stream;

/**
 * 流
 *
 * Collection提供了新的stream()方法
 * 流不存储值,通过管道的方式获取值
 * 本质是函数式的,对流的操作会生成一个结果,不过并不会修改底层的数据源,集合可以作为流的底层数据源
 * 延迟查找,很多流操作(过滤、映射、排序等)都可以延迟实现
 *
 * Java8中的Stream是对集合对象功能的增强,它专注于对集合对象进行各种非常便利、高效的聚合操作,或者大批量数据操作
 * Stream API借助于Lambda表达式,极大地提高了编程效率和程序可读性
 * 提供串行和并行两种模式进行汇聚操作,并发模式能够充分利用多核处理器的优势,使用fork/join并行方式来拆分任务和加速处理过程
 * Stream不是集合元素,它不是数据结构,并不保存数据,它是有关算法和计算的
 * Stream更像一个高级版本的Iterator
 * 原始版本的Iterator,用户只能显式地一个一个遍历元素并对其执行某些操作;高级版本Stream,用户只要给出需要对其包含的元素执行什么操作,
 * 比如"过滤掉长度大于10的字符串"、"获取每个字符串的首字母"等,Stream会隐式地在内部进行遍历,并做出相应的数据转换。
 * Stream就如同一个迭代器(Iterator),单向,不可重复,数据只能遍历一次
 * 和迭代器不同的是,Stream可以并行化操作,迭代器只能命令式地、串行化操作
 * 当使用串行方式去遍历时,每个item读完后再读下一个item
 * 使用并行去遍历时,数据会被分成多个段,其中每一个都在『不同的线程』中处理,然后将结果一起输出
 * Stream的并行操作依赖于Java7中引入的Fork/join框架
 *
 * Stream构成
 * 获取一个数据源(source) -> 数据转换 -> 执行操作获取想要的结果
 * 每次转换原有Stream对象不改变,返回一个新的Stream对象(可以有多次转换),这就允许对其操作可以像链条一样排列,变成一个管道(pipeline)
 *
 * Stream -> Source
 *              |
 *              v
 *           Transforming values
 *              |
 *              v
 *           Operations
 *
 * Stream操作类型
 * Intermediate: 一个流可以后面跟随零个或多个Intermediate操作。其目的主要是打开流，做出某种程度的数据映射/过滤,然后返回一个新的流,
 * 交给下一个流操作使用,这类操作都是延时的(lazy),就是说,仅仅调用到这类方法,并没有真正开始流的遍历。
 *
 * Terminal： 一个流只能有一个Terminal操作,当这个操作执行后,流就被使用"光"了,无法再被操作。所以这必定是流的最后一个操作。Terminal
 * 操作的执行,才会真正开始流的遍历,并且会生成一个结果。
 *
 * Stream的效率
 * 多个中间操作会导致循环集合多次么?
 * Stream使用
 * 对Stream的使用就是实现一个filter-map-reduce的过程,最终产生一个结果
 * 对于原生数据类型,提供了IntStream、LongStream与DoubleStream
 * 当然我们也可以用Stream<Integer>、Stream<Long>、Stream<Double>,但是boxing和unboxing会很耗时,所以特别为这三种基本数值型提供了
 * 对应的Stream
 *
 * Stream操作
 * Intermediate:
 * map(mapToInt,flatMap等)、filter、distinct、sorted、peek、limit、skip、parallel、sequential、unordered
 *
 * Terminal：
 * forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、noneMatch、findFirst、findAny、iterator
 *
 * 串行流: stream
 * 并行流: parallelStream
 *
 *
 * Stream 流
 *
 * 流由3部分构成:
 *
 * 1.源
 * 2.零个或多个中间操作
 * 3.终止操作
 *
 * 流操作的分类:
 * 1.惰性求值
 * 2.及早求值
 *
 * stream.xxx().yyy().zzz().count();
 *       \________________/ \_____/
 *               \/           \/
 *            惰性求值      及早求值
 *
 *
 * 1.流的创建方式,主要有三种:
 * Stream.of(T... values)
 * Arrays.stream(T[] array)
 * list.stream()
 *
 * map: 映射
 * filter: 过滤
 * reduce: 汇聚??
 *
 *
 */