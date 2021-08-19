 package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText workingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button currency  = findViewById(R.id.currency);
        Button clear     = findViewById(R.id.clear_btn);
        Button backspace = findViewById(R.id.backspace);
        Button brc       = findViewById(R.id.brc);
        Button div       = findViewById(R.id.div);
        Button mul       = findViewById(R.id.multiply);
        Button sub       = findViewById(R.id.sub);
        Button add       = findViewById(R.id.add);
        Button equal     = findViewById(R.id.equal);
        Button dot       = findViewById(R.id.dot);
        Button nine      = findViewById(R.id.nine);
        Button eight     = findViewById(R.id.eight);
        Button seven     = findViewById(R.id.seven);
        Button six       = findViewById(R.id.six);
        Button five      = findViewById(R.id.five);
        Button four      = findViewById(R.id.four);
        Button three     = findViewById(R.id.three);
        Button two       = findViewById(R.id.two);
        Button one       = findViewById(R.id.one);
        Button zero      = findViewById(R.id.zero);

        currency.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, currencyConvertor.class);
            startActivity(intent);
        });

        clear.setOnClickListener(view -> {
            int cursorPos = workingTextView.getSelectionStart();
            int textLength = workingTextView.getText().length();

            if(cursorPos == 0 && textLength == 0){

            }
            else{
                workingTextView.setText(R.string.blank);
            }
        });

        backspace.setOnClickListener(view -> {
            int cursorPos = workingTextView.getSelectionStart();
            int textLength = workingTextView.getText().length();

            if(cursorPos != 0 && textLength != 0){
                SpannableStringBuilder selection = (SpannableStringBuilder) workingTextView.getText();
                selection.replace(cursorPos -1, cursorPos, "");
                workingTextView.setText(selection);
                workingTextView.setSelection(cursorPos - 1);
            }
        });

        brc.setOnClickListener(view -> {
            int cursorPos = workingTextView.getSelectionStart();
            int openPar = 0;
            int closedPar = 0;
            int textLen = workingTextView.getText().length();

            for (int i = 0; i < cursorPos; i++) {
                if (workingTextView.getText().toString().substring(i, i+1).equals("(")) {
                    openPar += 1;
                }
                if (workingTextView.getText().toString().substring(i, i+1).equals(")")) {
                    closedPar += 1;
                }
            }

            if (openPar == closedPar || workingTextView.getText().toString().substring(textLen-1, textLen).equals("(")) {
                setWorkings("(");
            }
            else if (closedPar < openPar && !workingTextView.getText().toString().substring(textLen-1, textLen).equals("(")) {
                setWorkings(")");
            }
            workingTextView.setSelection(cursorPos + 1);
        });

        equal.setOnClickListener(view -> {
            String userExp = workingTextView.getText().toString();

            userExp = userExp.replaceAll("÷", "/");
            userExp = userExp.replaceAll("×", "*");

            Expression exp = new Expression(userExp);

            String result = String.valueOf(exp.calculate());

            workingTextView.setText(result);
            workingTextView.setSelection(result.length());
        });


        div.setOnClickListener(view -> setWorkings("/"));

        mul.setOnClickListener(view -> setWorkings("*"));

        sub.setOnClickListener(view -> setWorkings("-"));

        add.setOnClickListener(view -> setWorkings("+"));

        dot.setOnClickListener(view -> setWorkings("."));

        nine.setOnClickListener(view -> setWorkings("9"));

        eight.setOnClickListener(view -> setWorkings("8"));

        seven.setOnClickListener(view -> setWorkings("7"));

        six.setOnClickListener(view -> setWorkings("6"));

        five.setOnClickListener(view -> setWorkings("5"));

        four.setOnClickListener(view -> setWorkings("4"));

        three.setOnClickListener(view -> setWorkings("3"));

        two.setOnClickListener(view -> setWorkings("2"));

        one.setOnClickListener(view -> setWorkings("1"));

        zero.setOnClickListener(view -> setWorkings("0"));

    }
    private void setWorkings(String strToAdd) {
        workingTextView = findViewById(R.id.working_TextView);
        workingTextView.setShowSoftInputOnFocus(false);
        String oldStr = workingTextView.getText().toString();
        int cursorPos = workingTextView.getSelectionStart();
        String strLeft = oldStr.substring(0, cursorPos);
        String strRight = oldStr.substring(cursorPos);

        if(getString(R.string._0).equals(workingTextView.getText().toString())){
            workingTextView.setText(strToAdd);
        }
        else {
            workingTextView.setText(String.format("%s%s%s", strLeft, strToAdd, strRight));
        }
        workingTextView.setSelection(cursorPos + 1);

    }
}