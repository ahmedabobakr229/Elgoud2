package itboom.com.elgoud.contact_us;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.contact_us.mvp.ContactUsMVP;
import itboom.com.elgoud.pojo.base.BaseResponse;
import itboom.com.elgoud.views.EljoudButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

public class ContactUsFragment extends Fragment implements ContactUsMVP.View {

    @BindView(R.id.name)
    TextInputEditText name;

    @BindView(R.id.message)
    TextInputEditText message;

    @BindView(R.id.send)
    EljoudButton send;

    @BindView(R.id.contactUsProgress)
    ProgressBar progressBar;

    @Inject
    SharedPreferences preferences;

    @Inject
    ContactUsMVP.Presenter presenter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contact_us, container, false);

        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, view);

        progressBar.setVisibility(View.GONE);

        presenter.setView(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !message.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.contactUs(
                            preferences.getString(Constants.API_TOKEN, null),
                            name.getText().toString(),
                            message.getText().toString(),
                            preferences.getInt(Constants.USER_ID, 0)
                    );
                } else {
                    Toast.makeText(getActivity(), "name and message must not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onContactUsSuccess(BaseResponse response) {
        Toast.makeText(getActivity(), "your message sent successfully", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onContactUsFailure(NetworkResult result) {
        Toast.makeText(getActivity(), result.name().replaceAll("_", " ").toLowerCase(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}
