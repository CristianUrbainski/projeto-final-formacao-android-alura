package br.com.alura.ceep.ui.recyclerview.helper.callback;

import br.com.alura.ceep.database.repository.NotaRepository;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private final NotaRepository notaRepository;

    public NotaItemTouchHelperCallback(@NonNull ListaNotasAdapter adapter) {

        this.adapter = adapter;
        this.notaRepository = new NotaRepository();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
            @NonNull RecyclerView.ViewHolder target) {

        Nota notaPosicaoInicial = ((ListaNotasAdapter.NotaViewHolder) viewHolder).getNota();
        Nota notaPosicaoPara = ((ListaNotasAdapter.NotaViewHolder) target).getNota();
        trocaNotas(notaPosicaoInicial, notaPosicaoPara);
        return true;
    }

    private void trocaNotas(Nota notaPosicaoInicial, Nota notaPosicaoPara) {

        notaRepository.troca(notaPosicaoInicial, notaPosicaoPara);

        adapter.troca(notaPosicaoInicial, notaPosicaoPara);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        Nota nota = ((ListaNotasAdapter.NotaViewHolder) viewHolder).getNota();

        removeNota(nota);
    }

    private void removeNota(Nota nota) {

        notaRepository.remove(nota);

        adapter.remove(nota);
    }

}