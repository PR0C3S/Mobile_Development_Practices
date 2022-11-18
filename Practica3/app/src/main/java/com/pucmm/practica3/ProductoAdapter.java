package com.pucmm.practica3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Models.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoAdapterViewHolder>  {

    public List<Producto> mList = new ArrayList<>();

    public void setmList(List<Producto> lista) {
        this.mList = lista;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductoAdapter.ProductoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ProductoAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Producto act = mList.get(position);
        holder.tvDescripcion.setText(act.getDescripcion());
        holder.tvProducto.setText(act.getId()+" - "+act.getNombre());
        holder.tvPrecio.setText(act.getPrecio().toString());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Se ha agregao al carrito",Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("producto",MainActivity.gson.toJson(mList.get(position)));
                Navigation.findNavController(view).navigate(R.id.SecondFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ProductoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvPrecio, tvDescripcion;
        Button btnAdd, btnEdit;
        public ProductoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvNombreA);
            tvPrecio = itemView.findViewById(R.id.tvPrecioA);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionA);
            btnAdd = itemView.findViewById(R.id.btnAddCartA);
            btnEdit = itemView.findViewById(R.id.btnEditA);
        }
    }
}
