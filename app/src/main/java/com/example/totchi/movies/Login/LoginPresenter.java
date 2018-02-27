package com.example.totchi.movies.Login;



public interface LoginPresenter {
    void validate(String username, String password);
    void onDestroy();
}
