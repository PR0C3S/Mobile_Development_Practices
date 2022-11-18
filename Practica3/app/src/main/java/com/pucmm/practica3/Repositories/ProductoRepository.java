package com.pucmm.practica3.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.pucmm.practica3.Database.AppDatabase;
import com.pucmm.practica3.Database.Producto;
import com.pucmm.practica3.Database.ProductoDao;

import java.util.List;

public class ProductoRepository {

    private ProductoDao productoDao;
    private LiveData<List<Producto>> allProducts;

    public ProductoRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        productoDao = database.productoDao();
        allProducts = productoDao.getAllProducts();
    }

    public void insert(Producto producto)
    {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productoDao.insertarProducto(producto));
    }

    public void update(Producto producto)
    {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productoDao.updateProducts(producto));
    }

    public void delete(Producto producto)
    {
        AppDatabase.databaseWriteExecutor.execute(() ->
                productoDao.deleteProducto(producto));
    }


    public LiveData<List<Producto>> getAllProducts() {
        return allProducts;
    }


}
