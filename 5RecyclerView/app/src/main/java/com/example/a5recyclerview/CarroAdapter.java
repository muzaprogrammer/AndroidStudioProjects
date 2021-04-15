package com.example.a5recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5recyclerview.Carro;

import java.util.List;
public class CarroAdapter
        extends RecyclerView.Adapter<CarroAdapter.MyItemViewHolder> {

    private List<Carro> items;
    private Context context;

    public CarroAdapter(List<Carro> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carro_card,parent,false);
        return new MyItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, final int position) {
        holder.imageView.setImageResource(items.get(position).getIdImagen());
        holder.tvNombre.setText(items.get(position).getNombre());
        holder.tvLikes.setText("Likes : " + items.get(position).getLikes() );
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Carro seleccionado : " +
                                items.get(position).getNombre(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvNombre, tvLikes;
        RelativeLayout relativeLayout;
        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagen);
            tvNombre = itemView.findViewById(R.id.nombre);
            tvLikes = itemView.findViewById(R.id.likes);
            relativeLayout = itemView.findViewById(R.id.relativelayout);
        }
    }
}