package br.com.alura.ceep.ui.activity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.util.PreferencesUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Cristian Urbainski
 * @since 1.0 (03/06/21)
 */
public class SplashScreenActivity extends AppCompatActivity {

    private PreferencesUtil preferencesUtil;
    private boolean isPrimeiraExecucaoApp = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        preferencesUtil = PreferencesUtil.getInstance(getApplication());

        isPrimeiraExecucaoApp = preferencesUtil.isPrimeiraExecucaoApp();

        long time = getTimeDelayCloseSplashScreen();

        new Handler(Looper.myLooper()).postDelayed(() -> {

            ListaNotasActivity.newInstace(this);

            finish();
        }, time);
    }

    @Override
    public void finish() {

        if (isPrimeiraExecucaoApp) {

            preferencesUtil.setAppJaExecutado();
        }

        super.finish();
    }

    private long getTimeDelayCloseSplashScreen() {

        if (isPrimeiraExecucaoApp) {

            return 2000L;
        } else {

            return 500L;
        }
    }

}