package tk.nttuan.implicitintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

import javax.net.ssl.ManagerFactoryParameters;


public class MainActivity extends AppCompatActivity {
    //Truy cap web
    EditText edtLink;
    Button btnTruyCap;

    //Nhan tin
    EditText edtPhoneSms, edtsmsboby;
    Button btnSms;

    //call
    EditText edtPhoneCall;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvent();
    }

    private void addEvent() {
        btnTruyCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = edtLink.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(MainActivity.this,"Đã xảy ra lỗi, vui lòng kiềm tra lại!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhoneSms.getText().toString();
                String smsbody  = edtsmsboby.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body",smsbody);
                intent.setData(Uri.parse("sms:" + phone));
                startActivity(intent);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                    call();
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CALL_PHONE},113);
                }
            }
        });
    }

    private void call() {
        String phone = edtPhoneCall.getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }

    private void addControls() {
        //Truy cap link
        edtLink = findViewById(R.id.edtLink);
        btnTruyCap = findViewById(R.id.btnTruyCap);

        //Nhan tin
        edtPhoneSms = findViewById(R.id.edtPhoneSMS);
        edtsmsboby = findViewById(R.id.edtsmsboby);
        btnSms = findViewById(R.id.btnSms);

        //Goi dien
        edtPhoneCall = findViewById(R.id.edtPhoneCall);
        btnCall = findViewById(R.id.btnCall);
    }
}
