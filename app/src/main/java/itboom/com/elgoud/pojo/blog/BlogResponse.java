package itboom.com.elgoud.pojo.blog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogResponse {
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("data")
    @Expose
    Data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
