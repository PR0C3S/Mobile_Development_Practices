package com.pucmm.practica3;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Models.ProductoViewModel;
import com.pucmm.practica3.databinding.FragmentSecondBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ProductoViewModel productoViewModel;
    private Producto act;
    private String path = "images/";
    private FirebaseStorage storage;
    private byte[] image = null;
    private Boolean isUpdate = false;

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
        storage = FirebaseStorage.getInstance();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        }
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        Bundle bundle = getArguments();
        String json = bundle.getString("producto", MainActivity.gson.toJson(new Producto()));
        act = MainActivity.gson.fromJson(json, Producto.class);
        loadData();

        binding.imageViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setCancelable(true)
                        .setPositiveButton("Camara", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 0);
                            }
                        })
                        .setNegativeButton("Galery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, 1);
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Seleccionar origen");
                titulo.show();
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
                                if(deleteFoto(act.getFotoLocation()))
                                {
                                    Toast.makeText(getContext(),"Hubo un error eliminando la foto, intentalo de nuevo",Toast.LENGTH_SHORT).show();
                                    return;
                                };
                                productoViewModel.delete(act);
                                Toast.makeText(getContext(), "Se ha eliminado el producto: " + act.getNombre() + ".",Toast.LENGTH_LONG).show();
                                Navigation.findNavController(view).navigate(R.id.FirstFragment);
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

                if(binding.imageViewC.getDrawable() == null)
                {
                    Toast.makeText(getContext(), "Inserte la foto del producto.", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                } else {
                    if (Double.valueOf(precio) < 1d) {
                        Toast.makeText(getContext(), "El precio del producto debe ser mayor a 0.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                act.setNombre(nombre);
                act.setPrecio(Double.valueOf(precio));
                act.setDescripcion(descripcion);
                String mensaje = "";
                if(isUpdate)
                {
                    String imageLocation = uploadImage();
                    if(imageLocation.isEmpty())
                    {
                        return;
                    }else{
                        if(!act.getFotoLocation().isEmpty())
                        {
                            if(deleteFoto(act.getFotoLocation()))
                            {
                                Toast.makeText(getContext(),"Hubo un error actualizado la foto, intentalo de nuevo",Toast.LENGTH_SHORT).show();
                                return;
                            };
                        }
                        act.setFotoLocation(imageLocation);
                    }
                }
                if (act.getId() == null) {
                    productoViewModel.insert(act);
                    mensaje = "Se ha agregado el producto: " + act.getNombre() + ".";
                } else {
                    productoViewModel.update(act);
                    mensaje = "Se ha actualizado el producto: " + act.getNombre() + ".";
                }
                Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.FirstFragment);

            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                act = new Producto();
                isUpdate = false;
                image = null;
                loadData();
            }
        });
    }

    private void loadData() {
        if(!act.getFotoLocation().isEmpty())
        {
            StorageReference imageRef = storage.getReference();
            imageRef.child(act.getFotoLocation()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    binding.imageViewC.setImageURI(uri);
                    Picasso.get().load(uri).placeholder(R.drawable.ic_baseline_photo_24)
                            .error(R.drawable.ic_baseline_photo_24)
                            .into(binding.imageViewC);
                }
            });
        }else{
            binding.imageViewC.setImageResource(0);
        }
        if (act.getId() == null) {
            binding.btnDelete.setVisibility(View.GONE);
        } else {
            binding.btnDelete.setVisibility(View.VISIBLE);
        }
        if (act.getPrecio() > 0d) {
            binding.edPrecioC.setText(act.getPrecio().toString());
        }else{
            binding.edPrecioC.setText("");
        }
        binding.edDescripcionC.setText(act.getDescripcion());
        binding.edProductoC.setText(act.getNombre());
    }

    public  String uploadImage() {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Subiendo la imagen...");
        pd.show();

        final String[] location = {path + UUID.randomUUID().toString()};
        StorageReference photo = storage.getReference().child(location[0]);
        photo.putBytes(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00* snapshot.getBytesTransferred() /snapshot.getTotalByteCount());
                        pd.setMessage("Progreso: "+(int) progressPercent+"%");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Algo salio mal, vuelve a intentarlo",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        location[0] = "";
                    }
                });

        return location[0];

    }

    public Boolean deleteFoto(String location)
    {
        final Boolean[] complete = {false};
        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child(location);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                complete[0] = true;
            }
        });
        return complete[0];
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imaBitmap = (Bitmap) extras.get("data");
                    image = convertirAByte(imaBitmap);
                    binding.imageViewC.setImageBitmap(imaBitmap);
                    isUpdate = true;
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Hubo un error convirtiendo la foto a byte",Toast.LENGTH_SHORT).show();
                    }
                    image = convertirAByte(bitmap);
                    binding.imageViewC.setImageBitmap(bitmap);
                    isUpdate = true;
                }
                break;
        }

    }

    private byte[] convertirAByte(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90,bytes);
        return bytes.toByteArray();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}