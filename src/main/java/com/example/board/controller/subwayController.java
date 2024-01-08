package com.example.board.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class subwayController {

    @GetMapping("/subway")
    public String index(Model model){ //Model import org.springframework.ui.Model 이거 사용

        return "subway";
    }

    @GetMapping("/SbCall")
    @JsonIgnore
    @ResponseBody
    public String index(@RequestParam("subway") String subway) throws IOException {
        System.out.println("hello");
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder("http://swopenAPI.seoul.go.kr"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("api","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("subway","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("4946764c6e726b733733445345524e","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("realtimeStationArrival","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("0","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode(subway,"UTF-8")); /* 서비스별 추가 요청인자들*/
        System.out.println(urlBuilder.toString());
        URLEncoder.encode(urlBuilder.toString(),"UTF-8");
        System.out.println(urlBuilder.toString());
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(sb.toString());
        System.out.println("element : " + element.toString());
        JsonArray realtimeArrivalList = (JsonArray) element.getAsJsonObject().get("realtimeArrivalList");

        JsonArray jarr = new JsonArray();

        for(int i =0; i<realtimeArrivalList.size(); i++){
            JsonObject obj = new JsonObject();
            JsonObject object = (JsonObject) realtimeArrivalList.get(i);
            JsonElement jsonElement = parser.parse(object.toString());

            obj.addProperty("statnNm",jsonElement.getAsJsonObject().get("statnNm").getAsString());
            obj.addProperty("updnLine",jsonElement.getAsJsonObject().get("updnLine").getAsString());
            obj.addProperty("trainLineNm",jsonElement.getAsJsonObject().get("trainLineNm").getAsString());
            obj.addProperty("arvlMsg2",jsonElement.getAsJsonObject().get("arvlMsg2").getAsString());
            obj.addProperty("arvlMsg3",jsonElement.getAsJsonObject().get("arvlMsg3").getAsString());

            jarr.add(obj);
        }

        System.out.println("result : " + jarr.toString());

        return jarr.toString();
    }

}
