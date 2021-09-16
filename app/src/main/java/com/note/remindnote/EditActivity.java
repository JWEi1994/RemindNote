package com.note.remindnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EditActivity extends BaseActivity {

    EditText edTitle;
    EditText ed;
    private Context context;
    // private String content;
    // private String time;

    private Toolbar myToolbar;
    private String old_title = "";
    private String old_content = "";
    private String old_time = "";
    private int old_Tag = 1;
    private long id = 0;
    private int openMode = 0;
    private int tag = 1;
    public Intent intent = new Intent(); // message to be sent
    private boolean tagChange = false;
    ImageButton upload_image;
    ImageView image;
    private static final int pick_image = 1;
    byte[] bytes;
    ArrayList<byte[]> arrbytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        myToolbar = findViewById(R.id.my_Toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set toolbar and replace actionbar
        image = findViewById(R.id.image);

        findViewById(R.id.upload_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), pick_image);
            }
        });

        //click the icon back to menu
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSetMessage();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        edTitle = findViewById(R.id.edTitle);
        ed = findViewById(R.id.ed);
        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode", 0);

        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
        StyleSpan boldItalic = new StyleSpan(Typeface.BOLD_ITALIC);
        UnderlineSpan underline = new UnderlineSpan();

        if (openMode == 3) {//open existing note
            id = getIntent.getLongExtra("id", 0);
            old_title = getIntent.getStringExtra("title");
            old_content = getIntent.getStringExtra("content");
            old_time = getIntent.getStringExtra("time");
            old_Tag = getIntent.getIntExtra("tag", 1);
            edTitle.setText(old_title);
            ed.setText(old_content);
            edTitle.setSelection(old_title.length());
            ed.setSelection(old_content.length());

        }
    }

    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            Uri imageURL;
            imageURL = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURL);
                image.setImageBitmap(bitmap);
//                ByteArrayOutputStream bufferStream = new ByteArrayOutputStream(16*1024);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,80,bufferStream);
//                bytes = bufferStream.toByteArray();
//
//                arrbytes.add(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonBold(View view) {
        Spannable spannableString = new SpannableStringBuilder(ed.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), ed.getSelectionStart(), ed.getSelectionEnd(), 0);

        ed.setText(spannableString);
    }

    public void buttonItalic(View view) {
        Spannable spannableString = new SpannableStringBuilder(ed.getText());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), ed.getSelectionStart(), ed.getSelectionEnd(), 0);

        ed.setText(spannableString);
    }

    public void buttonItalicBold(View view) {
        Spannable spannableString = new SpannableStringBuilder(ed.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), ed.getSelectionStart(), ed.getSelectionEnd(), 0);

        ed.setText(spannableString);
    }

    public void buttonUnderline(View view) {
        Spannable spannableString = new SpannableStringBuilder(ed.getText());
        spannableString.setSpan(new UnderlineSpan(), ed.getSelectionStart(), ed.getSelectionEnd(), 0);

        ed.setText(spannableString);
    }

    public void buttonNormal(View view) {
        String stringText = ed.getText().toString();
        ed.setText(stringText);
    }

    @Override
    protected void needRefresh() {
        Log.d(TAG, "needRefresh: Edit");
        setNightMode();
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                new AlertDialog.Builder(EditActivity.this)
                        .setMessage("Did you want to delete it?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (openMode == 4) { //new note
                                    intent.putExtra("mode", -1);
                                    setResult(RESULT_OK, intent);
                                } else {//open exiting note
                                    intent.putExtra("mode", 2);
                                    intent.putExtra("id", id);
                                    setResult(RESULT_OK, intent);
                                }
                                finish();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            if (edTitle.getText().toString().length() != 0 || ed.getText().toString().length() != 0) {
                intent.putExtra("mode", 0); // create new note;
                intent.putExtra("title", edTitle.getText().toString());
                intent.putExtra("content", ed.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("tag", tag);

            } else {
                intent.putExtra("mode", -1); //nothing new happens.

            }
        } else {
            if (ed.getText().toString().equals(old_content) && edTitle.getText().toString().equals(old_title) && !tagChange)
                intent.putExtra("mode", -1); // edit nothing
            else {
                intent.putExtra("mode", 1); //edit content
                intent.putExtra("title", edTitle.getText().toString());
                intent.putExtra("content", ed.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("id", id);
                intent.putExtra("tag", tag);
            }
        }
    }

    public String dateToStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // date format 2021-01-11 23:59:59
        return simpleDateFormat.format(date);
    }
}