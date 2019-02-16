package top.xingzhaohu1212.blog.dao;

import org.apache.ibatis.annotations.*;
import top.xingzhaohu1212.blog.entity.Blog;

import java.util.List;

@Mapper
public interface BlogMapper {

    @Select("select * from blog")
    public List<Blog> getBlogByTitle();


    @Select("select * from blog where article_class like  #{article_class} limit #{start}, #{limit}")
    public List<Blog> searchBlogByClass(@Param("article_class") String article_class, @Param("start") Integer start, @Param("limit") Integer limit);
    @Select("select count(*) from blog where article_class like  #{article_class}")
    public Integer getBlogByClassQuantity(String article_class);


    @Insert("insert into blog (title, content, image, time, article_class) values (#{title}, #{content}, #{image}, #{time}, #{article_class})")
    public void insertBlog(Blog blog);

    @Update("update blog set content = #{content} where id = #{id}")
    public void updateBlog(Blog blog);



    @Select("select * from blog where content like  #{keyword} limit #{start}, #{limit}")
    public List<Blog> searchBlogByKeyword(@Param("keyword") String keyword, @Param("start") Integer start, @Param("limit") Integer limit);
    @Select("select count(*) from blog where content like  #{keyword}")
    public Integer getBlogByKeywordQuantity(String keyword);



}
