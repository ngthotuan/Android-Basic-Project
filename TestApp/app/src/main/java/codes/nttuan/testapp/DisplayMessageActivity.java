package codes.nttuan.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView txtMessage = findViewById(R.id.txtMessage);
        txtMessage.setText(data);
    }
}