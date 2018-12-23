package itboom.com.elgoud.pojo.course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseResponse implements Serializable {
    @Expose
    @SerializedName("status")
    int status;

    @Expose
    @SerializedName("data")
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
