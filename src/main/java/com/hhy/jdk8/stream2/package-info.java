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
 *                              源码分析
 * Collection: 集合父接口 衍生出 list set
 * list.stream(); -> Collection.stream()
 * @since 1.8
 * //接口中的默认方法,为了保证兼容性
 * //将当前集合作为源,返回一个串行流
 * //当方法不能返回分割迭代器 不可变的[IMMUTABLE]/并行的[CONCURRENT]/延迟绑定的[late-binding] 三种类型其中一个
 * //这个方法应该被重写
 * //默认的实现会从集合的spliterator当中创建一个串行流,返回一个针对当前集合元素的串行流
 * default Stream<E> stream() {
 *   return StreamSupport.stream(spliterator(), false);
 * }
 *
 * spliterator: 分割迭代器
 * @since 1.8
 * //this表示当前集合对象
 * //针对于集合的元素创建一个分割迭代器,实现应该对spliterator返回的特性值做一个文档化的说明,
 * //这种特性值并不要求报告:如果这spliterator报告了固定大小(Spliterator#SIZED)并且这个集合不包含任何元素
 * //默认的实现应该被子类重写,重写之后返回一个效率更高的分割迭代器,为了保留期望的stream()方法和parallelStream()方法的延迟行为
 * //spliterator要么拥有什么样的特性值呢 不可变的[IMMUTABLE]或者并发的[CONCURRENT]或者是延迟绑定的[late-binding],这个流才是延迟行为的
 * //如果上面这些都无法实现,那么重写的这个类应该去描述spliterator文档化的一些绑定的策略以及结构上的修改的一些行为{ 把修改描述出来 }
 * //这些要求确保了由stream()以及parallelStream()两个方法所生成的流,它会最终反映出集合的内容,从终止流操作开始发起的时候,反映出集合内容
 * //默认的实现会创建一个延迟绑定的spliterator,是从集合的迭代器[iterator];所创建的分割迭代器它会继承[inherits]集合迭代器的快速失败[fail-fast]属性
 * //{遇到问题不再继续往下走了,立刻抛出异常,称之为快速失败};
 * //所创建的分割迭代器,它会拥有Spliterator#SIZED[固定大小]的特性值
 * //所创建的分割迭代器还会拥有一个Spliterator#SUBSIZED[子大小]特性值
 * //{当对分割迭代器分割之后会生成若干个块,每个块的大小又是固定的,不会发生变化,称之为#SUBSIZED}
 * //如果Spliterator分割迭代器里面没有cover任何元素,那么额外的特性值的报告除了#SIZED和#SUBSIZED之外并其他特性不会帮助客户端去控制或简化一些计算
 * //然而,这样可以有助于一个不可变的并且空的分割迭代器spliterator实例共享的使用
 * //对于空的集合来说并且可以帮助客户端来确定是否这样一个spliterator里面是不是没有元素
 * //[创建的spliterator返回#SIZED和#SUBSIZED两个特性,其他特性不会对客户端的控制,计算起到帮助作用,但是它可以让分割迭代器实例得到重用]
 * //返回一个针对集合元素的分割迭代器
 * default Spliterator<E> spliterator() {
 *     return Spliterators.spliterator(this, 0);
 * }
 *
 *          Spliterator
 * Spliterator[接口] -> Spliterators[辅助类]
 * Collector[接口] -> Collectors[辅助类]
 * Spliterator是一个对象用于对源当中的元素遍历和分区[把源里的数据分成若干区域,不是分成两块]
 * 由Spliterator所涵盖的元素的源可以是数组[array]可以是集合[Collection]可以是IO通道[IO Channel]或者是一个生成器函数[generator function]
 *
 * 一个Spliterator可以一个一个的单个的遍历元素[tryAdvance],也可以按照顺序以块[bulk]成块的方式遍历[forEachRemaining]{实际forEachRemaining调用tryAdvance}
 *
 * 一个Spliterator还可以对他的元素进行分区,使用trySplit方法进行分区,分成另外一个Spliterator,并且可能是以并行的方式使用Spliterator的一些操作
 * 使用了Spliterator这样的操作,并且不能分割迭代器不能进行分割,或者是说能分割,但是是一个高度不平衡或者无效,效率非常低的方式去分割,这些操作,就不太可能会从并行当中获益
 * 遍历与分割都会消耗掉元素,每一个Spliterator仅仅是对单个的一个块的计算是有用的.
 *
 * 一个分割迭代器还会去报告它的源的结构、源、以及元素的特性值的集合;8个[ORDERED有序的、DISTINCT去重的、SORTED排序的、SIZED确定大小、NONNULL不为空的、
 * IMMUTABLE不可变的、CONCURRENT并发的、#SUBSIZED子部分固定大小的],这些可以被分割迭代器的客户端来去使用,使用他们用了控制具体化或者简化计算;
 * 比如说一个Collection的分割迭代器就会报告一个SIZED确定大小,一个Set的分割迭代器就会报告一个DISTINCT去重的,一个针对SortedSet的分割迭代器就会报告一个SORTED排序的
 * 特性值Characteristics就会作为一个简单的位操作去返回
 *
 * 有一些特性值会额外的限定方法的行为,比如说如果是ORDERED有序的话,那么遍历方法就必须遵循着它们在文档中定义好的顺序,新的特性值可能在未来被定义,因此实现者不应该对于
 * 这里面八个值没有列出来的其他值赋予相应的含义
 *
 * 分割迭代器如果没有报告,不包含IMMUTABLE or CONCURRENT的分割迭代器,我们期望它有一个文档化的策略话的考量,当分割迭代器绑定到了元素的源上面时,并且结构上的修改的一些检测
 * 在绑定之后要对元素源的结构上的修改要进行一个检测,延迟绑定late-binding的分割迭代器会在什么时候绑定到元素的源上面呢?会在第一次遍历,第一次分割,或者第一次查询大小时来去
 * 绑定到源上面,而不是在分割迭代器创建的时候绑定. 如果不是延迟绑定的分割迭代器他在什么时候绑定到源上面呢?他就在分割迭代器创建时绑定或者分割迭代器任一方法首次调用时绑定
 * 对于源的修改,如果修改发生在绑定之前,这种修改在分割迭代器遍历时就会被反应出来;在绑定之后分割迭代器就会抛出异常,ConcurrentModificationException,并发修改异常
 * 如果是按照这种方式来行事的分割迭代器,我们称之为fail-fast快速失败,[bulk]块遍历的方法叫forEachRemaining,它会优化遍历,并且会检查结构上的修改,在所有的元素都遍历完之后
 * 而不是检查每一个元素立刻去失败.[不是一个一个检查,那样效率是比较低的]
 *
 * Spliterators可以提供对于其余还没处理的,遍历的元素的一个大小的估算,通过estimateSize这样一个方法,这个方法为什么叫estimateSize是以为这个方法在某些情况是准确的,在某些情况
 * 是不准确的,仅仅是一个估算的值,并不是一个准确的值,在理想情况下,就如通过特性SIZED所反映的理想情况,那么这个值就非常精确的等于在随后的遍历当中遇到元素的数量,换句话说如果这个
 * 特性值包含了SIZED固定大小的话estimateSize的值一定等于接下来遍历的元素的总的数量,然而即便不是精确的知道再遍历元素的数量,一个估算的值呢也是可以对源的操作很有帮助的,比如说
 * 可以帮助你确定是否可以对源进一步地分割或者是说以串行的方式来去遍历其余的元素
 *
 * 尽管这种显著的功能在并行的算法当中存在显著的功能Spliterators并不被要求是线程安全的,相反并行算法使用了分割迭代器的并行算法的实现应该确保
 * 分割迭代器在某一时刻只是被唯一的一个线程来去使用,这是很容易的,通过serial-thread-confinement[串行线程围栏/约束]手段执行.通常是一种自然
 * 的结果,典型的并行算法,比如说通过递归解耦,一个线程调用了trySplit它可以将返回来的Spliterator交由另外一个线程接管,另外一个线程可能去遍历
 * 或者进一步去分割迭代器,不断的进行切割,分割以及遍历的行为是不确定的,如果两个或多个线程并行的操作同一个Spliterator.如果原来的线程将原来的
 * Spliterator交由另外一个线程处理,最好是这种传递是发生在任何元素使用tryAdvance方法被消费之前去完成,因为某些保证是遍历开始之前才是有效的
 *
 * Spliterator原生的子类型的特化也提供了{目的提升效率,减少不必要的拆装箱操作 OfInt,OfLong,OfDouble},子类型的默认实现就是
 * {Spliterator#tryAdvance(java.util.function.Consumer)}
 * and {Spliterator#forEachRemaining(java.util.function.Consumer)}
 * 将原生的值包装成对应包装类的实例,这种包装可能就会影响性能上的优势,相对于使用原生类型的特化失去了优势,因为它有装箱的操作,为了避免装箱操作
 * 相应的基于原生的方法就应该被使用,比如说:
 * {Spliterator.OfInt#tryAdvance(java.util.function.IntConsumer)} and so on  {OfInt -> IntConsumer}
 * 使用了基于装箱方法的consumer的原生值遍历,tryAdvance/forEachRemaining,它并不会影响值的顺序,它也是按照装箱之后的值的顺序来进行遍历
 *
 * Spliterators[分割迭代器]就像Iterator[迭代器]一样,它也是用于遍历源当中的元素的Spliterator API 除了串行遍历之外它也被设计成支持高效的并行操作,而显然
 * 之前的迭代器只支持串行的操作,方式是通过支持解耦分解以及单元素的遍历迭代,此外通过Spliterator这种方式来去访问元素的协议还被设计成是施加了更少的每个元素的负担
 * 相比于Iterator它的遍历成本是更低的,并且避免了本质上的竞争,是在使用Iterator单个的方法hasNext和next.
 * {Iterator遍历: 将hasNext和next方法搭配使用,Spliterator通过一个方法就可以判断是否有下一个元素,有则取出下一个,一个方法[tryAdvance]完场两个方法所完成的功能;
 * 而hasNext和next方法存在一种竞争,比如两个线程,一个访问hasNext,另一个访问next,可能存在并发修改异常,毕竟涉及两个方法,毕竟两个方法不是原子的,存在这样一个问题}
 *
 * 对于可变的源来说,任意的以及不确定的行为可能会发生,如果源在机构上被修改了,那么就可能出现一些不确定的行为;{修改行为指的是元素的添加,替换,删除;}在Spliterator绑定到
 * 数据源以及遍历结束这两个时间点,中间出现了修改操作那么这种可变的源的行为就是未知的,比如说这种修改可能会生成任意的不确定的结果在使用java.util.stream框架时候
 * {因此要求源是IMMUTABLE不可变的,因为可变源可能出现一些问题}
 *
 * 源结构上的修改可以通过如下的几种方式来进行管理的,
 * 1.源的结构是不能被修改的 例如 java.util.concurrent.CopyOnWriteArrayList {针对arrayList的并发实现,在write时将集合拷贝一份,不在原有的ArrayList中将数据添加进去
 * 相反它是将原来的底层结构整个拷贝一份,再去将新增加的元素追加到集合末尾,然后放到一个新的结构当中,比较有效的避免了并发修改的可能性,但是相应的效率是下降的,适合读多写少}
 * CopyOnWriteArrayList是一个不可变源,通过它创建的spliterator返回IMMUTABLE特性值
 * 2.源本身自己去管理并发 例如 java.util.concurrent.ConcurrentHashMap {}
 * ConcurrentHashMap建的集合是一个并发的源,通过这种源创建spliterator返回CONCURRENT特性值
 * 3.可变的源提供了一种延迟绑定以及快速失败的spliterator 延迟绑定会限制窗口,这种窗口指的是这种修改会影响计算,会限制修改影响计算的时间窗口,将两个时间点缩近了,变短了
 * fail-fast检测机构已经出现了变化在源遍历已经开始,并且抛出 ConcurrentModificationException 例如 ArrayList和其他的一些非并发集合在JDK中的一些类,它们都提供了一种
 * 延迟绑定,快速失败的这样一种分割迭代器
 * 4.可变的源会提供一个非延迟绑定的但是却是快速失败的spliterator,源会增加抛出 ConcurrentModificationException 可能性,因为潜在的这种时间窗口的修改被放大了
 * 5.可变的源提供了一种延迟绑定并且非快速失败的spliterator,这时源就会有这样一些风险,是任意的不确定的行为,是遍历已经开始之后
 * 6.可变的源提供了一个非延迟绑定并且非快速失败的spliterator,这时源就会有这样一些风险
 *
 * <p><b>Example.</b> Here is a class (not a very useful one, except
 * for illustration) that maintains an array in which the actual data
 * are held in even locations, and unrelated tag data are held in odd
 * locations. Its Spliterator ignores the tags.
 *
 * 例子 这有一个类维护了一个数组,偶数存放实际数据,奇数存放不相关的标签数据,这个类的分割迭代器会忽略掉标签
 * 具体见源码~~
 *
 * Spliterator的方法:
 *  tryAdvance [尝试前进,尝试的去遍历;]
 * 如果有剩余的元素存在,就会对剩余的元素执行给定的动作,同时返回一个true,否则返回false.
 * 如果Spliterator本身是ORDERED的,动作就会以遇到的下一个元素顺序对下一个元素按顺序执行,由动作所抛出的任何异常都会被传递给调用者
 *
 *  forEachRemaining
 * 针对于每一个后续的元素,都去执行给定的动作[action],是以当前线程串行的方式执行,直到所有的元素都已经被处理了,或者动作本身抛出异常,如果Spliterator
 * 动作是ORDERED的,动作会以遇到元素顺序将每个元素去执行,由action所抛出的任何异常都会被传递给调用者
 * 默认的实现会重复的调用tryAdvance方法直到返回false为止,在必要的情况下它应该被重写
 * 通常 do{}while{} do块里面写执行动作,while块里写值/信息判断 但是函数式编程,tryAdvance完成了两件事情{do,while}
 *
 *  trySplit [尝试进行分割]
 * 如果Spliterator对象是能够被进行分割的,那么它就会返回一个新的Spliterator对象涵盖元素,元素是从方法里返回来的,而且是不会被当前的Spliterator所涵盖掉
 * 分成两部份,一部分是由返回的Spliterator对象所覆盖,另外一部分是由当前对象所涵盖,两者之间没有交集,不会交叉
 * 如果这个Spliterator是ORDERED的,有序的 所返回来的Spliterator对象必须严格的涵盖一个前缀 {返回的Spliterator对象也应该是ORDERED的}
 * 除非这个当前的Spliterator涵盖的是一个无限的元素,否则重复的调用trySplit必须最终确定返回一个空[null]{代表Spliterator不能被进一步分割了}
 * 当不为空的返回值出现时
 * 如果返回值不为空,在分割之前所报告的估算大小所返回来的值,必须在分割完之后大于或者等于当前Spliterator的estimateSize,以及所返回来的estimateSize
 * 并且如果这个Spliterator有一个SUBSIZED特性值,那么在分割之前这个Spliterator的estimateSize大小必须要等于分割之后的所余下来的Spliterator的estimateSize
 * 大小加上分割之后所返回来的Spliterator的estimateSize大小
 * 这个方法出于任何原因都可能返回一个空值,包括
 * 原来的Spliterator空的,在遍历已经开始之后无法再进行分割了;数据结构存在一些限制;效率上的一些考量
 *
 * api说明
 * 一种理想的trySplit方法在不需要遍历的情况下,他会恰好分成两半,允许平衡的并行计算,很多时候与理想情况不是一致的,比如说只是分割一个近似于一个平衡的树,或者是说
 * 对于树中的叶子节点包含了一两个元素,那么不能再对这些节点进行进一步分割,然而极度不平衡的,没有效率的trySplit机制会导致并发效率急剧降低[并不是说多线程一定比单线程效率高]
 *
 * 返回一个Spliterator,它涵盖了部分元素,如果这个Spliterator是不能再继续分割的话,返回null
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */