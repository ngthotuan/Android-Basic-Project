package tk.nttuan.intentdataresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText edtNewText ;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newName",edtNewText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void addControls() {
        edtNewText = findViewById(R.id.edtNewText);
        btnConfirm = findViewById(R.id.btnConfirm);

    }
}
