package top.xingzhaohu1212.blog.entity;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class PostParams {

    private String keyword;

    private String article_class;

    private Integer limit;

    private Integer start;

    private Date time;

    private String image;

    MultipartFile file;//上传图片

    private String content;

    private String title;

    private String ifMainImage;


}
