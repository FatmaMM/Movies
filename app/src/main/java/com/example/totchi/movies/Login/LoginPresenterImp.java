package com.example.totchi.movies.Login;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.totchi.movies.R;


public class LoginPresenterImp implements LoginPresenter {

    LoginView loginView;

    LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void validate(final String email, final String password) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(loginView.getContext());
        SharedPreferences.Editor prefEditor = preferences.edit();

        if (loginView != null) {
            loginView.showProgress();
        }
        if (!email.isEmpty() && !password.isEmpty()) {
            if (email.contains("@") && email.contains(".")) {

                loginView.showProgress();

                prefEditor.putString("email", email).apply();
                prefEditor.putString("password", password).apply();

                Connector connector = new Connector(loginView.getContext(), loginView);
                connector.Login(email, password);

            } else {
                loginView.hideProgress();
                Toast.makeText(loginView.getContext(), loginView.getContext().getString(R.string.real_email), Toast.LENGTH_LONG).show();
            }
        } else if (email.isEmpty() && password.isEmpty()) {
            loginView.hideProgress();
            Toast.makeText(loginView.getContext(), loginView.getContext().getString(R.string.empty_mail_or_password), Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            loginView.hideProgress();
            loginView.setEmailError();
        } else if (password.isEmpty()) {
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

}
