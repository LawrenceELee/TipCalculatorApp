package com.example.lawrence.tipcalculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final int BASE_PERCENTAGE = 15;

    private double mBillAmount = 0.0;
    private int mPercent = BASE_PERCENTAGE;

    // View widgets
    private TextView mAmountTextView;
    private EditText mAmountEditText;

    private EditText mPercentEditText;

    private TextView mTipAmountTextView;
    private TextView mTotalTextView;

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inflate the GUI

        mAmountTextView = (TextView) findViewById(R.id.amountTextView);

        // set amountEditText's TextWatcher
        mAmountEditText = (EditText) findViewById(R.id.amountEditText);
        mAmountEditText.addTextChangedListener(amountEditTextWatcher);

        mPercentEditText = (EditText) findViewById(R.id.percentEditText);
        mPercentEditText.addTextChangedListener(percentEditTextWatcher);

        // display the amounts in dollars
        mTipAmountTextView = (TextView) findViewById(R.id.tipTextView);
        mTipAmountTextView.setText(CURRENCY_FORMAT.format(0));

        mTotalTextView = (TextView) findViewById(R.id.totalTextView);
        mTotalTextView.setText(CURRENCY_FORMAT.format(0));
    }

    // method to calc and display tip and total amounts
    private void calculate() {

        double percent = mPercent / 100.0;
        double tip = mBillAmount * percent;
        double total = mBillAmount + tip;

        mTipAmountTextView.setText(CURRENCY_FORMAT.format(tip));
        mTotalTextView.setText(CURRENCY_FORMAT.format(total));
    }

    // listener object for the
    private final TextWatcher percentEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            try {
                mPercent = Integer.parseInt(charSequence.toString());
            } catch (NumberFormatException e) {
                mPercent = 0;     // if parsing doesn't yield a number, set tip percent to 0.
            }

            calculate();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int after) {
            // not used
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // not used
        }
    }; // end object declaration and initialization

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            try {
                // get bill amount and display currency formatted value
                mBillAmount = Double.parseDouble(charSequence.toString());
                mAmountTextView.setText(CURRENCY_FORMAT.format(mBillAmount));

            } catch (NumberFormatException e) {
                // if s is empty or non-numeric
                mAmountTextView.setText("");
                mBillAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) {
            // not used
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // not used
        }
    }; // end amountEditTextWatcher object declaration and initialization
}

