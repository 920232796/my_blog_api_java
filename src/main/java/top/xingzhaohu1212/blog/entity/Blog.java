package top.xingzhaohu1212.blog.entity;




import java.sql.Date;

public class Blog {

    private Integer id;

    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRead_quantity() {
        return read_quantity;
    }

    public void setRead_quantity(Integer read_quantity) {
        this.read_quantity = read_quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getArticle_class() {
        return article_class;
    }

    public void setArticle_class(String article_class) {
        this.article_class = article_class;
    }

    private String content;

    private Integer read_quantity;

    private String image;

    private Date time;

    private String article_class;


}
