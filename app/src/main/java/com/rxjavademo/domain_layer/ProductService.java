package com.rxjavademo.domain_layer;

import com.rxjavademo.data.models.ResponseDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ProductService {
    @GET("products")
    Observable<List<ResponseDTO>> getProducts();
}
