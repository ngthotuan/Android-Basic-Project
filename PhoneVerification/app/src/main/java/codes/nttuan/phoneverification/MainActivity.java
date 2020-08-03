package codes.nttuan.phoneverification;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtInfo = findViewById(R.id.txtInfo);

        String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},113);
            }
        }
        String imei = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        String iccid = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        String phoneNumber = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        String serial = android.os.Build.SERIAL;
        String model = android.os.Build.MODEL;

        StringBuilder phoneInfo = new StringBuilder("Phone Info");
        phoneInfo.append("\nAndroid ID: ");
        phoneInfo.append(androidId);
        phoneInfo.append("\nIMEI: ");
        phoneInfo.append(imei);
        phoneInfo.append("\nICCID (Sim Serial Number): ");
        phoneInfo.append(iccid);
        phoneInfo.append("\nPhone Number: ");
        phoneInfo.append(phoneNumber);
        phoneInfo.append("\nAndroid Build.SERIAL: ");
        phoneInfo.append(serial);
        phoneInfo.append("\nAndroid Build.MODEL: ");
        phoneInfo.append(model);

        txtInfo.setText(phoneInfo);
    }
}
