package com.example.frizer.user;
import com.example.frizer.ChangePass;
import com.example.frizer.Termin;
import com.example.frizer.api.ServerResponse;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.api.ServerResponse3;
import com.example.frizer.api.ServerResponse4;
import com.example.frizer.api.ServerResponse5;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface TerminInterface {
    @GET("termin/all")
    Call<ServerResponse> getData(@Header("Authorization") String token);

    @DELETE("termin/{terminID}")
    Call<ServerResponse2> deleteTermin(@Header("Authorization") String token, @Path("terminID") String terminID);

    @POST("user/login")
    Call<ServerResponse3> login(@Body User loginUser);

    @POST("user")
    Call<ServerResponse3> register(@Body RegisterdUser regUser);

    @PUT("user/{userID}")
    Call<ServerResponse2> changePass(@Header("Authorization") String token, @Body ChangePass changePass, @Path("userID") String userID);

    @DELETE("user/{userID}")
    Call<ServerResponse2> deleteAcc(@Header("Authorization") String token,@Path("userID") String userID);

    @GET("termin/user/{userID}")
    Call<ServerResponse2> provjeri(@Header("Authorization") String token,@Path("userID") String userID);

    @GET("termin/user/{userID}")
    Call<ServerResponse4> getUserTermin(@Header("Authorization") String token,@Path("userID") String userID);

    @GET("termin/zauzeti/{datumTermina}")
    Call<ServerResponse5> getZauzeti(@Header("Authorization") String token, @Path("datumTermina") String datumTermina);

    @POST("termin")
    Call<ServerResponse2> zakazi(@Header("Authorization") String token,@Body Termin t);

    @GET("termin/one/{terminID}")
    Call<ServerResponse4> jedanTermin(@Header("Authorization") String token, @Path("terminID") String terminID);
}
