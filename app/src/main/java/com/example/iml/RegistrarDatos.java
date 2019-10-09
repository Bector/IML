package com.example.iml;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegistrarDatos extends AppCompatActivity {

    TextView ver;
    EditText nombres, apellidos, edad, pais, correo, profesion, institucion, contrasena, rptContrasena;
    CheckBox medicina, odontologia, antropologia, arqueologia, juridica, bioquimica, genetica, psicologia, trabajoSocial, ddhh;
    ImageView imgView;
    StringBuffer area;
    private static final int SELECT_FILE = 1;


    /*
    private static final String carpeta_principal="imgenesApp/";
    private static final String carpeta_imagen="imagenes";
    private static final String directorio_imagen=carpeta_principal+carpeta_imagen;
    private String path;
    File fileImagen;
    Bitmap bitmap;
    private static final int cod_selecciona=10;
    private static final int cod_foto=20;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos);

    }

    private void mostrarNotificacion(String cadena) {
        Toast.makeText(getApplicationContext(), cadena, Toast.LENGTH_SHORT).show();
    }

    private boolean validarCampos(String p1, String p2, String p3, String p4,
                                  String p5, String p6, String p7, String p8,
                                  String p9) {

        boolean estado = false;

        if (p1.isEmpty() || p2.isEmpty() || p3.isEmpty() || p4.isEmpty() || p5.isEmpty() || p6.isEmpty() || p7.isEmpty() || p8.isEmpty() || p9.isEmpty()) {
            estado = true;
        }
        return estado;
    }

    public void inicializarDatos() {
        nombres = (EditText) findViewById(R.id.EditNombre);
        apellidos = (EditText) findViewById(R.id.EditApellido);
        edad = (EditText) findViewById(R.id.EditEdad);
        pais = (EditText) findViewById(R.id.EditPais);
        correo = (EditText) findViewById(R.id.EditCorreo);
        profesion = (EditText) findViewById(R.id.EditProfesion);
        institucion = (EditText) findViewById(R.id.EditInstitucion);
        contrasena = (EditText) findViewById(R.id.EditContrasena);
        rptContrasena = (EditText) findViewById(R.id.EditRptContrasena);
        medicina = (CheckBox) findViewById(R.id.checkMedicina);
        odontologia = (CheckBox) findViewById(R.id.checkOdontologia);
        antropologia = (CheckBox) findViewById(R.id.checkAntropologia);
        arqueologia = (CheckBox) findViewById(R.id.checkArqueologia);
        juridica = (CheckBox) findViewById(R.id.checkJuridica);
        bioquimica = (CheckBox) findViewById(R.id.checkBioquimica);
        genetica = (CheckBox) findViewById(R.id.checkGenetica);
        psicologia = (CheckBox) findViewById(R.id.checkPsicologia);
        trabajoSocial = (CheckBox) findViewById(R.id.checkTrabajoSocial);
        ddhh = (CheckBox) findViewById(R.id.checkDDHH);
        imgView = (ImageView) findViewById(R.id.imageView);

    }

    public void registrarDatos(View V) {

        this.inicializarDatos();

        area = new StringBuffer();

        CheckBox areas[] = {medicina, odontologia, antropologia,
                arqueologia, juridica, bioquimica, genetica,
                psicologia, trabajoSocial, ddhh};

        int c = 0;
        for (int i = 0; i < areas.length; i++) {
            if (areas[i].isChecked()) {
                area = area.append(areas[i].getText().toString() + " ");
                c++;
            }
        }


        if (this.validarCampos(nombres.getText().toString(), apellidos.getText().toString()
                , edad.getText().toString(), pais.getText().toString(), correo.getText().toString()
                , profesion.getText().toString(), institucion.getText().toString(), contrasena.getText().toString()
                , rptContrasena.getText().toString())) {

            this.mostrarNotificacion("Rellene todos los campos por favor.");

        } else {
            if (c == 0) {
                this.mostrarNotificacion("Debe seleccionar algún tema de interés.");
            } else {
                if (contrasena.getText().toString().equals(rptContrasena.getText().toString())) {
                    this.ejecutarServicio("http://proyectosml.000webhostapp.com/insertar.php");
                    startActivity(new Intent(RegistrarDatos.this, actividad_login.class));
                } else {
                    this.mostrarNotificacion("La contraseña debe coincidir.");
                }
            }
        }

    }

    private void ejecutarServicio(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mostrarNotificacion("Registro exitoso.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarNotificacion(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombres.getText().toString());
                parametros.put("apellido", apellidos.getText().toString());
                parametros.put("edad", edad.getText().toString());
                parametros.put("pais", pais.getText().toString());
                parametros.put("correo", correo.getText().toString());
                parametros.put("profesion", profesion.getText().toString());
                parametros.put("institucion", institucion.getText().toString());
                parametros.put("contrasena", contrasena.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void subirImg(View V){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Seleccione una imagen"),
                    SELECT_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == RegistrarDatos.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            imgView= (ImageView) findViewById(R.id.imageView);
                            imgView.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }

    /*
    CODIGO PARA CARGAR IMAGEN EN PROCESO(NO FUNCIONAL)

    public void cargarOpciones(){
        final CharSequence[] opciones={"Tomar Foto","Elegir de la galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(RegistrarDatos.this);
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Tomar Foto")){
                    mostrarNotificacion("en proceso.");
                }else{
                    if(opciones[i].equals("Elegir de la galeria")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),cod_selecciona);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case cod_selecciona:
                ver=(TextView)findViewById(R.id.ver);
                try {
                    Uri miPath=data.getData();
                    imgView.setImageURI(miPath);

                }catch (Exception error){
                    mostrarNotificacion("Error : "+error);
                }
                break;
        }
    }*/
}
