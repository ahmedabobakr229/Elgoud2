package itboom.com.elgoud.profile.mvp;

import com.esafirm.imagepicker.model.Image;

import java.io.File;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.user.UserResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileModel implements ProfileMVP.Model {

    EljoudApi api;

    public ProfileModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber, String password, String confirmPassword, Image image) {
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(userId));
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), apiToken);
        RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), mobileNumber);

        File file = new File(image.getPath());
        RequestBody body =  RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageFile = MultipartBody.Part.createFormData("image", image.getName(), body);

        if (password != null && confirmPassword != null) {
            RequestBody pass = RequestBody.create(MediaType.parse("multipart/form-data"), password);
            RequestBody confirmPass = RequestBody.create(MediaType.parse("multipart/form-data"), confirmPassword);
            return this.api.updateProfile(token, id, name, mobile, pass, confirmPass, imageFile);
        } else {
            return this.api.updateProfile(token, id, name, mobile, imageFile);
        }
    }

    @Override
    public Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber) {
        return this.api.updateProfile(apiToken, userId, username, mobileNumber);
    }

    @Override
    public Single<UserResponse> updateProfile(String apiToken, int userId, String username, String mobileNumber, String password, String confirmPassword) {
        return this.api.updateProfile(apiToken, userId, username, mobileNumber, password, confirmPassword);
    }
}
