package com.hhy.jdk8.optional;

/**
 * Optional[解决空指针异常问题]
 * A container object which may or may not contain a non-null value.
 * 一个容器对象,可能包含一个空或非空的值
 * NPE NullPointerException [防止出现NullPointerException]
 *
 * if(null != person){
 *     Address address = person.getAddress();
 *     if(null != address){
 *         ......
 *     }
 * }
 *
 * String str = null;
 *
 * String str = ....;
 *
 * if(null != str){
 *      str....
 * }
 *
 * Optional<String> optional = Optional.of(str);
 *
 * if(optional.isPresent){
 *      optional.get();
 * }
 *
 * List<Person> 应该返回空集合而不是null
 *
 * Value-based Classes 基于值的类
 * https://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html
 * 基于值的对象是不能使用 "==" 号进行比较的
 *
 * Optional的创建
 * empty: 构造容器里面为空的Optional对象
 * of: 构造容器里面不可为空的Optional对象
 * ofNullable: 构造容器里面可为空的Optional对象
 *
 * Optional注意事项:未实现序列化接口;不要将Optional作为方法参数进行定义,也不要在类当中声明Optional成员变量,
 * Optional通常只作为方法返回值用来规避空指针异常问题
 *
 * Swift,Groovy,Scala....等语言都已存在 Optional的类似情况
 */