package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText userName,userpass;
    TextView textView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login=findViewById(R.id.login);
        userName=findViewById(R.id.username);
        userpass=findViewById(R.id.password);
        login.setOnClickListener(v->{
            Log.d(TAG, "onCreate: "+userName.getText().toString());
                MyHttp.sendHttpRequest("http://10.0.2.2:8080/login_war_exploded/hello-servlet", userName.getText().toString(), userpass.getText().toString(), new HttpCallbackListener() {
                    @Override
                    public void Finish(String response) {
                        Log.d(TAG, "Finish:"+response);
                        if (login(response)){
                            runOnUiThread(()->{
                                Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            });
                        }else {
                            runOnUiThread(()->{
                                Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_LONG).show();
                            });
                        }

                    }

                    @Override
                    public void onError() {

                    }
                });
        });


    }

    private boolean login(String data) {
       try {
           JSONObject jsonObject=new JSONObject(data);

           if (jsonObject.getString("message").equals("yes")){
               Log.d(TAG, "login: "+jsonObject.getString("message"));
               return true;
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return false;
    }
}