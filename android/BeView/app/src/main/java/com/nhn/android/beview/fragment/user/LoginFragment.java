package com.nhn.android.beview.fragment.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhn.android.beview.AppConstants;
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

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";
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
                if (processLogin()) {
                    new LoginTask(getActivity()).execute(editId.getText().toString(), editPassword.getText().toString());
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

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        private static final String SET_COOKIE = "Set-Cookie";
        private static final String USER_LOGIN = "http://10.110.243.78/api/user/login";
        private String id, password;
        private ProgressDialog progressDialog;
        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("회원가입에 진행중입니다.");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            id = params[0];
            password = params[1];
            HttpURLConnection connection = null;
            BufferedOutputStream outputStream = null;
            try {
                URL url = new URL(USER_LOGIN);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                outputStream = new BufferedOutputStream(connection.getOutputStream());
                User user = new User();
                user.setId(id);
                user.setPassword(password);
                writeStream(outputStream, user);

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "responseCode : " + responseCode);

                Map<String, List<String>> headers = connection.getHeaderFields();
                saveCookie(headers.get(SET_COOKIE), id);

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

        private void saveCookie(List<String> cookies, String id) {
            for (String cookie : cookies) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(AppConstants.USER_SHARED_PREFER, Context.MODE_PRIVATE).edit();
                editor.putString(AppConstants.USER_ID_SHARED_PREFER, id);
                CookieManager.getInstance().setCookie(AppConstants.HOST_URL, cookie);
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            progressDialog.dismiss();
            if (success) {

            } else {

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
}
