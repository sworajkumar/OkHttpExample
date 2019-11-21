package com.cpets.sp.okhttpexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult=(TextView)findViewById(R.id.text_view_result);
        OkHttpClient client = new OkHttpClient();

        String url = "https://reqres.in/api/users?page=2";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    try {
                        JSONObject jsonObject=new JSONObject(myResponse);
                        String page=jsonObject.getString("page");
                        String per_page=jsonObject.getString("per_page");
                        String total=jsonObject.getString("total");
                        String total_pages=jsonObject.getString("total_pages");
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        if (jsonArray.length()>0){
                            for (int i=0 ; i<jsonArray.length() ; i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String id=jsonObject1.getString("id");
                                String email=jsonObject1.getString("email");
                                String first_name=jsonObject1.getString("first_name");
                                String last_name=jsonObject1.getString("last_name");
                                String avatar=jsonObject1.getString("avatar");
                                Log.i("Result",jsonObject1.toString());
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });
    }
}
