package com.greenmate.greenmate.model.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GreenMateService {

    /** User **/
    @FormUrlEncoded
    @POST("/signUp.do")
    suspend fun signup(
        @Field("name") userName: String,
        @Field("id") userId: String,
        @Field("password") password: String,
        @Field("birth") birth: String,
    ): Response<UserIdStringDTO>

    @POST("/login.do")
    suspend fun login(
        @Body data: LoginDTO,
    ): Response<Map<String, UserDTO>>

    /** getData **/
    @POST("/getUserAllData.do")
    suspend fun getAllGreenMates(
        @Body data: LoginDTO,
    ): Response<AllGreenMatesDTO>

    @POST("/getDailyRecords.do")
    suspend fun getAllDiaries(
        @Body data: ModuleIdStringDTO,
    ): Response<Map<String, List<DailyDiaryDTO>>>

    /** Add **/
    @POST("/registerModule.do")
    suspend fun checkModule(
        @Body data: ModuleIdStringDTO,
    ): Response<Map<String, Int>>

    @POST("/relationModule.do")
    suspend fun addGreenMate(
        @Body data: AddGreenMateDTO,
    ): Response<ModuleIdStringDTO>

    @POST("/addDailyRecord.do")
    suspend fun addDailyRecord(
        @Body data: AddDiaryDTO,
    ): Response<ModuleIdStringDTO>

    /** edit **/


    /** delete **/
    @POST("/deleteGreenmate.do")
    suspend fun deleteGreenMate(
        @Body data: ModuleIdStringDTO,
    ): Response<ModuleIdStringDTO>

}