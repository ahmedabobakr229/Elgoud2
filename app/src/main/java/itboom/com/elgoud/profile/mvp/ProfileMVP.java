package itboom.com.elgoud.profile.mvp;

import com.esafirm.imagepicker.model.Image;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.pojo.user.UserResponse;

public interface ProfileMVP {
    interface View {
        void onUpdateSuccess(User user);
        void onUpdateFailure(NetworkResult result);
        String getUsername();
        String getMobile();
        void usernameError();
        void mobileError();
    }

    interface Presenter {
        void setView(View view);
        void updateProfile(String apiToken, int userId, String password, String confirmPassword, Image image);
        void updateProfile(String apiToken, int userId, String password, String confirmPassword);
        void updateProfile(String apiToken, int userId, Image image);
        void updateProfile(String apiToken, int userId);
        void rxUnsubscribe();
    }

    interface Model {
        Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber, String password, String confirmPassword, Image image);
        Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber);
        Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber, String password, String confirmPassword);
    }
}
