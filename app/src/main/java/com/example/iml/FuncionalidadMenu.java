package com.example.iml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FuncionalidadMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionalidad_menu);
    }
    public void irPDF(View V){
        startActivity(new Intent(FuncionalidadMenu.this,Descargar_PDF.class));
    }
}
