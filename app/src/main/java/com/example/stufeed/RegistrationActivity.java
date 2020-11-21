package com.example.stufeed;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    private Button signup;
    private EditText rollno, username, password;
    private Spinner department, year, section;
    private String message;
    private RequestQueue queue;
    JsonObjectRequest registerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        queue = Volley.newRequestQueue(this);

        signup = findViewById(R.id.signup);
        rollno = findViewById(R.id.rollno);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        department = findViewById(R.id.department);
        year = findViewById(R.id.year);
        section = findViewById(R.id.section);

        final String[] dname = new String[1];
        final String[] yr = new String[1];
        final String[] sec = new String[1];

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dname[0] = adapterView.getSelectedItem().toString();
                System.out.println(dname[0] + " " + adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(getApplicationContext(), "Select your department", Toast.LENGTH_SHORT).show();

            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yr[0] = year.getSelectedItem().toString();
                System.out.println(yr[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),
                        "Select your Year",
                        Toast.LENGTH_SHORT).show();
                year.requestFocus();
            }
        });

        section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sec[0] = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select Your section", Toast.LENGTH_SHORT).show();
                section.requestFocus();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rno = rollno.getText().toString();
                String uname = username.getText().toString();
                String pass = password.getText().toString();
                System.out.println(rno + " " + uname);


                JSONObject data = new JSONObject();
                try {
                    data.put("rollno", rno);
                    data.put("username", uname);
                    data.put("password", pass);
                    data.put("department", dname[0]);
                    data.put("section", sec[0]);
                    data.put("year", yr[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String URL = "https://stufeed.herokuapp.com/register";

                registerRequest = new JsonObjectRequest(Request.Method.POST, URL, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            message = response.getString("message");
                            OpenLoginActivity(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(registerRequest);

            }


        });
    }


    public void OpenLoginActivity(String message) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}