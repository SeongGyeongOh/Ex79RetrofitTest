package com.osg.ex79retrofittest;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitService {
    //추상메소드 만들기
    //1. 단순하게 GET방식으로 json 문자열을 읽어오는 추상메소드 - 어노테이션 주석으로 이용
    @GET ("/Retrofit/board.json") //상세주소를 여기에 적자
    Call<BoardItem> getBoardJson(); //리턴 타입이 Call<>인 함수(), 즉 읽어온 JSON 값을 어떤 클래스에 넣어 객체를 만들어 줄지 정하는 것

    //2. GET방식으로 부를 때 경로의 이름을 1번처럼 고정하지 않고 파라미터로 전달받아 지정하는 형식 (1번과 기능은 동일함)
    @GET("/{folder}/board.json")
    Call<BoardItem> getBoardJsonbyPath(@Path("folder") String path); //스트링으로 전달받은 경로값을 위 {folder}에 바꿔치기로 집어넣겠다!

    //3. GET 방식으로 서버에 데이터 전달(@Query - 키값을 정해줌)
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest(@Query("name") String name, @Query("msg") String msg);

    //4. GET 방식으로 값을 전달하면서 경로의 파일명까지 지정하기  -@Query, @Path 같이 사용
    @GET("/Retrofit/{file}")
    Call<BoardItem> getMethodTest2(@Path("file") String fileName, @
                                    Query("name") String name, @Query("msg") String msg);

    //5. GET 방식으로 보낼 값들을 Map Collection으로 한방에 전달하기 @QueryMap
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest3(@QueryMap Map<String, String> datas); //@QueryMap ... : 문자열로 된 식별자가 String 데이터를 끌고 다닐 것이다!

    //6. POST 방식으로 데이터 보내기 @Body 사용 권장
    //객체를 전달하면 자동으로 json 문자열로 시리얼라이저블(변환)하여 body데이터에 넣어 서버로 전송
    @POST("/Retrofit/postTest.php")
    Call<BoardItem> postMethodTest(@Body BoardItem item); //객체의 멤버를 일일이 꺼내 보내는게 아니라, 객체를 통째로 보내버림

    //7. POST 방식이지만 멤버값들을 별도로 보내고 싶을 때 (@GET처럼) - @Field
    //단, 반드시 @FormUrlEncoded와 함께 써야 함
    @FormUrlEncoded
    @POST("/Retrofit/postTest2.php")
    Call<BoardItem> postMethodTest2(@Field("name") String name, @Field("msg") String msg);

    //8. 응답받을 데이터가 JSON 배열일 때
    @GET("/Retrofit/boardArray.json")
    Call<ArrayList<BoardItem>> getBoardArray();

    //9. BaseURL을 무시하고 지정된 URL로 연결 - @Url
    @GET
    Call<BoardItem> urlTest(@Url String url);

    //10. 이때까지 예제는 응답결과가 항상 json이었음 -> 이것을 Gson을 이용해서 BoardItem객체로 받아왔음.
    //      만약 에코되는 것이 그냥 문자열String이라면? 그냥 String으로만 받고싶다면??
    @GET("/Retrofit/board.json")
    Call<String> getJsonString();



}
