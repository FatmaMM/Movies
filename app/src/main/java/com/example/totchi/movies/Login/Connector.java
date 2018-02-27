package com.example.totchi.movies.Login;


import android.content.Context;
import android.content.Intent;

import com.example.totchi.movies.MainActivity;

class Connector {
    LoginView loginView;
    Context context;

    public Connector(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    /**
     * move to main activity
     *
     * @param email
     * @param password
     */
    public void Login(String email, String password) {
        context.startActivity(new Intent(context, MainActivity.class));
        loginView.hideProgress();

    }
}
