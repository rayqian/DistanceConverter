package com.example.distanceconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_MainActivity_tag";
    private EditText userInput;
    private TextView outputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.input_field);
        outputField = findViewById(R.id.output_lable);
    }

    public void doPress(View v){
        Log.d(TAG,  "doPress is working " + userInput.getText().toString());
        outputField.setText(userInput.getText().toString());
    }

}