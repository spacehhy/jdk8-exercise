package com.hhy.jdk8.optional;

import com.hhy.jdk8.optional.bean.Company;
import com.hhy.jdk8.optional.bean.Employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * OptionalTest
 */
public class Test1 {

    public static void main(String[] args) {
        //Optional<String> optional = Optional.empty();
        //Optional<String> optional = Optional.of("hello");
        Optional<String> optional = Optional.ofNullable("world");

        //不推荐采用此种传统方式编写程序
        /*if (optional.isPresent()) {
            System.out.println(optional.get());
        }*/

        optional.ifPresent(item -> System.out.println(item));//推荐的Optional使用方式
        /*System.out.println("-------");

        System.out.println(optional.orElse("world"));
        System.out.println("-------");

        System.out.println(optional.orElseGet(() -> "nihao"));*/

        System.out.println("---------分割线---------");

        Employee employee1 = new Employee();
        employee1.setName("zhangsan");

        Employee employee2 = new Employee();
        employee2.setName("lisi");

        Company company = new Company();
        company.setName("company1");

        List<Employee> employees = Arrays.asList(employee1,employee2);
        //注释此段代码,当Optional为空时,执行Optional.orElse()方法
        company.setEmployees(employees);

        List<Employee> list = company.getEmployees();
        Optional<Company> companyOptional = Optional.ofNullable(company);

        System.out.println(companyOptional.map(theCompany -> theCompany.getEmployees()).orElse(Collections.emptyList()));
        System.out.println(companyOptional.map(Company::getEmployees).orElse(Collections.emptyList()));

    }

    //Optional注意事项:未实现序列化接口;不要将Optional作为方法参数进行定义,也不要在类当中声明Optional成员变量,
    //Optional通常只作为方法返回值用来规避空指针异常问题
    public void test(Optional optional) {

    }
}
