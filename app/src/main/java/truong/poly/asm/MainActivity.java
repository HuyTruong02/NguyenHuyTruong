 package truong.poly.asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import truong.poly.asm.fragment.ChiFragment;
import truong.poly.asm.fragment.GioithieuFragment;
import truong.poly.asm.fragment.ThongKeFragment;
import truong.poly.asm.fragment.ThuFragment;


 public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);

        imageView = findViewById(R.id.menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager faFragmentManager = getSupportFragmentManager();
                Fragment fragment;
                switch (item.getItemId()){
                    case  R.id.mThu:
                        fragment = new ThuFragment();
                        Toast.makeText(MainActivity.this, "Khoản thu", Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.mChi:
                        fragment = new ChiFragment();
                        Toast.makeText(MainActivity.this, "Khoản chi", Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.mThongke:
                        fragment = new ThongKeFragment();
                        Toast.makeText(MainActivity.this, "Thống kê", Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.mGioithieu:
                        fragment = new GioithieuFragment();
                        Toast.makeText(MainActivity.this, "Giới thiệu", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        fragment = new ThuFragment();
                        Toast.makeText(MainActivity.this, "Thoát", Toast.LENGTH_SHORT).show();
                        break;
                }
                faFragmentManager
                        .beginTransaction()
                        .replace(R.id.LinearLayout, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });
    }
}