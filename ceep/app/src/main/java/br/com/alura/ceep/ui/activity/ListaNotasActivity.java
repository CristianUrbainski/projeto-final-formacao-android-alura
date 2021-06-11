package br.com.alura.ceep.ui.activity;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.database.repository.NotaRepository;
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
    private NotaRepository notaRepository;

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

        notaRepository = new NotaRepository();

        configuraRecyclerView();
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

                altera(notaRecebida);
            }
        }
    }

    private void altera(Nota nota) {

        notaRepository.altera(nota);

        adapter.altera(nota);
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {

        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {

        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {

        notaRepository.insere(nota);

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

    private void configuraRecyclerView() {

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(listaNotas);
        configuraItemTouchHelper(listaNotas);
        configuraListaNotasRecyclerViewLayoutManager(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(RecyclerView listaNotas) {

        final List<Nota> notas = notaRepository.todos();

        adapter = new ListaNotasAdapter(this, notas);
        adapter.setOnItemClickListener(new OnItemClickListener<Nota>() {
            @Override
            public void onItemClick(Nota nota) {

                vaiParaFormularioNotaActivityAltera(nota);
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

    private void vaiParaFormularioNotaActivityAltera(Nota nota) {

        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    public static void newInstace(Context context) {

        Intent intent = new Intent(context, ListaNotasActivity.class);
        context.startActivity(intent);
    }

}