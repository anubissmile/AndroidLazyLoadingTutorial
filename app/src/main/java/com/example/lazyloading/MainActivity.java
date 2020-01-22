package com.example.lazyloading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lazyloading.listview.ListViewBaseAdapterBluePrint;
import com.example.lazyloading.listview.ListViewBaseAdapterListPOJO;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    private List<Coupon> mCoupons = new ArrayList<>();
    private ListViewBaseAdapterListPOJO mAdapter;

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
        renderListView();
        loadMore();


    }

    private void loadMore() {
        btnLoadMore.setOnClickListener(v -> {
            loadData(++offset);
            notifyListView();
        });
    }

    private void notifyListView() {
        mAdapter.notifyDataSetChanged();
    }

    private void renderListView() {
        mAdapter = new ListViewBaseAdapterListPOJO(
                fContext,
                R.layout.listview_coupons,
                mCoupons,
                new ListViewBaseAdapterBluePrint() {
                    @Override
                    public void bindConvertView(View v) {

                    }

                    @Override
                    public View getView(View v, int pos, JSONObject data) {
                        return null;
                    }

                    @Override
                    public View getView(View v, int pos, Object data) {
                        Coupon coupon = (Coupon) data;
                        TextView title = v.findViewById(R.id.txtTitle);
                        TextView code = v.findViewById(R.id.txtCode);
                        TextView description = v.findViewById(R.id.txtDescription);
                        TextView enddate = v.findViewById(R.id.txtEndDate);
                        title.setText(coupon.getTitle());
                        code.setText(coupon.getCode());
                        description.setText(coupon.getDescription());
                        enddate.setText(coupon.getEndDate());
                        return v;
                    }
                }
        );

        lvContent.setAdapter(mAdapter);
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

            List<Coupon> coupons = call.execute().body();

            if(coupons.size() > 0){
                Log.d("Myapp",  String.format("Success : %s with size : %s", coupons.toString(), coupons.size()));
                appendListCoupon(coupons);
            }else{
                Log.d("Myapp", "Coupon not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Myapp", String.format("Error : %s", e.getMessage()));
        }
    }

    private void appendListCoupon(List<Coupon> coupons) {
        mCoupons.addAll(coupons);
    }

    private void bindObj() {
        btnLoadMore = findViewById(R.id.btnLoadMore);
        lvContent = findViewById(R.id.lvContent);
        refresh = findViewById(R.id.refresh);
    }

}
