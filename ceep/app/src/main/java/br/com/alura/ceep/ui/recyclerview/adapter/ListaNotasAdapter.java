package br.com.alura.ceep.ui.recyclerview.adapter;

import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notas;
    private final Context context;
    private final LayoutInflater inflater;
    private OnItemClickListener<Nota> onItemClickListener;

    public ListaNotasAdapter(@NonNull Context context, @NonNull List<Nota> notas) {

        this.context = context;
        this.notas = notas;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener<Nota> onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    @Override
    @NonNull
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCriada = inflater.inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {

        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {

        return notas.size();
    }

    public void adiciona(Nota nota) {

        incrementaPosicoes();

        this.notas.add(nota.getPosicao(), nota);

        notifyItemInserted(nota.getPosicao());
    }

    public void altera(Nota nota) {

        this.notas.set(nota.getPosicao(), nota);

        notifyItemChanged(nota.getPosicao());
    }

    public void remove(Nota nota) {

        this.notas.remove(nota);

        notifyItemRemoved(nota.getPosicao());

        decrementarPocisoes(nota);
    }

    public void troca(Nota notaPosicaoInicial, Nota notaPosicaoPara) {

        Collections.swap(notas, notaPosicaoInicial.getPosicao(), notaPosicaoPara.getPosicao());

        notifyItemMoved(notaPosicaoInicial.getPosicao(), notaPosicaoPara.getPosicao());
    }

    private void decrementarPocisoes(Nota notaBase) {

        for (Nota nota : notas) {

            if (nota.getPosicao() < notaBase.getPosicao()) {

                continue;
            }

            nota.setPosicao(nota.getPosicao() - 1);
        }
    }

    private void incrementaPosicoes() {

        for (Nota nota : notas) {

            nota.setPosicao(nota.getPosicao() + 1);
        }
    }

    public class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private final ConstraintLayout constraintLayout;
        private Nota nota;

        public NotaViewHolder(View itemView) {

            super(itemView);

            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            constraintLayout = itemView.findViewById(R.id.item_nota_constraint_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(nota);
                }
            });
        }

        public void vincula(Nota nota) {

            this.nota = nota;

            preencheCampos();
        }

        private void preencheCampos() {

            titulo.setText(nota.getTitulo());

            descricao.setText(nota.getDescricao());

            constraintLayout.setBackgroundColor(context.getResources().getColor(nota.getColor().getColorRes()));
        }

        public Nota getNota() {

            return nota;
        }
    }

}