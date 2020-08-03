package tk.nttuan.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable, Comparable<Object> {
    private int id;
    private String title, content, time, tag;

    public Note() {
    }

    public Note(int id, String title, String content, String time, String tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(Object o) {
        Note note = (Note)o;
        try {
            @SuppressLint("SimpleDateFormat") Date t_date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(this.time);
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(note.getTime());
            assert date != null;
            return date.compareTo(t_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
