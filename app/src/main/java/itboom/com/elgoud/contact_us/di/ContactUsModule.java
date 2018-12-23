package itboom.com.elgoud.contact_us.di;

import dagger.Module;
import dagger.Provides;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.contact_us.ContactUsFragment;
import itboom.com.elgoud.contact_us.mvp.ContactUsMVP;
import itboom.com.elgoud.contact_us.mvp.ContactUsModel;
import itboom.com.elgoud.contact_us.mvp.ContactUsPresenter;

@Module
public class ContactUsModule {
    @Provides
    public ContactUsFragment getContactUsFragment() {
        return new ContactUsFragment();
    }

    @Provides
    public ContactUsMVP.Model getModel(EljoudApi api) {
        return new ContactUsModel(api);
    }

    @Provides
    public ContactUsMVP.Presenter getPresenter(ContactUsMVP.Model model){
        return new ContactUsPresenter(model);
    }
}
