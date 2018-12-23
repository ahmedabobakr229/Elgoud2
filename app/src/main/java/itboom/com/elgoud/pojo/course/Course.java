package itboom.com.elgoud.pojo.course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course implements Serializable {
    @Expose
    @SerializedName("id")
    int id;

    @Expose
    @SerializedName("name")
    String name;

    @Expose
    @SerializedName("description")
    String description;

    @Expose
    @SerializedName("thumbnail")
    Thumbnail thumbnail;

    /**
     * youtube_videos -> individual videos
     * youtube_playlist -> videos in playlist with playlist id
     * videos -> videos from another resources
     */
    @Expose
    @SerializedName("type")
    String type;

    @Expose
    @SerializedName("is_free")
    int isFree;

    @Expose
    @SerializedName("price")
    int price;

    @Expose
    @SerializedName("created_at")
    String createdAt;

    @Expose
    @SerializedName("updated_at")
    String UpdatedAt;

    @Expose
    @SerializedName("playlists")
    PlaylistItem[] playlists;


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

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int isFree() {
        return isFree;
    }

    public void setFree(int free) {
        isFree = free;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public PlaylistItem[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(PlaylistItem[] playlists) {
        this.playlists = playlists;
    }
}
