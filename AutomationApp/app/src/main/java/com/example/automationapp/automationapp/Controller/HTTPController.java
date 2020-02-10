package com.example.automationapp.automationapp.Controller;

import android.os.AsyncTask;

import com.example.automationapp.automationapp.TriggerService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import DTO.Function;
import DTO.FunctionAction;
import DTO.Trigger;

/**
 * Created by laiwf on 04/05/2017.
 */

public class HTTPController {
    private TriggerService mService;
    private Function function;

    public HTTPController(Function function, TriggerService service){
        mService=service;
        this.function=function;
        new RequestTask().execute();
    }

    class RequestTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... uri) {
            try {
                FunctionAction fa=function.getAction();
                HashMap<String,String> parameters=fa.getParameter();
                if(parameters.containsKey("url")){
                    String request=parameters.get("url");
                    URL url = new URL(request);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int response = conn.getResponseCode();
                    if(response==HttpURLConnection.HTTP_OK){
                        mService.setNotification(function.getName(),function.getSuccess());
                    }else{
                        mService.setNotification(function.getName(),function.getFail());
                    }
                    System.out.println(response);
                }
            }catch (IOException ex){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
