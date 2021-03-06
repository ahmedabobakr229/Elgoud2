package itboom.com.elgoud.pojo.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("url")
    @Expose
    String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
