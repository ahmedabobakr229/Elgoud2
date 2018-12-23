package itboom.com.elgoud.pojo.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderResponse {
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("slider")
    @Expose
    Slider[] slider;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Slider[] getSlider() {
        return slider;
    }

    public void setSlider(Slider[] slider) {
        this.slider = slider;
    }
}
