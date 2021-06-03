package br.com.alura.ceep.ui.activity;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.enumerator.ListaNotasViewLayoutEnum;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;
import br.com.alura.ceep.util.PreferencesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private ListaNotasViewLayoutEnum layoutView;
    private PreferencesUtil preferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_notas);

        setTitle(R.string.notas);

        preferencesUtil = PreferencesUtil.getInstance(getApplication());

        layoutView = preferencesUtil.getListaNotasLayoutView();

        if (layoutView == null) {

            layoutView = ListaNotasViewLayoutEnum.LIST;
        }

        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem = null;

        switch (layoutView) {
            case LIST:
                menuItem = menu.findItem(R.id.menu_lista_notas_ic_list);
                break;
            case GRID:
                menuItem = menu.findItem(R.id.menu_lista_notas_ic_grid);
                break;
        }

        if (menuItem != null) {

            menuItem.setVisible(false);

            return true;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ListaNotasViewLayoutEnum layoutView = null;

        if (item.getItemId() == R.id.menu_lista_notas_ic_grid) {

            layoutView = ListaNotasViewLayoutEnum.GRID;
        } else if (item.getItemId() == R.id.menu_lista_notas_ic_list) {

            layoutView = ListaNotasViewLayoutEnum.LIST;
        }

        if (layoutView != null) {

            this.layoutView = layoutView;

            atualizaListaNotasLayoutView();

            configuraListaNotasRecyclerViewLayoutManager();

            invalidateOptionsMenu();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void atualizaListaNotasLayoutView() {

        preferencesUtil.insere(this.layoutView);
    }

    private void configuraBotaoInsereNota() {

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {

        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {

        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {

                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);

                adiciona(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {

                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);

                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

                if (ehPosicaoValida(posicaoRecebida)) {

                    altera(notaRecebida, posicaoRecebida);
                }
            }
        }
    }

    private void altera(Nota nota, int posicao) {

        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {

        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {

        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {

        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {

        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {

        return ehCodigoRequisicaoInsereNota(requestCode) && temNota(data);
    }

    private boolean temNota(Intent data) {

        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {

        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {

        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
        configuraListaNotasRecyclerViewLayoutManager(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {

        adapter = new ListaNotasAdapter(this, todasNotas);
        adapter.setOnItemClickListener(new OnItemClickListener<Nota>() {
            @Override
            public void onItemClick(Nota nota, int posicao) {

                vaiParaFormularioNotaActivityAltera(nota, posicao);
            }
        });

        listaNotas.setAdapter(adapter);
    }

    private void configuraListaNotasRecyclerViewLayoutManager() {

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraListaNotasRecyclerViewLayoutManager(listaNotas);
    }

    private void configuraListaNotasRecyclerViewLayoutManager(RecyclerView listaNotas) {

        LayoutManager layoutManager = null;

        switch (layoutView) {
            case LIST:
                layoutManager = new LinearLayoutManager(this);
                break;
            case GRID:
                layoutManager = new GridLayoutManager(this, 2);
                break;
        }

        listaNotas.setLayoutManager(layoutManager);
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {

        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    public static void newInstace(Context context) {

        Intent intent = new Intent(context, ListaNotasActivity.class);
        context.startActivity(intent);
    }

}