package com.example.stufeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {


    private JSONObject profileData;
    private JsonObjectRequest profileDataRequest;
    private TextView name, department, rollno, year, section;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        initView();

        String URL = "https://stufeed.herokuapp.com/user/";

        profileDataRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            rollno.setText(response.getString("rollno"));
                            name.setText(response.getString("username"));
                            section.setText(response.getString("section"));
                            department.setText(response.getString("department"));
                            year.setText(response.getString("year"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + HomePage.acc_tkn);
                return params;
            }
        };

        queue = Volley.newRequestQueue(this);
        queue.add(profileDataRequest);

    }


    public void initView() {
        name = findViewById(R.id.student_name);
        rollno = findViewById(R.id.student_rollno);
        section = findViewById(R.id.student_section);
        department = findViewById(R.id.student_department);
        year = findViewById(R.id.student_year);
    }
}