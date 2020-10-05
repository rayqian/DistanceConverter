package com.example.distanceconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private TextView inputTextView;
    private TextView outputTextView;
    private TextView outputField;
    private TextView historyField;
    private boolean fromMiles = true;//use a boolean value to determine the conversion direction
    private StringBuilder sb = new StringBuilder();
    //private String history = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.input_field);
        inputTextView = findViewById(R.id.input_textView);
        outputTextView = findViewById(R.id.output_textView);
        outputField = findViewById(R.id.output_lable);
        historyField = findViewById(R.id.history);
        historyField.setMovementMethod(new ScrollingMovementMethod());
        //set default value
        inputTextView.setText(R.string.M_value_String);
        outputTextView.setText(R.string.K_value_String);
    }

    public void doPress(View v){
        String input = userInput.getText().toString();
        Double value = 0.0;
        Double result;
        //validate Input
        try{
            value = Double.parseDouble(input);
        }catch (NumberFormatException ex){
            //handle invalid input
        }
        if(value == 0.0){
            Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
        }
        else{
            //call private method to calculate the double result
            result = calculateResult(value);
            String output = String.format("%.1f", result);
            //show the result to the output field
            outputField.setText(output);
            //clear the input field
            userInput.setText("");
            //store the result to history filed
            setHistoryValues(sb, input, output);
        }

    }

    public void radioOnClick(View v){
        switch(v.getId()){
            case R.id.m_to_k:
                //change the boolean value for calculation method
                fromMiles = true;
                //toast warning
                String msg1 = "Now converting from Miles to Kilometers.";
                Toast.makeText(this, msg1, Toast.LENGTH_SHORT).show(); //Toast is a static class
                //convert the unit of 2 textViews
                inputTextView.setText(R.string.M_value_String);
                outputTextView.setText(R.string.K_value_String);
                break;
            case R.id.k_to_m:
                //change the boolean value for calculation method
                fromMiles = false;
                //toast warning
                String msg2 = "Now converting from Kilometers to Miles.";
                Toast.makeText(this, msg2, Toast.LENGTH_SHORT).show();
                //convert the unit of 2 textViews
                inputTextView.setText(R.string.K_value_String);
                outputTextView.setText(R.string.M_value_String);
                break;
        }
    }

    private Double calculateResult(Double value){
        Double res;
        if(fromMiles){
            res = value * 1.60934;
        }
        else{
            res = value * 0.621371;
        }
        return res;
    }

    private void setHistoryValues(StringBuilder sb, String in, String out){
            String from;
            String to;
            if(fromMiles){
                from = "Mi";
                to = "Km";
            }
            else{
                from = "Km";
                to = "Mi";
            }
            sb.insert(0,in + " " + from + " ==> " + out + " " + to);
            sb.insert(0,"\n");
            historyField.setText(sb.toString());
    }

    public void clearOnClick(View v){
        sb.setLength(0);
        historyField.setText(sb.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("HISTORY", historyField.getText().toString());
        outState.putString("INPUT", userInput.getText().toString());
        outState.putBoolean("UNIT", fromMiles);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        historyField.setText(savedInstanceState.getString("HISTORY"));
        userInput.setText(savedInstanceState.getString("INPUT"));
        fromMiles = savedInstanceState.getBoolean("UNIT");
        sb.append(savedInstanceState.getString("HISTORY"));//restore the StringBuilder
    }
}