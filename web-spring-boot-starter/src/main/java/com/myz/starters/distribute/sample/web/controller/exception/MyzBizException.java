package com.myz.starters.distribute.sample.web.controller.exception;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 15:59
 * @email 2641007740@qq.com
 */
public class MyzBizException extends RuntimeException{


    private String code;

    private String msg;

    private Object[] params;

    public MyzBizException( String code, String msg, Object ...params){
        super(msg);
        this.code = code;
        this.msg = msg;
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object[] getParams() {
        return params;
    }
}
