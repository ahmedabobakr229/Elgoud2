package itboom.com.elgoud.pojo.single_course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import itboom.com.elgoud.pojo.course.Course;

public class SingleCourseResponse {
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("course")
    @Expose
    Course course;

    @SerializedName("videos")
    @Expose
    String[] videos;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }
}
