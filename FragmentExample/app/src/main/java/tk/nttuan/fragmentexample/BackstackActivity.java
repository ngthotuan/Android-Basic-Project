package tk.nttuan.fragmentexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class BackstackActivity extends AppCompatActivity {
    Button btnReplaceFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstack);
        btnReplaceFrame=findViewById(R.id.btnReplaceFrame);
        btnReplaceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment2();
                addFragmentContent(fragment);
            }
        });
        replaceFragmentContent(new Fragment1());

    }
    private void replaceFragmentContent(Fragment fragment)
    {
        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutContent,fragment);
            fragmentTransaction.commit();
            Log.e("Replace by Fragment",fragment.getClass().getSimpleName());
        }
    }
    private void addFragmentContent(Fragment fragment)
    {
        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayoutContent,fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
            Log.e("Add Fragment",fragment.getClass().getSimpleName());
        }
    }
}
