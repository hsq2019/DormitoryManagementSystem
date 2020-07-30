package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {
    private boolean success;
    private String code;
    private String message;//错误消息字段
    private Integer total;//做分页用的
    private Object data;
    private String stackTrance;//错误堆栈信息

}
