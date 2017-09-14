package com.example.utsav.bulksmsphone;


import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class MainActivity extends AppCompatActivity {
    Button snd;
    static EditText msg; File sdcard;
    static TextView tv;
    File file;BufferedReader br;


    String line="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        snd = (Button) findViewById(R.id.send);
        msg = (EditText) findViewById(R.id.editText2);
        tv = (TextView) findViewById(R.id.textView3);




        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Send Bulk Messages");
                alert.setMessage("Are you sure you want to Send?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        snd.setEnabled(false);
                       sendmsg();


                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();



            }
        });
    }

    void sendmsg(){
        try {
           sdcard = Environment.getExternalStorageDirectory();


           file = new File(sdcard, "smsnumbers.txt");

            StringBuilder text = new StringBuilder();


                br = new BufferedReader(new FileReader(file));
        }catch (Exception e) {
            Toast.makeText(this,"File Not Found or Permission Not Given",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        try{
                while ((line = br.readLine()) != null) {
                    if (line.length() == 10) {
                        for (int i = 0; i < 10; i++) {
                            int tem = line.charAt(i);
                            if (tem < 0 || tem > 9)
                                break;
                        }
                    }
                    else{
                        continue;
                    }
                  SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(line, null,msg.getText().toString(), null, null);

                    Thread.sleep(500);
                }
            snd.setEnabled(true);
            tv.setText("Messages Sent Successfully");
        }catch(Exception e){
                Toast.makeText(this,"message not send!!!",Toast.LENGTH_LONG).show();
                return ;

             }



    }

}