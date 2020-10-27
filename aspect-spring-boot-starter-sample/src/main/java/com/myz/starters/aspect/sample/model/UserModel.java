package com.myz.starters.aspect.sample.model;

import java.io.Serializable;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 20:20
 * @email 2641007740@qq.com
 */
public class UserModel implements Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
