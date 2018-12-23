package itboom.com.elgoud.slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.login.LoginActivity;
import itboom.com.elgoud.pojo.slider.Image;
import itboom.com.elgoud.pojo.slider.Slider;
import itboom.com.elgoud.pojo.slider.SliderResponse;
import itboom.com.elgoud.slider.mvp.SliderMVP;
import itboom.com.elgoud.views.EljoudButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rd.PageIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class WelcomeActivity extends AppCompatActivity implements SliderMVP.View {

    @BindView(R.id.skip_btn)
    EljoudButton skip;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.logo)
    ImageView logo;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @Inject
    SliderMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    String[] images = new String[3];

    @Inject
    EljoudApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ((App) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        presenter.setView(this);
        presenter.getImage("b0a7b18b414b52b5a6ca657d4acc7d1b67158058ef5090c726e7071fd6c8");

        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelection(1);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onSuccess(Slider[] sliders) {
        pageIndicatorView.setVisibility(View.VISIBLE);
        skip.setVisibility(View.VISIBLE);
        logo.setVisibility(View.GONE);

        for (int i = 0; i < sliders.length; i++) {
            images[i] = sliders[i].getUrl();
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (images != null && images[position] != null){
                    return ImageSliderFragment.getFragment(images[position]);
                } else {
                    return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
    }

    @Override
    public void onError(NetworkResult result) {
        pageIndicatorView.setVisibility(View.VISIBLE);
        skip.setVisibility(View.VISIBLE);
    }
}
