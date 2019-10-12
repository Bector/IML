package com.example.iml;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Descargar_PDF extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdaptador recyclerViewAdaptador;

    String nombrePDF;

    private static final String URL = "https://proyectosml.000webhostapp.com/verPDF.php";

    List<PDF> pdfList;

    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargar__pdf);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPDF);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pdfList = new ArrayList<>();

        loadPdf();
    }

    private void loadPdf() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject pdf = array.getJSONObject(i);
                        pdfList.add(new PDF(
                                pdf.getString("nombre"),
                                pdf.getString("url")
                        ));
                    }
                    RecyclerViewAdaptador recyclerViewAdaptador = new RecyclerViewAdaptador(Descargar_PDF.this, pdfList);

                    recyclerViewAdaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            nombrePDF = pdfList.get(recyclerView.getChildAdapterPosition(view)).getNombrePdf();

                            String urlADescargar = pdfList.get(recyclerView.getChildAdapterPosition(view)).getUrlPdf();

                            ProgressDialog progressDialog = new ProgressDialog(Descargar_PDF.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setMessage("Descargando PDF...");

                            new DescargarPDFAsyncTask(progressDialog).execute(urlADescargar);

                            downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri=Uri.parse(urlADescargar);
                            DownloadManager.Request request=new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            Long reference=downloadManager.enqueue(request);
                        }
                    });

                    recyclerView.setAdapter(recyclerViewAdaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        Volley.newRequestQueue(this).add(stringRequest);


    }

    class DescargarPDFAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        DescargarPDFAsyncTask(ProgressDialog progressDialog){
            this.progressDialog=progressDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urlPDF) {
            String urlADescargar=urlPDF[0];

            HttpURLConnection conexion=null;
            InputStream input=null;
            OutputStream output=null;


            try {
                java.net.URL url=new URL(urlADescargar);

                conexion=(HttpURLConnection) url.openConnection();
                conexion.connect();

                if(conexion.getResponseCode()!=HttpURLConnection.HTTP_OK){
                    return "Conexion no realizada correctamente";
                }

                input=conexion.getInputStream();
                String rutaFicheroGuardado= getFilesDir()+"/"+nombrePDF+".pdf";

                output=new FileOutputStream(rutaFicheroGuardado);

                byte[] data=new byte[1024];
                int count;

                while((count=input.read(data))!=-1){
                    output.write(data, 0, count);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error1: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error2: "+e.getMessage();
            }finally {
                try {
                    if(input!=null) input.close();
                    if(output!=null) output.close();
                    if(conexion!=null) conexion.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "Se realizo la descarga correctamente";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
