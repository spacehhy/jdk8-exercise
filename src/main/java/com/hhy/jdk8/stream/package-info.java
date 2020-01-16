package com.hhy.jdk8.stream;

/**
 * 流
 * stream（和IO流没有关系）是针对Collection的增强。它专注于对集合对象进行各种非常便利、高效的聚合操作。
 * 配合lambda表达式能提升编码效率和程序的可读性
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
 *              ↓
 *           Transforming values
 *              ↓
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
 * map: 归类，结果一般是一组数据
 * list.stream().map( x-> x.getSalary() )   //将person中salary归类成一组数据
 *
 * filter: 根据条件过滤
 * list.stream().filter( p-> 1==p.getGender() )
 *
 * sorted: 排序
 * list.stream().sorted( (p1,p2)-> p1.getNickName().compareTo(p2.getNickName()) )
 *
 * limit: 取集合中的前多少个元素
 * list.stream().filter( p -> 1==p.getGender() ).limit(5)
 *
 *
 *
 * Terminal：
 * forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、noneMatch、findFirst、findAny、iterator
 *
 * reduce: 用来计算结果，结果是一个数值
 * Integer sum = list.stream().map( x->x.getSalary() ).reduce(0, (x,y) -> x+y );   //求总和，其中0是基准
 *
 * collect: 将处理后的结果输出到新的集合中,如list，set，map等 ;或者返回处理的结果，如求集合的个数，平均值等
 * 『toList』
 * list.stream().filter().collect(Collectors.toList());
 * 『toSet』
 * list.stream().filter().collect(Collectors.toSet());
 * 『count』
 * Long count = list.stream().filter( p -> p.getGender()==0 ).collect(Collectors.counting());
 * 『summingDouble』
 * Double sumSalary = list.stream().collect(Collectors.summingDouble( x->x.getSalary() ));
 * 『toMap』
 * Map map = list.stream().filter( p -> 1==p.getGender() ).limit(2).collect(Collectors.toMap(Person::getName,Person::getNickName));  // key=name ,value=nickname
 *     map = list.stream().collect(Collectors.toMap( x->x.getNickName(),Function.identity()));    //key=nickname,  value = key对应的对象；注意key重复会抛出异常
 *     map = list.stream().collect(Collectors.toMap( x->x.getNickName(),Function.identity(),(oldValue,newValue)->newValue));   //key重复， 取新的key
 *     map = list.stream().collect(Collectors.toMap( x->x.getNickName(),Function.identity(),(oldValue,newValue)->oldValue));  //key重复， 取旧的key
 *
 *
 * min: 求集合中最小对象
 * Object obj = list.stream().min((p1,p2)-> p1.getSalary()-p2.getSalary() ).get();
 *
 * max: 求集合最大对象
 * Object obj = list.stream().max((p1,p2)-> p1.getSalary()-p2.getSalary() ).get();
 *
 *
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
 *               ↓             ↓
 *            惰性求值       及早求值
 *
 *
 * 1.流的创建方式,主要有三种:
 * Stream.of(T... values)
 * Arrays.stream(T[] array)
 * list.stream()
 *
 * map: 映射
 * filter: 过滤
 * reduce: 汇聚
 * 查找 映射 过滤 排序 分组 分区
 *
 * 练习:
 * Stream.iterator(1,item -> item +2).limit(6);
 * 1 3 5 7 9 11
 * 6 10 14 18 22
 * 14 + 18 = 32
 * 找出该流中大于2的元素,然后将每个元素乘以2,然后忽略掉流中的前两个元素,然后再取流中的前两个元素,最后求出流中元素总和
 * int sum = stream.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).sum();
 *
 * 我们发现Stream的链式调用和sql语句有着异曲同工的
 *
 * select name from student where age > 20 and address = 'beijing' order by age desc;
 * 描述性语言:
 * students.stream().filter(student -> student.getAge > 20).filter(student -> "beijing".equals(student.getAddress())).
 * sorted(Comparator.comparingInt(Student::getAge)).forEach(student -> System.out.println(student.getMame));
 *    ↑
 * 内部迭代: 流与coder编写的指令性代码融合在一起,当遇到终止操作时统一执行函数式指令代码;操作的是流
 *
 * 外部迭代: (for/foreach/iterator)[foreach与iterator本质上是一会事儿,foreach是jdk语法糖];操作的是集合
 *    ↓
 * List<Student> list = new ArrayList<>();
 *
 * for (int i = 0; i < students.size(); ++i) {
 *      Student student = students.get(i);
 *      if(student.getAge > 20 && "beijing".equals(student.getAddress()){
 *          list.add(student);
 *      }
 * }
 *
 * Collections.sorted(list,Comparator()...);
 *
 * for(Student student : list){
 *      System.out.println(student.getName);
 * }
 *
 * 集合关注的是数据与数据存储本身;
 * 流关注的则是对数据的计算。
 *
 * 流与迭代器类似的一点是：流是无法重复使用或消费的。
 *
 * 中间操作都会返回一个Stream对象,比如说返回Stream<Student>,Stream<Integer>,Stream<String>
 * 终止操作则不会返回Stream类型,可能不返回值,也可能返回其他类型的单个值。
 *
 */