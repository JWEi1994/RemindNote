package com.note.remindnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

//public class EditActivity extends BaseActivity {
//
//    EditText ed;
////    private String content;
////    private String time;
//
//    private Toolbar myToolbar;
//    private String old_content = "";
//    private String old_time = "";
//    private int old_Tag = 1;
//    private long id = 0;
//    private int openMode = 0;
//    private int tag = 1;
//    public Intent intent = new Intent(); // message to be sent
//    private boolean tagChange = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.edit_layout);
//        ed = findViewById(R.id.ed);
//
//        Intent getIntent = getIntent();
//        openMode = getIntent.getIntExtra("mode", 0);
//
//        if (openMode == 3) { //open existing note
//            id = getIntent.getLongExtra("id", 0);
//            old_content = getIntent.getStringExtra("content");
//            old_time = getIntent.getStringExtra("time");
//            old_Tag = getIntent.getIntExtra("tag", 1);
//            ed.setText(old_content);
//            ed.setSelection(old_content.length());
//
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.edit_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_HOME) {
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//            autoSetMessage();
////            Intent intent = new Intent();
////            intent.putExtra("content", ed.getText().toString());
////            intent.putExtra("time", dateToStr());
//            setResult(RESULT_OK, intent);
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void autoSetMessage() {
//        if (openMode == 4) {
//            if (ed.getText().toString().length() == 0) {
//                intent.putExtra("mode", -1); //nothing happens
//            } else {
//                intent.putExtra("mode", 0); // create new note
//                intent.putExtra("content", ed.getText().toString());
//                intent.putExtra("time", dateToStr());
//                intent.putExtra("tag", tag);
//            }
//        } else {
//            if (ed.getText().toString().equals(old_content) && !tagChange)
//                intent.putExtra("mode", -1); // edit nothing
//            else {
//                intent.putExtra("mode", 1); //edit content
//                intent.putExtra("content", ed.getText().toString());
//                intent.putExtra("time", dateToStr());
//                intent.putExtra("id", id);
//                intent.putExtra("tag", tag);
//            }
//        }
//    }
//
//    private String dateToStr() {
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // date format 2021-01-11 23:59:59
//        return simpleDateFormat.format(date);
//    }
//}

public class EditActivity extends BaseActivity {

    EditText ed;
    // private String content;
    // private String time;

    private Toolbar myToolbar;
    private String old_content = "";
    private String old_time = "";
    private int old_Tag = 1;
    private long id = 0;
    private int openMode = 0;
    private int tag = 1;
    public Intent intent = new Intent(); // message to be sent
    private boolean tagChange = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        myToolbar = findViewById(R.id.my_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set toolbar and replace actionbar

        //click the icon back to menu
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSetMessage();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ed = findViewById(R.id.ed);
        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode", 0);

        if (openMode == 3) {//open existing note
            id = getIntent.getLongExtra("id", 0);
            old_content = getIntent.getStringExtra("content");
            old_time = getIntent.getStringExtra("time");
            old_Tag = getIntent.getIntExtra("tag", 1);
            ed.setText(old_content);
            ed.setSelection(old_content.length());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            autoSetMessage();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void autoSetMessage() {
        if (openMode == 4) {
            if (ed.getText().toString().length() == 0) {
                intent.putExtra("mode", -1); //nothing new happens.
            } else {
                intent.putExtra("mode", 0); // create new note;
                intent.putExtra("content", ed.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("tag", tag);
            }
        } else {
            if (ed.getText().toString().equals(old_content) && !tagChange)
                intent.putExtra("mode", -1); // edit nothing
            else {
                intent.putExtra("mode", 1); //edit content
                intent.putExtra("content", ed.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("id", id);
                intent.putExtra("tag", tag);
            }
        }
    }

    public String dateToStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // date format 2021-01-11 23:59:59
        return simpleDateFormat.format(date);
    }
}