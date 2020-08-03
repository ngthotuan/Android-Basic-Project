package tk.nttuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tk.nttuan.listviewnangcao.R;
import tk.nttuan.modle.SanPham;

public class SanPhamAdapter2 extends BaseAdapter {
    Context context;
    int resource;
    List<SanPham> listSanPham;

    public SanPhamAdapter2(Context context, int resource, List<SanPham> listSanPham) {
        this.context = context;
        this.resource = resource;
        this.listSanPham = listSanPham;
    }

    @Override
    public int getCount() {
        return listSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //c2
    private class ViewHolder
    {
        ImageView imgHinh;
        TextView txtTen;
        TextView txtGia;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Cơ bản tốn tài nguyên findViewById nhiều lần mỗi khi hiển thị
//        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(resource,null);
//        ImageView imgHinh = convertView.findViewById(R.id.imgHinh);
//        TextView txtTen = convertView.findViewById(R.id.txtTen);
//        TextView txtGia = convertView.findViewById(R.id.txtGia);
//
//        SanPham sp = listSanPham.get(position);
//        imgHinh.setImageResource(sp.getHinh());
//        txtTen.setText(sp.getTen());
//        txtGia.setText(sp.getGia()+" VND");

        //Nâng cao, tối ưu code áp dụng cho c1,c2
        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,null);
            holder.imgHinh = convertView.findViewById(R.id.imgHinh);
            holder.txtTen = convertView.findViewById(R.id.txtTen);
            holder.txtGia = convertView.findViewById(R.id.txtGia);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sp = listSanPham.get(position);
        holder.imgHinh.setImageResource(sp.getHinh());
        holder.txtTen.setText(sp.getTen());
        holder.txtGia.setText(sp.getGia()+" VND");
        return convertView;
    }
}
