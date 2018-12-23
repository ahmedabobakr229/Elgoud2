package itboom.com.elgoud.pojo.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import itboom.com.elgoud.pojo.course.PlaylistItem;
import itboom.com.elgoud.pojo.course.Thumbnail;

public class InnerCourse implements Serializable {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("thumbnail")
    @Expose
    Thumbnail thumbnail;

    @SerializedName("playlists")
    @Expose
    PlaylistItem[] playlistItems;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PlaylistItem[] getPlaylistItems() {
        return playlistItems;
    }

    public void setPlaylistItems(PlaylistItem[] playlistItems) {
        this.playlistItems = playlistItems;
    }
}
