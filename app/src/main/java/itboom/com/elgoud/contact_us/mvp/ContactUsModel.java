package itboom.com.elgoud.contact_us.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.base.BaseResponse;

public class ContactUsModel implements ContactUsMVP.Model {

    EljoudApi api;

    public ContactUsModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<BaseResponse> contactUs(String apiToken, String name, String message, int userId) {
        return this.api.contactUs(apiToken, name, message, userId);
    }
}
