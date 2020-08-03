package tk.nttuan.intentdataresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtName;
    Button btnEdit;
    int REQUEST_CODE_SEND = 113;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SEND);
            }
        });
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        btnEdit = findViewById(R.id.btnEdit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SEND &&  resultCode == RESULT_OK)
        {
            txtName.setText(data.getStringExtra("newName"));
        }
    }
}
