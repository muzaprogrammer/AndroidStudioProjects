package com.example.a4gridview;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
public class AdaptadorDePlayas extends BaseAdapter {
    private Activity context;

    public AdaptadorDePlayas(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MainActivity.ITEMS.length;
    }

    @Override
    public Playa getItem(int position) {
        return MainActivity.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenPlaya = view.findViewById(R.id.imagen_playa);
        TextView nombrePlaya =  view.findViewById(R.id.nombre_playa);

        final Playa item = getItem(position);
        Glide.with(imagenPlaya.getContext())
                .load(item.getIdImagen())
                .into(imagenPlaya);

        nombrePlaya.setText(item.getNombre());
        return view;
    }
}