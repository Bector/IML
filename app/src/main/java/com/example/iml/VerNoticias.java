package com.example.iml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerNoticias extends AppCompatActivity {

    ListView lista;
    private static final String URL = "https://proyectosml.000webhostapp.com/consulta_noticias.php";
    List<Noticias> noticiasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_noticias);

        lista = findViewById(R.id.lista);
        noticiasList = new ArrayList<>();
        cargarNoticias();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent visorDetalles = new Intent(view.getContext(), detalles.class);
                Noticias noti = noticiasList.get(i);
                visorDetalles.putExtra("titulo", noti.getTitulo());
                visorDetalles.putExtra("subtitulo", noti.getSubtitulo());
                visorDetalles.putExtra("fecha", noti.getFecha());
                visorDetalles.putExtra("imagen", noti.getImagen());
                visorDetalles.putExtra("descripcion", noti.getDescripcion());
                startActivity(visorDetalles);
            }
        });

    }

    private void cargarNoticias() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject n = array.getJSONObject(i);
                        noticiasList.add(new Noticias(
                                n.getString("titulo"),
                                n.getString("subtitulo"),
                                n.getString("fecha"),
                                n.getString("imagen"),
                                n.getString("descripcion")

                        ));
                    }
                    lista.setAdapter(new Adaptador(VerNoticias.this, noticiasList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}