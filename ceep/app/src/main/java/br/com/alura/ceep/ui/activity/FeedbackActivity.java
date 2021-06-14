package br.com.alura.ceep.ui.activity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.util.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Cristian Urbainski
 * @since 1.0 (11/06/21)
 */
public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);

        EditText txtInputEditTextEmail = findViewById(R.id.txtInputEditTextEmail);

        ViewUtil.showKeyboard(this, txtInputEditTextEmail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_feedback, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_feedback_ic_send) {

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {

        ViewUtil.closeKeyboard(this, getCurrentFocus());

        super.finish();
    }

    public static void newInstance(Context context) {

        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

}