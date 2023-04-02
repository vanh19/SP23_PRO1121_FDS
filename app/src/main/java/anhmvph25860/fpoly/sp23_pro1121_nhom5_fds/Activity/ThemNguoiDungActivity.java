package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity.ManHinhDangNhapActivity;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.CheckConection;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.Server;

public class ThemNguoiDungActivity extends AppCompatActivity {
    private TextInputEditText edTenTaiKhoan, edPass, edRePass;
    private Button btnXacNhan, btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);

        AnhXa();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (CheckConection.HaveConnection(getApplicationContext())){
            EventButtonConfirm();//sự kiện nút xác nhận
        }else{
            CheckConection.ShowToast(getApplicationContext(), "Không có kết nối mạng");
        }
    }

    private void EventButtonConfirm() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenTaiKhoan = edTenTaiKhoan.getText().toString().trim();
                String pass = edPass.getText().toString().trim();
                String rePass = edRePass.getText().toString().trim();
                if (tenTaiKhoan.length() > 0 && pass.length() > 0 && rePass.length() > 0){
                    if (rePass.equals(pass)){
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkThongTinNguoiDung, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                CheckConection.ShowToast(getApplicationContext(), "Thêm thành công");
                                startActivity(new Intent(getApplicationContext(), ManHinhDangNhapActivity.class));
                                finish();
                            }
                        }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CheckConection.ShowToast(getApplicationContext(), error.toString());
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("tentaikhoan", tenTaiKhoan);
                                hashMap.put("matkhau", pass);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }else{
                        CheckConection.ShowToast(getApplicationContext(), "Mật khẩu nhập lại không đúng");
                    }
                }else{
                    CheckConection.ShowToast(getApplicationContext(), "Vui lòng điền đầy đủ thông tin");
                }
            }
        });
    }

    private void AnhXa() {
        edTenTaiKhoan = findViewById(R.id.edTenTaiKhoan);
        edPass = findViewById(R.id.edMatKhauAdd);
        edRePass = findViewById(R.id.edReMatKhau);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnTroVe);
    }
}