package itboom.com.elgoud.pojo.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @Expose
    @SerializedName("status")
    int status;

    @Expose
    @SerializedName("message")
    String message;

    @Expose
    @SerializedName("error")
    UserError error;

    @Expose
    @SerializedName("user")
    User user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserError getError() {
        return error;
    }

    public void setError(UserError error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
