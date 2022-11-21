package com.rxjavademo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.jakewharton.rxbinding4.view.RxView;
import com.rxjavademo.databinding.ActivityMainBinding;
import com.rxjavademo.domain_layer.ProductService;
import com.rxjavademo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setProductsAdapter();

        //setObserables();
        createObserable();

        RxView.clicks(binding.getDataButton).throttleFirst(1500, TimeUnit.MILLISECONDS)
                .subscribe( it-> {
                    implementNetworkCall();
                    Log.d("TAG", "binding.getDataButton clicked(): ");
                        }
                );
        implementNetworkCall();
    }

    private void setProductsAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void implementNetworkCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CommonUtils.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        productService.getProducts()
                .subscribeOn(Schedulers.io())
                .subscribe(it->{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.recyclerView.setAdapter(new ProductAdapter(it));
                        }
                    });
            Log.d("TAG", "implementNetworkCall: "+it.toString());
        });
    }

    private void createObserable() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("A");

            Thread.sleep(1_500);
            emitter.onNext("B");

            Thread.sleep(500);
            emitter.onNext("C");

            Thread.sleep(250);
            emitter.onNext("D");

            Thread.sleep(2_000);
            emitter.onNext("E");
            emitter.onComplete();
        });


        source.subscribeOn(AndroidSchedulers.from(Looper.myLooper())).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG", "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d("TAG", "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete: ");
            }
        });
    }

    private void setObserables() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        Observable<String> observableList = io.reactivex.Observable.fromIterable(list);

        observableList.subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG", "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d("TAG", "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete: ");
            }
        });

        list.add("E");
        list.add("F");
        list.add("G");

        new Handler().postDelayed(() -> runOnUiThread(() -> list.add("What does happend")), 2000);
    }
}