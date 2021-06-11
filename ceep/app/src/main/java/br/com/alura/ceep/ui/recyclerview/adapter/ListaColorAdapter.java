package br.com.alura.ceep.ui.recyclerview.adapter;

import java.util.Arrays;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.enumerator.ColorEnum;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaColorAdapter.ViewHolder;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Cristian Urbainski
 * @since 1.0 (01/06/21)
 */
public class ListaColorAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<ColorEnum> listaColor = Arrays.asList(ColorEnum.values());
    private final LayoutInflater inflater;
    private final Context context;
    private OnItemClickListener<ColorEnum> onItemClickListener;

    public ListaColorAdapter(Context context) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener<ColorEnum> onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ColorEnum colorEnum = listaColor.get(position);
        holder.bind(colorEnum);
    }

    @Override
    public int getItemCount() {

        return listaColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View itemCorCirculo;
        private ColorEnum colorEnum;

        public ViewHolder(View view) {

            super(view);

            itemCorCirculo = view.findViewById(R.id.item_cor_circulo);
        }

        public void bind(final ColorEnum colorEnum) {

            this.colorEnum = colorEnum;

            preencheCampos();
        }

        private void preencheCampos() {

            Drawable wrapDrawable = DrawableCompat.wrap(itemCorCirculo.getBackground());

            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, colorEnum.getColorRes()));

            itemCorCirculo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener == null) {

                        return;
                    }

                    onItemClickListener.onItemClick(colorEnum);
                }
            });
        }

    }

}