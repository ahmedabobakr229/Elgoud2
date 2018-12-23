package itboom.com.elgoud.slider.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.slider.Slider;
import itboom.com.elgoud.pojo.slider.SliderResponse;

public interface SliderMVP {
    interface View {
        void onSuccess(Slider[] sliders);
        void onError(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getImage(String apiToken);
        void rxUnsubscribe();
    }

    interface Model {
        Single<SliderResponse> getImages(String apiToken);
    }
}
