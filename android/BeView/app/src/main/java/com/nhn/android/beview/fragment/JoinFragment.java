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

import com.nhn.android.beview.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.process;

public class JoinFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "JoinFragment";
    private OnJoinFragmentListener mListener;

    private TextInputLayout inputLayoutId, inputLayoutPassword, inputLayoutPassword2;
    private EditText editId, editPassword, editPassword2;
    private View textGoToJoin, btnSuccess;
    private boolean idValidation, passwordLengthValidation, passwordSameValidation;

    public JoinFragment() {
    }

    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_join, container, false);
        inputLayoutId = (TextInputLayout) layout.findViewById(R.id.input_layout_id);
        inputLayoutPassword = (TextInputLayout) layout.findViewById(R.id.input_layout_pw1);
        inputLayoutPassword2 = (TextInputLayout) layout.findViewById(R.id.input_layout_pw2);

        editId = (EditText) layout.findViewById(R.id.edit_id);
        editPassword = (EditText) layout.findViewById(R.id.edit_pw1);
        editPassword2 = (EditText) layout.findViewById(R.id.edit_pw2);
        editId.addTextChangedListener(new MyTextWatcher(editId));
        editPassword.addTextChangedListener(new MyTextWatcher(editPassword));
        editPassword2.addTextChangedListener(new MyTextWatcher(editPassword2));

        textGoToJoin = layout.findViewById(R.id.text_go_to_login);
        btnSuccess = layout.findViewById(R.id.button_success);

        textGoToJoin.setOnClickListener(this);
        btnSuccess.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnJoinFragmentListener) {
            mListener = (OnJoinFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_go_to_login: {
                if (mListener != null) {
                    mListener.onClickMoveToLoginFragment();
                }
                break;
            }

            case R.id.button_success: {
                processJoin();
                getActivity().finish();
                break;
            }

        }

    }

    private void processJoin() {

    }

    public interface OnJoinFragmentListener {
        void onClickMoveToLoginFragment();
    }

    private class MyTextWatcher implements TextWatcher {

        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (editText.getId()) {
                case R.id.edit_id: {
                    idValidation = validateId();
                    break;
                }
                case R.id.edit_pw1: {
                    passwordLengthValidation = validatePasswordLength();
                    break;
                }
                case R.id.edit_pw2: {
                    passwordSameValidation = validatePasswordSame();
                    break;
                }
            }

            if (idValidation && passwordLengthValidation && passwordSameValidation) {
                btnSuccess.setEnabled(true);
            } else {
                btnSuccess.setEnabled(false);
            }
        }

        private boolean validateId() {
            return validateEmail() && validateDuplicate();
        }

        private boolean validatePasswordLength() {
            if (editPassword.getText().length() < 6) {
                inputLayoutPassword.setErrorEnabled(true);
                inputLayoutPassword.setError("6자리 이상 입력하셔야 합니다.");
                return false;
            } else {
                inputLayoutPassword.setErrorEnabled(false);
                return true;
            }
        }

        private boolean validatePasswordSame() {
            String password1 = editPassword.getText().toString();
            String password2 = editPassword2.getText().toString();
            if (!password1.equals(password2)) {
                inputLayoutPassword2.setErrorEnabled(true);
                inputLayoutPassword2.setError("두 비밀번호가 일치하지 않습니다.");
                return false;
            } else {
                inputLayoutPassword2.setErrorEnabled(false);
                return true;
            }
        }


        private boolean validateDuplicate() {
            //TODO 아이디 중복체크
            return true;
        }

        private boolean validateEmail() {
            String email = editId.getText().toString();
            Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
            if (!matcher.find()) {
                inputLayoutId.setErrorEnabled(true);
                inputLayoutId.setError("이메일 형식이 아닙니다.");
                return false;
            } else {
                inputLayoutId.setErrorEnabled(false);
                return true;
            }
        }
    }
}
