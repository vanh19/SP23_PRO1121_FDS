package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.DonHang;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.GioHang;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.DoAnFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.DoUongFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.DonHangFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.GioHangFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.HomeFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment.NguoiDungFragment;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    public static ArrayList<GioHang> listGioHang;
    public static ArrayList<DonHang> listDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//aaa
        AnhXa();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0,0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ChooseFragment(HomeFragment.newInstance());
        toolbar.setTitle("Trang chủ");
    }


    private void AnhXa() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        listDonHang = new ArrayList<anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.DonHang>();
        if (listGioHang == null){
            listGioHang = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_shopping:
                ChooseFragment(GioHangFragment.newInstance());
                toolbar.setTitle("Giỏ hàng");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_trang_chu:
                ChooseFragment(HomeFragment.newInstance());
                toolbar.setTitle("Trang chủ");
                break;
            case R.id.menu_do_an:
                ChooseFragment(DoAnFragment.newInstance());
                toolbar.setTitle("Đồ ăn");
                break;
            case R.id.menu_do_uong:
                ChooseFragment(DoUongFragment.newInstance());
                toolbar.setTitle("Đồ uống");
                break;
            case R.id.menu_gio_hang:
                ChooseFragment(GioHangFragment.newInstance());
                toolbar.setTitle("Giỏ hàng");
                break;
            case R.id.menu_don_hang:
                ChooseFragment(DonHangFragment.newInstance());
                toolbar.setTitle("Đơn hàng");
                break;
            case R.id.menu_nguoi_dung:
                ChooseFragment(NguoiDungFragment.newInstance());
                toolbar.setTitle("Liên hệ");
                break;
            case R.id.menu_dang_xuat:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Bạn chắc chắn muốn đăng xuất?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), ManHinhDangNhapActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    private void ChooseFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack("back");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else{
            super.onBackPressed();
            toolbar.setTitle("Trang chủ");
        }
    }
}