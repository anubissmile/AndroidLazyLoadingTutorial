package com.example.lazyloading;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CouponService {

    String TOKEN = "bc7f9996e7b9e3e8d1694cab5bdae9e2";

    String ACTION = "getCoupons";

    Integer RESULT_PER_PAGE = 3;

    String COUPON_TYPE = "All";


    @GET("/zawasde/api/coupons.php")
    public Call<List<Coupon>> getCoupon(
        @Query("token_api") String tokenApi,
        @Query("action") String action,
        @Query("from") Integer from,
        @Query("result_per_page") Integer resultPerPage,
        @Query("coupons_type") String couponType
    );

}
