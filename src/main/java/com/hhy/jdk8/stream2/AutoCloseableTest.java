package com.hhy.jdk8.stream2;

/**
 * 5.AutoCloseable since jdk 1.7
 */
public class AutoCloseableTest implements AutoCloseable{

    public void doSomething() {
        System.out.println("doSomething invoked!");
    }

    @Override
    public void close() throws Exception {
        System.out.println("close invoked!");
    }

    public static void main(String[] args) throws Exception {
        //try-with-resources block  自动执行close方法
        try (AutoCloseableTest autoCloseableTest = new AutoCloseableTest()){
            autoCloseableTest.doSomething();
        }
    }
}
