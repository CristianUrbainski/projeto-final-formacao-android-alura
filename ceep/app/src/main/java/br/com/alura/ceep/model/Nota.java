package br.com.alura.ceep.model;

import java.io.Serializable;

import br.com.alura.ceep.ui.enumerator.ColorEnum;

import androidx.annotation.NonNull;

public class Nota implements Serializable {

    private String titulo;
    private String descricao;
    private ColorEnum color;

    public Nota() {

        this.color = ColorEnum.BRANCO;
    }

    public Nota(String titulo, String descricao, @NonNull ColorEnum color) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.color = color;
    }

    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;
    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public ColorEnum getColor() {

        return color;
    }

    public void setColor(ColorEnum color) {

        this.color = color;
    }

}