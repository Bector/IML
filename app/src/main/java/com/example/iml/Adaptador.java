package com.example.iml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater=null;

    Context contexto;
    List<Noticias> listaNoticias;

    public Adaptador(Context contexto, List<Noticias> listaNoticias){

        this.contexto=contexto;
        this.listaNoticias=listaNoticias;

        inflater=(LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return listaNoticias.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista = inflater.inflate(R.layout.elemento, null);

        Noticias noti=listaNoticias.get(i);

        TextView titulo = (TextView) vista.findViewById(R.id.textView2);
        TextView subtitulo = (TextView) vista.findViewById(R.id.textView3);
        TextView fecha = (TextView) vista.findViewById(R.id.textView4);

        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView);


        titulo.setText(noti.getTitulo());
        subtitulo.setText(noti.getSubtitulo());
        fecha.setText(noti.getFecha());

        Picasso.get()
                .load(noti.getImagen())
                .resize(1500,1000)
                .centerCrop()
                .into(imagen);

        return vista;
    }
}
