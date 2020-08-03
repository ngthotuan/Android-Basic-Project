package tk.nttuan.note;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tk.nttuan.model.Note;

import static tk.nttuan.note.MainActivity.NOTE_SELECTED;
import static tk.nttuan.note.MainActivity.TABLE_NAME;
import static tk.nttuan.note.MainActivity.database;

public class EditNoteActivity extends AppCompatActivity {

    EditText edtTitle, edtContent, edtTag;
    TextView txtLastSaved;

    int idState = 0; // check state on pause
    boolean btnSaveClick = false; // check if save click
    boolean btnDiscardClick = false; // check if discard click

    Note noteSelected = null;

    // add Ad
    private AdView mAdView;
    private static String TAG = "EditNoteActivity";

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        addControls();
        addEvents();
        setValueIfUpdate();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    private void addControls() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtTag = findViewById(R.id.edtTag);
        txtLastSaved = findViewById(R.id.txtLastSave);
        edtTitle.requestFocus();
        txtLastSaved.setText(getResources().getString(R.string.last_save)+getTime());

        mAdView = findViewById(R.id.adView);
    }

    private void addEvents() {
        edtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    txtLastSaved.setText(getResources().getString(R.string.last_save)+getTime());
                }
            }
        });
        edtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    txtLastSaved.setText(getResources().getString(R.string.last_save)+getTime());
                }
            }
        });
        edtTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    txtLastSaved.setText(getResources().getString(R.string.last_save)+getTime());
                }
            }
        });

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.e(TAG, "onAdClosed");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.e(TAG, "onAdOpened");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setValueIfUpdate() {
        noteSelected = (Note) getIntent().getSerializableExtra(NOTE_SELECTED); // if update
        if(noteSelected != null){
            edtTitle.setText(noteSelected.getTitle());
            edtContent.setText(noteSelected.getContent());
            edtTag.setText(noteSelected.getTag());
            txtLastSaved.setText(getResources().getString(R.string.last_save)+noteSelected.getTime());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!btnSaveClick && !btnDiscardClick && noteSelected == null){
            processSaveNote();
        }
        if(noteSelected != null){
            processUpdateNote();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(idState != 0)
        {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = " + idState;
            database.queryData(sql);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.mnuSave:
                btnSaveClick = true;
                // create -> save
                if(noteSelected == null)
                {
                    if(processSaveNote())
                    {
                        Toast.makeText(EditNoteActivity.this, getResources().getString(R.string.add_success),Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditNoteActivity.this, getResources().getString(R.string.add_fail),Toast.LENGTH_LONG).show();

                    }
                }
                // update note
                else
                {
                    if(processUpdateNote())
                    {
                        Toast.makeText(EditNoteActivity.this, getResources().getString(R.string.update_success),Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditNoteActivity.this, getResources().getString(R.string.update_fail),Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.mnuDiscard:
                btnDiscardClick = true;
                if(noteSelected != null){
                    processDeleteNote(noteSelected);
                }
                else{
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean processSaveNote() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String time = getTime();
        String tag = edtTag.getText().toString();

        if(!title.isEmpty() || !content.isEmpty() || !tag.isEmpty())
        {
            String sql = "INSERT INTO " + TABLE_NAME +
                    " VALUES(null,'" + title + "','" + content + "','" + time + "','" + tag + "')";
            try {
                database.queryData(sql);
            }catch (Exception ex) {
                Log.e(getResources().getString(R.string.error_tag), ex.toString());
                return false;
            }

            Cursor cursor = database.getData("SELECT MAX(id) FROM " + TABLE_NAME);
            cursor.moveToNext();
            idState = cursor.getInt(0);
            cursor.close();
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean processUpdateNote() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        String time = noteSelected.getTime();
        String tag = edtTag.getText().toString();

        if(!title.isEmpty() || !content.isEmpty() || !tag.isEmpty()) {
            if(!title.equals(noteSelected.getTitle()) || !content.equals(noteSelected.getContent()) || !tag.equals(noteSelected.getTag())){
                time = getTime();
            }
            String sql = "UPDATE " + TABLE_NAME + " SET " +
                    " title = '" + title +
                    "', content = '" + content +
                    "', time = '" + time +
                    "', tag = '" + tag +
                    "' WHERE id = " + noteSelected.getId();
            try {
                database.queryData(sql);
            } catch (Exception ex) {
                Log.e(getResources().getString(R.string.error_tag), ex.toString());
                return false;
            }

            Cursor cursor = database.getData("SELECT MAX(id) FROM " + TABLE_NAME);
            cursor.moveToNext();
            idState = cursor.getInt(0);
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }

    private void processDeleteNote(final Note note) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.confirm_delete));
        builder.setMessage(getResources().getString(R.string.question_delete)+note.getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @SuppressLint("Recycle")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM "+TABLE_NAME+" WHERE id = "+note.getId());
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(calendar.getTime());
        return time;
    }
}
