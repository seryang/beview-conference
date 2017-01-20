package com.nhn.android.beview.fragment.admin.register;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.beview.R;
import com.nhn.android.beview.listener.OnDateSelectedListener;
import com.nhn.android.beview.model.Conference;

import java.util.Calendar;

public class RegisterTrackFragment extends RegisterFragment implements View.OnClickListener, OnDateSelectedListener {

    private static final String CONFERENCE = "conference";

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private Conference conference;
    private OnRegisterConferenceFragmentListener registerDoneListener;

    private EditText editId, editName, editLocation;
    private Button btnDone, btnStartDate, btnEndDate;
    private TextView tvStartDate, tvEndDate;

    public RegisterTrackFragment() {
    }

    public static RegisterTrackFragment newInstance(@Nullable Conference conference) {
        RegisterTrackFragment fragment = new RegisterTrackFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONFERENCE, conference);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conference = (Conference) getArguments().getSerializable(CONFERENCE);
        if (conference == null) {
            conference = new Conference();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_register_conference, container, false);
        editId = (EditText) layout.findViewById(R.id.edit_id);
        editName = (EditText) layout.findViewById(R.id.edit_name);
        editLocation = (EditText) layout.findViewById(R.id.edit_location);
        btnDone = (Button) layout.findViewById(R.id.button_done);
        btnStartDate = (Button) layout.findViewById(R.id.button_start_date);
        btnEndDate = (Button) layout.findViewById(R.id.button_end_date);
        tvStartDate = (TextView) layout.findViewById(R.id.text_start_date);
        tvEndDate = (TextView) layout.findViewById(R.id.text_end_date);

        btnDone.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);

        if (conference == null) {

        }
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterConferenceFragmentListener) {
            registerDoneListener = (OnRegisterConferenceFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterConferenceFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        registerDoneListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done: {
                if (checkConferenceData()) {
                    registerConferenceData();
                    registerDoneListener.onClickDoneButton();
                }
                break;
            }
            case R.id.button_start_date: {
                DatePickerFragment dialogFragment = new DatePickerFragment();
                dialogFragment.addDateSelectedListener(this);
                dialogFragment.setCurrentState(START_DATE);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            }
            case R.id.button_end_date: {
                DatePickerFragment dialogFragment = new DatePickerFragment();
                dialogFragment.addDateSelectedListener(this);
                dialogFragment.setCurrentState(END_DATE);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            }
        }
    }

    //TODO
    private void registerConferenceData() {

    }

    private boolean checkConferenceData() {
        conference.setLocation(editLocation.getText().toString());
        conference.setStrId(editId.getText().toString());
        conference.setName(editName.getText().toString());

        if (conference.getStrId().equals("") || conference.getName().equals("") ||
                conference.getStartDate() == null || conference.getEndDate() == null ||
                conference.getLocation().equals("")) {
            Toast.makeText(getActivity(), "내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (conference.getStartDate().getTime() > conference.getEndDate().getTime()) {
            Toast.makeText(getActivity(), "종료 일자가 시작 일자보다 빠를 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void OnDateSelected(String state, Calendar calendar) {
        switch (state) {
            case START_DATE: {
                conference.setStartDate(calendar.getTime());
                tvStartDate.setText(conference.getStringStartDate());
                break;
            }
            case END_DATE: {
                conference.setEndDate(calendar.getTime());
                tvEndDate.setText(conference.getStringEndDate());
                break;
            }
        }
    }

    public interface OnRegisterConferenceFragmentListener {
        void onClickDoneButton();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private OnDateSelectedListener onDateSelectedListener;
        private String currentState;

        public void addDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
            this.onDateSelectedListener = onDateSelectedListener;
        }

        public void setCurrentState(String state) {
            currentState = state;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            onDateSelectedListener.OnDateSelected(currentState, calendar);
        }
    }
}
