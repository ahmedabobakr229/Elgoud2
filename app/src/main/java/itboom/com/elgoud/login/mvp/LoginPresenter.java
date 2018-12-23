package itboom.com.elgoud.login.mvp;

import android.util.Log;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.UserResponse;

public class LoginPresenter implements LoginMVP.Presenter {

    LoginMVP.View view;
    LoginMVP.Model model;
    Disposable disposable;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void login() {
        if (view != null){
            if (view.getLoginEmail().isEmpty()){
                view.onLoginEmailError();
                return;
            }

            if (view.getLoginPassword().isEmpty()){
                view.onLoginPasswordError();
                return;
            }

            model.login(view.getLoginEmail(), view.getLoginPassword())
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
                                view.onLoginSuccess(userResponse.getUser());
                            } else if (userResponse.getStatus() == 500) {
                                if (!userResponse.getMessage().isEmpty() && userResponse.getMessage().contentEquals("wrong password")){
                                    view.onLoginFailure(NetworkResult.WRONG_PASSWORD);
                                } else {
                                    view.onLoginFailure(NetworkResult.WRONG_PASSWORD);
                                }
                            }
                            else {
                                if (!userResponse.getMessage().isEmpty() && userResponse.getMessage().contentEquals("wrong credentials")){
                                    view.onLoginFailure(NetworkResult.INVALID_CREDENTIALS);
                                } else {
                                    view.onLoginFailure(NetworkResult.UNKNOWN_ERROR);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(Constants.PRESENTER_ERROR, "onError: " + e.getMessage());
                            view.onLoginFailure(NetworkResult.UNKNOWN_ERROR);
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
