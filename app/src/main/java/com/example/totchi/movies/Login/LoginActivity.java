package com.example.totchi.movies.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.totchi.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.email_log_in)
    EditText emailEditText;
    @BindView(R.id.password_log_in)
    EditText passwordEditText;
    LoginPresenterImp imp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        ButterKnife.bind(this);

        imp = new LoginPresenterImp(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String Email = preferences.getString("email", "");
        String pass = preferences.getString("password", "");
        if (!Email.isEmpty()) {
            Connector connector = new Connector(LoginActivity.this, this);
            connector.Login(Email, pass);
            finish();
        }
    }


    @Override
    public void setEmailError() {
        emailEditText.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        passwordEditText.setError(getString(R.string.password_error));
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.log_in_button)
    void Login() {
        /* check internet connection */
        if (!isNetworkConnected()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Check your internet connection and try again")
                    .setCancelable(false)
                    .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else
            imp.validate(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
    }

    /**
     * check network status
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
