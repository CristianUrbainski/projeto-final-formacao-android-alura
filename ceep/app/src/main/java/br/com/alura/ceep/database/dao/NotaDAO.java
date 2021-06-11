package br.com.alura.ceep.database.dao;

import java.util.List;

import br.com.alura.ceep.model.Nota;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotaDAO {

    @Query("select * from nota order by posicao asc")
    List<Nota> todos();

    @Insert
    Long insere(Nota nota);

    @Update
    Integer altera(Nota nota);

    @Delete
    Integer remove(Nota nota);

    @Query("update nota set posicao = posicao + 1")
    void incrementarPosicoes();

    @Query("update nota set posicao = posicao - 1 where posicao > :posicaoRemovida")
    void decrementarPosicoes(Integer posicaoRemovida);

}