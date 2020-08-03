package tk.nttuan.layout_1712878;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import tk.nttuan.adapter.AlarmAdapter;
import tk.nttuan.model.Alarm;

public class AlarmActivity extends AppCompatActivity {
    ListView lvAlarm;
    AlarmAdapter alarmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        addControls();
    }

    private void addControls() {
        lvAlarm = findViewById(R.id.lvAlarm);
        alarmAdapter = new AlarmAdapter(AlarmActivity.this,R.layout.item_alarm);
        alarmAdapter.add(new Alarm("04:45","Tue, Oct 1",false));
        alarmAdapter.add(new Alarm("12:43","Tue, Oct 1",true));
        alarmAdapter.add(new Alarm("15:35","Mon, Sep 30",false));
        alarmAdapter.add(new Alarm("21:00","Mon, Sep 30",false));
        alarmAdapter.add(new Alarm("12:43","Sat, Sep 29",true));
        alarmAdapter.add(new Alarm("12:43","Sat, Sep 29",true));
        lvAlarm.setAdapter(alarmAdapter);
    }
}
