package com.example.theory.gratuity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText currentTotal;

    private SeekBar gratuityBar;

    private TextView gratuity;
    private TextView gratuityPercent;

    private TextView subtotal;

    private Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTotal = (EditText) findViewById(R.id.currentTotal);

        gratuityBar = (SeekBar) findViewById(R.id.seekBar);
        gratuity = (TextView) findViewById(R.id.tipValue);
        gratuityPercent = (TextView) findViewById(R.id.tipPercent);

        subtotal = (TextView) findViewById(R.id.subtotal);

        currentTotal.setOnEditorActionListener(gratuityInputEvent);
        gratuityBar.setOnSeekBarChangeListener(seekEvent);
    }

    TextView.OnEditorActionListener gratuityInputEvent = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                int progress = gratuityBar.getProgress();
                double tip = progress * .01;
                updateView(tip);
                return true;
            }

            return false;
        }
    };

    SeekBar.OnSeekBarChangeListener seekEvent = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            gratuityPercent.setText(String.format(Locale.getDefault(), "%d%%", progress));

            double tip = progress * .01;

            updateView(tip);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void updateView(double tip) {
        String total = currentTotal.getText().toString();

        if (total.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
        } else {
            calculator.setTotal(total);
            calculator.setGratuityBase(tip);

            gratuity.setText(calculator.getGratuityString());
            subtotal.setText(calculator.getSubtotalString());
        }

    }

}
