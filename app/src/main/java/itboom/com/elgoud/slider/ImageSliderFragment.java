package itboom.com.elgoud.slider;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.EljoudApi;
import itboom.com.elgoud.pojo.slider.SliderResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSliderFragment extends Fragment {

    @BindView(R.id.roundedImage)
    RoundedImageView imageView;

    @BindView(R.id.sliderProgress)
    ProgressBar progressBar;



    public ImageSliderFragment() {
        // Required empty public constructor
    }

    public static ImageSliderFragment getFragment(String resource){
        ImageSliderFragment fragment = new ImageSliderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("resource", resource);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);
        ButterKnife.bind(this, view);


        if (getArguments() != null && getArguments().getString("resource", null) != null){
            Picasso.with(getActivity()).load(getArguments().getString("resource")).into(imageView);
            progressBar.setVisibility(View.GONE);
        }

        return view;
    }

}
