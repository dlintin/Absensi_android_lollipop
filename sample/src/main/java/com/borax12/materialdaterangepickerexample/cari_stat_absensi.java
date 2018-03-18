package com.borax12.materialdaterangepickerexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Kuncoro on 22/03/2016.
 */
public class cari_stat_absensi extends Fragment implements
        DatePickerDialog.OnDateSetListener{

    public cari_stat_absensi(){}
    RelativeLayout view;
    private TextView dateTextView;
    private TextView timeTextView;
    private boolean mAutoHighlight = true;
    Intent intent;
    String tglawal,tglakhir;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.date_range, container, false);


        getActivity().setTitle("Lihat Berdasarkan Tanggal");


        // Find our View instances
        dateTextView = rootView.findViewById(R.id.date_textview);
//        timeTextView = (TextView)rootView.findViewById(R.id.time_textview);
        Button dateButton = rootView.findViewById(R.id.date_button);
//        Button timeButton = (Button)rootView.findViewById(R.id.time_button);
        Button btn_lihat = rootView.findViewById(R.id.lihat_data);

//        CheckBox ahl = (CheckBox) rootView.findViewById(R.id.autohighlight_checkbox);
//        ahl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                mAutoHighlight = b;
//            }
//        });

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        cari_stat_absensi.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
//
//        timeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar now = Calendar.getInstance();
//                TimePickerDialog tpd = TimePickerDialog.newInstance(
//                        Tools.this,
//                        now.get(Calendar.HOUR_OF_DAY),
//                        now.get(Calendar.MINUTE),
//                        false
//                );
//                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        Log.d("TimePicker", "Dialog was cancelled");
//                    }
//                });
//                tpd.show(getFragmentManager(), "Timepickerdialog");
//            }
//        });

        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Bundle bundle = new Bundle();
                bundle.putString("tglawal",tglawal);
                bundle.putString("tglakhir",tglakhir);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                stat_absensi stat_absensi = new stat_absensi();
                stat_absensi.setArguments(bundle);

                fragmentTransaction.replace(R.id.layout, stat_absensi);

                getFragmentManager().popBackStackImmediate();
//
//                select_tanggalreport select_tanggalreport = new select_tanggalreport();
//
//              fragTransaction.replace(R.id.layout,select_tanggalreport);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//
            }
        });

        return rootView;

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

//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
//        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
//        String minuteString = minute < 10 ? "0"+minute : ""+minute;
//        String hourStringEnd = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
//        String minuteStringEnd = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;
//        String time = "You picked the following time: From - "+hourString+"h"+minuteString+" To - "+hourStringEnd+"h"+minuteStringEnd;
//
//        timeTextView.setText(time);
//    }

}