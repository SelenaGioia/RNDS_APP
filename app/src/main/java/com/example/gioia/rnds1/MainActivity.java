package com.example.gioia.rnds1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gioia.http.MyHttpRequest;

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

        new AsyncTask<Void, Void, UserStatus>(){

            @Override
            protected UserStatus doInBackground(Void... voids) {
                try{
                    return MyHttpRequest.getUserStatus();
                }
                catch(Throwable t){
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
                    Toast toast = Toast.makeText(getApplicationContext(), "HTTP PROBLEM", Toast.LENGTH_LONG);
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
