package tk.nttuan.relativelayout_kp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    EditText edtsource, edtusername, edtkey, edtres;
    Button btnProcess, btnShowAll;

    public static String DATABASE_NAME = "pass.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String tablename = "MyPass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyPass();

            }
        });
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void xuLyLuuDatabase(ContentValues values) {

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        database.insert(tablename,null,values);
    }

    private void xuLyPass() {
        String source = edtsource.getText().toString();
        String user = edtusername.getText().toString();
        String key = edtkey.getText().toString();
        if(source.isEmpty() || user.isEmpty() || key.isEmpty())
        {
            Toast.makeText(MainActivity.this, "You must input source, username, private key", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int n = parseString(source)  + parseString(user) + parseString(key);
            String res = decode(user, n);
            edtres.setText(res);
            //Luu database
            ContentValues values = new ContentValues();
            values.put("source",source);
            values.put("user",user);
            values.put("privatekey",key);
            values.put("pass",res);
            xuLyLuuDatabase(values);
        }
    }

    private String decode(String s, int n) {
        int up = 65+ n%10 + 17;// + random.nextInt(26);
        int low = 97 + n%10+ 17;//random.nextInt(26);
        int num = n%10;
        int spe = 33+ n%10 + 6;//random.nextInt(15);
        String res ="";
        int size = s.length();
        if(size < 4)
        {
            s =  "RanD";
            size = 4;
        }
        if(size > 10)
            size =10;
        for(int i = 0 ; i< size; i++)
        {
            if(i == size/4)
            {
                res += (char)up;
                res += (char)spe;
            }
            if(i==size/3)
                s+= Integer.toHexString(n);
            if(i == size/2)
            {
                res += (char)low;
                res += num;
            }
            res+= (char) ((int)s.charAt(i)+1);
        }
        return res;
    }

    private int parseString(String s) {
        int n = s.length();
        if(n > 15)
            n=15;
        int sum  = 0 ;
        for(int i = 0; i< n ; i++)
        {
            sum += i*(int)s.charAt(i);
        }
        return sum;
    }

    private void addControls() {
        edtkey = findViewById(R.id.edtPrivateKey);
        edtres = findViewById(R.id.edtRes);
        edtsource = findViewById(R.id.edtSource);
        edtusername = findViewById(R.id.edtUsername);
        btnProcess = findViewById(R.id.btnProcess);
        btnShowAll  =findViewById(R.id.btnShowAll);
    }
    private void processCopy()
    {
        try {
            File dbFile = getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                copyDatabaseFromAsset();
                Toast.makeText(MainActivity.this,"Sao chép sqlite từ Assets vào hệ thống thành công",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Sao chép lỗi",Toast.LENGTH_LONG).show();
            Log.e("Loi",ex.toString());
        }
    }
    private String getDatabasePath()
    {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }

    private void copyDatabaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFilename = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFilename);
            byte temp[] = new byte[1024];
            int len;
            while((len = myInput.read(temp)) > 0)
            {
                myOutput.write(temp,0,len);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("LOI", ex.toString());
        }
    }
}
