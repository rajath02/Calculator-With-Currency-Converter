package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.calculator.Retrofit.RetrofitBuilder;
import com.example.calculator.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class currencyConvertor extends AppCompatActivity {

    Button cals ;
    Button convert ;
    EditText num ;
    Spinner convFrom ;
    Spinner convTo ;
    TextView resultCurrency ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_convertor);


        cals = (Button) findViewById(R.id.cals);
        convert = (Button) findViewById(R.id.convert);
        num = (EditText) findViewById(R.id.num);
        convFrom = (Spinner) findViewById(R.id.convertFrom);
        convTo = (Spinner) findViewById(R.id.convertTo);
        resultCurrency = (TextView) findViewById(R.id.result);

        String[] dropDownList = {"USD", "INR", "EUR", "NZD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convTo.setAdapter(adapter);
        convFrom.setAdapter(adapter);

        convert.setOnClickListener(v -> {
            RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convFrom.getSelectedItem().toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    assert res != null;
                    JsonObject rates = res.getAsJsonObject("rates");
                    double currency = Double.parseDouble(num.getText().toString());
                    double multiplier = Double.parseDouble(rates.get(convTo.getSelectedItem().toString()).toString());
                    double result = currency * multiplier;
                    resultCurrency.setText(String.valueOf(result));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        });


        cals.setOnClickListener(view -> {
            Intent intent = new Intent(currencyConvertor.this, MainActivity.class);
            startActivity(intent);
        });
    }
}