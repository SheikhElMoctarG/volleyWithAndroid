package com.sheikh.connecttoserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    JSONArray posts = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPosts();
        new Handler().postDelayed(()-> {
            try {
                connectToRecyclerView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, 5000);

    }
    public void connectToRecyclerView() throws JSONException {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        List<listItem> listOfPosts = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = posts.getJSONObject(i);
            listOfPosts.add(new listItem(post.getString("title")));
        }
        MyAdapture myAdapture = new MyAdapture(listOfPosts, this);
        recyclerView.setAdapter(myAdapture);
    }
    public void getPosts(){
        RequestQueue queue = Volley.newRequestQueue(this);;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/posts", response -> {
            try {
                posts = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        queue.add(stringRequest);
    }
}