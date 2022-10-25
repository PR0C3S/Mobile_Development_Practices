package com.pucmm.examen1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData> {

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView articulo, descripcion, price;
        Button delete,share;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            articulo = itemView.findViewById(R.id.articulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            price = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.item_btndelete);
            share = itemView.findViewById(R.id.item_btnshare);
        }
    }
    @NonNull
    @Override
    public AdapterData.ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout , parent , false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.ViewHolderData holder, @SuppressLint("RecyclerView") int position) {

        Data data = MainActivity.listaDatos.get(position);

        holder.articulo.setText(data.getArticulo());
        holder.descripcion.setText(data.getDescripcion());
        holder.price.setText(String.valueOf(data.getPrice()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Se ha eliminado el objeto con el nombre: "+MainActivity.listaDatos.get(position).getArticulo()+".",Toast.LENGTH_LONG).show();
                MainActivity.listaDatos.remove(position);
                MainActivity.loadDatos();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.listaDatos.get(position).getDescripcion());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                view.getContext().startActivity(shareIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.listaDatos.size();
    }
}

