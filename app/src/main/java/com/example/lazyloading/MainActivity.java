package com.example.lazyloading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView lvContent;
    private SwipeRefreshLayout refresh;
    private Button btnLoadMore;
    private Context fContext;
    private Integer offset = 0;
    private List<Coupon> coupons;

    private String ENDPOINT = "http://ja-designdio.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        fContext = this;

        bindObj();

        loadData(offset);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(fContext, "Refreshing...", Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });

    }

    private void loadData(Integer offset) {

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            CouponService couponService = retrofit.create(CouponService.class);
            Call<List<Coupon>> call = couponService.getCoupon(
                    CouponService.TOKEN,
                    CouponService.ACTION,
                    offset,
                    CouponService.RESULT_PER_PAGE,
                    CouponService.COUPON_TYPE
            );

            coupons = call.execute().body();

            if(coupons.size() > 0){
                Log.d("Myapp",  String.format("Success : %s with size : %s", coupons.toString(), coupons.size()));
                renderListView();
            }else{
                Log.d("Myapp", "Coupon not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Myapp", String.format("Error : %s", e.getMessage()));
        }
    }

    private void renderListView() {

    }

    private void bindObj() {
        btnLoadMore = findViewById(R.id.btnLoadMore);
        lvContent = findViewById(R.id.lvContent);
        refresh = findViewById(R.id.refresh);
    }

}
