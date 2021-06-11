package br.com.alura.ceep;

import br.com.alura.ceep.database.CeepDatabase;

import android.app.Application;

/**
 * @author Cristian Urbainski
 * @since 1.0 (04/06/21)
 */
public class CeepApplication extends Application {

    @Override
    public void onCreate() {

        // inicializando room database
        CeepDatabase.getInstance(this);

        super.onCreate();
    }

}
