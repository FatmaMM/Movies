package com.example.totchi.movies.Login;


import android.content.Context;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    Context getContext();

}
