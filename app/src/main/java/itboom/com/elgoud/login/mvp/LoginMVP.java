package itboom.com.elgoud.login.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.pojo.user.UserResponse;

public interface LoginMVP {
    interface View {
        String getLoginEmail();
        String getLoginPassword();
        void onLoginEmailError();
        void onLoginPasswordError();
        void onLoginFailure(NetworkResult result);
        void onLoginSuccess(User user);
    }

    interface Presenter {
        void setView(View view);
        void login();
        void rxUnsubscribe();
    }

    interface Model {
        Single<UserResponse> login(String email, String password);
    }
}
