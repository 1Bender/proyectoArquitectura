package com.example.arquitecturaandroid.DTO;

import java.util.List;

public class Notas {

    private List<Double> matematicas;

    private List<Double> social;

    private List<Double> lengua;

    private List<Double> dibujo;

    private String id;

    public Notas() {

    }

    public List<Double> getMatematicas() {
        return matematicas;
    }

    public void setMatematicas(Double nuevaNota) {

        this.matematicas.add(nuevaNota);
    }

    public List<Double> getSocial() {
        return social;
    }

    public void setSocial(Double nuevaNota) {

        this.social.add(nuevaNota);
    }

    public List<Double> getLengua() {
        return lengua;
    }

    public void setLengua(Double nuevaNota) {

        this.lengua.add(nuevaNota);
    }

    public List<Double> getDibujo() {
        return dibujo;
    }

    public void setDibujo(Double nuevaNota) {

        this.dibujo.add(nuevaNota);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
