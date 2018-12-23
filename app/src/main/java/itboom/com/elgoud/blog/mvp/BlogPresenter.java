package itboom.com.elgoud.blog.mvp;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.blog.BlogResponse;

public class BlogPresenter implements BlogMVP.Presenter {

    BlogMVP.View view;
    BlogMVP.Model model;
    Disposable disposable;

    public BlogPresenter(BlogMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(BlogMVP.View view) {
        this.view = view;
    }

    @Override
    public void getBlog(String apiToken) {
        if (view != null) {
            this.model.getBlogs(apiToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<BlogResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(BlogResponse blogResponse) {
                            if (blogResponse.getStatus() == 200){
                                view.onBlogsSuccess(blogResponse.getData());
                            } else {
                                view.onBlogsError(NetworkResult.NO_ITEM_AVAILABLE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onBlogsError(NetworkResult.NETWORK_ERROR);
                        }
                    });
        }
    }

    @Override
    public void rxUnsubscribe() {
        if (this.disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }
}
