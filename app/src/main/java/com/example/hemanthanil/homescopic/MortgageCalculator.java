package com.example.hemanthanil.homescopic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MortgageCalculator extends AppCompatActivity {
    ArrayAdapter<CharSequence> marrayAdapter;
    Spinner spinner;
    Button convertbtn;
    EditText listprice_text,interest_text,editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listprice_text=(EditText)findViewById(R.id.listprice_text);
        interest_text=(EditText)findViewById(R.id.interest_text);
        editText=(EditText)findViewById(R.id.editText);

        convertbtn=(Button)findViewById(R.id.convertbtn);
        convertbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        convert();
    }
});

        spinner=(Spinner)findViewById(R.id.spinner);
        marrayAdapter=ArrayAdapter.createFromResource(this, R.array.spinnerarray,R.layout.support_simple_spinner_dropdown_item);
        marrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(marrayAdapter);
    }

    public void convert(){
double n=0;
        String spinnerString1=spinner.getSelectedItem().toString();

        if (spinnerString1.equals("5 Years"))
        {
             n=5*12;
        }
        else if (spinnerString1.equals("10 Years"))
        {
            n=10*12;
        }else if (spinnerString1.equals("15 Years"))
        {
            n=15*12;
        }else if (spinnerString1.equals("20 Years"))
        {
            n=20*12;
        }
        else if (spinnerString1.equals("25 Years"))
        {
            n=25*12;
        }else if (spinnerString1.equals("30 Years"))
        {
            n=30*12;
        }
        double principal=Double.parseDouble(listprice_text.getText().toString());
        double rate=Double.parseDouble(interest_text.getText().toString());
        double rate1=rate/(12.0*100);
        double math=(Math.pow((1+rate1),n)*rate1)*principal;
        double den=(Math.pow((1+rate1),n)-1);
        double ans=(math/den);
        editText.setText("$ "+Double.toString(ans));

    }

}
