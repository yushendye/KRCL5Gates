package com.smgdevs.chinmay.krcl5gates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MessageListener{
    private EditText train_no;
    TextView my_sm_pn, my_sm_pn_time, my_gm_pn, my_gm_pn_time;

    TextView sm1, sm2, sm3, sm4, sm5;
    TextView smt1, smt2, smt3, smt4, smt5;
    TextView gm1, gm2, gm3, gm4, gm5;
    TextView gmt1, gmt2, gmt3, gmt4, gmt5;

    private final String STATION_MASTER_ID = "station_master_vbw";
    private static final String communication_detail_url = "https://smgdevelopers.000webhostapp.com/fill_communication_details.php";
    private static final String station_master_pn_url = "https://smgdevelopers.000webhostapp.com/insert_station_master_pn.php";
    private static final String station_master_log = "https://smgdevelopers.000webhostapp.com/sm_log.php";
    private static final String final_commit_url = "https://smgdevelopers.000webhostapp.com/sm_commit.php";

    String station_name = "VBW";
    private String communication_id;
    private String my_train_no;
    private String station_master_pn;
    private String date;
    private String time;
    private boolean write = false;

    private final String GM_ID1 = "LC_21";
    private final String GM_ID2 = "LC_22";
    private final String GM_ID3 = "LC_23";
    private final String GM_ID4 = "LC_24";
    private final String GM_ID5 = "LC_25";

    private String CONTACT = "";
    String gate = "";

    Button ack1, ack2, ack3, ack4, ack5, final_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request_permissions();

        MessageChecker.bindListener(this);
        train_no = findViewById(R.id.train_no);

        sm1 = findViewById(R.id.sm_pn1);
        sm2 = findViewById(R.id.sm_pn2);
        sm3 = findViewById(R.id.sm_pn3);
        sm4 = findViewById(R.id.sm_pn4);
        sm5 = findViewById(R.id.sm_pn5);

        smt1 = findViewById(R.id.sm_pn_time1);
        smt2 = findViewById(R.id.sm_pn_time2);
        smt3 = findViewById(R.id.sm_pn_time3);
        smt4 = findViewById(R.id.sm_pn_time4);
        smt5 = findViewById(R.id.sm_pn_time5);

        gm1 = findViewById(R.id.gm_pn1);
        gm2 = findViewById(R.id.gm_pn2);
        gm3 = findViewById(R.id.gm_pn3);
        gm4 = findViewById(R.id.gm_pn4);
        gm5 = findViewById(R.id.gm_pn5);

        gmt1 = findViewById(R.id.gm_pn_time1);
        gmt2 = findViewById(R.id.gm_pn_time2);
        gmt3 = findViewById(R.id.gm_pn_time3);
        gmt4 = findViewById(R.id.gm_pn_time4);
        gmt5 = findViewById(R.id.gm_pn_time5);

        ack1 = findViewById(R.id.ack_btn1);
        ack2 = findViewById(R.id.ack_btn2);
        ack3 = findViewById(R.id.ack_btn3);
        ack4 = findViewById(R.id.ack_btn4);
        ack5 = findViewById(R.id.ack_btn5);



    }
    public void print(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public int generateAndSend(){
        Random r = new Random();
        int random_no = r.nextInt(100);
        return  random_no;
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String date = simpleDateFormat.format(ts);

        return  date;
    }

    public String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String time = simpleDateFormat.format(ts);

        return time;
    }

    public void sendSMS(String number, String msg){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, msg, null, null);
    }

    public void generate_PN(View view){
        train_no.setEnabled(false);
        Button clicked_btn = (Button)view;
        int btn_id = clicked_btn.getId();
        switch (btn_id){
            case R.id.gen_btn1:
                gate = GM_ID1;
                my_sm_pn = sm1;
                my_sm_pn_time = smt1;
                my_gm_pn = gm1;
                my_gm_pn_time = gmt1;
                CONTACT = "9004447008";
                break;
            case R.id.gen_btn2:
                gate = GM_ID2;
                my_sm_pn = sm2;
                my_sm_pn_time = smt2;
                my_gm_pn = gm2;
                my_gm_pn_time = gmt2;
                CONTACT = "9004447008";
                break;
            case R.id.gen_btn3:
                gate = GM_ID3;
                my_sm_pn = sm3;
                my_sm_pn_time = smt3;
                my_gm_pn = gm3;
                my_gm_pn_time = gmt3;
                CONTACT = "9423876095";
                break;
            case R.id.gen_btn4:
                gate = GM_ID4;
                my_sm_pn = sm4;
                my_sm_pn_time = smt4;
                my_gm_pn = gm4;
                my_gm_pn_time = gmt4;
                CONTACT = "9004447008";
                break;
            case R.id.gen_btn5:
                gate = GM_ID5;
                my_sm_pn = sm5;
                my_sm_pn_time = smt5;
                my_gm_pn = gm5;
                my_gm_pn_time = gmt5;
                CONTACT = "9004447008";
                break;
        }

        //code copied from old module
        communication_id = "kr_comm_id" + generateAndSend();         //communication ID for all upcoming communications between connected devices
        my_train_no = train_no.getText().toString();                 //train number for which gate is being closed
        station_master_pn = generateAndSend() + "";                  //PN number generated by the station master
        date = getDate();
        time = getTime();

        my_sm_pn.setText(station_master_pn);
        my_sm_pn_time.setText(time);

        StringRequest req = new StringRequest(StringRequest.Method.POST, station_master_pn_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("inserted")){
                    String msg = communication_id + "," + my_train_no + "," + getTime()  + "," + STATION_MASTER_ID + "," + station_master_pn + "," + date + "," + gate;
                    //print("Sending to " + CONTACT);
                    sendSMS(CONTACT, msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                print("Volley Error : " + error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("stn_name", station_name);
                map.put("communication_id", communication_id);
                map.put("train_no", my_train_no);
                map.put("sm_id", STATION_MASTER_ID);
                map.put("sm_pn", station_master_pn);
                map.put("entry_date", date);
                map.put("sm_pn_time", getTime());
                map.put("gm_id", gate);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest request2 = new StringRequest(StringRequest.Method.POST, communication_detail_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                print("resp : " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                print("Volley Error : " + error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("communication_id", communication_id);
                map.put("sm_id", STATION_MASTER_ID);
                map.put("sm_pn", station_master_pn);
                map.put("comm_date", date);
                map.put("comm_time", time);
                return map;
            }
        };
        queue.add(request2);
        queue.add(req);
    }

    public void request_permissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            print("Please grant permission to send / Receive SMS");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            print("Please grant permission to send / Re        final_commit.setBackgroundColor(Color.CYAN);ceive SMS");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            print("Please grant permission to bluetooth");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 1);
            }
        }
    }

    @Override
    public void messageReceived(String msg) {
        //This means that gate-man has generated some PN
        String[] arr = msg.split(",");
        String gate_man = arr[1];
        print("From GateMan ID = " + gate_man);
        switch(gate_man){
            case GM_ID1:
                my_sm_pn = findViewById(R.id.sm_pn1);
                my_sm_pn_time = findViewById(R.id.sm_pn_time1);
                my_gm_pn = findViewById(R.id.gm_pn1);
                my_gm_pn_time = findViewById(R.id.gm_pn_time1);
                final_commit = findViewById(R.id.ack_btn1);
                break;
            case GM_ID2:
                my_sm_pn = findViewById(R.id.sm_pn2);
                my_sm_pn_time = findViewById(R.id.sm_pn_time2);
                my_gm_pn = findViewById(R.id.gm_pn2);
                my_gm_pn_time = findViewById(R.id.gm_pn_time2);
                final_commit = findViewById(R.id.ack_btn2);
                break;
            case GM_ID3:
                my_sm_pn = findViewById(R.id.sm_pn3);
                my_sm_pn_time = findViewById(R.id.sm_pn_time3);
                my_gm_pn = findViewById(R.id.gm_pn3);
                my_gm_pn_time = findViewById(R.id.gm_pn_time3);
                final_commit = findViewById(R.id.ack_btn3);
                break;
            case GM_ID4:
                my_sm_pn = findViewById(R.id.sm_pn4);
                my_sm_pn_time = findViewById(R.id.sm_pn_time4);
                my_gm_pn = findViewById(R.id.gm_pn4);
                my_gm_pn_time = findViewById(R.id.gm_pn_time4);
                final_commit = findViewById(R.id.ack_btn4);
                break;
            case GM_ID5:
                my_sm_pn = findViewById(R.id.sm_pn5);
                my_sm_pn_time = findViewById(R.id.sm_pn_time5);
                my_gm_pn = findViewById(R.id.gm_pn5);
                my_gm_pn_time = findViewById(R.id.gm_pn_time5);
                final_commit = findViewById(R.id.ack_btn5);
                break;
        }

        try {
            my_gm_pn.setText(arr[2]);
            my_gm_pn_time.setText(arr[4]);
        }catch (Exception e){
            print("You got a wrong message!!");
            my_gm_pn.setText("0");
            my_gm_pn_time.setText("0");
        }
        write = true;
        final_commit.setEnabled(true);
        final_commit.setClickable(true);
        StringRequest request = new StringRequest(StringRequest.Method.POST, station_master_log, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("communication_id", communication_id);
                map.put("train_no", my_train_no);
                map.put("sm_id", STATION_MASTER_ID);
                map.put("sm_pn", station_master_pn);
                map.put("entry_date", date);
                map.put("sm_pn_time", getTime());
                map.put("gm_id", gate);
                return  map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void final_commit_action(View view){
        Button final_commit = new Button(getApplicationContext());
        Button ack_btn = (Button)view;
        int btn_id = ack_btn.getId();

        switch (btn_id){
            case R.id.ack_btn1:
                final_commit = findViewById(R.id.ack_btn1);
                my_sm_pn = findViewById(R.id.sm_pn1);
                my_sm_pn_time = findViewById(R.id.sm_pn_time1);
                my_gm_pn = findViewById(R.id.gm_pn1);
                my_gm_pn_time = findViewById(R.id.gm_pn_time1);
                break;
            case R.id.ack_btn2:
                final_commit = findViewById(R.id.ack_btn2);
                my_sm_pn = findViewById(R.id.sm_pn2);
                my_sm_pn_time = findViewById(R.id.sm_pn_time2);
                my_gm_pn = findViewById(R.id.gm_pn2);
                my_gm_pn_time = findViewById(R.id.gm_pn_time2);
                break;
            case R.id.ack_btn3:
                final_commit = findViewById(R.id.ack_btn3);
                my_sm_pn = findViewById(R.id.sm_pn3);
                my_sm_pn_time = findViewById(R.id.sm_pn_time3);
                my_gm_pn = findViewById(R.id.gm_pn3);
                my_gm_pn_time = findViewById(R.id.gm_pn_time3);
                break;
            case R.id.ack_btn4:
                final_commit = findViewById(R.id.ack_btn4);
                my_sm_pn = findViewById(R.id.sm_pn4);
                my_sm_pn_time = findViewById(R.id.sm_pn_time4);
                my_gm_pn = findViewById(R.id.gm_pn4);
                my_gm_pn_time = findViewById(R.id.gm_pn_time4);
                break;
            case R.id.ack_btn5:
                final_commit = findViewById(R.id.ack_btn5);
                my_sm_pn = findViewById(R.id.sm_pn5);
                my_sm_pn_time = findViewById(R.id.sm_pn_time5);
                my_gm_pn = findViewById(R.id.gm_pn5);
                my_gm_pn_time = findViewById(R.id.gm_pn_time5);
                break;
        }

        final_commit.setEnabled(false);
        StringRequest request = new StringRequest(StringRequest.Method.POST, final_commit_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                print(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                print("Volley Error : " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("comm_id", communication_id);
                map.put("sm_pn", my_sm_pn.getText().toString());
                map.put("sm_time", my_gm_pn_time.getText().toString());
                map.put("gm_pn", my_gm_pn.getText().toString());
                map.put("gm_time", my_gm_pn_time.getText().toString());
                map.put("reg_date", getDate());
                map.put("reg_time", getTime());
                return  map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
