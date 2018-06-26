package com.example.mohamed.smartgate.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohamed.smartgate.R;
import com.example.mohamed.smartgate.utils.SetupDataUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText mUsernameEditText;
    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        SetupDataUtils.initialize(this);
    }


    public void loginMe(View view) {
        Intent startDetailsIntent = new Intent(this, DetailActivity.class);

        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.contains(username)) {
            if (preferences.getString(username, "").equals(password)) {
                startDetailsIntent.putExtra("username", username);
                startDetailsIntent.putExtra("password", password);
                startDetailsIntent.setAction("action-login");

                startActivity(startDetailsIntent);
            } else {
                showToast("password is incorrect!");
            }
        } else {
            showToast("username is incorrect!");
        }

    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
