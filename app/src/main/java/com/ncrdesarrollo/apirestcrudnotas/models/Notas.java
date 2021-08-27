package com.ncrdesarrollo.apirestcrudnotas.models;

public class Notas {

    private String id;
    private String titulo;
    private String nota;
    private String imagen;

    public Notas() {
    }

    public Notas(String id, String titulo, String nota, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.nota = nota;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
