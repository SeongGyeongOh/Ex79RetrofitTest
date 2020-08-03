package com.osg.ex79retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }

    public void clickBtn(View view) {
        //네트워크에서 읽어들인 json을 곧바로 객체로 생성하기

        //Retrofit 라이브러리로 HTTP 통신작업을 시작하자!
        //  1. Retrofit 객체 생성 및 기본설정
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kamniang.dothome.co.kr"); //기본 URL 설정 - 일반적으로 서버 주소까지만 적는다 (상세주소는 2번 인터페이스 참고)
        builder.addConverterFactory(GsonConverterFactory.create()); //retrofit이 읽어온 것을 Gson을 이용해서 파싱하기 위한 설정 - **받아오는 것이 무조건! JSON 이어야 한다!!!**

        Retrofit retrofit = builder.build(); //retrofit 객체 생성됨

        //  2. RetrofitService 인터페이스 설계
        //원하는 기능의 추상메소드를 설계 - getBoardJson()추상메소드
        
        //  3. RetrofitService 인터페이스를 객체로 설계
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        //위 코드를 쓰면 retrofitService 객체의 추상메소드의 기능을 - 인터넷과 연결, 인풋스트림....모두 알아서 만들어낸다...
        
        //  4. RetrofitService 객체의 원하는 기능메소드 실행해서 Call 객체 얻어오기
        Call<BoardItem> call = retrofitService.getBoardJson(); //실 네트워크 작업은 이 Call이란 녀석이 실행시킨다
        
        //  5. 진짜 원하는 기능으로 네트워크 작업을 수행하도록 Call 객체를 실행
        call.enqueue(new Callback<BoardItem>() {//queue 방식은 FIFO, 하지만 돌아오는건 누가 먼저 돌아올 지 모른다 - 돌아올 때 자동 발동하는 리스너를 달아라
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) { //응답 받음. 파라미터 reponse - 응답 결과 갖고있는 놈
                Toast.makeText(MainActivity.this, "응답 받음", Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    //응답객체로부터 Gson 라이브러리에 의해 자동으로 BoardItem으로 parsing되어있는 Json 문자열의 데이터값 body 얻어오기
                    BoardItem item = response.body();
                    tv.setText(item.name+", "+item.msg);
                }

            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) { //응답 못받음
                Toast.makeText(MainActivity.this, "응답 실패", Toast.LENGTH_SHORT).show();
            }
        }); 
        
    }

    public void clickBtn2(View view) {
        //1. retrofit 객체 생성 및 설정
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kamniang.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        //2. 인터페이스 설계 - RetrofitService interface에 원하는 추상메소드 설계 getBoardJsonbyPath()

        //3. RetrofitService 인터페이스를 객체로 설계
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        //4. RetrofitService 객체의 원하는 기능메소드를 실행해서 Call 객체 얻어오기
        Call<BoardItem> call = retrofitService.getBoardJsonbyPath("Retrofit");

        //5. 실제 네트워크 작업 실행
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item = response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void clickBtn3(View view) {
        //  1. 위 retrofit 객체 생성 및 설정을 별도 클래스로 작성, static 메소드로 만들어 바로 사용 가능하도록..
        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();

        //  2.

        //3. 인터페이스 객체로 설계
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        //4. Call 객체 얻어오기
        Call<BoardItem> call = retrofitService.getMethodTest("홍길동", "만나서 반가워");

        //5. 네트워크 작업하기
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item = response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }


    public void clickBtn4(View view) {

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class); //클래스의 정보를 오버라이드, 객체로 만들어줌
        Call<BoardItem> call = retrofitService.getMethodTest2("getTest.php", "Hwang", "Good Morning!");

        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item = response.body();
                    tv.setText(item.name + "\n"+ item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void clickBtn5(View view) {

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        //서버에 전달할 데이터를 Map Collection으로 저장하자
        Map<String, String> webData = new HashMap<>();
        webData.put("name", "고양이");
        webData.put("msg", "Hungry...get me some milk and fish"  );

        Call<BoardItem> call = retrofitService.getMethodTest3(webData);
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()) {
                    BoardItem item = response.body();
                    tv.setText(item.name +"\n"+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });

    }

    public void clickBtn6(View view) {
        RetrofitService retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        //보낼 데이터를 가진 BoardItem 객체 만들기
        BoardItem item = new BoardItem("lee", "hey!");
        Call<BoardItem> call = retrofitService.postMethodTest(item);

        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()) {
                    BoardItem item = response.body();
                    tv.setText(item.name+"\n"+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void clickBtn7(View view) {
        RetrofitService retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        Call<BoardItem> call = retrofitService.postMethodTest2("냥냐리", "낮잠....");

        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()) {
                    BoardItem item = response.body();
                    tv.setText(item.name +"\n"+item.msg);
                }

            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }

    public void clickBtn8(View view) {
        RetrofitService retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        Call<ArrayList<BoardItem>> call = retrofitService.getBoardArray();

        call.enqueue(new Callback<ArrayList<BoardItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BoardItem>> call, Response<ArrayList<BoardItem>> response) {
                ArrayList<BoardItem> items = response.body();
//                tv.setText(items.size()+"");
                for(BoardItem item : items){
                    Toast.makeText(MainActivity.this, items.size()+"", Toast.LENGTH_SHORT).show();
                    tv.append("\n"+item.name+"\n"+item.msg+"\n");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BoardItem>> call, Throwable t) {

            }
        });
    }

    public void clickBtn9(View view) {
        RetrofitService retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        Call<BoardItem> call = retrofitService.urlTest("http://kamniang.dothome.co.kr/Retrofit/board.json"); //base URL을 무시하고 바로 URL 주소 지정

        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                BoardItem item = response.body();
                tv.setText(item.name);
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });

    }

    public void clickBtn10(View view) {
        //Json을 받는 것이 아니라, 문자열 그대로 받아오기 - (GsonConverterFactory 사용하면 안됨)
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://kamniang.dothome.co.kr");
        //여기에 GsonConverterFactory ㄴㄴㄴ -> ScalarsConverter 사용해야함 : converter-scalars 라이브러리 추가!!
        builder.addConverterFactory(ScalarsConverterFactory.create());

        Retrofit retrofit = builder.build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<String> call = retrofitService.getJsonString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                tv.setText(result);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
