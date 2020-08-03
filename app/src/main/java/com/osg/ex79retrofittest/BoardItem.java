package com.osg.ex79retrofittest;

import com.google.gson.annotations.SerializedName;

public class BoardItem {
    // ftp://kamniang.dothome.co.kr/html/Retrofit/board.json에서 받아서 만들어질 객체
    //*** 멤버 변수명은 JSON에서 변환할 JSON의 키값들과 같은 이름이어야 한다***(자료형도 마찬가지)

    String name;
    String msg;

//    //만약, JSON의 키값과 다른 변수명을 쓰고 싶다면?
//    @SerializedName("msg") //직렬화, 객체를 파일로 저장하고 싶을 때
//    String message; //Json에 msg키값을 자바에서는 변수명 message로 받을 수 있음!!

    public BoardItem(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public BoardItem() {

    }
}
