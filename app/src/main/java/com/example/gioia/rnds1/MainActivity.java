package com.example.gioia.rnds1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gioia.http.MyHttpRequest;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView UserName;
    TextView UserStatus;

    Spinner ChangeStatus;


    Button goToSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sele: Get the references to the layout widgets
        UserName = findViewById(R.id.ShowUser);
        UserStatus = findViewById(R.id.ShowStatus);
        goToSettings = findViewById(R.id.Button_Settings);
        ChangeStatus = findViewById(R.id.spinner_availablestatuses);

        new AsyncTask<Void, Void, UserStatus>(){

            @Override
            protected UserStatus doInBackground(Void... voids) {
                try{
                    if(MyHttpRequest.getUserStatus().get(0) != null)
                        return MyHttpRequest.getUserStatus().get(0);
                    else
                        return null;
                }
                catch(Throwable t){
                    t.printStackTrace();
                    return null;

                }
            }

            @Override
            protected void onPostExecute(UserStatus userStatus){
                super.onPostExecute(userStatus);
                if(userStatus != null){
                    UserName.setText(userStatus.getUserName());
                    UserStatus.setText(userStatus.getCurrentStatus());
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "HTTP PROBLEM WITH USERDATA", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        }.execute();

        new AsyncTask<Void, Void,List<Statuses>>(){

            @Override
            protected List<Statuses> doInBackground(Void... voids) {
                try{
                    if(MyHttpRequest.getAvailableStatuses() != null)
                        return MyHttpRequest.getAvailableStatuses();
                    else
                        return null;
                }
                catch(Throwable t){
                    t.printStackTrace();
                    return null;

                }
            }

            @Override
            protected void onPostExecute(List<Statuses> statuses) {
                super.onPostExecute(statuses);
                if (statuses != null) {
                    ArrayAdapter<Statuses> dataAdapter = new ArrayAdapter<Statuses>(getApplicationContext(), android.R.layout.simple_spinner_item, statuses);
                    ChangeStatus.setAdapter(dataAdapter);

                    ChangeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final Statuses selected = (Statuses) parent.getItemAtPosition(position);
                            ////
                            new AsyncTask<Void, Void, Statuses>() {

                                @Override
                                protected Statuses doInBackground(Void... voids) {
                                    try {
                                        MyHttpRequest.putUserStatus(selected);
                                        return null;
                                    } catch (Throwable t) {
                                        t.printStackTrace();
                                        return null;

                                    }
                                }

                                @Override
                                protected void onPostExecute(Statuses selected2) {
                                    //super.onPostExecute(selected);
                                    if (selected != null) {
                                        UserStatus.setText(selected.toString());
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "USERSTATUS", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }
                            }.execute();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "HTTP PROBLEM ON SPINNER STATUSES", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        }.execute();


        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatusSettings.class);
                startActivity(intent);

            }
        });





    }
}
