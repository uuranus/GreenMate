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

//    fun getGreenMates(): List<GreenMate> {
//        return FakeGreenMateService.greenMates
//    }

    /** getData **/

    @POST("/getDailyRecords.do")
    suspend fun getAllDiaries(
        @Body data: ModuleIdStringDTO,
    ): Response<Map<String, List<DailyDiaryDTO>>>

    /** Add **/
    @POST("/checkModuleRelation.do")
    suspend fun checkModule(
        @Body data: ModuleIdStringDTO,
    ): Response<Map<String, Int>>

    @POST("/relationModule.do")
    suspend fun addGreenMate(
        @Body data: GreenMateDTO,
    ): Response<ModuleIdStringDTO>

    @POST("/addDailyRecord.do")
    suspend fun addDailyRecord(
        @Body data: AddDiaryDTO,
    ): Response<ModuleIdStringDTO>

//    fun editGreenMateInfo(greenMate: GreenMate): GreenMate {
//        var index = 0
//        FakeGreenMateService.greenMates.forEachIndexed { idx, it ->
//            if (it.id == greenMate.id) {
//                index = idx
//            }
//        }
//
//        FakeGreenMateService.greenMates[index] = greenMate
//        return greenMate
//    }
//
//    fun deleteGreenMate(id: String): Boolean {
//        val target = FakeGreenMateService.greenMates.find { it.id == id }
//        FakeGreenMateService.greenMates.remove(target)
//        return true
//    }
//
//    fun isSerialNumberExist(number: String): Boolean {
//        return true
//    }
//
//    fun addDiary(id: String, diary: String): String {
//        val today = getToday()
//        if (FakeGreenMateService.diaries.containsKey(id)) {
//            val todayDiary = FakeGreenMateService.diaries[id]!!
//            val target =
//                todayDiary.find { it.dateMonth == today.month && it.dateDate == today.date }
//            println("target $target")
//            if (target == null) {
//                FakeGreenMateService.diaries[id]?.add(
//                    Diary(
//                        today.month,
//                        today.date,
//                        mutableListOf(Todo(diary, R.drawable.icon_water))
//                    )
//                )
//            } else {
//                target.list.add(
//                    Todo(diary, R.drawable.icon_water)
//                )
//            }
//        } else {
//            println("!!!!")
//            FakeGreenMateService.diaries[id] = mutableListOf(
//                Diary(
//                    today.month,
//                    today.date,
//                    mutableListOf(Todo(diary, R.drawable.icon_water))
//                )
//            )
//        }
//        return "null"
//    }
}