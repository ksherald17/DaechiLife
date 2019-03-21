package com.softfinger.seunghyun.daechilife.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

public class AddClassAlreadyInDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView ok;

    public AddClassAlreadyInDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addclassalreadyindialog);

        ok = findViewById(R.id.addclassalreadyindialogok);
        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.equals(ok)){
            dismiss();
        }
    }
}