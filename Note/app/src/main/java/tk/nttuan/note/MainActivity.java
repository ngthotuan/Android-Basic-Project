package tk.nttuan.note;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tk.nttuan.adapter.NoteAdapter;
import tk.nttuan.model.Note;

public class MainActivity extends AppCompatActivity {
    //database
    public static  Database database = null;
    public static String DATABASE_NAME = "MyNote.sqlite";
    public static String TABLE_NAME = "Note";
    public static String NOTE_SELECTED = "tk.nttuan.NOTE_SELECTED";

    ImageView imgNewNote;
    ListView lvListNote;
    List<Note> data;
    NoteAdapter adapter;

    // add Ad
    private AdView mAdView;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        addControls();
        addEvents();

        this.setTitle(getResources().getString(R.string.all_note));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllNotes();
    }

    private void addEvents() {
        imgNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                startActivity(intent);
            }
        });
        lvListNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note noteSelected = data.get(position);
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(NOTE_SELECTED,noteSelected);
                startActivity(intent);
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

    private void addControls() {
        imgNewNote = findViewById(R.id.imgNewNote);

        lvListNote = findViewById(R.id.lvListNote);
        data = new ArrayList<>();
        adapter = new NoteAdapter(MainActivity.this,R.layout.note_item,data);
        lvListNote.setAdapter(adapter);
        mAdView = findViewById(R.id.adView);
    }

    private void showAllNotes() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor c = database.getData(sql);
        data.clear();
        while (c.moveToNext())
        {
            int id = c.getInt(0);
            String title = c.getString(1);
            String content = c.getString(2);
            String time = c.getString(3);
            String tag = c.getString(4);
            Note note = new Note(id,title,content,time,tag);
            data.add(note);
        }
        Collections.sort(data);
        adapter.notifyDataSetChanged();
        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem mnuSearch = menu.findItem(R.id.searchNote);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processFindNote(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuNewNote:
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.mnuDeleteAll:
                database.queryData("DELETE FROM " + TABLE_NAME);
                showAllNotes();
                break;
            case R.id.mnuSetting:
                Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_LONG).show();
                break;
            case R.id.mnuAbout:
                Toast.makeText(this, R.string.about_author, Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void processFindNote(String s) {
        if(s.isEmpty())
        {
            showAllNotes();
        }
        else
        {
            String clause = "'%"+s+"%'";
            String sql = "SELECT * FROM " + TABLE_NAME +
                        " WHERE title LIKE " + clause + " OR content LIKE " + clause + " OR tag LIKE " + clause;
            Cursor c = database.getData(sql);
            data.clear();
            while (c.moveToNext())
            {
                int id = c.getInt(0);
                String title = c.getString(1);
                String content = c.getString(2);
                String time = c.getString(3);
                String tag = c.getString(4);
                Note note = new Note(id,title,content,time,tag);
                data.add(note);
            }
            adapter.notifyDataSetChanged();
            c.close();
        }
    }

    private void createDatabase() {
        database = new Database(this,DATABASE_NAME,null,1);
        database.queryData("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT," +
                "tag TEXT)");
    }
}
