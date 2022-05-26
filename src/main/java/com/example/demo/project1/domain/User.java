package com.example.demo.project1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liwenji
 * @ClassName User
 * @Description TODO
 * @date 2022/5/25 11:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    private String note;
}
