package com.zyz.usualcode.mockito;

import com.zyz.usualcode.mockito.user.MockUserDao;
import com.zyz.usualcode.mockito.user.MockUserService;
import com.zyz.usualcode.mockito.user.ServiceException;
import com.zyz.usualcode.mockito.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Mockito主要的功能就是创建Mock对象
 *
 * @author 张远卓
 * @date 2020/10/13 17:14
 */
public class TestCase {
    public static void main(String[] args) {
        // 官网示例
        // mock creation
        List list = mock(List.class);
        // using mock object - it does not throw any "unexpected interaction" exception
        list.add("one");
        list.clear();
        // selective, explicit, highly readable verification
        verify(list).add("one");
        verify(list).clear();


        // you can mock concrete classes, not only interfaces
        LinkedList linkedList = mock(LinkedList.class);
        // stubbing appears before the actual execution
        when(linkedList.get(0)).thenReturn("first");
        // the following prints "first"
        System.out.println(linkedList.get(0));
        // the following prints "null" because get(999) was not stubbed
        System.out.println(linkedList.get(999));


    }

    @Test
    public void configMockObject() {
        List mockedList = mock(List.class);

        // 我们定制了当调用 mockedList.add("one") 时, 返回 true
        when(mockedList.add("one")).thenReturn(true);
        // 当调用 mockedList.size() 时, 返回 1
        when(mockedList.size()).thenReturn(1);

        Assert.assertTrue(mockedList.add("one"));
        // 因为我们没有定制 add("two"), 因此返回默认值, 即 false.
        Assert.assertFalse(mockedList.add("two"));
        Assert.assertEquals(1, mockedList.size());

        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello,").thenReturn("Mockito!");
        String result = i.next() + " " + i.next();
        //assert
        Assert.assertEquals("Hello, Mockito!", result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testForIOException() {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello,").thenReturn("Mockito!"); // 1
        String result = i.next() + " " + i.next(); // 2
        Assert.assertEquals("Hello, Mockito!", result);
        // 当调用x.methodCall方法后，抛出异常ExceptionX
        // 当第三次调用i.next()后，抛出异常NoSuchElementException
        // 等价于throw new NoSuchElementException();
        doThrow(new NoSuchElementException()).when(i).next(); // 3
        i.next(); // 4
    }

    @Test
    public void testVerify() {
        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");
        when(mockedList.size()).thenReturn(5);
        Assert.assertEquals(mockedList.size(), 5);

        // verify不关注方法返回值，而是测试运行是否通过
        // mockedList里面并没有值，只是模拟存取和各种操作
        // add("one")至少被调用一次
        verify(mockedList, atLeastOnce()).add("one");
        // add("two")被调用一次
        verify(mockedList, times(1)).add("two");
        // add(""three times")被调用3次
        verify(mockedList, times(3)).add("three times");
        // false
        verify(mockedList, never()).isEmpty();
    }

    @Test
    public void testSpy() {
        // spy 方法可以包装一个真实的 Java 对象, 并返回一个包装后的新对象.
        // 若没有特别配置的话, 对这个新对象的所有方法调用, 都会委派给实际的 Java 对象
        List list = new LinkedList();
        List spy = spy(list);

        // 对 spy.size() 进行定制.
        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        // 没有元素
        list.forEach(System.out::println);
        // 0
        System.out.println(list.size());

        // 因为我们没有对 get(0), get(1) 方法进行定制,
        // 因此这些调用其实是调用的真实对象的方法.
        Assert.assertEquals(spy.get(0), "one");
        Assert.assertEquals(spy.get(1), "two");

        Assert.assertEquals(spy.size(), 100);
    }

    @Test
    public void testCaptureArgument() {
        List<String> list = Arrays.asList("1", "2");
        List mockedList = mock(List.class);
        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
        mockedList.addAll(list);
        verify(mockedList).addAll(argument.capture());

        Assert.assertEquals(2, argument.getValue().size());
        Assert.assertEquals(list, argument.getValue());
    }

    private MockUserDao mockUserDao = mock(MockUserDao.class);
    private MockUserService userService = mock(MockUserService.class);

    @Test
    public void save() {
        User user = new User();
        user.setLoginName("admin");
        // 第一次调用findUserByLoginName返回user 第二次调用返回null
        when(mockUserDao.findUserByLoginName(anyString())).thenReturn(user).thenReturn(null);
        try {
            // 测试如果重名会抛出异常
            userService.save(user);
            // 如果没有抛出异常测试不通过
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (ServiceException se) {
        }
        verify(mockUserDao).findUserByLoginName("admin");

        // userService.save(user);
        user.setPassword("123456");
        String userId = userService.save(user);
        // 断言返回结果
        assertEquals(32, userId.length());

        verify(mockUserDao, times(2)).findUserByLoginName(anyString());
        verify(mockUserDao).save(any(User.class));
    }

    private void failBecauseExceptionWasNotThrown(Class<RuntimeException> aClass) throws ServiceException {

    }

    @Test
    public void save2() {
        User user = new User();
        user.setLoginName("admin");
        user.setPassword("123456");
        userService.save(user);

        // 通过ArgumentCaptor(参数捕获器) 对传入参数进行验证
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(mockUserDao).save(argument.capture());
        assertEquals("admin", argument.getValue().getLoginName());

        // stub 调用save方法时抛出异常
        doThrow(new ServiceException("测试抛出异常")).when(mockUserDao).save(any(User.class));
        try {
            userService.save(user);
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (ServiceException se) {
        }
    }
}
