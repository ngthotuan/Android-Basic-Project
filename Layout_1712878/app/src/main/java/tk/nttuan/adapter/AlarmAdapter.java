package tk.nttuan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import tk.nttuan.layout_1712878.R;
import tk.nttuan.model.Alarm;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private Activity context;
    private int resource;

    public AlarmAdapter( Activity context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(resource,null);

        TextView txtTime = convertView.findViewById(R.id.txtTime);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        Switch swState = convertView.findViewById(R.id.swState);

        Alarm alarm = getItem(position);

        txtTime.setText(alarm.getTime());
        txtDate.setText(alarm.getDate());
        swState.setChecked(alarm.isState());
        return convertView;
    }
}
