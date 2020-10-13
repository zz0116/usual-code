package com.zyz.usualcode.mockito.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 张远卓
 * @date 2020/10/13 17:55
 */
@Data
@Accessors(chain = true)
public class User {
    private String loginName;
    private String password;
}
