package br.com.alura.ceep.model;

import java.io.Serializable;

import br.com.alura.ceep.ui.enumerator.ColorEnum;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "nota")
public class Nota implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_nota")
    private Long id;
    @NonNull
    @ColumnInfo(name = "titulo")
    private String titulo;
    @NonNull
    @ColumnInfo(name = "descricao")
    private String descricao;
    @NonNull
    @ColumnInfo(name = "color")
    private ColorEnum color;
    @NonNull
    @ColumnInfo(name = "posicao")
    private Integer posicao = 0;

    public Nota() {

        this.color = ColorEnum.BRANCO;
    }

    @NonNull
    public Long getId() {

        return id;
    }

    public void setId(@NonNull Long id) {

        this.id = id;
    }

    @NonNull
    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {

        this.titulo = titulo;
    }

    @NonNull
    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {

        this.descricao = descricao;
    }

    @NonNull
    public ColorEnum getColor() {

        return color;
    }

    public void setColor(@NonNull ColorEnum color) {

        this.color = color;
    }

    @NonNull
    public Integer getPosicao() {

        return posicao;
    }

    public void setPosicao(@NonNull Integer posicao) {

        this.posicao = posicao;
    }

}