package com.castraining.app_castraining_publica.api;

import com.google.gson.annotations.SerializedName;

public class DatosConvocatoria {

    @SerializedName("post_content")
    private String descripcion;
    @SerializedName("post_title")
    private String title;
    @SerializedName("post_type")
    private String tipo;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
