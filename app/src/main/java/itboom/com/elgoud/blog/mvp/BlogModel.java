package itboom.com.elgoud.blog.mvp;

import javax.inject.Inject;

import io.reactivex.Single;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.blog.BlogResponse;

public class BlogModel implements BlogMVP.Model {

    EljoudApi api;

    public BlogModel(EljoudApi api) {
        this.api = api;
    }

    @Override
    public Single<BlogResponse> getBlogs(String apiToken) {
        return this.api.getBlogs(apiToken);
    }
}
