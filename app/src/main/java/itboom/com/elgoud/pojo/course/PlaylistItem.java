package itboom.com.elgoud.pojo.course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaylistItem implements Serializable {

    @Expose
    @SerializedName("id")
    int id;

    @Expose
    @SerializedName("video_id")
    String videoId;

    @Expose
    @SerializedName("youtube_playlist_id")
    String youtubePlaylistId;

    @Expose
    @SerializedName("youtube_video_id")
    String youtubeVideoId;

    @Expose
    @SerializedName("course_id")
    int courseId;

    @Expose
    @SerializedName("created_at")
    String createdAt;

    @Expose
    @SerializedName("updated_at")
    String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getYoutubePlaylistId() {
        return youtubePlaylistId;
    }

    public void setYoutubePlaylistId(String youtubePlaylistId) {
        this.youtubePlaylistId = youtubePlaylistId;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
