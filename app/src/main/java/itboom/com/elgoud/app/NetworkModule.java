package itboom.com.elgoud.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    public static final String BASE_URL = "https://joud-academy.com/api/";

    @Provides
    public Retrofit getRetrofitClient(OkHttpClient client, GsonConverterFactory converterFactory,
                                      RxJava2CallAdapterFactory adapterFactory){
        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .build();
    }

    @Provides
    public OkHttpClient getHttpClient(){
        return new OkHttpClient()
                .newBuilder()
                .build();
    }

    @Provides
    public GsonConverterFactory getGsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public RxJava2CallAdapterFactory getRxJavaCallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    public Gson getGson() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return gson;
    }

    @Provides
    public Picasso getPicasso(Application application){
        return Picasso.with(application);
    }

    @Singleton
    @Provides
    public EljoudApi getApi(Retrofit retrofit){
        return retrofit.create(EljoudApi.class);
    }
}

