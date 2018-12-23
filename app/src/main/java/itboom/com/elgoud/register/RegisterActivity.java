package itboom.com.elgoud.register;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.login.LoginActivity;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.register.mvp.RegisterMVP;
import itboom.com.elgoud.views.EljoudButton;
import itboom.com.elgoud.views.EljoudEditText;
import itboom.com.elgoud.views.EljoudTextView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements RegisterMVP.View {

    @BindView(R.id.username)
    EljoudEditText username;

    @BindView(R.id.email)
    EljoudEditText email;

    @BindView(R.id.password)
    EljoudEditText password;

    @BindView(R.id.confirm_password)
    EljoudEditText confirmPassword;

    @BindView(R.id.phone)
    EljoudEditText phone;

    @BindView(R.id.have_account)
    EljoudTextView haveAccount;

    @BindView(R.id.register)
    EljoudButton register;

    @BindView(R.id.parent)
    LinearLayout parent;

    @Inject
    RegisterMVP.Presenter presenter;

    @Inject
    SharedPreferences preferences;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);

        presenter.setView(this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                presenter.register();
            }
        });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public String getRegisterUsername() {
        return username.getText().toString();
    }

    @Override
    public String getRegisterEmail() {
        return email.getText().toString();
    }

    @Override
    public String getRegisterPhone() {
        return phone.getText().toString();
    }

    @Override
    public String getRegisterPassword() {
        return password.getText().toString();
    }

    @Override
    public String getRegisterConfirmPassword() {
        return confirmPassword.getText().toString();
    }

    @Override
    public void onRegisterUsernameError() {
        dialog.dismiss();
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void onRegisterEmailError() {
        dialog.dismiss();
        email.setError(getString(R.string.email_error));
    }

    @Override
    public void onRegisterPhoneError() {
        dialog.dismiss();
        phone.setError(getString(R.string.phone_error));
    }

    @Override
    public void onRegisterPasswordError() {
        dialog.dismiss();
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void onRegisterConfirmPasswordError() {
        dialog.dismiss();
        confirmPassword.setError(getString(R.string.confirm_pass_error));
    }

    @Override
    public void onRegisterSuccess(User user) {
        dialog.dismiss();
        preferences.edit()
                .putBoolean(Constants.LOGGED_IN, true)
                .putString(Constants.API_TOKEN, user.getApiToken())
                .putInt(Constants.USER_ID, user.getId())
                .putString(Constants.EMAIL, user.getEmail())
                .putString(Constants.USERNAME, user.getName())
                .putString(Constants.PHONE, user.getPhone())
                .apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRegisterFailure(NetworkResult result) {
        dialog.dismiss();
        Toast.makeText(this, result.name().toLowerCase().replaceAll("_", " "), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        presenter.rxUnsubscribe();
        super.onDestroy();
    }
}
