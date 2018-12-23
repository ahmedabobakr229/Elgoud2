package itboom.com.elgoud.register.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.user.UserResponse;

public class RegisterModel implements RegisterMVP.Model {

    EljoudApi api;

    public RegisterModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<UserResponse> register(String name, String email, String phone, String password) {
        return api.register(name, email, phone, password, password);
    }

    @Override
    public Single<UserResponse> fbRegister(String name, String email, String fbId, String password) {
        return api.fbRegister(name, email, fbId, password, password);
    }
}
