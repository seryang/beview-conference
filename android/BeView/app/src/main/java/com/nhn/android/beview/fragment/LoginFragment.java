package com.nhn.android.beview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.nhn.android.beview.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private OnLoginFragmentListener listener;
    private TextInputLayout inputLayoutId;
    private EditText editId, editPassword;
    private View btnLogin, textGoToJoin;


    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        editId = (EditText) layout.findViewById(R.id.edit_id);
        editPassword = (EditText) layout.findViewById(R.id.edit_pw);
        btnLogin = layout.findViewById(R.id.button_login);
        textGoToJoin = layout.findViewById(R.id.text_go_to_join);
        inputLayoutId = (TextInputLayout) layout.findViewById(R.id.input_layout_id);


        btnLogin.setOnClickListener(this);
        textGoToJoin.setOnClickListener(this);
        editId.addTextChangedListener(new MyTextWacher(editId, inputLayoutId));
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            listener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login: {
                if (processLogin()){
                    getActivity().finish();
                }
                break;
            }

            case R.id.text_go_to_join: {
                if (listener != null) {
                    listener.onClickMoveToJoinFragment();
                }
                break;
            }
        }
    }

    private boolean processLogin() {
        if (editId.getText().toString().equals("") || editPassword.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "아이디와 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public interface OnLoginFragmentListener {
        void onClickMoveToJoinFragment();
    }


    private static class MyTextWacher implements TextWatcher {

        private final TextInputLayout editInputLayout;
        private final EditText editId;

        public MyTextWacher(EditText editId, TextInputLayout inputLayout) {
            this.editId = editId;
            this.editInputLayout = inputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validateEmail();
        }

        private boolean validateEmail() {
            String email = editId.getText().toString();
            Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
            if (!matcher.find()) {
                editInputLayout.setErrorEnabled(true);
                editInputLayout.setError("이메일 형식이 아닙니다.");
                return false;
            } else {
                editInputLayout.setErrorEnabled(false);
                return true;
            }
        }
    }
}
