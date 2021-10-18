package com.example.mylogin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttp {
    public static void sendHttpRequest(final String address,String userName,String userpass,final HttpCallbackListener listener){
        new Thread(()->{
            HttpURLConnection connection=null;
            try {
                URL url=new URL(address);
                connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                DataOutputStream out=new DataOutputStream(connection.getOutputStream());
                out.writeBytes("userName="+userName+"&userpass="+userpass);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in =connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuffer response=new StringBuffer();
                String line;
                while ((line=reader.readLine())!=null){
                    response.append(line);
                }
                if (listener!=null){
                    listener.Finish(response.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }
        }).start();
    }
}
