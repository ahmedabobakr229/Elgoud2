package itboom.com.elgoud.login;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import itboom.com.elgoud.MainActivity;
import itboom.com.elgoud.R;
import itboom.com.elgoud.app.App;
import itboom.com.elgoud.app.Constants;
import itboom.com.elgoud.app.NetworkResult;
import itboom.com.elgoud.login.mvp.LoginMVP;
import itboom.com.elgoud.pojo.user.User;
import itboom.com.elgoud.register.RegisterActivity;
import itboom.com.elgoud.register.mvp.RegisterMVP;
import itboom.com.elgoud.views.EljoudButton;
import itboom.com.elgoud.views.EljoudEditText;
import itboom.com.elgoud.views.EljoudTextView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View, RegisterMVP.View {

    @BindView(R.id.register)
    EljoudTextView register;

    @BindView(R.id.email)
    EljoudEditText email;

    @BindView(R.id.password)
    EljoudEditText password;

    @BindView(R.id.login_btn)
    EljoudButton login;

    @Inject
    SharedPreferences preferences;

    @Inject
    LoginMVP.Presenter presenter;

    @Inject
    RegisterMVP.Presenter registerPresenter;

    ProgressDialog dialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().inject(this);

        registerPresenter.setView(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        presenter.setView(this);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                presenter.login();
            }
        });

        LoginButton loginButton = findViewById(R.id.login_facebook);
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // App Code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivityFB", response.toString());
                                try {
                                    Log.i("FB_DATA", "onCompleted: " + object.getString("name") + " " + object.getString("email"));
                                    dialog.show();
                                    registerPresenter.fbRegister(object.getString("name"),
                                            object.getString("email"), object.getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("public_profile", "email")
                );
            }
        });

    }

    @Override
    public String getLoginEmail() {
        return email.getText().toString();
    }

    @Override
    public String getLoginPassword() {
        return password.getText().toString();
    }

    @Override
    public void onLoginEmailError() {
        dialog.dismiss();
        email.setError(getString(R.string.email_error));
    }

    @Override
    public void onLoginPasswordError() {
        dialog.dismiss();
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void onLoginFailure(NetworkResult result) {
        dialog.dismiss();
        Toast.makeText(this, result.name().replaceAll("_", " ").toLowerCase(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess(User user) {
        dialog.dismiss();
        preferences.edit()
                .putBoolean(Constants.LOGGED_IN, true)
                .putString(Constants.API_TOKEN, user.getApiToken())
                .putInt(Constants.USER_ID, user.getId())
                .putString(Constants.EMAIL, user.getEmail())
                .putString(Constants.USERNAME, user.getName())
                .putString(Constants.PHONE, user.getPhone()).apply();

        if (user.getAvatar() != null && user.getAvatar().getUrl() != null){
            preferences.edit().putString(Constants.PP_URL, user.getAvatar().getUrl()).apply();
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.rxUnsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getRegisterUsername() {
        return null;
    }

    @Override
    public String getRegisterEmail() {
        return null;
    }

    @Override
    public String getRegisterPhone() {
        return null;
    }

    @Override
    public String getRegisterPassword() {
        return null;
    }

    @Override
    public String getRegisterConfirmPassword() {
        return null;
    }

    @Override
    public void onRegisterUsernameError() {

    }

    @Override
    public void onRegisterEmailError() {

    }

    @Override
    public void onRegisterPhoneError() {

    }

    @Override
    public void onRegisterPasswordError() {

    }

    @Override
    public void onRegisterConfirmPasswordError() {

    }

    @Override
    public void onRegisterSuccess(User user) {
        preferences.edit().putBoolean(Constants.IS_FACEBOOK_USER, true).apply();
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
}
