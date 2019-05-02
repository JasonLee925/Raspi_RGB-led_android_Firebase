package com.example.course_iot_rgbled;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Spinner sprLED1, sprLED2;
    private Button bSpeech, bFlash;
    private  int isFlash = 0;

    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    DatabaseReference led1_R, led1_G, led1_B, led2_R, led2_G, led2_B, flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImplementsIni();
        FireDBSetup();

        // Spinner declaration
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Colors, R.layout.support_simple_spinner_dropdown_item);
        sprLED1.setAdapter(adapter);
        sprLED2.setAdapter(adapter);

        // LED_1 Spinner onSelected Listener
        sprLED1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        led1_R.setValue(0);
                        led1_G.setValue(0);
                        led1_B.setValue(0);
                        break;
                    case 1:
                        led1_R.setValue(1);
                        led1_G.setValue(0);
                        led1_B.setValue(0);
                        break;
                    case 2:
                        led1_R.setValue(0);
                        led1_G.setValue(1);
                        led1_B.setValue(0);
                        break;
                    case 3:
                        led1_R.setValue(0);
                        led1_G.setValue(0);
                        led1_B.setValue(1);
                        break;
                    case 4:
                        led1_R.setValue(1);
                        led1_G.setValue(1);
                        led1_B.setValue(0);
                        break;
                    case 5:
                        led1_R.setValue(1);
                        led1_G.setValue(0);
                        led1_B.setValue(1);
                        break;
                    case 6:
                        led1_R.setValue(0);
                        led1_G.setValue(1);
                        led1_B.setValue(1);
                        break;
                    case 7:
                        led1_R.setValue(1);
                        led1_G.setValue(1);
                        led1_B.setValue(1);
                        break;
                    default:
                         led1_R.setValue(0);
                         led1_G.setValue(0);
                         led1_B.setValue(0);
                         break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // LED_2 Spinner onSelected Listener
        sprLED2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        led2_R.setValue(0);
                        led2_G.setValue(0);
                        led2_B.setValue(0);
                        break;
                    case 1:
                        led2_R.setValue(1);
                        led2_G.setValue(0);
                        led2_B.setValue(0);
                        break;
                    case 2:
                        led2_R.setValue(0);
                        led2_G.setValue(1);
                        led2_B.setValue(0);
                        break;
                    case 3:
                        led2_R.setValue(0);
                        led2_G.setValue(0);
                        led2_B.setValue(1);
                        break;
                    case 4:
                        led2_R.setValue(1);
                        led2_G.setValue(1);
                        led2_B.setValue(0);
                        break;
                    case 5:
                        led2_R.setValue(1);
                        led2_G.setValue(0);
                        led2_B.setValue(1);
                        break;
                    case 6:
                        led2_R.setValue(0);
                        led2_G.setValue(1);
                        led2_B.setValue(1);
                        break;
                    case 7:
                        led2_R.setValue(1);
                        led2_G.setValue(1);
                        led2_B.setValue(1);
                        break;
                    default:
                        led2_R.setValue(0);
                        led2_G.setValue(0);
                        led2_B.setValue(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Speech Button onClick Listener
    public void bSpeechClick(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speech");
        startActivityForResult(intent, 1);
    }

    // Flash Button onClick Listener
    public void onFlashClick(View view){
        isFlash = (isFlash == 0)?1:0;
        flash.setValue(isFlash);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String off = "關";
        final String on = "開";

        if (requestCode == 1 && resultCode == RESULT_OK){
            String firstMatch = data != null ? data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0) : null;

            flash.setValue(0);
            isFlash = 0;

            if(firstMatch.contains(on)){
                led1_R.setValue(1);
                led1_G.setValue(1);
                led1_B.setValue(1);
                led2_R.setValue(1);
                led2_G.setValue(1);
                led2_B.setValue(1);
            }else if(firstMatch.contains(off)){
                led1_R.setValue(0);
                led1_G.setValue(0);
                led1_B.setValue(0);
                led2_R.setValue(0);
                led2_G.setValue(0);
                led2_B.setValue(0);
            }
        }
    }

    // Implements initialization
    private void ImplementsIni(){
        sprLED1 = findViewById(R.id.sprLED1);
        sprLED2 = findViewById(R.id.sprLED2);
        bSpeech =  findViewById(R.id.bSpeech);
        bFlash =  findViewById(R.id.bFlash);
    }

    // FireBase Setup
    private void FireDBSetup(){
        led1_R = fireDB.getReference().child("led").child("Red");
        led1_G = fireDB.getReference().child("led").child("Green");
        led1_B = fireDB.getReference().child("led").child("Blue");
        led2_R = fireDB.getReference().child("led2").child("Red");
        led2_G = fireDB.getReference().child("led2").child("Green");
        led2_B = fireDB.getReference().child("led2").child("Blue");
        flash = fireDB.getReference().child("flash");
    }
}
