package itboom.com.elgoud.login.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.user.UserResponse;

public class LoginModel implements LoginMVP.Model {

    EljoudApi api;

    public LoginModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<UserResponse> login(String email, String password) {
        return api.login(email, password);
    }
}
