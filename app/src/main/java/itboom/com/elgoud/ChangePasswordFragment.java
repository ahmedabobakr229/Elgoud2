package itboom.com.elgoud;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.views.EljoudButton;
import itboom.com.elgoud.views.EljoudEditText;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class ChangePasswordFragment extends DialogFragment {

    @BindView(R.id.password)
    EljoudEditText password;

    @BindView(R.id.confirm_password)
    EljoudEditText confirmPassword;

    @BindView(R.id.save_btn)
    EljoudButton save;

    @BindView(R.id.close)
    ImageView close;

    PasswordCallback callback;

    public void setCallback(PasswordCallback callback) {
        this.callback = callback;
    }

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ButterKnife.bind(this, view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()){
                    callback.onUpdatePassword(password.getText().toString(), confirmPassword.getText().toString());
                } else {
                    if (password.getText().toString().isEmpty()){
                        password.setError(getString(R.string.password_error));
                    } else {
                        confirmPassword.setError(getString(R.string.confirm_pass_error));
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClose();
            }
        });

        return view;
    }

    public interface PasswordCallback {
        void onClose();
        void onUpdatePassword(String password, String changePassword);
    }
}
