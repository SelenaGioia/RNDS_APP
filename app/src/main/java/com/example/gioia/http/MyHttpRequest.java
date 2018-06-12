package com.example.gioia.http;

import com.example.gioia.rnds1.Statuses;
import com.example.gioia.rnds1.UserSettings;
import com.example.gioia.rnds1.UserStatus;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

public class MyHttpRequest {
    private static String baseURL = "http://192.168.1.37:8080";

    public static List<UserStatus> getUserStatus () {
        RestTemplate template1 = new RestTemplate();
        UserStatus[] userDetails = template1.getForObject( baseURL + "/users/1",UserStatus[].class );

        List<UserStatus> users = new LinkedList<>();
        for(UserStatus status : userDetails)
            users.add(status);
        return users;

    }

    public static List<Statuses> getAvailableStatuses () {
        RestTemplate template1 = new RestTemplate();
        Statuses[] statuses = template1.getForObject( baseURL + "/statuses",Statuses[].class );

        List<Statuses> status = new LinkedList<>();
        for(Statuses stat : statuses) {
            status.add(stat);
        }
        return status;

    }

    public static UserSettings getUserSettings(String StatusID) {
        RestTemplate template1 = new RestTemplate();
        UserSettings usersettings = template1.getForObject( baseURL + "/usersettings/1_"+StatusID,UserSettings.class );
        return usersettings;

    }

    public static void putUserSettings(UserSettings usersettings) {
        RestTemplate template1 = new RestTemplate();
        template1.put( baseURL + "/usersettings/1_" + usersettings.getStateID().toString() ,UserSettings.class );


    }


    public static void putUserStatus(Statuses status){
        RestTemplate template1 = new RestTemplate();
        template1.put(baseURL + "/statuses/1/1_" + status.getStatusID(), status);
    }

}

