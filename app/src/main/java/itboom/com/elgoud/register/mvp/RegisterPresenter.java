package itboom.com.elgoud.register.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.UserResponse;

public class RegisterPresenter implements RegisterMVP.Presenter {

    RegisterMVP.Model model;
    RegisterMVP.View view;
    Disposable disposable;

    public RegisterPresenter(RegisterMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(RegisterMVP.View view) {
        this.view = view;
    }

    @Override
    public void register() {
        if (view != null){
            if (view.getRegisterEmail().isEmpty()){
                view.onRegisterEmailError();
                return;
            }

            if (view.getRegisterUsername().isEmpty()){
                view.onRegisterUsernameError();
                return;
            }

            if (view.getRegisterPhone().isEmpty()){
                view.onRegisterPhoneError();
                return;
            }

            if(view.getRegisterPassword().isEmpty()){
                view.onRegisterPasswordError();
                return;
            }

            if (view.getRegisterConfirmPassword().isEmpty()){
                view.onRegisterConfirmPasswordError();
                return;
            }

            model.register(view.getRegisterUsername(), view.getRegisterEmail(), view.getRegisterPhone(), view.getRegisterPassword())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            Log.i(Constants.PRESENTER_RESPONSE, "onSuccess: " + userResponse.getMessage());
                            if (userResponse.getStatus() == 200){
                                view.onRegisterSuccess(userResponse.getUser());
                            } else {
                                if (!userResponse.getMessage().isEmpty() && userResponse.getMessage().contentEquals("validation error")){
                                    view.onRegisterFailure(NetworkResult.INVALID_EMAIL);
                                } else {
                                    view.onRegisterFailure(NetworkResult.UNKNOWN_ERROR);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onRegisterFailure(NetworkResult.UNKNOWN_ERROR);
                        }
                    });
        }
    }

    @Override
    public void fbRegister(String name, String email, String fbId) {
        if (view != null) {
            model.fbRegister(name, email, fbId, "123456")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            Log.i(Constants.PRESENTER_RESPONSE, "onSuccess: " + userResponse.getMessage());
                            if (userResponse.getStatus() == 200) {
                                view.onRegisterSuccess(userResponse.getUser());
                            } else {
                                if (!userResponse.getMessage().isEmpty() && userResponse.getMessage().contentEquals("validation error")) {
                                    view.onRegisterFailure(NetworkResult.INVALID_EMAIL);
                                } else {
                                    view.onRegisterFailure(NetworkResult.UNKNOWN_ERROR);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onRegisterFailure(NetworkResult.UNKNOWN_ERROR);
                        }
                    });
        }
    }


    @Override
    public void rxUnsubscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
