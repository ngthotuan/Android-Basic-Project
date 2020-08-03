package tk.nttuan.layout_1712878;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalculator;
    Button btnGoogleMap;
    Button btnAlarm;
    Button btnCreateAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CalculatorActivity.class));
            }
        });

        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));

            }
        });
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AlarmActivity.class));
            }
        });
        btnCreateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AlarmCreateActivity.class));
            }
        });
    }

    private void addControls() {
        btnCalculator = findViewById(R.id.btnCalculator);
        btnGoogleMap = findViewById(R.id.btnGoogleMap);
        btnAlarm = findViewById(R.id.btnAlarm);
        btnCreateAlarm = findViewById(R.id.btnCreateAlarm);
    }
}
