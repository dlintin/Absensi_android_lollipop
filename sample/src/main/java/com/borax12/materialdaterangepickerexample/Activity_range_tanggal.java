package com.borax12.materialdaterangepickerexample;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import java.util.Calendar;

public class Activity_range_tanggal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    RelativeLayout view;
    private TextView dateTextView;
    private boolean mAutoHighlight = true;
    String tglawal,tglakhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_range);

        this.setTitle("Lihat Berdasarkan Tanggal");

        // Find our View instances
        dateTextView = findViewById(R.id.date_textview);
        Button dateButton = findViewById(R.id.date_button);
        Button btn_lihat = findViewById(R.id.lihat_data);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        Activity_range_tanggal.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Bundle bundle = new Bundle();
                bundle.putString("tglawal",tglawal);
                bundle.putString("tglakhir",tglakhir);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                select_tanggalreport select_tanggalreport = new select_tanggalreport();
                select_tanggalreport.setArguments(bundle);

                fragmentTransaction.replace(R.id.layout, select_tanggalreport);
                getFragmentManager().popBackStackImmediate();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        //seperate date to begin and end
        tglawal = year+"-"+(++monthOfYear)+"-"+dayOfMonth;
        tglakhir = yearEnd+"-"+(++monthOfYearEnd)+"-"+dayOfMonthEnd;
        String date = "Anda Memilih Tanggal dari - "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" Hingga "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
        //mengatur textview dengan string tanggal
        dateTextView.setText(date);
    }


}