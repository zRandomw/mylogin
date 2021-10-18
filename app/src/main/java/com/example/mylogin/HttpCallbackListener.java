package com.example.mylogin;

public interface HttpCallbackListener {
    void Finish(String response);
    void onError();
}
