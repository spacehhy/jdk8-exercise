package com.hhy.jdk8.binaryoperator;

/**
 * BinaryOperator
 *
 * Represents an operation upon two operands of the same type, producing a result
 * of the same type as the operands.  This is a specialization of
 * {@link java.util.function.BiFunction} for the case where the operands and the result are all of
 * the same type.
 *
 * 代表一种操作,这种操作是针对于相同类型的两个操作数,它会生成与操作数类型相同的结果,是BiFunction的一种特例
 *
 * BinaryOperator.minBy
 * BinaryOperator接收一个比较器对象[Comparator]返回一个BinaryOperator对象,这个对象会根据比较器调用apply方法
 * 返回两个元素中最小的一个
 *
 *
 *
 *
 */