package br.com.alura.ceep.ui.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;

/**
 * @author Cristian Urbainski
 * @since 1.0 (13/06/21)
 */
public abstract class ViewUtil {

    public static void showKeyboard(@NonNull Context context, @NonNull EditText editText) {

        editText.requestFocus();

        editText.postDelayed(() -> {

            getInputMethodManager(context).showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }, 100);
    }

    public static void closeKeyboard(@NonNull Context context, @NonNull View view) {

        getInputMethodManager(context).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static InputMethodManager getInputMethodManager(@NonNull Context context) {

        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

}