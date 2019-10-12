package com.example.iml;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class detalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);


        TextView titulo = (TextView) findViewById(R.id.t1);
        TextView subtitulo = (TextView) findViewById(R.id.t3);
        TextView fecha = (TextView) findViewById(R.id.t4);
        TextView descripcion = (TextView) findViewById(R.id.t2);
        ImageView img = (ImageView) findViewById(R.id.imageView2);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            titulo.setText(b.getString("titulo"));
            subtitulo.setText(b.getString("subtitulo"));
            fecha.setText(b.getString("fecha"));

            Picasso.get()
                    .load(b.getString("imagen"))
                    .resize(720,500)
                    .centerCrop()
                    .into(img);

            descripcion.setText(b.getString("descripcion"));

        };
    }
}
