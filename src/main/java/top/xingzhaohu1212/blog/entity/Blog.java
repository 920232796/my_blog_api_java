package top.xingzhaohu1212.blog.entity;


import lombok.Data;

import java.sql.Date;

@Data
public class Blog {

    private Integer id;

    private String title;

    private String content;

    private Integer read_quantity;

    private String image;

    private Date time;

    private String article_class;
}
