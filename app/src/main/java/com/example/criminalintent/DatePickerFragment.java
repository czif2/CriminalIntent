package com.example.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_DATE="date";
    public static final String EXTRA_DATE =
            "com.example.criminalintent.date";

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle args=new Bundle();
        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date=(Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);

        mDatePicker= (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year,month,day,null);

        return new AlertDialog.Builder(getActivity())
                .setView(v).setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year=mDatePicker.getYear();
                        int month=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        Date date=new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                }).create();
    }
    private void sendResult(int resultCode,Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
