package com.example.iml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> implements View.OnClickListener {

    private Context mCtx;
    private List<PDF> pdfLista;

    private View.OnClickListener listener;

    public RecyclerViewAdaptador(Context mCtx,List<PDF> pdfLista) {
        this.mCtx=mCtx;
        this.pdfLista = pdfLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.item_pdf, null);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PDF pdf=pdfLista.get(position);


        holder.nombrePDF.setText(pdf.getNombrePdf());
        holder.txtUrl.setText(pdf.getUrlPdf());
    }

    public int getItemCount(){return pdfLista.size();}

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombrePDF;
        private TextView txtUrl;

        public ViewHolder( View itemView) {
            super(itemView);
            nombrePDF=(TextView)itemView.findViewById(R.id.nombrePDF);
            txtUrl=(TextView)itemView.findViewById(R.id.txtURL);
        }
    }

}

