package com.blog.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
@ApiModel("结果集")
public class MsgResult {

    @ApiModelProperty("响应码 200 成功 100失败")
    private int code;
    @ApiModelProperty("信息")
    private String msg;
    @ApiModelProperty("对象信息集")
    private Map<String,Object> extend = new HashMap<>();

    public static MsgResult success(){
        MsgResult msgResult = new MsgResult();
        msgResult.setCode(200);
        msgResult.setMsg("成功");
        return msgResult;
    }

    public static MsgResult fail(){
        MsgResult msgResult = new MsgResult();
        msgResult.setCode(100);
        msgResult.setMsg("失败");
        return msgResult;
    }

    public MsgResult add(String key,Object val){
        this.extend.put(key, val);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
