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
    private Context fContext;

//    TODO : STEP #5 สร้าง field offset สำหรับเก็บช่วงของข้อมูลที่เราจะดึงจาก api โดยเริ่มจาก 0
    private Integer offset = 0;

//    TODO : STEP #6 สร้าง field mCoupons สำหรับเก็บข้อมูลที่ดึงจาก API และจะนำไปแสดงใน ListView
    private List<Coupon> mCoupons = new ArrayList<>();

//    TODO : STEP #7 สร้าง field adapter สำหรับสร้าง แสดงรายการบน listview
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

//        TODO : STEP #8 ทำการผูก View เข้ากับ Object
        bindObj();

        refresh.setRefreshing(true);
//        TODO : STEP #9 ดึงข้อมูลช่วงแรกมาแสดงโดยการส่ง offset เข้าไป
        loadData(offset);
//        TODO : STEP #12 นำข้อมูลที่ได้มาแสดงใส่ ListView
        renderListView();

//        TODO : STEP #13 เขียน Listener สำหรับการโหลดข้อมูลเพิ่ม
        onRefresh();


    }

    private void onRefresh() {
        refresh.setOnRefreshListener(() -> {
//            TODO : STEP #14 ดึงข้อมูลช่วงต่อไปโดยการส่ง offset ของช่วงต่อไป
            loadData(++offset);

//            TODO : STEP #15 เนื่องจากเราได้สร้าง adapter ไว้ในครั้งแรกแล้ว เราจะแจ้งให้ adapter อัพเดทข้อมูลใหม่ลงไปโดยใช้ mAdapter.notifyDataSetChanged()
            notifyListView();
            refresh.setRefreshing(false);
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
        refresh.setRefreshing(false);
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

//            TODO : STEP #10 ได้ข้อมูลมาจาก API
            List<Coupon> coupons = call.execute().body();

            if(coupons.size() > 0){
                Log.d("Myapp",  String.format("Success : %s with size : %s", coupons.toString(), coupons.size()));
//                TODO : STEP #11 นำข้อมูลช่วยที่ได้ต่อไปใน mCoupons
                appendFirst(coupons);
            }else{
                Log.d("Myapp", "Coupon not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Myapp", String.format("Error : %s", e.getMessage()));
        }
    }

    private void appendFirst(List<Coupon> coupons) {
//        ต่อหัว
        mCoupons.addAll(0, coupons);

//        ต่อท้าย
//        mCoupons.addAll(coupons);
    }

    private void bindObj() {
        lvContent = findViewById(R.id.lvContent);
        refresh = findViewById(R.id.refresh);
    }

}
