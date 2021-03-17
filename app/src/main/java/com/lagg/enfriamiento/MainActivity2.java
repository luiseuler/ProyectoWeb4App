package com.lagg.enfriamiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    //RecyclerView rvMsg;
    SharedPreferences sesion;
    String lista[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //rvMsg = findViewById(R.id.rvMsg);
        //rvMsg.setLayoutManager(new LinearLayoutManager(this));

        sesion = getSharedPreferences("sesion", 0);
        getSupportActionBar().setTitle("Registros de : " + sesion.getString("user", ""));

        llenar();
    }

    private void llenar() {
        String url = Uri.parse(Config.URL + "temperatura.php")
                .buildUpon()
                .build().toString();
        JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        llenarRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error de red", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> h = new HashMap<>();
                h.put("Authorization", sesion.getString("token", "Error"));
                return h;
            }
        };
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);
    }




    private void llenarRespuesta(JSONArray response) {
        try {
            lista = new String[response.length()][5];
            for(int i = 0; i < response.length(); i++){
                lista[i][0] = response.getJSONObject(i).getString("id");
                lista[i][1] = response.getJSONObject(i).getString("temperatura_ini");
                lista[i][2] = response.getJSONObject(i).getString("Fecha");
            }
            //rvMsg.setAdapter(new Adaptador(lista));
        }catch (Exception e){

        }
    }
}