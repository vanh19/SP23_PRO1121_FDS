package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity.MainActivity;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.GioHangAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.TenSpDonHangAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.DonHang;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.GioHang;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.CheckConection;

public class GioHangFragment extends Fragment {
    private ListView lvDs;
    private TextView tvThongBao;
    public static TextView tvTongTien;
    private Button btnDatMua;
    private GioHangAdapter adapter;
    public static ArrayList<GioHang> listThucDon = new ArrayList<>();
    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance() {
        GioHangFragment fragment = new GioHangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.b_gio_hang_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa(view);
        CheckData();//Kiểm tra có dữ liệu hay không
        TotalMoney();//tổng tiền
        DeleteProduct();//xóa sản phẩm
        ButtonBought();//sự kiện đặt mua
    }

    private void ButtonBought() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listGioHang.size() > 0){
                    Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.layout_chi_tiet_don_hang);
                    dialog.setCanceledOnTouchOutside(false);

                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    //Set vị trị dialog
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.gravity = Gravity.BOTTOM;
                    window.setAttributes(layoutParams);

                    TextView tvTongTien;
                    EditText edThanhToan, edHoTen, edDiaChi, edSoDienThoai;
                    Button btnDatHang, btnHuyBo;

                    tvTongTien = dialog.findViewById(R.id.tvTongTienDonHangCt);
                    edThanhToan = dialog.findViewById(R.id.edPhuongThucThanhToan);
                    edHoTen = dialog.findViewById(R.id.edTenDonHangCt);
                    edDiaChi = dialog.findViewById(R.id.edDiaChiDonHangCt);
                    edSoDienThoai = dialog.findViewById(R.id.edSoDienThoaiDonHangCt);
                    btnDatHang = dialog.findViewById(R.id.btnDatHangDonHangCt);
                    btnHuyBo = dialog.findViewById(R.id.btnHuyDonHangCt);
                    RecyclerView lvDsTenSp = dialog.findViewById(R.id.lvDsTenSpDonHangCt);


                    edThanhToan.setEnabled(false);
                    ArrayList<GioHang> listTenSpDonHang = new ArrayList<>();
                    int money = 0;
                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                        money += MainActivity.listGioHang.get(i).getGiasp();
                        listTenSpDonHang.add(MainActivity.listGioHang.get(i));
                        listThucDon.add(MainActivity.listGioHang.get(i));
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvTongTien.setText("đ"+decimalFormat.format(money));

                    TenSpDonHangAdapter tenSpDonHangAdapter = new TenSpDonHangAdapter(getActivity(), listTenSpDonHang);
                    lvDsTenSp.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lvDsTenSp.setAdapter(tenSpDonHangAdapter);

                    int finalMoney = money;
                    btnDatHang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ten = edHoTen.getText().toString();
                            String diachi = edDiaChi.getText().toString();
                            String sdt = edSoDienThoai.getText().toString();
                            for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                if (ten.equals("") || diachi.equals("") || sdt.equals("")){
                                    CheckConection.ShowToast(getActivity(), "Vui lòng điền đầy đủ thông tin");
                                }else{
                                    String tenSp = MainActivity.listGioHang.get(i).getTensp();
                                    long giasp = MainActivity.listGioHang.get(i).getGiasp();
                                    int soLuong = MainActivity.listGioHang.get(i).getSoluong();
                                    String thucDon = " - "+tenSp+" (đ"+giasp+")"+" - Số lượng: "+soLuong;
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
                                    String ngayDatHang = sdf.format(Calendar.getInstance().getTime());
                                    String thanhToan = edThanhToan.getText().toString();
                                    MainActivity.listDonHang.add(new DonHang(ten, sdt, diachi, thucDon, finalMoney, thanhToan, ngayDatHang));
                                    CheckConection.ShowToast(getActivity(), "Đã thanh toán");
                                    MainActivity.listGioHang.clear();
                                    listTenSpDonHang.clear();
                                    adapter.notifyDataSetChanged();
                                    TotalMoney();
                                    dialog.dismiss();
                                }
                            }
                        }
                    });

                    btnHuyBo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            listTenSpDonHang.clear();
                        }
                    });
                    dialog.show();

                }else{
                    CheckConection.ShowToast(getActivity(), "Không có sản phẩm thanh toán");
                }
            }
        });
    }

    private void DeleteProduct() {
        lvDs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa sản phẩm");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Chắc chắn xóa sản phẩm này?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.listGioHang.remove(position);
                        adapter.notifyDataSetChanged();
                        TotalMoney();
                        if (MainActivity.listGioHang.size() <= 0){
                            tvThongBao.setVisibility(View.VISIBLE);
                        }
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TotalMoney();
                    }
                });

                builder.show();
                return true;
            }
        });
    }

    public static void TotalMoney() {
        int money = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            money += MainActivity.listGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText("đ"+decimalFormat.format(money));
    }

    private void CheckData() {
        if (MainActivity.listGioHang.size() <= 0){
            tvThongBao.setVisibility(View.VISIBLE);
            lvDs.setVisibility(View.INVISIBLE);
        }else{
            tvThongBao.setVisibility(View.INVISIBLE);
            lvDs.setVisibility(View.VISIBLE);
        }
    }

    private void AnhXa(View view) {
        lvDs = view.findViewById(R.id.lvGioHang);
        tvThongBao = view.findViewById(R.id.tvThongBao);
        tvTongTien = view.findViewById(R.id.tvGiaGioHang);
        btnDatMua = view.findViewById(R.id.btnThanhToanGioHang);
        adapter = new GioHangAdapter(getActivity(), MainActivity.listGioHang);
        lvDs.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        CheckData();
    }
}