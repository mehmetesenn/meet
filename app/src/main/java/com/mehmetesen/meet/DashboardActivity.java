package com.mehmetesen.meet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mehmetesen.meet.databinding.ActivityDashboardBinding;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding=ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = activityDashboardBinding.getRoot();
        setContentView(view);

        URL serverURL;
        try{
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

      

    }


    public void join(View view) {
        String roomcode = activityDashboardBinding.CodeBox.getText().toString();
        if(!roomcode.matches("")){
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(activityDashboardBinding.CodeBox.getText().toString())
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeetActivity.launch(DashboardActivity.this,options);


        }else{
            Toast.makeText(this, "You must enter a room name", Toast.LENGTH_LONG).show();
        }
        


    }


    public void share(View view) {

        String string = activityDashboardBinding.CodeBox.getText().toString();
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,string);
        intent.setType("text/plain");
        startActivity(intent);
       


    }

    public void Signout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
        finish();

    }
}