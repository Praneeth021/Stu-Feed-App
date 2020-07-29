package com.example.stufeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {


    static  String acc_tkn;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private MaterialToolbar toolbar;
    private RecyclerView teacherRecView;
    private ArrayList<Teachers> teachers;
    private TeachersRecViewAdapter teachersRecViewAdapter;

    private RequestQueue queue;
    private StringRequest teachersRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initViews();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView= findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        Intent intent = new Intent(HomePage.this, ProfilePage.class);
                        startActivity(intent);break;
                    case R.id.dashboard:
                        Intent intent1 = new Intent(HomePage.this, HomePage.class);
                        startActivity(intent1);;break;
                    case R.id.logout:
                        Intent intent2 = new Intent(HomePage.this, LoginActivity.class);
                        startActivity(intent2);break;
                    default:
                        return true;
                }

                return false;
            }
        });




        String URL = "https://stufeed.herokuapp.com/t_feedback";

        teachers = new ArrayList<>();

        teachersRequest = new StringRequest(Request.Method.GET, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Teachers teacher = new Teachers(object.getString("tid"),object.getString("username"),object.getString("department"),object.getString("gender"),object.getString("image"),object.getString("designation"),object.getString("email"),object.getString("date_joined"));
                        teachers.add(teacher);
                    }
                    teachersRecViewAdapter = new TeachersRecViewAdapter(getApplicationContext());
                    teachersRecViewAdapter.setTeachers(teachers);
                    teacherRecView.setAdapter(teachersRecViewAdapter);
                    teacherRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","Bearer "+acc_tkn);

                return params;
            }
        };

        queue = Volley.newRequestQueue(this);
        queue.add(teachersRequest);


    }




    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        teacherRecView = findViewById(R.id.teachersRecView);
    }
}