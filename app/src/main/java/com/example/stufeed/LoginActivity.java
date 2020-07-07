package com.example.stufeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText rollno;
    private EditText password;

    private RequestQueue queue;
    private Button loginbtn;
    private JsonObjectRequest loginRequest;

    static String acc_tkn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue= Volley.newRequestQueue(this);

        rollno = findViewById(R.id.rollno1);
        password = findViewById(R.id.password1);

        loginbtn = findViewById(R.id.login);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rno=rollno.getText().toString();
                String pass=rollno.getText().toString();


                JSONObject data = new JSONObject();

                try {
                    data.put("rollno",rno);
                    data.put("password",pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String URL="https://stufeed.herokuapp.com/login";
                loginRequest=new JsonObjectRequest(Request.Method.POST, URL, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            acc_tkn = response.getString("access_token");
                            Toast.makeText(getApplicationContext(), acc_tkn, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(loginRequest);

            }
        });
    }
}