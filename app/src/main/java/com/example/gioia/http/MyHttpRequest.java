package com.example.gioia.http;

import com.example.gioia.rnds1.UserStatus;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MyHttpRequest {
    private static String baseURL = "http://192.168.1.37:8080";

    public static UserStatus getUserStatus () {
        RestTemplate template1 = new RestTemplate();
        UserStatus UserDetails = template1.getForObject( baseURL + "/users/1",UserStatus.class );
        return UserDetails;

    }

    public static void putUserStatus(UserStatus status){
        RestTemplate template1 = new RestTemplate();
        template1.put(baseURL + "/userDetails/1" + status.getAvailableStates(), status);
    }

}

