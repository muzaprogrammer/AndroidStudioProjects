package com.edu.sv.Parcial3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.sv.Parcial3.databinding.ItemProductoBinding;
import com.edu.sv.Parcial3.interfaces.Servicio;
import com.edu.sv.Parcial3.models.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Producto> productos;
    private Activity context;

    public ProductAdapter(Activity context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos != null ? productos.size() : 0;
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductoBinding itemProductoBinding;

        public ProductViewHolder(View view) {
            super(view);
            itemProductoBinding = ItemProductoBinding.bind(view);
        }

        public void bind(Producto producto) {
            if (producto.getCodigo().equals("recarge") && producto.getDescripcion().equals("recarge") && producto.getPrecio() == 1) {
                itemProductoBinding.tvCodigo.setText("Recargue los datos por favor!");
                itemProductoBinding.tvDescripcion.setText("");
                itemProductoBinding.tvPrecio.setText("");
            } else {
                itemProductoBinding.tvCodigo.setText("Código      : " + producto.getCodigo());
                itemProductoBinding.tvDescripcion.setText("Descripción : " + producto.getDescripcion());
                itemProductoBinding.tvPrecio.setText("Precio      : " + producto.getPrecio());
            }

            itemProductoBinding.cardProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            itemProductoBinding.cardProducto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });

        }
    }
}