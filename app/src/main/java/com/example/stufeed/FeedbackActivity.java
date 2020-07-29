package com.example.stufeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private JsonObjectRequest feedbackRequest;
    private String tid;
    private TextView teacher_name, teacher_department, teacher_designation;
    private ImageView teacher_image;
    private Button feedbackbtn;
    private EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        initViews();
        Intent intent = getIntent();

        if (null != intent) {
            tid = intent.getStringExtra("tid");
        }


        JSONObject data;
        data = new JSONObject();
        try {
            data.put("tid", tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL = "https://stufeed.herokuapp.com/pteacher?tid="+tid;

        JsonObjectRequest teacherRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    teacher_name.setText(response.getString("username"));
                    teacher_department.setText(response.getString("department"));
                    teacher_designation.setText((response.getString("designation")));
                    Glide.with(getApplicationContext()).asBitmap().load(response.getString("image")).into(teacher_image);
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


        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "https://stufeed.herokuapp.com/feedback";

                JSONObject data = new JSONObject();

                String fb = feedback.getText().toString();

                if(fb.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid feedback",Toast.LENGTH_SHORT).show();
                }

                else if(fb.length() < 3){
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid Feedback", Toast.LENGTH_SHORT).show();
                }

                else{
                    try {
                        data.put("t_id",tid);
                        data.put("feedback",fb);
                        System.out.print(fb);
                        System.out.println(URL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    feedbackRequest = new JsonObjectRequest(Request.Method.POST, URL, data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                                OpenDashBoardActivity();
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
                            Map<String, String> params = new HashMap<>();
                            params.put("Authorization", "Bearer " + HomePage.acc_tkn);
                            return params;
                        }
                    };

                    RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                    queue1.add(feedbackRequest);
                }
            }
        });


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(teacherRequest);

    }

    public void initViews() {
        teacher_name = findViewById(R.id.teacher_name);
        teacher_department = findViewById(R.id.teacher_department);
        teacher_designation = findViewById(R.id.teacher_designation);
        teacher_image = findViewById(R.id.teacher_image);
        feedbackbtn = findViewById(R.id.feedbackbtn);
        feedback = findViewById(R.id.feedbackInput);
    }


    private void OpenDashBoardActivity(){
        Intent intent = new Intent(FeedbackActivity.this,HomePage.class);
        startActivity(intent);
    }
}