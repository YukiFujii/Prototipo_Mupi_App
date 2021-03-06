package com.example.yuki.prototipo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private boolean flagAccess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void callHome(View v)
    {
        if(flagAccess) {
            Intent it = new Intent(this, Home.class);
            startActivityForResult(it, 0);
            finish();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Login incorreto!");
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }
}
