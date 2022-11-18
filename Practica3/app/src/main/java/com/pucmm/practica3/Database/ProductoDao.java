package com.pucmm.practica3.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface ProductoDao {

    @Query("select * from producto_table")
    List<Producto> findAllProducts();

    @Insert
    long insertarProducto(Producto producto);

    @Delete
    void deleteProducto(Producto producto);

    @Update
    void updateProducts(Producto producto);

    @Query("select * from producto_table")
    LiveData<List<Producto>> getAllProducts();
}
