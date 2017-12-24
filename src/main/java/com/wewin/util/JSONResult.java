package com.wewin.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.Serializable;

public class JSONResult implements Serializable {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private int state;
    /** 错误消息  */
    private String message;
    /** 返回正确时候的数据 */
    private Object data;

    public JSONResult() {
    }

    public JSONResult(boolean b,String message){

        if(b == Boolean.FALSE){state = ERROR;}
        else{
            state = SUCCESS;
        }
        this.message = message;
    }

    public JSONResult(Object data){
        state = SUCCESS;
        this.data = data;
    }

    public JSONResult(Throwable e) {
        state = ERROR;
        message = e.getMessage();
    }

    public JSONResult(int state, Throwable e) {
        this.state = state;
        this.message = e.getMessage();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {

        JSONArray jsonArray = null;
        String  dataString = "";
        try{
             jsonArray = JSONArray.fromObject(data);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(null != jsonArray){
            dataString =jsonArray.toString();
        }else{
            dataString = data.toString();
        }
        return "JSONResult [state=" + state + ", message=" + message + ", data=" + dataString + "]";
    }

}