package com.example.mohamed.smartgate.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mohamed.smartgate.R;
import com.example.mohamed.smartgate.data.MilitaryContract;
import com.example.mohamed.smartgate.data.MilitaryProvider;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 105;
    @BindView(R.id.img_user)
    ImageView mUserPhotoImageView;

    @BindView(R.id.img_done)
    ImageView mDoneImageView;

    @BindView(R.id.tv_name)
    TextView mNameTextView;

    @BindView(R.id.tv_username)
    TextView mUsernameTextView;

    @BindView(R.id.tv_description)
    TextView mDescriptionTextView;

    @BindView(R.id.tv_no_data_found)
    TextView mNoDataFoundTextView;

    @BindView(R.id.bp_loading_indicator)
    ProgressBar mLoadingIndicatorProgressBar;

    private String mUsername;
    private String mPassword;

    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (!TextUtils.isEmpty(action)) {
            if (action.equals("action-login")) {
                mUsername = intent.getStringExtra("username");
                mPassword = intent.getStringExtra("password");

                hideData();
                mDoneImageView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        mDoneImageView.setVisibility(View.INVISIBLE);
                        showData();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> loader = loaderManager.getLoader(LOADER_ID);


        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, null, this);
        }

    }

    private void showData() {
        mUserPhotoImageView.setVisibility(View.VISIBLE);
        mNameTextView.setVisibility(View.VISIBLE);
        mUsernameTextView.setVisibility(View.VISIBLE);
        mDescriptionTextView.setVisibility(View.VISIBLE);
    }

    private void hideData() {
        mUserPhotoImageView.setVisibility(View.INVISIBLE);
        mNameTextView.setVisibility(View.INVISIBLE);
        mUsernameTextView.setVisibility(View.INVISIBLE);
        mDescriptionTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_next: {

                Intent bluetoothIntent = new Intent(DetailActivity.this, BluetoothActivity.class);
                bluetoothIntent.putExtra(Intent.EXTRA_TEXT, mPassword);
                startActivity(bluetoothIntent);
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MilitaryProvider.Users.USERS_URI,
                null,
                MilitaryContract.UsersEntry.COLUMN_USERNAME + "=?",
                new String[]{mUsername},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        updateUi(data);
    }

    private void updateUi(Cursor data) {
        mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);

        if (data == null || data.getCount() == 0) {
            mNoDataFoundTextView.setVisibility(View.VISIBLE);
            return;
        }

        while (data.moveToNext()) {
            mNoDataFoundTextView.setVisibility(View.INVISIBLE);
            Picasso.with(this)
                    .load(data.getInt(data.getColumnIndexOrThrow(MilitaryContract.UsersEntry.COLUMN_PHOTO)))
                    .into(mUserPhotoImageView);

            mUsernameTextView.setText(data.getString
                    (data.getColumnIndexOrThrow(MilitaryContract.UsersEntry.COLUMN_USERNAME)));

            String name = data.getString(data.getColumnIndexOrThrow(MilitaryContract.UsersEntry.COLUMN_NAME));
            mNameTextView.setText(name);
            mDescriptionTextView.setText("Welcome " + name + " in the military app authentication");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
