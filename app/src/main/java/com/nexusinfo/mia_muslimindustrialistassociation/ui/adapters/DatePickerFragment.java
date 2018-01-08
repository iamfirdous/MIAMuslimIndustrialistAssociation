package com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nexusinfo.mia_muslimindustrialistassociation.R;

import java.util.Calendar;

/**
 * Created by firdous on 12/21/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText etFromDate, etToDate;

    private static final String FROM_DATE = "FromDate", TO_DATE = "ToDate";

    private int day, month, year;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.MIATheme_DatePicker, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String tag = getTag();

//        etFromDate = getActivity().findViewById(R.id.editText_from_date);
//        etToDate = getActivity().findViewById(R.id.editText_to_date);
//
//        this.day = view.getDayOfMonth();
//        this.month = view.getMonth();
//        this.year = view.getYear();
//
//        if (tag.equals(FROM_DATE))
//            etFromDate.setText(this.day+"-"+(this.month+1)+"-"+this.year);
//
//        if (tag.equals(TO_DATE))
//            etToDate.setText(this.day+"-"+(this.month+1)+"-"+this.year);
    }
}
