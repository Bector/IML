package com.example.iml;

public class PDF {
    private String nombrePdf;
    private String urlPdf;


    public PDF(String nombrePdf, String urlPdf) {
        this.nombrePdf = nombrePdf;
        this.urlPdf=urlPdf;
    }

    public String getNombrePdf() {
        return nombrePdf;
    }

    public void setNombrePdf(String nombrePdf) {
        this.nombrePdf = nombrePdf;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = nombrePdf;
    }
}

