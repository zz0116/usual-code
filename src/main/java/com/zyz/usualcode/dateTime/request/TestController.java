package com.zyz.usualcode.dateTime.request;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author 张远卓
 * @date 2020/10/14 16:15
 */
@RestController
public class TestController {
    /**
     * 测试LocalDate能不能接收yyyy-MM-dd日期
     *
     * @param date 需要注解@DateTimeFormat(pattern = "yyyy-MM-dd")才能接收到
     */
    @GetMapping("/localDate")
    public String m1(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return date.toString();
    }

    /**
     * 需要进行mvc的统一配置解析规则，无法像上面的方法一样通过简单注解
     *
     * @param date !无法传入
     */
    @PostMapping("/localDate")
    public String m2(@RequestBody LocalDate date) {
        return date.toString();
    }
}
