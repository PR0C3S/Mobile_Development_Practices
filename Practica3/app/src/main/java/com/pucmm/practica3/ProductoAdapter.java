package com.pucmm.practica3;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Models.ProductoViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoAdapterViewHolder> {

    public List<Producto> mList = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

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
        holder.tvProducto.setText(act.getNombre());
        holder.tvPrecio.setText(act.getPrecio().toString());

        StorageReference imageRef = storage.getReference();
        imageRef.child(act.getFotoLocation()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                holder.imageView.setImageURI(uri);
                Picasso.get().load(uri).placeholder(R.drawable.ic_baseline_photo_24)
                        .error(R.drawable.ic_baseline_photo_24)
                        .into(holder.imageView);
            }
        });


        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Se ha agregado al carrito", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("producto", MainActivity.gson.toJson(mList.get(position)));
                Navigation.findNavController(view).navigate(R.id.SecondFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ProductoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvPrecio, tvDescripcion;
        ImageView imageView;
        Button btnAdd, btnEdit;

        public ProductoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewA);
            tvProducto = itemView.findViewById(R.id.tvNombreA);
            tvPrecio = itemView.findViewById(R.id.tvPrecioA);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionA);
            btnAdd = itemView.findViewById(R.id.btnAddCartA);
            btnEdit = itemView.findViewById(R.id.btnEditA);

        }
    }
}
