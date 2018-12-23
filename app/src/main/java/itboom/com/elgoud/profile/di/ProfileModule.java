package itboom.com.elgoud.profile.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.profile.ProfileFragment;
import itboom.com.elgoud.profile.mvp.ProfileMVP;
import itboom.com.elgoud.profile.mvp.ProfileModel;
import itboom.com.elgoud.profile.mvp.ProfilePresenter;

@Module
public class ProfileModule {
    @Provides
    public ProfileFragment getProfileFragment(){
        return new ProfileFragment();
    }

    @Provides
    public ProfileMVP.Model getModel(EljoudApi api) {
        return new ProfileModel(api);
    }

    @Provides
    public ProfileMVP.Presenter getPresenter(ProfileMVP.Model model){
        return new ProfilePresenter(model);
    }
}
