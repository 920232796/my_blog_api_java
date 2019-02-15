package top.xingzhaohu1212.blog.entity;

import lombok.Data;

import java.util.List;


@Data
public class ResponseResult<T> {
    String ret = "success";
    String msg = "";
    String url = "";//上传图片返回url
    String content_type = "";//no permission, rank info,activity info
    T obj;
    List<T> arrays;
    Integer total_number;//返回的题目数目
}
