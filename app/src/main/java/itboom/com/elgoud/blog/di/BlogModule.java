package itboom.com.elgoud.blog.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.blog.BlogFragment;
import itboom.com.elgoud.blog.BlogsAdapater;
import itboom.com.elgoud.blog.mvp.BlogMVP;
import itboom.com.elgoud.blog.mvp.BlogModel;
import itboom.com.elgoud.blog.mvp.BlogPresenter;

@Module
public class BlogModule {
    @Provides
    public BlogFragment getBlogFragment() {
        return new BlogFragment();
    }

    @Provides
    public BlogMVP.Model getBlogModel(EljoudApi api) {
        return new BlogModel(api);
    }

    @Provides
    public BlogMVP.Presenter getPresenter(BlogMVP.Model model){
        return new BlogPresenter(model);
    }

    @Provides
    public BlogsAdapater getBlogsAdapater() {
        return new BlogsAdapater();
    }
}
