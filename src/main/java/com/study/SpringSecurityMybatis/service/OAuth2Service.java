package com.study.SpringSecurityMybatis.service;

import com.study.SpringSecurityMybatis.dto.request.ReqOAuth2MergeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OAuth2Service implements OAuth2UserService {

    @Autowired
    private DefaultOAuth2UserService defaultOAuth2UserService;

    @Override
    // 로그인한 유저들의 정보를 담음
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> oAuth2Attributes = new HashMap<>();
        oAuth2Attributes.put("provider", userRequest.getClientRegistration().getClientName());

        switch (userRequest.getClientRegistration().getClientName()) {
            case "Google":
                oAuth2Attributes.put("id", attributes.get("sub").toString());
                break;
            case "Naver":
                attributes = (Map<String, Object>) attributes.get("response");
                oAuth2Attributes.put("id", attributes.get("id").toString());
                break;
            case "Kakao":
                oAuth2Attributes.put("id", attributes.get("id").toString());

        }

        return new DefaultOAuth2User(new HashSet<>(), oAuth2Attributes, "id");

//        System.out.println(userRequest.getClientRegistration());
//        System.out.println(oAuth2User.getAttributes());
//        System.out.println(oAuth2User.getAuthorities());
//        System.out.println(oAuth2User.getName());
//        String id = oAuth2User.getName();

        // naver 일때 실행
//        if(userRequest.getClientRegistration().getClientName().equals("Naver")) {
//            Map<String, Object> attributes = (Map<String, Object>) oAuth2User.getAttribute("response");
//            id = (String) attributes.get("id"); // id 값을 따로 빼놓겠다
//        }

    }

    public void merge(com.study.SpringSecurityMybatis.entity.OAuth2User oAuth2User) {

    }
}
