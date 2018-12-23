package itboom.com.elgoud.register.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.pojo.user.UserResponse;

public interface RegisterMVP {
    interface View {
        String getRegisterUsername();
        String getRegisterEmail();
        String getRegisterPhone();
        String getRegisterPassword();
        String getRegisterConfirmPassword();
        void onRegisterUsernameError();
        void onRegisterEmailError();
        void onRegisterPhoneError();
        void onRegisterPasswordError();
        void onRegisterConfirmPasswordError();
        void onRegisterSuccess(User user);
        void onRegisterFailure(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void register();
        void fbRegister(String name, String email, String fbId);
        void rxUnsubscribe();
    }

    interface Model {
        Single<UserResponse> register(String name, String email, String phone, String password);
        Single<UserResponse> fbRegister(String name, String email, String fbId, String password);
    }
}
