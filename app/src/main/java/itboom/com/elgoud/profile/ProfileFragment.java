package itboom.com.elgoud.profile;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.ChangePasswordFragment;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.profile.mvp.ProfileMVP;
import itboom.com.elgoud.views.EljoudButton;
import itboom.com.elgoud.views.EljoudEditText;
import itboom.com.elgoud.views.EljoudTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileMVP.View, ChangePasswordFragment.PasswordCallback {

    @BindView(R.id.actualName)
    EljoudTextView actualName;

    @BindView(R.id.actualMail)
    EljoudTextView actualMail;

    @BindView(R.id.userImage)
    RoundedImageView userImage;

    @BindView(R.id.username)
    EljoudEditText username;

    @BindView(R.id.email)
    EljoudEditText email;

    @BindView(R.id.mobile)
    EljoudEditText phone;

    @BindView(R.id.save_btn)
    EljoudButton saveBtn;

    @Inject
    ProfileMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    Image image;
    ProgressDialog dialog;
    ChangePasswordFragment changePasswordFragment;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void setImage(Image image) {
        Log.i("profile_fragment", "setImage: ");
        this.image = image;
        userImage.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        presenter.setView(this);
        changePasswordFragment = new ChangePasswordFragment();
        changePasswordFragment.setCallback(this);
        changePasswordFragment.setCancelable(false);

        actualName.setText(preferences.getString(Constants.USERNAME, null));
        actualMail.setText(preferences.getString(Constants.EMAIL, null));

        username.setText(preferences.getString(Constants.USERNAME, null));
        email.setText(preferences.getString(Constants.EMAIL, null));
        if (preferences.getString(Constants.PHONE, null) != null)
            phone.setText(preferences.getString(Constants.PHONE, null));

        if (preferences.getString(Constants.PP_URL, null) != null){
            Transformation transformation = new RoundedTransformationBuilder()
                    .oval(false)
                    .build();

            Picasso.with(getActivity())
                    .load(preferences.getString(Constants.PP_URL, null))
                    .fit()
                    .transform(transformation)
                    .into(userImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("PicassoImage", "onSuccess: ");
                        }

                        @Override
                        public void onError() {
                            Log.i("PicassoImage", "onError: ");
                        }
                    });
        }

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).pickImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordFragment.show(getFragmentManager(), null);
            }
        });

        return view;
    }


    @Override
    public void onUpdateSuccess(User user) {
        dialog.dismiss();
        preferences.edit()
                .putString(Constants.API_TOKEN, user.getApiToken())
                .putString(Constants.EMAIL, user.getEmail())
                .putString(Constants.USERNAME, user.getName())
                .putString(Constants.PHONE, user.getPhone())
                .apply();

        //Log.i("user_pp", "onUpdateSuccess: " + user.getApiToken() + "----" + user.getId());
        if (user.getAvatar() != null && user.getAvatar().getUrl() != null){
            preferences.edit().putString(Constants.PP_URL, user.getAvatar().getUrl()).apply();
            if (getActivity() != null)
                ((MainActivity) getActivity()).updateProfilePicture();
        }

        actualName.setText(user.getName());
        actualMail.setText(user.getEmail());
        username.setText(user.getName());
        phone.setText(user.getPhone());
    }

    @Override
    public void onUpdateFailure(NetworkResult result) {
        dialog.dismiss();
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getMobile() {
        return phone.getText().toString();
    }

    @Override
    public void usernameError() {
        dialog.dismiss();
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void mobileError() {
        dialog.dismiss();
        phone.setError(getString(R.string.phone_error));
    }

    @Override
    public void onClose() {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
        }

        if (changePasswordFragment.isVisible())
            changePasswordFragment.dismiss();
        dialog.show();

        if (image != null) {
            presenter.updateProfile(preferences.getString(Constants.API_TOKEN, null),
                    preferences.getInt(Constants.USER_ID, 0), image);
        } else {
            presenter.updateProfile(preferences.getString(Constants.API_TOKEN, null),
                    preferences.getInt(Constants.USER_ID, 0));
        }
    }

    @Override
    public void onUpdatePassword(String password, String changePassword) {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
        }

        if (changePasswordFragment.isVisible())
            changePasswordFragment.dismiss();
        dialog.show();

        if (image != null) {
            presenter.updateProfile(preferences.getString(Constants.API_TOKEN, null),
                    preferences.getInt(Constants.USER_ID, 0), password, changePassword, image);
        } else {
            presenter.updateProfile(preferences.getString(Constants.API_TOKEN, null),
                    preferences.getInt(Constants.USER_ID, 0), password, changePassword);
        }
    }
}
