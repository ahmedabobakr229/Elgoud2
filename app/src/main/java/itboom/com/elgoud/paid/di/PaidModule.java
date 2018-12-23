package itboom.com.elgoud.paid.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.paid.PaidFragment;
import itboom.com.elgoud.paid.mvp.PaidMVP;
import itboom.com.elgoud.paid.mvp.PaidModel;
import itboom.com.elgoud.paid.mvp.PaidPresenter;

@Module
public class PaidModule {
    @Provides
    public PaidFragment getPaidFragment(){
        return new PaidFragment();
    }

    @Provides
    public PaidMVP.Presenter getPresenter(PaidMVP.Model model){
        return new PaidPresenter(model);
    }

    @Provides
    public PaidMVP.Model getModel(EljoudApi api){
        return new PaidModel(api);
    }
}
