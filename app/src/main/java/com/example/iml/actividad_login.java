package com.example.iml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class actividad_login extends AppCompatActivity {

    EditText etCorreo, etContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void inicializarDatos(){
        this.etCorreo=(EditText)findViewById(R.id.etCorreo);
        this.etContrasena=(EditText)findViewById(R.id.etContrasena);
    }

    public void mostrarNotificacion(String cadena){//Metodo para mostrar un toast a travez de un parametro.
        Toast.makeText(getApplicationContext(),cadena,Toast.LENGTH_SHORT).show();
    }

    public void IniciarSesion(View v) {

        this.inicializarDatos();

        validarUsuarios("http://proyectosml.000webhostapp.com/validarusuario.php");


    }

    public void registrarse(View V){
        startActivity(new Intent(actividad_login.this,RegistrarDatos.class));
    }

    private void validarUsuarios(String URL){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (etCorreo.getText().toString().isEmpty() || etContrasena.getText().toString().isEmpty()){
                    Toast.makeText(actividad_login.this,"Debes rellenar todos los campos para iniciar sesion",Toast.LENGTH_SHORT).show();
                }else{
                    if (!response.isEmpty()){
                        Intent intent=new Intent(getApplicationContext(),FuncionalidadMenu.class);
                        startActivity(intent);
                        actividad_login.this.finish();
                    }else{
                        Toast.makeText(actividad_login.this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actividad_login.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",etCorreo.getText().toString());
                parametros.put("password",etContrasena.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void cambiarContrasena(View V){
        mostrarNotificacion("En proceso...");
    }

}
