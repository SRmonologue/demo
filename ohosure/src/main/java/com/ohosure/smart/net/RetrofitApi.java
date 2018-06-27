package com.ohosure.smart.net;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lee on 2017/11/22.
 */

public interface RetrofitApi {

    //注册
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("/v2/users")
    Call<ResponseBody> postRegister(@Body RequestBody body);

    //重置密码
    @Headers("Content-type:application/json;charset=UTF-8")
    @PUT("/v2/users/{username}/passwords")
    Call<ResponseBody> changePassword(@Path("username") String username, @Body RequestBody body);

    //获取token信息
    @POST("uaa/oauth/token?")
    Call<ResponseBody> getToken(@Query("username") String username, @Query("password") String password, @Query("grant_type") String grant_type, @Query("client_id") String client_id);

    //获取账号下绑定的网关信息
    @GET("/v2/users/{userName}/gateways?")
    Call<ResponseBody> getGateWaysInfo(@Path("userName") String userName, @Query("access_token") String access_token);

    //绑定网关
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("/v2/users/{username}/gateways/{gwId}/bindings")
    Call<ResponseBody> postBinding(@Header("Authorization") String string, @Path("username") String username, @Path("gwId") String gwId);

    //修改网关名称
    @Headers("Content-type:application/json;charset=UTF-8")
    @PUT("/v2/gateway-manage/name")
    Call<ResponseBody> putGatewayName(@Header("Authorization") String string, @Body RequestBody body);

    //获取网关下用户列表
    @GET("/v2/gateways/{gwId}/users")
    Call<ResponseBody> getGateUserList(@Header("Authorization") String string, @Path("gwId") String gwId);

    //网关设置管理员
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("/v2/gateways/{gwId}/administrators")
    Call<ResponseBody> putAdmin(@Header("Authorization") String string, @Path("gwId") String gwId, @Body RequestBody body);

    //网关解除管理员
    @Headers("Content-type:application/json;charset=UTF-8")
    @HTTP(method = "DELETE", path = "/v2/gateways/{gwId}/administrators/{username}")
    Call<ResponseBody> deleteAdmin(@Header("Authorization") String string, @Path("gwId") String gwId, @Path("username") String username);

    //管理员确认用户网关绑定
    @Headers("Content-type:application/json;charset=UTF-8")
    @PATCH("/v2/gateways/{gwId}/users/{username}")
    Call<ResponseBody> verifyUser(@Header("Authorization") String string, @Path("gwId") String gwId, @Path("username") String username, @Body RequestBody body);

    //管理员解除用户网关绑定
    @Headers("Content-type:application/json;charset=UTF-8")
    @HTTP(method = "DELETE", path = "/v2/gateways/{gwId}/users/{username}")
    Call<ResponseBody> deleteUser(@Header("Authorization") String string, @Path("username") String username, @Path("gwId") String gwId);

    //管理员添加用户网关绑定
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("/v2/gateways/{gwId}/users")
    Call<ResponseBody> setAddUser(@Header("Authorization") String string, @Path("gwId") String gwId, @Body RequestBody body);

    //获取配置信息
    @GET("configinfo/users")
    Call<ResponseBody> getConfigInfo(@Query("username") String username, @Query("password") String password);

    //获取网关升级信息
    @Headers("Content-type:application/json;charset=UTF-8")
    @GET("/v2/gateways/{gwId}/softwares/type/optional/version/{version}")
    Call<ResponseBody> getGateWays(@Path("gwId") String gwId, @Path("version") String version);
}
