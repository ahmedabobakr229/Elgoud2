package itboom.com.elgoud.profile.mvp;

import android.util.Log;

import com.esafirm.imagepicker.model.Image;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.UserResponse;

public class ProfilePresenter implements ProfileMVP.Presenter {

    ProfileMVP.View view;
    ProfileMVP.Model model;
    Disposable disposable;

    public ProfilePresenter(ProfileMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ProfileMVP.View view) {
        this.view = view;
    }

    @Override
    public void updateProfile(String apiToken, int userId, String password, String confirmPassword, Image image) {
        if (view != null){

            if (view.getUsername() == null || view.getUsername().isEmpty()){
                view.usernameError();
                return;
            }

            if (view.getMobile() == null || view.getMobile().isEmpty()){
                view.mobileError();
                return;
            }

            model.updateProfile(apiToken, userId, view.getUsername(), view.getMobile(), password, confirmPassword, image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            if (userResponse.getStatus() == 200){
                                view.onUpdateSuccess(userResponse.getUser());
                            } else {
                                view.onUpdateFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("network_result", "onError: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void updateProfile(String apiToken, int userId, String password, String confirmPassword) {
        if (view != null){

            if (view.getUsername() == null || view.getUsername().isEmpty()){
                view.usernameError();
                return;
            }

            if (view.getMobile() == null || view.getMobile().isEmpty()){
                view.mobileError();
                return;
            }

            model.updateProfile(apiToken, userId, view.getUsername(), view.getMobile(), password, confirmPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            if (userResponse.getStatus() == 200){
                                view.onUpdateSuccess(userResponse.getUser());
                            } else {
                                view.onUpdateFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("network_result", "onError: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void updateProfile(String apiToken, int userId, Image image) {
        if (view != null){

            if (view.getUsername() == null || view.getUsername().isEmpty()){
                view.usernameError();
                return;
            }

            if (view.getMobile() == null || view.getMobile().isEmpty()){
                view.mobileError();
                return;
            }

            model.updateProfile(apiToken, userId, view.getUsername(), view.getMobile(), null, null, image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            if (userResponse.getStatus() == 200){
                                view.onUpdateSuccess(userResponse.getUser());
                            } else {
                                view.onUpdateFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("network_result", "onError: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void updateProfile(String apiToken, int userId) {
        if (view != null){

            if (view.getUsername() == null || view.getUsername().isEmpty()){
                view.usernameError();
                return;
            }

            if (view.getMobile() == null || view.getMobile().isEmpty()){
                view.mobileError();
                return;
            }

            model.updateProfile(apiToken, userId, view.getUsername(), view.getMobile())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            if (userResponse.getStatus() == 200){
                                view.onUpdateSuccess(userResponse.getUser());
                            } else {
                                view.onUpdateFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("network_result", "onError: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void rxUnsubscribe() {
        if (this.disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
