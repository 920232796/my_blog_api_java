package top.xingzhaohu1212.blog.controller;


import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xingzhaohu1212.blog.dao.BlogMapper;
import top.xingzhaohu1212.blog.entity.Blog;
import top.xingzhaohu1212.blog.entity.PostParams;
import top.xingzhaohu1212.blog.entity.ResponseResult;
import top.xingzhaohu1212.blog.lib.ImageHandle;
import top.xingzhaohu1212.blog.lib.RandomGenerateStr;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/api")
@RestController
public class BlogController {

    @Autowired
    private BlogMapper blogMapper;

//    @RequestMapping("/test")
//    public String test() {
//
//        String title = "xzh";
//        List<Blog> result = blogMapper.getBlogByTitle();
//        for (Blog each:result) {
//            System.out.println(each.getContent());
//        }
//        RandomGenerateStr.getRandomStr(20);
//        return "hello world";
//    }

    @RequestMapping("/getBlog")
    public ResponseResult<Blog> getBlog(PostParams postParams) {

        ResponseResult<Blog> responseResult = new ResponseResult<Blog>();
        Integer id = postParams.getId();
        Blog blog = blogMapper.getBlogById(id);
        if (blog != null) {
            Integer readQuantity = blog.getRead_quantity();
            if (readQuantity == null) {
                readQuantity = 0;
            }
            readQuantity += 1;
            blog.setRead_quantity(readQuantity);
            blogMapper.updateBlogReadQuantity(blog);
            responseResult.setObj(blog);
        }
        else {
            responseResult.setRet("fail");
        }
        return responseResult;
    }

    @RequestMapping("/searchBlog")
    public ResponseResult searchBlog(PostParams postParams) {
        // t通过文章类别  limit start  来进行分页显示文章。
        ResponseResult<Blog> responseResult = new ResponseResult<Blog>();
        String article_class = postParams.getArticle_class();
        String keyword = postParams.getKeyword();
        int start = postParams.getStart();
        Integer limit = postParams.getLimit();
        List<Blog> blogs = new ArrayList<>();
        Integer number = 0;
        if (article_class == null) {
            article_class = "%python%";
        }
        if (limit == null) {
            limit = 5;
        }
        if (keyword != null) {
            keyword = "%" + keyword + "%" ;
            blogs = blogMapper.searchBlogByKeyword(keyword, start, limit);
            number = blogMapper.getBlogByKeywordQuantity(keyword);
        }
        if (keyword == null) {
            article_class = "%" + article_class + "%";
            blogs = blogMapper.searchBlogByClass(article_class, start, limit);
            number = blogMapper.getBlogByClassQuantity(article_class);
        }

        responseResult.setArrays(blogs);
        responseResult.setTotal_number(number);

        return responseResult;
    }

    @RequestMapping(value = "/insertBlog", method = RequestMethod.POST)
    public ResponseResult insertBlog(PostParams postParams) {
        //后台提交博客!
        ResponseResult<Blog> responseResult = new ResponseResult<>();
        Blog blog = new Blog();
        blog.setArticle_class(postParams.getArticle_class());
        blog.setTitle(postParams.getTitle());
        blog.setContent(postParams.getContent());
        blog.setTime(postParams.getTime());
        blog.setImage(postParams.getImage());
        try {
            blogMapper.insertBlog(blog);

        } catch (Exception e) {
            responseResult.setRet("fail");
            responseResult.setMsg("插入博客失败。");
            e.printStackTrace();
        }
        return responseResult;
    }

    @RequestMapping(value = "/editBlog", method = RequestMethod.POST)
    public ResponseResult editBlog(PostParams postParams) {
        //后台提交博客!
        ResponseResult<Blog> responseResult = new ResponseResult<>();

        Blog blog = new Blog();
//        blog.setArticle_class(postParams.getArticle_class());
//        blog.setTitle(postParams.getTitle());
        blog.setId(postParams.getId());
        blog.setContent(postParams.getContent());
//        blog.setTime(postParams.getTime());
//        blog.setImage(postParams.getImage());
        try {
            blogMapper.updateBlog(blog);

        } catch (Exception e) {
            responseResult.setRet("fail");
            responseResult.setMsg("更新博客失败。");
            e.printStackTrace();
        }
        return responseResult;
    }

    @RequestMapping("/uploadImage")
    public ResponseResult<String> uploadImage(PostParams postParams) {

        //如果这个不是false 那么就是上传的文章封面图片 这个需要根据文章封面图的大小比例来进行对图片的放缩！！
        String ifMainImage = postParams.getIfMainImage();

        String rootUrlLinux = "/usr/image/";
        String rootUrlWin = "d://java/";
        ResponseResult<String> responseResult = new ResponseResult<>();
        MultipartFile file = postParams.getFile();
        if (file.isEmpty()) {
            responseResult.setRet("fail");
            responseResult.setMsg("文件为空");
            return responseResult;

        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!suffix.equals("png") & !suffix.equals("jpg") & !suffix.equals("jpeg"))
        {
            responseResult.setRet("fail");
            return responseResult;
        }
        //随机生成字符串 成为file name
        fileName = RandomGenerateStr.getRandomStr_1() + "." + suffix;
        File dest = new File(rootUrlLinux + fileName);
        System.out.println("filename: " + fileName);
        try {
            file.transferTo(dest);
            responseResult.setRet("succ");
            responseResult.setUrl("http://www.zhxing.online/image/" + fileName);
            System.out.println(responseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }


    @RequestMapping("/searchHotBlog")
    public ResponseResult searchHotBlog(PostParams postParams) {
        // t通过文章类别  limit start  来进行分页显示文章。
        ResponseResult<Blog> responseResult = new ResponseResult<Blog>();
        int start = postParams.getStart();
        Integer limit = postParams.getLimit();
        List<Blog> blogs = new ArrayList<>();
        if (limit == null) {
            limit = 5;
        }
        blogs = blogMapper.searchHotBlog(start, limit);
        responseResult.setArrays(blogs);

        return responseResult;
    }

}
