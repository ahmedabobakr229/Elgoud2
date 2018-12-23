package itboom.com.elgoud.pojo.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import itboom.com.elgoud.pojo.blog.Blog;
import itboom.com.elgoud.pojo.course.Course;

public class Notification implements Serializable {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("body")
    @Expose
    String body;

    @SerializedName("seen")
    @Expose
    int seen;

    @SerializedName("created_at")
    @Expose
    CreatedAt createdAt;

    @SerializedName("course")
    @Expose
    Course course;

    @SerializedName("blog")
    @Expose
    Blog blog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSeen() {
        return seen;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }
}
