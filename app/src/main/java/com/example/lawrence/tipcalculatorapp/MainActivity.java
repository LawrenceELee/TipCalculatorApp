package com.example.lawrence.tipcalculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; // for saving state information

import android.text.Editable;
import android.text.TextWatcher;

import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    private double mBillAmount = 0.0;
    private double mPercent = 0.15; // set default tip percentage to 15%

    // View widgets
    private TextView mAmountTextView;
    private TextView mPercentTextView;
    private TextView mTipTextView;
    private TextView mTotalTextView;

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to widgets
        mAmountTextView = (TextView) findViewById(R.id.amountTextView);
        mPercentTextView = (TextView) findViewById(R.id.percentTextView);
        mTipTextView = (TextView) findViewById(R.id.tipTextView);
        mTotalTextView = (TextView) findViewById(R.id.totalTextView);

        // set tip and total to 0 initially
        mTipTextView.setText(CURRENCY_FORMAT.format(0));
        mTotalTextView.setText(CURRENCY_FORMAT.format(0));

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    // method to calc and display tip and total amounts
    private void calculate() {
        mPercentTextView.setText(PERCENT_FORMAT.format(mPercent));

        double tip = mBillAmount * mPercent;
        double total = mBillAmount + tip;

        mTipTextView.setText(CURRENCY_FORMAT.format(tip));
        mTotalTextView.setText(CURRENCY_FORMAT.format(total));
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {

        // update mPercent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            mPercent = progress / 100.0; // set mPercent based on progress
            calculate(); // calculate and display tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // not used
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // not used
        }
    }; // end seekBarListener object declaration and initialization

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                mBillAmount = Double.parseDouble(s.toString()) / 100.0;
                mAmountTextView.setText(CURRENCY_FORMAT.format(mBillAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
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

