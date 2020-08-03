package tk.nttuan.layout_1712878;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

public class CalculatorActivity extends AppCompatActivity {
    EditText edtInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        addControls();
        addEvents();
    }

    private void addEvents() {
        edtInput.requestFocus();

    }

    private void addControls() {
        edtInput = findViewById(R.id.edtInput);
    }

}
