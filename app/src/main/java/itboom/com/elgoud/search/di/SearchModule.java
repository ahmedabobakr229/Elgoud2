package itboom.com.elgoud.search.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.search.SearchAdapter;
import itboom.com.elgoud.search.SearchFragment;
import itboom.com.elgoud.search.mvp.SearchMVP;
import itboom.com.elgoud.search.mvp.SearchModel;
import itboom.com.elgoud.search.mvp.SearchPresenter;

@Module
public class SearchModule {

    @Provides
    public SearchFragment getSearchFragment() {
        return new SearchFragment();
    }

    @Provides
    public SearchMVP.Model getModel(EljoudApi api){
        return new SearchModel(api);
    }

    @Provides
    public SearchMVP.Presenter getPresenter(SearchMVP.Model model){
        return new SearchPresenter(model);
    }

    @Provides
    public SearchAdapter getAdapter(){
        return new SearchAdapter();
    }
}
