package client.data;


import java.util.List;

import config.DomainNameOrIPAndPort;
import core.businessmessages.toServer.ParamsForCreateUser;
import core.businessmessages.toServer.ParamsForGetDirtyEntities;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessmessages.toServer.ParamsForGetFamilyMembers;
import core.businessmessages.toServer.ParamsForGetFamilyMessages;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessmessages.toServer.ParamsForLogin;
import core.businessmessages.toServer.ParamsForNotifyCompleted;
import core.businessmessages.toServer.ParamsForRateFamilyMember;
import core.businessmessages.toServer.ParamsForRequestAccount;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessmessages.toServer.ParamsForSendHeartbeat;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessmessages.toServer.ParamsForVerifyAccount;
import core.businessobjects.DirtyEntities;
import core.businessobjects.Family;
import core.businessobjects.FamilyMember;
import core.businessobjects.FamilyMemberRating;
import core.businessobjects.FamilyMessage;
import core.businessobjects.FileDetails;
import core.businessobjects.Heartbeat;
import core.businessobjects.Proposal;
import core.businessobjects.SessionDetails;
import core.businessobjects.Trip;
import core.businessobjects.User;
import core.businessobjects.admin.PerformanceData;
import core.businessobjects.admin.ProgressData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import resources.strings.Parameters;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api2 {
    static String BASE_URL = "http://"+ DomainNameOrIPAndPort.get();

    //=====================
    static String API_PATH = "/RideServer/api/";

    static String API_FULL_PATH = BASE_URL + API_PATH;

    static String contentTypeUrlEncoded = "Content-Type: application/x-www-form-urlencoded; charset=UTF-8";


    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "users/create/")
    Call<List<User>> createUser(@Body ParamsForCreateUser body);


    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "users/request/account/")
    Call<List<String>> requestAccount(@Body ParamsForRequestAccount body);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "users/verify/account/")
    Call<List<User>> verifyAccount(@Body ParamsForVerifyAccount body);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "users/login/")
    Call<List<SessionDetails>> login(@Body ParamsForLogin paramsForLogin);

    @Multipart
    @POST(API_PATH + "users/profile/photos/upload/")
    Call<List<FileDetails>> uploadProfilePic(
            @Part MultipartBody.Part file,
            @Part MultipartBody.Part sessionId
    );

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "heartbeat/send/")
    Call<List<Heartbeat>> sendHeartbeat(@Body ParamsForSendHeartbeat paramsForSendHeartbeat);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "trips/get/")
    Call<List<Trip>> getTrips(@Body ParamsForGetTrips paramsForGetTrips);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "trips/schedule/")
    Call<List<Trip>> scheduleTrip(@Body ParamsForScheduleTrip paramsForScheduleTrip);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "proposals/send/")
    Call<List<Proposal>> sendProposal(@Body ParamsForSendTTProposal paramsForSendTTProposal);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/get/")
    Call<List<Family>> getFamilies(@Body ParamsForGetFamilies paramsForGetFamilies);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/messages/")
    Call<List<FamilyMessage>> getFamilyMessages(@Body ParamsForGetFamilyMessages paramsForGetFamilyMessages);


    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/notify_completed/")
    Call<List<Family>> notifyCompleted(@Body ParamsForNotifyCompleted paramsForNotifyCompleted);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/members/get/")
    Call<List<FamilyMember>> getFamilyMembers(@Body ParamsForGetFamilyMembers params);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/members/send_message/")
    Call<List<FamilyMessage>> sendFamilyMessage(@Body ParamsForSendFamilyMessage paramsForSendFamilyMessage);


    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "families/members/rate/")
    Call<List<FamilyMemberRating>> rateFamilyMember(@Body ParamsForRateFamilyMember paramsForRateFamilyMember);

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "admin/progress_report/")
    Call<List<ProgressData>> getProgressReport();

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "admin/performance_report/")
    Call<List<PerformanceData>> getPerformanceReport();

    @Multipart
    @POST(API_PATH + "files/upload/")
    Call<List<FileDetails>> uploadFile(
            @Part MultipartBody.Part file,
            @Part("metadata") RequestBody description
    );

    @Multipart
    @POST(API_PATH + "files/upload/")
    Call<List<FileDetails>> uploadFile2(
            @Part MultipartBody.Part file,
            @Part MultipartBody.Part field
    );



    @GET(API_PATH + "files/get/list/")
    Call<List<FileDetails>> getFiles(
            @Query(Parameters.sessionId) String sessionId
    );

    @Headers(contentTypeUrlEncoded)
    @POST(API_PATH + "entities/dirty/get/")
    Call<List<DirtyEntities>> getDirtyEntities(@Body ParamsForGetDirtyEntities paramsForGetDirtyEntities);



    /*
    @GET(API_PATH + "buses/register/")
    Call<List<Bus>> registerBus(@QueryMap HashMap<String, String> parameters);


    @Headers("Content-Type: application/x-www-form-urlencoded; charset=UTF-8")
    @POST(API_PATH + "seatmaps/objects/post/")
    Call<List<SeatMapObject>> postSeatMapObjects(@Body SeatMapPostRequest body);*/


    /*
    @Multipart
    @POST(API_PATH + "files/upload/")
    Call<List<FileDetails>> uploadFile(
            @Part MultipartBody.Part file,
            @Part("metadata") RequestBody description
    );

    @GET(API_PATH + "files/get/list/")
    Call<List<FileDetails>> getFiles(
            @Query(Parameters.sessionId) String sessionId
    );



    @GET(API_PATH + "run/")
    <T> Call<T> run(Class<T> classOfReturnType,@QueryMap HashMap<String, String> parameters);

    @GET(API_PATH + "run/")
    <T> Call<T> run(Class<T> classOfReturnType);*/




    /*
    //---------------- parking adminFacilities --------------------
    @POST("add_parking_facility")
    Call <APIResponse<Facility>> addParkingFacility(@QueryMap HashMap<String,String> parameters);*/


}