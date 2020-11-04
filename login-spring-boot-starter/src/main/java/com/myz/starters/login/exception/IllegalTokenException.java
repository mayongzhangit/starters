package com.myz.starters.login.exception;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/13 10:59
 * @email 2641007740@qq.com
 */
public class IllegalTokenException extends RuntimeException{

    public IllegalTokenException(String messsage){
        super(messsage);
    }
}
