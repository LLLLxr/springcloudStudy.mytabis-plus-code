package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
//        user.setId(5L);
        user.setUsername("LeLei");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(new UserInfo(24,"English Teacher","femal"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    @Test
    void testQuery() {
        List<User> users = userService.listByIds(List.of(1L, 2L, 4L));
        users.forEach(System.out::println);
    }

//    @Test
//    //普通插入测试性能
//    void testSaveOneByOne() {
//        long b = System.currentTimeMillis();
//        for (int i = 1; i <= 100000; i++) {
//            userService.save(buildUser(i));
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("耗时：" + (e - b));
//    }
//
//    private User buildUser(int i) {
//        User user = new User();
//        user.setUsername("user_" + i);
//        user.setPassword("123");
//        user.setPhone("" + (18688190000L + i));
//        user.setBalance(2000);
////        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
//        user.setInfo(new UserInfo(24,"English Teacher","femal"));
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(user.getCreateTime());
//        return user;
//    }
//
//    @Test
//    //mybatis插入
//    void testSaveBatch() {
//        // 准备10万条数据
//        List<User> list = new ArrayList<>(1000);
//        long b = System.currentTimeMillis();
//        for (int i = 1; i <= 100000; i++) {
//            list.add(buildUser(i));
//            // 每1000条批量插入一次，只有一次网络请求
//            if (i % 1000 == 0) {
//                userService.saveBatch(list);
//                list.clear();
//            }
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("耗时：" + (e - b));
//    }

    @Test
    //debug test 枚举
    void testService() {
        List<User> list = userService.list();
        list.forEach(System.out::println);
    }

    @Test
    void testPageQuery() {
        // 1.分页查询，new Page()的两个参数分别是：页码、每页大小
        Page<User> p = userService.page(new Page<>(2, 2));
        // 2.总条数
        System.out.println("total = " + p.getTotal());
        // 3.总页数
        System.out.println("pages = " + p.getPages());
        // 4.数据
        List<User> records = p.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    void testPageQuery2() {

        int pageNo = 1, pageSize = 5;
        // 分页参数
        Page<User> page = Page.of(pageNo, pageSize);
        // 排序参数, 通过OrderItem来指定
        page.addOrder(new OrderItem("balance", false));

        userService.page(page);
        // 1.分页查询，new Page()的两个参数分别是：页码、每页大小
        Page<User> p = userService.page(page);
        // 2.总条数
        System.out.println("total = " + p.getTotal());
        // 3.总页数
        System.out.println("pages = " + p.getPages());
        // 4.数据
        List<User> records = p.getRecords();
        records.forEach(System.out::println);
    }
}