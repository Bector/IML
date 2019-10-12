package com.example.iml;

public class Noticias {
    private String titulo;
    private String subtitulo;
    private String fecha;
    private String imagen;
    private String descripcion;

    public Noticias(String titulo, String subtitulo, String fecha, String imagen, String descripcion) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
