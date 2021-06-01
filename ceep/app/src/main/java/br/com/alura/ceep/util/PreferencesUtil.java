package br.com.alura.ceep.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import androidx.annotation.NonNull;

/**
 * @author Cristian Urbainski
 * @since 1.0 (31/05/21)
 */
public final class PreferencesUtil {

    private static String PREFERENCES_NAME = "ceep-preferences";
    private static String PREFERENCE_LISTA_NOTAS_LAYOUT = "lista-notas-layout";
    private final SharedPreferences preferences;
    private static PreferencesUtil INSTACE;

    private PreferencesUtil(Application application) {

        this.preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void insere(@NonNull ListaNotasLayoutView layoutView) {

        Editor editor = this.preferences.edit();

        editor.putInt(PREFERENCE_LISTA_NOTAS_LAYOUT, layoutView.ordinal());

        editor.apply();
    }

    public ListaNotasLayoutView getListaNotasLayoutView() {

        int pref = this.preferences.getInt(PREFERENCE_LISTA_NOTAS_LAYOUT, -1);

        if (pref == -1) {

            return null;
        }

        for (ListaNotasLayoutView listaNotasLayoutView : ListaNotasLayoutView.values()) {

            if (listaNotasLayoutView.ordinal() == pref) {

                return listaNotasLayoutView;
            }
        }

        return null;
    }

    public static PreferencesUtil getInstance(@NonNull Application application) {

        if (INSTACE == null) {

            INSTACE = new PreferencesUtil(application);
        }

        return INSTACE;
    }

}