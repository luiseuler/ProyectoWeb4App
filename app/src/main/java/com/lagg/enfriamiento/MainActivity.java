package com.lagg.enfriamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextInputEditText etUser, etPass;
    Button btnIn;
    SharedPreferences sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.e_user);
        etPass = findViewById(R.id.e_pass);
        btnIn = findViewById(R.id.btn_in);

        sesion = getSharedPreferences("sesion", 0);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String url = Uri.parse(Config.URL + "login.php")
                .buildUpon()
                .appendQueryParameter("user", etUser.getText().toString())
                .appendQueryParameter("pass", etPass.getText().toString())
                .build().toString();

        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            respuesta(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);
    }

    private void respuesta(JSONObject response) throws JSONException {
        if(response.getString("login").compareTo("s") == 0){

            String jwt = response.getString("token");
            SharedPreferences.Editor editor = sesion.edit();
            editor.putString("token", jwt);
            editor.putString("user", etUser.getText().toString());
            editor.commit();

            startActivity(new Intent(this, MainActivity2.class));
        }else{
            Toast.makeText(this, "Error de Usuario/Contraseña", Toast.LENGTH_SHORT).show();
        }
    }
}