package br.com.alura.ceep.ui.enumerator;

import br.com.alura.ceep.R;

import androidx.annotation.ColorRes;

/**
 * @author Cristian Urbainski
 * @since 1.0 (02/06/21)
 */
public enum ColorEnum {
    AZUL(R.color.colorBackgroundNotaAzul),
    BRANCO(R.color.colorBackgroundNotaBranco),
    VERMELHO(R.color.colorBackgroundNotaVermelho),
    VERDE(R.color.colorBackgroundNotaVerde),
    AMARELO(R.color.colorBackgroundNotaAmarelo),
    LILAS(R.color.colorBackgroundNotaLilas),
    CINZA(R.color.colorBackgroundNotaCinza),
    MARROM(R.color.colorBackgroundNotaMarrom),
    ROXO(R.color.colorBackgroundNotaRoxo);

    ColorEnum(@ColorRes int colorRes) {

        this.colorRes = colorRes;
    }

    private final int colorRes;

    public int getColorRes() {

        return colorRes;
    }

}