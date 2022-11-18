package com.pucmm.practica3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.github.drjacky.imagepicker.ImagePicker;
import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Models.ProductoViewModel;
import com.pucmm.practica3.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ProductoViewModel productoViewModel;
    private Producto act;
    private String path;
    private Uri uri;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        Bundle bundle = getArguments();
        String json = bundle.getString("producto", MainActivity.gson.toJson(new Producto()));

        act = MainActivity.gson.fromJson(json, Producto.class);
        if (act.getId() == null) {
            binding.btnDelete.setVisibility(View.GONE);
        } else {
            binding.btnDelete.setVisibility(View.VISIBLE);
        }
        if (act.getPrecio() > 0d) {
            binding.edPrecioC.setText(act.getPrecio().toString());
        }
        binding.edDescripcionC.setText(act.getDescripcion());
        binding.edProductoC.setText(act.getNombre());

        binding.imageViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Deseas eliminar este producto?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        productoViewModel.delete(act);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putString("msg", "Se ha eliminado el producto: "+act.getNombre()+".");
                                        Navigation.findNavController(view).navigate(R.id.FirstFragment, bundle1);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Aviso");
                titulo.show();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nombre = binding.edProductoC.getText().toString();
                String descripcion = binding.edDescripcionC.getText().toString();
                String precio = binding.edPrecioC.getText().toString();

                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(getContext(), "Ingrese el nombre del producto.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descripcion)) {
                    Toast.makeText(getContext(), "Ingrese la descrippcion del producto", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(precio)) {
                    Toast.makeText(getContext(), "Ingrese el precio del producto", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(Double.valueOf(precio) < 1d)
                    {
                        Toast.makeText(getContext(), "El precio del producto debe ser mayor a 0.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                act.setNombre(nombre);
                act.setPrecio(Double.valueOf(precio));
                act.setDescripcion(descripcion);
                String mensaje = "";
                if (act.getId() == null) {
                    productoViewModel.insert(act);
                    mensaje="Se ha agregado el producto: " + act.getNombre() + ".";
                } else {
                    productoViewModel.update(act);
                    mensaje="Se ha actualizado el producto: " + act.getNombre() + ".";
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("msg", mensaje);
                Navigation.findNavController(view).navigate(R.id.FirstFragment, bundle1);

            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.edProductoC.getText())) {
                    binding.edProductoC.setText("");
                }
                if (!TextUtils.isEmpty(binding.edDescripcionC.getText())) {
                    binding.edDescripcionC.setText("");
                }
                if (!TextUtils.isEmpty(binding.edPrecioC.getText())) {
                    binding.edPrecioC.setText("");
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}