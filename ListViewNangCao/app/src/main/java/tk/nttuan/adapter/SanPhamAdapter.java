package tk.nttuan.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tk.nttuan.listviewnangcao.R;
import tk.nttuan.modle.SanPham;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;

    public SanPhamAdapter(Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(resource,null);

        ImageView imgHinh = convertView.findViewById(R.id.imgHinh);
        TextView txtTen = convertView.findViewById(R.id.txtTen);
        TextView txtGia = convertView.findViewById(R.id.txtGia);

        SanPham sp = getItem(position);
        imgHinh.setImageResource(sp.getHinh());
        txtTen.setText(sp.getTen());
        txtGia.setText(sp.getGia()+" VND");

        return convertView;
    }
}

