package com.geekhive.foodeydeliveryboy.utils;

import com.geekhive.foodeydeliveryboy.beans.alreadypaid.AlreadyPaid;
import com.geekhive.foodeydeliveryboy.beans.cakeorderHistory.CakeOrderHistory;
import com.geekhive.foodeydeliveryboy.beans.cashpay.CashPayment;
import com.geekhive.foodeydeliveryboy.beans.citylist.CityList;
import com.geekhive.foodeydeliveryboy.beans.delstatus.DelWorkingStatus;
import com.geekhive.foodeydeliveryboy.beans.delupdate.UpdateDelStatus;
import com.geekhive.foodeydeliveryboy.beans.floatcash.FloatingCash;
import com.geekhive.foodeydeliveryboy.beans.groceryorderHistory.GrocOrderHistory;
import com.geekhive.foodeydeliveryboy.beans.login.LoginOut;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.NewOrderList;
import com.geekhive.foodeydeliveryboy.beans.onlinepay.OnlinePayment;
import com.geekhive.foodeydeliveryboy.beans.orderCanceled.OrderCanceled;
import com.geekhive.foodeydeliveryboy.beans.orderConfirmed.OrderConfirmed;
import com.geekhive.foodeydeliveryboy.beans.orderDelivered.OrderDelivered;
import com.geekhive.foodeydeliveryboy.beans.orderHistory.OrderHistory;
import com.geekhive.foodeydeliveryboy.beans.orderPicked.OrderPicked;
import com.geekhive.foodeydeliveryboy.beans.otpverify.VerifyOtp;
import com.geekhive.foodeydeliveryboy.beans.resendotp.ResendOtp;
import com.geekhive.foodeydeliveryboy.beans.revenue.RevenueDetails;
import com.geekhive.foodeydeliveryboy.beans.signup.SignupOut;
import com.geekhive.foodeydeliveryboy.beans.superadminlist.SuperAdminList;
import com.geekhive.foodeydeliveryboy.beans.trackloc.UpdateCurrentLocation;
import com.geekhive.foodeydeliveryboy.beans.wallet.WalletBal;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GitApi {

    @POST("delivery_login")
    @Multipart
    Call<LoginOut> getLoginDetails(@Part MultipartBody.Part email, @Part MultipartBody.Part password, @Part MultipartBody.Part firebase_id);

    @POST("delivery_signup")
    @Multipart
    Call<SignupOut> getSignupDetails(@Part MultipartBody.Part firstname, @Part MultipartBody.Part lastname, @Part MultipartBody.Part mobile,
                                     @Part MultipartBody.Part email, @Part MultipartBody.Part bike_number, @Part MultipartBody.Part password, @Part MultipartBody.Part city_id,
                                     @Part MultipartBody.Part sub_admin_id, @Part MultipartBody.Part id_proof,
                                     @Part MultipartBody.Part driving_license,
                                     @Part MultipartBody.Part residance_proof, @Part MultipartBody.Part profile);

    @POST("del_new_order")
    @Multipart
    Call<NewOrderList> newOrderList(@Part MultipartBody.Part del_id, @Part MultipartBody.Part city_id);

    @POST("del_order_history")
    @Multipart
    Call<OrderHistory> getOrderHistory(@Part MultipartBody.Part del_id);

    @POST("del_confirm_order")
    @Multipart
    Call<OrderConfirmed> getOrderConfirmed(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);

    @POST("del_cancel_order")
    @Multipart
    Call<OrderCanceled> getOrderCanceled(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);

    @POST("del_order_delivered")
    @Multipart
    Call<OrderDelivered> getOrderDelivered(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);

    @POST("del_revenue")
    @Multipart
    Call<RevenueDetails> getRevenueDetails(@Part MultipartBody.Part del_id);

    @POST("del_pickup_order")
    @Multipart
    Call<OrderPicked> getOrderPicked(@Part MultipartBody.Part cart_id);

    @POST("save_location_del")
    @Multipart
    Observable<UpdateCurrentLocation> updateCurrentLocation(@Part MultipartBody.Part del_id, @Part MultipartBody.Part order_id,
                                                            @Part MultipartBody.Part latitude, @Part MultipartBody.Part longitude);

    @GET("city_list")
    Call<CityList> getCityLIst();

    @GET("super_admin_details")
    Call<SuperAdminList> getAdminDetails();

    @POST("del_verify_otp")
    @Multipart
    Call<VerifyOtp> verifyDelOtp(@Part MultipartBody.Part otp, @Part MultipartBody.Part del_id,
                                 @Part MultipartBody.Part order_id);


    @POST("grocery_del_verify_otp")
    @Multipart
    Call<VerifyOtp> grocverifyDelOtp(@Part MultipartBody.Part otp, @Part MultipartBody.Part del_id,
                                     @Part MultipartBody.Part order_id);

    @POST("del_resend_otp")
    @Multipart
    Call<ResendOtp> resendOtpDel(@Part MultipartBody.Part order_id,
                                 @Part MultipartBody.Part del_id);


    @POST("del_cash_payment")
    @Multipart
    Call<CashPayment> cashPayment(@Part MultipartBody.Part cart_id,
                                  @Part MultipartBody.Part del_id);

    @POST("del_online_payment")
    @Multipart
    Call<OnlinePayment> onlinePayment(@Part MultipartBody.Part cart_id,
                                      @Part MultipartBody.Part del_id);

    @POST("del_already_payment")
    @Multipart
    Call<AlreadyPaid> alreadyPaid(@Part MultipartBody.Part cart_id,
                                  @Part MultipartBody.Part del_id);

    @POST("store_del_confirm_order")
    @Multipart
    Call<OrderConfirmed> getGrocOrderConfirmed(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);


    @POST("store_del_order_history")
    @Multipart
    Call<GrocOrderHistory> getGrocOrderHistory(@Part MultipartBody.Part del_id);

    @POST("store_del_pickup_order")
    @Multipart
    Call<OrderPicked> getGrocOrderPicked(@Part MultipartBody.Part cart_id);

    @POST("grocery_del_cash_payment")
    @Multipart
    Call<CashPayment> cashGrocPayment(@Part MultipartBody.Part cart_id,
                                      @Part MultipartBody.Part del_id);

    @POST("grocery_del_online_payment")
    @Multipart
    Call<OnlinePayment> onlineGrocPayment(@Part MultipartBody.Part cart_id,
                                          @Part MultipartBody.Part del_id);

    @POST("grocery_del_already_payment")
    @Multipart
    Call<AlreadyPaid> alreadyGrocPaid(@Part MultipartBody.Part cart_id,
                                      @Part MultipartBody.Part del_id);

    @POST("store_del_order_delivered")
    @Multipart
    Call<OrderDelivered> getGrocOrderDelivered(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);


    //Cakes

    @POST("cake_del_confirm_order")
    @Multipart
    Call<OrderConfirmed> getCakeOrderConfirmed(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);

    @POST("cake_del_verify_otp")
    @Multipart
    Call<VerifyOtp> verifyCakeDelOtp(@Part MultipartBody.Part otp, @Part MultipartBody.Part del_id,
                                     @Part MultipartBody.Part order_id);

    @POST("cake_del_pickup_order")
    @Multipart
    Call<OrderPicked> getCakeOrderPicked(@Part MultipartBody.Part cart_id);

    @POST("cake_del_order_history")
    @Multipart
    Call<CakeOrderHistory> getCakeOrderHistory(@Part MultipartBody.Part del_id);


    @POST("cake_del_cash_payment")
    @Multipart
    Call<CashPayment> cashCakePayment(@Part MultipartBody.Part cart_id,
                                      @Part MultipartBody.Part del_id);

    @POST("cake_del_online_payment")
    @Multipart
    Call<OnlinePayment> onlineCakePayment(@Part MultipartBody.Part cart_id,
                                          @Part MultipartBody.Part del_id);

    @POST("cake_del_already_payment")
    @Multipart
    Call<AlreadyPaid> alreadyCakePaid(@Part MultipartBody.Part cart_id,
                                      @Part MultipartBody.Part del_id);

    @POST("cake_del_order_delivered")
    @Multipart
    Call<OrderDelivered> getCakeOrderDelivered(@Part MultipartBody.Part del_id, @Part MultipartBody.Part cart_id);

    @POST("del_wallet_balance")
    @Multipart
    Call<WalletBal> getDelWalletBal(@Part MultipartBody.Part del_id);

    @POST("del_float_cash")
    @Multipart
    Call<FloatingCash> getFloatingCash(@Part MultipartBody.Part del_id);

    @POST("delivery_boy_status")
    @Multipart
    Call<UpdateDelStatus> updateDel(@Part MultipartBody.Part del_id, @Part MultipartBody.Part working_status);

    @POST("check_delivery_boy_status")
    @Multipart
    Call<DelWorkingStatus> getWorkingStatus(@Part MultipartBody.Part del_id);

}
