package org.luncert.objpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 直接内存存储对象，测试内存占用
 */
@RunWith(JUnit4.class)
public class Test1 {

    private class User {
        int age;
        int level;
        User(int age, int level) {
            this.age = age;
            this.level = level;
        }
        int getAge() { return age; }
        int getLevet() { return level; }
    }
    
    @Test
    public void test() throws InterruptedException {
        List<User> array = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            array.add(new User(random.nextInt(10) + 10, random.nextInt(5)));
        }
        System.out.println("over");
        Thread.sleep(3000);
    }

}
