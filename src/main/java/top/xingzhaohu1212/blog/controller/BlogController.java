package top.xingzhaohu1212.blog.controller;


import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/test")
    public String test() {

        String title = "xzh";
        List<Blog> result = blogMapper.getBlogByTitle();
        for (Blog each:result) {
            System.out.println(each.getContent());
        }
        RandomGenerateStr.getRandomStr(20);
        return "hello world";
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
            blogs = blogMapper.searchBlogByClass(article_class, start, limit);
            number = blogMapper.getBlogByClassQuantity(article_class);
        }

        responseResult.setArrays(blogs);
        responseResult.setTotal_number(number);

        return responseResult;
    }

    @RequestMapping("/insertBlog")
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
        fileName = RandomGenerateStr.getRandomStr(20) + "." + suffix;
        File dest = new File(rootUrlWin + fileName);
        System.out.println("filename: " + fileName);
        try {
            file.transferTo(dest);

            if (ifMainImage == null) {
                //证明是封面图！！按照 150 * 80 来放缩 因为封面图是element ui 组件上传 不会传递ifMainImage这个参数 所以肯定为null
                HashMap<String, Integer> resultMap = ImageHandle.getWidthHeight(rootUrlWin + fileName);
                Integer width = resultMap.get("width");
                Integer height = resultMap.get("height");
                if (width.equals(height)) {
                    //证明是正方形
                    double ratio = width / 100;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
                if (width > height) {
                    //证明是横着的图片
                    //按照宽为100来缩放
                    double ratio = width / 100;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
                if (width < height) {
                    //按照高100来缩放  高一定不能超过100！！ 要不布局就乱了
                    double ratio = height / 100;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
            }
            else {
                // 证明是上传的博客文章图片！！ 而不是封面图片 这个又要按照另一个规则来进行缩放了
                HashMap<String, Integer> resultMap = ImageHandle.getWidthHeight(rootUrlWin + fileName);
                Integer width = resultMap.get("width");
                Integer height = resultMap.get("height");
                if (width.equals(height)) {
                    double ratio = width / 200;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
                if (width > height) {
                    //证明是横着的图片
                    //按照宽为100来缩放
                    double ratio = width / 350;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
                if (width < height) {

                    double ratio = height / 200;
                    ImageHandle.modifyImageSize(rootUrlWin + fileName, suffix, ratio );
                }
            }

            responseResult.setRet("succ");
            responseResult.setUrl("http://47.100.10.8/image/" + fileName);
            System.out.println(responseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("hello world");
        return responseResult;
    }




}
