package itboom.com.elgoud.slider.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.slider.SliderResponse;

public class SliderModel implements SliderMVP.Model {

    EljoudApi api;

    public SliderModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<SliderResponse> getImages(String apiToken) {
        return this.api.slider(apiToken);
    }
}
