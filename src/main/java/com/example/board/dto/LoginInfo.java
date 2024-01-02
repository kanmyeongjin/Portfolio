package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
//@AllArgsConstructor // 모든 필드에 대한 생성자가 자동으로 만들어진다. 그래서 세가지만 있는 생성자 추가 위해 주석처리함
public class LoginInfo {
    private int userId;
    private String email;
    private String name;
    private List<String> roles = new ArrayList<>(); //비어있는 롤 리스트 초기화

    public LoginInfo(int userId, String email, String name) { //ctrl+enter 쳐서 Generate에서 Constructor에서 세가지 생성자 선택해서 추가
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    //사용안함
//    public void addRole(String roleName){
//        roles.add(roleName);
//    }
}
