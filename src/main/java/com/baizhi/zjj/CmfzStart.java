package com.baizhi.zjj;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//指定dao包的位置
@MapperScan("com.baizhi.zjj.dao")    //import tk.mybatis.spring.annotation.MapperScan;  使用通用mapper
//表示当前项目为springboot应用
@SpringBootApplication
public class CmfzStart extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CmfzStart.class,args);
    }

}
/*@SpringBootApplication 注解等价于:
@Configuration           项目启动时自动配置spring 和 springmvc 初始搭建
@EnableAutoConfiguration 自动与项目中集成的第三方技术进行集成
@ComponentScan			 扫描入口类所在子包以及子包后代包中注解(也可以从新指定要扫描哪些包)
*/
