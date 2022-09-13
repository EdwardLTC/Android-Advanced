package com.edward.myapplication;

import static com.edward.myapplication.config.CONFIG.ACTION_REMOVE_KEY_REMOVEUSER;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_ID;
import static com.edward.myapplication.config.CONFIG.DATABASE_KEY_USER_NAME;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_ACTION;
import static com.edward.myapplication.config.CONFIG.INTENT_GETALLUSER_KEY_USERLIST;
import static com.edward.myapplication.config.CONFIG.INTENT_GETREGISTERINFO_KEY_REGISTINFO;
import static com.edward.myapplication.config.CONFIG.SERVICE_ACTION;
import static com.edward.myapplication.config.CONFIG.SERVICE_GETALLUSER_NAME;
import static com.edward.myapplication.config.CONFIG.SERVICE_RESULT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.edward.myapplication.adapter.UserAdapter;
import com.edward.myapplication.modal.User;
import com.edward.myapplication.service.GetAllUserService;
import com.edward.myapplication.ui.fragment.AdminUserManager;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {
    // TODO: 9/12/2022
    ArrayList<User> userList = new ArrayList<>();
    private final String ROLE = "role";
    private final String USERNAME = "username";
    private final int ROLE_ADMIN = 0;
    private final int ROLE_USER = 1;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        IntentFilter filterGetAllUser = new IntentFilter(INTENT_GETALLUSER_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(getAllUserReceiver, filterGetAllUser);

        Intent intent = new Intent(this, GetAllUserService.class);
        intent.setAction(SERVICE_GETALLUSER_NAME);
        this.startService(intent);

        Button btnLogin = findViewById(R.id.login);
        EditText user = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        CheckLogin();

        btnLogin.setOnClickListener(view -> DoLogin(user.getText().toString(), pass.getText().toString()));
    }

    private void login(String username, String pass) {
        Intent intent = new Intent(this, MainActivity.class);
        if (username.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Username and Pass is Empty", Toast.LENGTH_SHORT).show();
        }
        if (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
            intent.putExtra(USERNAME, "admin");
            intent.putExtra(ROLE, ROLE_ADMIN);
            startActivity(intent);
            return;
        }
        for (User user : userList) {
            if (user.get_FullName().equalsIgnoreCase(username) && user.get_ID().equalsIgnoreCase(pass)) {
                intent.putExtra(DATABASE_KEY_USER_ID, user.get_ID());
                intent.putExtra(ROLE, 1);
                intent.putExtra(USERNAME, user.get_FullName());
                startActivity(intent);
                finish();
                return;
            }
        }
        Toast.makeText(this, "Login False", Toast.LENGTH_SHORT).show();
    }

    public void DoLogin(String username, String pass) {
        try {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            if (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
                intent.putExtra(USERNAME, "admin");
                intent.putExtra(ROLE, ROLE_ADMIN);
                shpEditor = shp.edit();
                shpEditor.putString("name", username);
                shpEditor.commit();
                startActivity(intent);
                return;
            }
            for (User user : userList) {
                if (user.get_FullName().equalsIgnoreCase(username) && user.get_ID().equalsIgnoreCase(pass)) {
                    intent.putExtra(DATABASE_KEY_USER_ID, user.get_ID());
                    intent.putExtra(ROLE, 1);
                    intent.putExtra(USERNAME, user.get_FullName());
                    shpEditor = shp.edit();
                    shpEditor.putString("name", username);
                    shpEditor.commit();
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            Toast.makeText(this, "Login False", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private final BroadcastReceiver getAllUserReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra(SERVICE_RESULT, RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra(SERVICE_ACTION);
                switch (action) {
                    case SERVICE_GETALLUSER_NAME:
                        new ProcessInBackground(intent).execute();
                        break;
                    default:
                        break;
                }
            }

        }
    };

    @SuppressLint("StaticFieldLeak")
    private class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        Exception exception = null;
        Intent intent;

        public ProcessInBackground(Intent intent) {
            this.intent = intent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("loading data");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            userList.clear();
            if (intent.getSerializableExtra(INTENT_GETALLUSER_KEY_USERLIST) != null) {
                userList.addAll((ArrayList<User>) intent.getSerializableExtra(INTENT_GETALLUSER_KEY_USERLIST));
            }
            return exception;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getAllUserReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("des", "onDestroy: ");
    }


}