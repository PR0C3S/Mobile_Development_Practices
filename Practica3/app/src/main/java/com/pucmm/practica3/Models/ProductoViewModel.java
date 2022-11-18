package com.pucmm.practica3.Models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Repositories.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

    private ProductoRepository productoRepository;
    private LiveData<List<Producto>> allProducts;
    public ProductoViewModel(@NonNull Application application) {
        super(application);
        productoRepository = new ProductoRepository(application);
        allProducts = productoRepository.getAllProducts();
    }

    public void insert(Producto producto) {
        productoRepository.insert(producto);
    }

    public void update(Producto producto) {
        productoRepository.update(producto);
    }

    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    public LiveData<List<Producto>> getAllProducts() {
        return allProducts;
    }

}
