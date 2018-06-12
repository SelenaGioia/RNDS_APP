package com.example.gioia.rnds1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gioia.http.MyHttpRequest;

import java.util.List;

public class StatusSettings extends AppCompatActivity {

    Switch Windows;
    Switch Doors;
    Switch Lights;
    Spinner Status_Spinner;
    TextView StatusDesc;
    SeekBar Noise;
    Button SaveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_settings);

        Status_Spinner = (Spinner) findViewById(R.id.spinner_Status_to_Modify);
        //Status_Spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //     //  String selected = parent.getItemAtPosition(position);
        //    }
        //});
        Windows = findViewById(R.id.switch_windows);
        Doors = findViewById(R.id.switch_doors);
        Lights = findViewById(R.id.switch_lights);
        StatusDesc = findViewById(R.id.textView_StatusDesc);
        Noise = findViewById(R.id.seekBar_Noise);
        SaveButton = findViewById(R.id.SaveButton);

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
                    Status_Spinner.setAdapter(dataAdapter);

                    Status_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final Statuses selected = (Statuses) parent.getItemAtPosition(position);
                            ////
                            new AsyncTask<Void, Void, UserSettings>() {

                                @Override
                                protected UserSettings doInBackground(Void... voids) {
                                    try {
                                        MyHttpRequest.getUserSettings(selected.statusID);
                                        return null;
                                    } catch (Throwable t) {
                                        t.printStackTrace();
                                        return null;

                                    }
                                }

                                @Override
                                protected void onPostExecute(UserSettings userSettings) {
                                    super.onPostExecute(userSettings);
                                    if (userSettings != null) {
                                        StatusDesc.setText(userSettings.getCustomisedDescription().toString());
                                        //set DOORS check
                                        if(userSettings.getDoorOpen() == 1)
                                            Doors.setChecked(true);
                                        else
                                            Doors.setChecked(false);
                                        //set WINDOWS check
                                        if(userSettings.getWindowsOpen()== 1)
                                            Windows.setChecked(true);
                                        else
                                            Windows.setChecked(false);
                                        //set LIGHTS check
                                        if(userSettings.getLightsOn() == 1)
                                            Lights.setChecked(true);
                                        else
                                            Lights.setChecked(false);
                                        //Set Noise Seekbar
                                        Noise.setProgress(userSettings.getNoiseLevel());
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "PROBLEMS LOADING USERSETTINGS ", Toast.LENGTH_LONG);
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
                    Toast toast = Toast.makeText(getApplicationContext(), "HTTP PROBLEM ON SPINNER2", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        }.execute();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, UserSettings>() {

                    @Override
                    protected UserSettings doInBackground(Void... voids) {
                        try {
                            UserSettings userSettings2 = new UserSettings();
                           // userSettings2.setCustomisedDescription(StatusDesc.getText().toString());
                            //userSettings2.setStateID(String stateID);
                            //userSettings2.setWindowsOpen(Integer windowsOpen);
                            //userSettings2.setDoorOpen(Integer doorOpen);
                            //userSettings2.setLightsOn(Integer lightsOn);
                            //userSettings2.setNoiseLevel(Integer noiseLevel);
                            //userSettings2.setCustomisedDescription(String customisedDescription);
                            MyHttpRequest.putUserSettings(userSettings2);
                            return null;
                        } catch (Throwable t) {
                            t.printStackTrace();
                            return null;

                        }
                    }

                    @Override
                    protected void onPostExecute(UserSettings userSettings) {
                        super.onPostExecute(userSettings);
                        if (userSettings != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "STATUS DETAILS UPDATED", Toast.LENGTH_LONG);
                            toast.show();
                            Noise.setProgress(userSettings.getNoiseLevel());
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "IMPOSSIBLE TO UPDATE STATUS", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }.execute();


            }
        });

        //Windows.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //    @Override
        //    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //        //if (isChecked)
        //    }
        //});
    }
}
