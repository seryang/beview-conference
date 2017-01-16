package com.nhn.android.beview.fragment.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhn.android.beview.R;
import com.nhn.android.beview.model.User;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.HttpURLConnection.HTTP_OK;

public class JoinFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "JoinFragment";
    private static final String USER_ADD_URL = "http://10.110.243.78/api/user";
    private static final String USER_DUPL_CHECK_URL = "http://10.110.243.78/api/user/check?email=";

    private OnJoinFragmentListener mListener;

    private TextInputLayout inputLayoutId, inputLayoutPassword, inputLayoutPassword2;
    private EditText editId, editPassword, editPassword2;
    private View textGoToJoin, btnSuccess;
    private boolean idValidation, idDuplicateValidation, passwordLengthValidation, passwordSameValidation;

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
        new PostTask(getActivity()).execute(editId.getText().toString(), editPassword.getText().toString());
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

            checkButtonEnable();
        }

        private boolean validateId() {
            return validateEmail();
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

        private boolean validateEmail() {
            String email = editId.getText().toString();
            Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
            if (!matcher.find()) {
                inputLayoutId.setErrorEnabled(true);
                inputLayoutId.setError("이메일 형식이 아닙니다.");
                return false;
            } else {
                inputLayoutId.setErrorEnabled(false);
                new DuplicateCheckTask().execute(editId.getText().toString());
                return true;
            }
        }
    }

    private void checkButtonEnable() {
        if (idValidation && idDuplicateValidation && passwordLengthValidation && passwordSameValidation) {
            btnSuccess.setEnabled(true);
        } else {
            btnSuccess.setEnabled(false);
        }
    }

    private class DuplicateCheckTask extends AsyncTask<String, Void, Boolean> {

        private String email;
        private static final String COOKIE_KEY = "Set-Cookie";

        @Override
        protected Boolean doInBackground(String... params) {
            email = params[0];

            HttpURLConnection connection = null;
            BufferedOutputStream outputStream = null;
            try {
                URL url = new URL(USER_DUPL_CHECK_URL + email);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "responseCode : " + responseCode);

                if (responseCode != HTTP_OK) {
                    return false;
                } else {
                    return true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                inputLayoutId.setErrorEnabled(false);
                idDuplicateValidation = true;
            } else {
                inputLayoutId.setErrorEnabled(true);
                inputLayoutId.setError("이미 존재하는 아이디입니다.");
                idDuplicateValidation = false;
            }

            checkButtonEnable();
        }
    }

    private class PostTask extends AsyncTask<String, Void, Boolean> {

        private String email, password;
        private ProgressDialog progressDialog;
        private final Context context;

        public PostTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("회원가입에 진행중입니다.");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            email = params[0];
            password = params[1];
            HttpURLConnection connection = null;
            BufferedOutputStream outputStream = null;
            try {
                URL url = new URL(USER_ADD_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                outputStream = new BufferedOutputStream(connection.getOutputStream());
                User user = new User();
                user.setId(email);
                user.setPassword(password);
                writeStream(outputStream, user);

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "responseCode : " + responseCode);

                if (responseCode != HTTP_OK) {
                    return false;
                } else {
                    return true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            progressDialog.dismiss();
            if (!success) {
                Toast.makeText(getActivity(), "회원가입에 실패하였습니다. 다시 시도 해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeStream(OutputStream outputStream, User user) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            Gson gson = new Gson();
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            String json = gson.toJson(user);
            Log.d(TAG, json);
            outputStreamWriter.write(json);
            outputStreamWriter.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {

                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
