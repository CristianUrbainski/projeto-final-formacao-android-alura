package br.com.alura.ceep.database.repository;

import java.util.List;

import br.com.alura.ceep.database.CeepDatabase;
import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

import androidx.room.Transaction;

/**
 * @author Cristian Urbainski
 * @since 1.0 (04/06/21)
 */
public class NotaRepository {

    private final NotaDAO notaDAO;

    public NotaRepository() {

        notaDAO = CeepDatabase.getInstance().notaDAO();
    }

    @Transaction
    public void insere(Nota nota) {

        notaDAO.incrementarPosicoes();

        Long id = notaDAO.insere(nota);

        nota.setId(id);
    }

    public void altera(Nota nota) {

        notaDAO.altera(nota);
    }

    @Transaction
    public void remove(Nota nota) {

        notaDAO.decrementarPosicoes(nota.getPosicao());

        notaDAO.remove(nota);
    }

    public List<Nota> todos() {

        return notaDAO.todos();
    }

    @Transaction
    public void troca(Nota notaPosicaoInicial, Nota notaPosicaoPara) {

        int posicaoInicial = notaPosicaoInicial.getPosicao();
        int posicaoPara = notaPosicaoPara.getPosicao();

        notaPosicaoInicial.setPosicao(posicaoPara);

        altera(notaPosicaoInicial);

        notaPosicaoPara.setPosicao(posicaoInicial);

        altera(notaPosicaoPara);
    }

}