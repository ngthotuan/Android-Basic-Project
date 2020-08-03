package tk.nttuan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import tk.nttuan.model.Note;
import tk.nttuan.note.R;

import static tk.nttuan.note.MainActivity.TABLE_NAME;
import static tk.nttuan.note.MainActivity.database;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int resource;
    private List<Note> data;

    public NoteAdapter(@NonNull Context context, int resource, List<Note> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public Note getItem(int position) {
        return data.get(position);
    }

    private class ViewHolder
    {
        TextView txtTitle;
        TextView txtTag;
        TextView txtTime;
        ImageView imgDeleteNote;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(resource,null);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.txtTag = convertView.findViewById(R.id.txtTag);
            holder.txtTime = convertView.findViewById(R.id.txtTime);
            holder.imgDeleteNote = convertView.findViewById(R.id.imgDeleteNote);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        final Note note = getItem(position);
        assert note != null;
        if(note.getTitle().isEmpty()){
            holder.txtTitle.setText(this.context.getResources().getString(R.string.no_title));
        }
        else{
            holder.txtTitle.setText(optimizeShow(note.getTitle(),25));
        }

        if(note.getTag().isEmpty()){
            holder.txtTag.setText(this.context.getResources().getString(R.string.no_tag));
        }
        else{
            holder.txtTag.setText(this.context.getResources().getString(R.string.tag)+optimizeShow(note.getTag(),25));
        }
        holder.txtTime.setText(this.context.getResources().getString(R.string.last_save)+note.getTime());
        holder.imgDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDeleteNote(note);
            }
        });
        return convertView;
    }

    private void processDeleteNote(final Note note) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this.context);
        builder.setTitle(context.getResources().getString(R.string.confirm_delete));
        builder.setMessage(context.getResources().getString(R.string.question_delete)+note.getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @SuppressLint("Recycle")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM "+TABLE_NAME+" WHERE id = "+note.getId());
                data.remove(note);
                notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private String optimizeShow(String s, int length){
        int size = s.length();
        if(size <= length)
            return s;
        else
        {
            String res;
            res = s.substring(0,length-3);
            res += "...";
            return res;
        }
    }
}