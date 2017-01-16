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
import com.nhn.android.beview.model.Section;

import java.util.Calendar;

public class RegisterSectionFragment extends RegisterFragment implements View.OnClickListener, OnDateSelectedListener {

    private static final String SECTION = "section";

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    private Section section;
    private OnRegisterConferenceFragmentListener registerDoneListener;

    private EditText editId, editName, editLocation;
    private Button btnDone, btnStartDate, btnEndDate;
    private TextView tvStartDate, tvEndDate;

    public RegisterSectionFragment() {
    }

    public static RegisterSectionFragment newInstance(@Nullable Section section) {
        RegisterSectionFragment fragment = new RegisterSectionFragment();
        Bundle args = new Bundle();
        args.putSerializable(SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        section = (Section) getArguments().getSerializable(SECTION);
        if (section == null) {
            section = new Section();
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

        if (section == null) {

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
        section.setDescription(editLocation.getText().toString());
        section.setDescription(editId.getText().toString());
        section.setName(editName.getText().toString());

        if (section.getFile().equals("") || section.getName().equals("") ||
                section.getStartTime() == null || section.getEndTime() == null ||
                section.getFile().equals("")) {
            Toast.makeText(getActivity(), "내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (section.getStartTime().getTime() > section.getEndTime().getTime()) {
            Toast.makeText(getActivity(), "종료 시간이 시작 시간보다 빠를 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void OnDateSelected(String state, Calendar calendar) {
        switch (state) {
            case START_DATE: {
                section.setStartTime(calendar.getTime());
                tvStartDate.setText(section.getStringStartTime());
                break;
            }
            case END_DATE: {
                section.setEndTime(calendar.getTime());
                tvEndDate.setText(section.getStringEndTime());
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
