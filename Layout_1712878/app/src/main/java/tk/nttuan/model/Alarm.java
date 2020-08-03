package tk.nttuan.model;

import java.io.Serializable;

public class Alarm implements Serializable {
    private String time, date;
    private boolean state;

    public Alarm() {
    }

    public Alarm(String time, String date, Boolean state) {
        this.time = time;
        this.date = date;
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
