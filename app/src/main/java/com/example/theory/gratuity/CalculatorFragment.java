package com.example.theory.gratuity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    private EditText currentTotal;

    private SeekBar gratuityBar;

    private TextView gratuity;
    private TextView gratuityPercent;

    private TextView subtotal;

    private Calculator calculator = new Calculator();

    private OnFragmentInteractionListener mListener;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    public static CalculatorFragment newInstance() {
        CalculatorFragment fragment = new CalculatorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator, container, false);

        currentTotal = (EditText) v.findViewById(R.id.currentTotal);

        gratuityBar = (SeekBar) v.findViewById(R.id.seekBar);
        gratuity = (TextView) v.findViewById(R.id.tipValue);
        gratuityPercent = (TextView) v.findViewById(R.id.tipPercent);

        subtotal = (TextView) v.findViewById(R.id.subtotal);

        currentTotal.setOnKeyListener(keyListener);
        gratuityBar.setOnSeekBarChangeListener(seekEvent);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
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
            Toast.makeText(getContext(), R.string.enter_amount, Toast.LENGTH_SHORT).show();

            gratuity.setText("0");
            subtotal.setText("0");
        } else if (total.matches(".*\\d|\\)$")) {
            double tot = parseInput(total);
            calculator.setTotal(tot);
            calculator.setGratuityBase(tip);

            gratuity.setText(calculator.getGratuityString());
            subtotal.setText(calculator.getSubtotalString());
        }

    }

    private Double parseInput(String input) {
        if (input.matches(".*[+-/*()]*.")) {
            Expression eh = new Expression(input);

            if (eh.checkSyntax())
                return eh.calculate();
            else
                return 0d;
        } else {
            return Double.parseDouble(input);
        }
    }
}
