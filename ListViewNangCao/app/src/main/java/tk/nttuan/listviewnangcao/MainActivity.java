package tk.nttuan.listviewnangcao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tk.nttuan.adapter.SanPhamAdapter;
import tk.nttuan.adapter.SanPhamAdapter2;
import tk.nttuan.modle.SanPham;

public class MainActivity extends AppCompatActivity {
    ListView lvDanhSachSanPham;
    SanPhamAdapter adapter;
    SanPhamAdapter2 adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }


    private void addControls() {
        lvDanhSachSanPham =findViewById(R.id.lvDanhSachSanPham);
        // C1 ---
//        adapter = new SanPhamAdapter(MainActivity.this,R.layout.item);
//        adapter.add(new SanPham(R.drawable.h1,"Hoa 1",10300));
//        adapter.add(new SanPham(R.drawable.h2,"Hoa 2",15000));
//        adapter.add(new SanPham(R.drawable.h3,"Hoa 3",17000));
//        adapter.add(new SanPham(R.drawable.h4,"Hoa 4",20000));
//        adapter.add(new SanPham(R.drawable.h5,"Hoa 5",18000));
//        adapter.add(new SanPham(R.drawable.h6,"Hoa 6",34000));
//        adapter.add(new SanPham(R.drawable.h7,"Hoa 7",23000));
//        adapter.add(new SanPham(R.drawable.h8,"Hoa 8",54000));
//        adapter.add(new SanPham(R.drawable.h9,"Hoa 9",13000));
//        adapter.add(new SanPham(R.drawable.h10,"Hoa 10",5000));
//        lvDanhSachSanPham.setAdapter(adapter);

        // C2 ---
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        listSanPham.add(new SanPham(R.drawable.h1,"Hoa 1",10300));
        listSanPham.add(new SanPham(R.drawable.h2,"Hoa 2",15000));
        listSanPham.add(new SanPham(R.drawable.h3,"Hoa 3",17000));
        listSanPham.add(new SanPham(R.drawable.h4,"Hoa 4",20000));
        listSanPham.add(new SanPham(R.drawable.h5,"Hoa 5",18000));
        listSanPham.add(new SanPham(R.drawable.h6,"Hoa 6",34000));
        listSanPham.add(new SanPham(R.drawable.h7,"Hoa 7",23000));
        listSanPham.add(new SanPham(R.drawable.h8,"Hoa 8",54000));
        listSanPham.add(new SanPham(R.drawable.h9,"Hoa 9",13000));
        listSanPham.add(new SanPham(R.drawable.h10,"Hoa 10",5000));
        adapter2 = new SanPhamAdapter2(MainActivity.this, R.layout.item, listSanPham);
        lvDanhSachSanPham.setAdapter(adapter2);
    }


}
