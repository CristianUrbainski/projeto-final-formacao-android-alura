package br.com.alura.ceep.ui.activity;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.enumerator.ColorEnum;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaColorAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FormularioNotaActivity extends AppCompatActivity {

    private Nota nota;
    private TextView titulo;
    private TextView descricao;
    private RecyclerView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_formulario_nota);

        setTitle(R.string.insere_nota);

        inicializaCampos();

        Intent dadosRecebidos = getIntent();

        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {

            setTitle(R.string.altera_nota);

            nota = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
        } else {

            nota = new Nota();
        }

        preencheCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (ehMenuSalvaNota(item)) {

            atualizarDadosNota();

            retornaNota();

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheCampos() {

        titulo.setText(nota.getTitulo());

        descricao.setText(nota.getDescricao());

        changeBackgroundColor();
    }

    private void inicializaCampos() {

        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        color = findViewById(R.id.formulario_nota_color);

        configuraColorRecyclerView();
    }

    private void configuraColorRecyclerView() {

        final ListaColorAdapter coresAdapter = new ListaColorAdapter(this);

        coresAdapter.setOnItemClickListener(new OnItemClickListener<ColorEnum>() {
            @Override
            public void onItemClick(ColorEnum colorEnum) {

                nota.setColor(colorEnum);

                changeBackgroundColor();
            }
        });

        color.setAdapter(coresAdapter);
    }

    private void retornaNota() {

        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);

        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    private void changeBackgroundColor() {

        View rootView = getWindow().getDecorView().getRootView();

        rootView.setBackgroundColor(getResources().getColor(nota.getColor().getColorRes()));
    }

    private void atualizarDadosNota() {

        nota.setTitulo(titulo.getText().toString());

        nota.setDescricao(descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(MenuItem item) {

        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

}