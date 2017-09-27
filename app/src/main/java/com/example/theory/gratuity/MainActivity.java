package com.example.theory.gratuity;



import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CalculatorFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CalculatorFragment cf = CalculatorFragment.newInstance();
        fragmentTransaction.add(R.id.calculators, cf, cf.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
    }

}
