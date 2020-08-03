package tk.nttuan.pickerdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText edtChonNgay, edtChonGio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        edtChonNgay.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        edtChonNgay.setText(new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        edtChonGio.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minus = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       calendar.set(1,1,1,hourOfDay,minute);
                       edtChonGio.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
                    }
                },hour,minus,true);
                timePickerDialog.show();
            }
        });
    }

    private void addControls() {
        edtChonGio = findViewById(R.id.edtChonGio);
        edtChonNgay = findViewById(R.id.edtChonNgay);

    }
}
