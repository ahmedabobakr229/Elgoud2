package itboom.com.elgoud.contact_us.mvp;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.base.BaseResponse;

public class ContactUsPresenter implements ContactUsMVP.Presenter {

    ContactUsMVP.View view;
    ContactUsMVP.Model model;
    Disposable disposable;

    public ContactUsPresenter(ContactUsMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ContactUsMVP.View view) {
        this.view = view;
    }

    @Override
    public void contactUs(String apiToken, String name, String message, int userId) {
        if (view != null) {
            model.contactUs(apiToken, name, message, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (response.getStatus() == 200) {
                                view.onContactUsSuccess(response);
                            } else {
                                view.onContactUsFailure(NetworkResult.UNKNOWN_ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onContactUsFailure(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void unSubscribe() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }
}
