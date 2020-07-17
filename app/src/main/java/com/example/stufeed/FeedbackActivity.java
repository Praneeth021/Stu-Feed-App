package com.example.stufeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private JsonObjectRequest teacherRequest;
    private RequestQueue queue;
    private String tid;
    Teachers teacher;
    private JSONObject data;


    private TextView teacher_name, teacher_department, teacher_designation;
    private ImageView teacher_image;
    private Button feedbackbtn;
    private TextInputEditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        initViews();
        Intent intent = getIntent();

        if (null != intent) {
            tid = intent.getStringExtra("tid");
        }


        data = new JSONObject();
        try {
            data.put("tid", tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL = "https://stufeed.herokuapp.com/pteacher";
        teacherRequest = new JsonObjectRequest(Request.Method.GET, URL, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    teacher_name.setText(object.getString("username"));
                    teacher_designation.setText(object.getString("designation"));
                    teacher_department.setText(object.getString("department"));
                    Glide.with(getApplicationContext()).asBitmap().load(object.getString("image")).into(teacher_image);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
        queue.add(teacherRequest);
    }

    public void initViews() {
        teacher_name = findViewById(R.id.teacher_name);
        teacher_department = findViewById(R.id.teacher_department);
        teacher_designation = findViewById(R.id.teacher_designation);
        feedbackbtn = findViewById(R.id.feedbackbtn);
        feedback = findViewById(R.id.feedbackInput);
    }
}