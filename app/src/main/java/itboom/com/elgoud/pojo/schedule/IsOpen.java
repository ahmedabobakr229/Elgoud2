package itboom.com.elgoud.pojo.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsOpen {
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("Is Open")
    @Expose
    boolean isOpen;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
