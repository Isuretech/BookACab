package com.isure.viahero.bookacab.mFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.isure.viahero.bookacab.R;
import com.isure.viahero.bookacab.vhTasks.vhTasks;

/**
 * Created by nec on 8/24/2016.
 */
public class Login extends Activity {
    private EditText txtUsername;
    private EditText txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            txtUsername.setText(data.getStringExtra("Username"));
            txtPassword.setText(data.getStringExtra("password"));
        }
    }

    public void btnLogin_Click(View v){
        if("".equals(txtUsername.getText().toString())){
            txtUsername.setError("Username is required!");
            return;
        }
        if("".equals(txtPassword.getText().toString())) {
            txtPassword.setError("Password is required!");
            return;
        }
        new vhTasks.TaskLogin(
                Login.this,txtUsername.getText().toString(),
                txtPassword.getText().toString())
                .execute(txtUsername.getText().toString(),
                        txtPassword.getText().toString(),
                        "Passenger");
    }
}
