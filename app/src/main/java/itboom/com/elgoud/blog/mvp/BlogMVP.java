package itboom.com.elgoud.blog.mvp;

import io.reactivex.Single;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.blog.BlogResponse;
import itboom.com.elgoud.pojo.blog.Data;

public interface BlogMVP {
    interface View {
        void onBlogsSuccess(Data data);
        void onBlogsError(NetworkResult result);
    }

    interface Presenter {
        void setView(View view);
        void getBlog(String apiToken);
        void rxUnsubscribe();
    }

    interface Model {
        Single<BlogResponse> getBlogs(String apiToken);
    }
}
