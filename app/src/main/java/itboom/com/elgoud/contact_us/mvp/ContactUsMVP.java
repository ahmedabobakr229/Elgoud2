package itboom.com.elgoud.contact_us.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.base.BaseResponse;

public interface ContactUsMVP {
    interface View {
        void onContactUsSuccess(BaseResponse response);
        void onContactUsFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void contactUs(String apiToken, String name, String message, int userId);
        void unSubscribe();
    }

    interface Model {
        Single<BaseResponse> contactUs(String apiToken, String name, String message, int userId);
    }
}
