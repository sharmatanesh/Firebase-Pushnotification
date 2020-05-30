package com.example.dtdpush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button sendNotify;
    private RequestQueue requestQueue;
    private String URL="https://fcm.googleapis.com/fcm/send";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNotify=(Button) findViewById(R.id.SendNotify);

        requestQueue= Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        sendNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendNotification();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void sendNotification() throws JSONException {

        JSONObject mainObj=new JSONObject();
        try {
            mainObj.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","You are covid positive");
            JSONObject extraData=new JSONObject();
            extraData.put("brandId","puma");

            mainObj.put("notification",notificationObj);
            mainObj.put("data",extraData);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAJQjY6OA:APA91bGhNlyjEYmiCbovN4Ut44ufauZG3GBuTxlKtiL4PWYvhevB8N0DaGecY3Sef_rX48PWLUQXzJE_6sG8aFhRchbKjyo-Kj51SMMJK3ozLNGaWrspVu658rOnjXTG7cRABsa7vujz");
                    return header;
                }
            };

        requestQueue.add(request);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
