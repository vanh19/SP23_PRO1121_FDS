package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.NguoiDung;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.CheckConection;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.Server;

public class ManHinhDangNhapActivity extends AppCompatActivity {
    private EditText edTenDangNhap, edMatKhau;
    private CheckBox chkLuu;
    private Button btnDangNhap;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String tenTaiKhoanSv, matKhauSv;
    private ArrayList<NguoiDung> list;
    private TextView tvTaoTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        AnhXa();
        if (CheckConection.HaveConnection(getApplicationContext())){
            GetInformationUser();
        }else{
            CheckConection.ShowToast(getApplicationContext(), "Không có kết nối mạng");
        }
        FillData();
        CheckLogin();
        CreateAccount();
    }

    private void CreateAccount() {
        //Tạo phần gạch chân
        String ten = tvTaoTaiKhoan.getText().toString();
        SpannableString spannableString = new SpannableString(ten);
        spannableString.setSpan(new UnderlineSpan(), 0, ten.length(), 0);
        tvTaoTaiKhoan.setText(spannableString);

        tvTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ThemNguoiDungActivity.class));
            }
        });
    }

    private void GetInformationUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkGetThongTinNguoiDung, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                if (response != null){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenTaiKhoanSv = jsonObject.getString("tentk");
                            matKhauSv = jsonObject.getString("mk");
                            list.add(new NguoiDung(id, tenTaiKhoanSv, matKhauSv));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.ShowToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void FillData() {
        sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        edTenDangNhap.setText(sharedPreferences.getString("tendangnhap", ""));
        edMatKhau.setText(sharedPreferences.getString("matkhau", ""));
        chkLuu.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    private void CheckSave(String ten, String mk) {
        if (chkLuu.isChecked()){
            editor = sharedPreferences.edit();
            editor.putString("tendangnhap", ten);
            editor.putString("matkhau", mk);
            editor.putBoolean("checked", true);
        }else{
            editor = sharedPreferences.edit();
            editor.remove("matkhau");
            editor.remove("checked");
        }
        editor.commit();
    }

    private void CheckLogin() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                String ten = edTenDangNhap.getText().toString();
                String mk = edMatKhau.getText().toString();
                for (int i = 0; i < list.size(); i++) {
                    if (ten.isEmpty() || mk.isEmpty()) {
                        CheckConection.ShowToast(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin");
                        check = true;
                    } else if (ten.equals(list.get(i).getTenTaiKhoan()) && mk.equals(list.get(i).getMatKhau())) {
                        check = true;
                        CheckSave(ten, mk);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (check == false){
                    CheckConection.ShowToast(getApplicationContext(), "Thông tin không chính xác");
                }
            }
        });
    }

    private void AnhXa() {
        edTenDangNhap = findViewById(R.id.edTenDangNhap);
        edMatKhau = findViewById(R.id.edMatKhau);
        chkLuu = findViewById(R.id.chkLuuThongTin);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvTaoTaiKhoan = findViewById(R.id.tvTaoTaiKhoan);
        list = new ArrayList<>();
    }
}