package cn.myfourm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;//状态码，成功返回200，失败返回500

    private String message;//提示信息

    private Object obj;//返回对象

    //成功返回结果
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }

    //失败返回结果
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    public static RespBean error(String message,Object obj){
        return new RespBean(500,message,obj);
    }

}
