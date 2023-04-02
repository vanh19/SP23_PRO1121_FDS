package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.HinhAnhAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.LoaiSanPhamAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.SanPhamMoiNhatAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Animation.ZoomOutPageTransformer;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.HinhAnh;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.LoaiSanPham;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.SanPham;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.CheckConection;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.Server;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    private Button btnTimKiem;
    private ImageView imgDoAn, imgDoUong, imgVanChuyen, imgGiaoHang, imgKhuyenMai, imgCuaHang;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private ArrayList<HinhAnh> list = new ArrayList<>();
    private RecyclerView dsSanPhamMoiNhat;
    private ArrayList<LoaiSanPham> listLoaiSanPham;
    private ArrayList<SanPham> listSanPhamNew;
    private LoaiSanPhamAdapter loaiSanPhamAdapter;
    private SanPhamMoiNhatAdapter sanPhamMoiNhatAdapter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size()-1){
                viewPager2.setCurrentItem(0);
            }else{
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

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
        return inflater.inflate(R.layout.b_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        SliderImg();
        if (CheckConection.HaveConnection(getActivity())){
            ActionImpressImg();
            GetDataFromServer();
            GetDataNew();
            ButtonFindData();
        }else{
            CheckConection.ShowToast(getActivity(), "Không có kết nối mạng");
        }
    }

    private void ButtonFindData() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_search);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                SearchView searchView = dialog.findViewById(R.id.searchViewDialog);
                RecyclerView recyclerView = dialog.findViewById(R.id.dsTimKiemDialog);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(sanPhamMoiNhatAdapter);
                SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        sanPhamMoiNhatAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        sanPhamMoiNhatAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                dialog.show();
            }
        });
    }

    private void GetDataNew() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkSanPhamMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String tensp = "";
                int giasp = 0;
                String diachi = "";
                String mota = "";
                String hinhanh = "";
                int idloaisp = 0;
                if (response != null){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            diachi = jsonObject.getString("diachi");
                            mota = jsonObject.getString("mota");
                            hinhanh = jsonObject.getString("hinhanh");
                            idloaisp = jsonObject.getInt("idloaisp");
                            listSanPhamNew.add(new SanPham(id, tensp, giasp, diachi, mota, hinhanh, idloaisp));
                            sanPhamMoiNhatAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.ShowToast(getActivity(), error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkLoaiSp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String ten = "", hinhanh = "";
                if (response != null){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ten = jsonObject.getString("tenloai");
                            hinhanh = jsonObject.getString("hinnhanh");
                            listLoaiSanPham.add(new LoaiSanPham(id, ten, hinhanh));
                            loaiSanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.ShowToast(getActivity(), error.toString());
                Log.d("prrrrrrrrrrrrr", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionImpressImg() {
        imgDoAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Bundle bd = new Bundle();
//               bd.putInt("idloaisp", 1);
                DoAnFragment doAnFragment = DoAnFragment.newInstance();
//               doAnFragment.setArguments(bd);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, doAnFragment);
                transaction.addToBackStack("back");
                transaction.commit();
            }
        });

        imgDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoUongFragment fragment = DoUongFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack("back");
                transaction.commit();
            }
        });

        ActionButton(imgCuaHang);
        ActionButton(imgGiaoHang);
        ActionButton(imgVanChuyen);
        ActionButton(imgKhuyenMai);
    }

    private void ActionButton(ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OthersFragment fragment = OthersFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack("prrrrrrrrr");
                transaction.commit();
            }
        });
    }

    private void SliderImg() {
        list.add(new HinhAnh(R.drawable.imgfood1));
        list.add(new HinhAnh(R.drawable.imgfood2));
        list.add(new HinhAnh(R.drawable.imgfood3));
        list.add(new HinhAnh(R.drawable.imgfood4));
        list.add(new HinhAnh(R.drawable.imgfood5));
        list.add(new HinhAnh(R.drawable.imgfood6));
        list.add(new HinhAnh(R.drawable.imgfood7));
        list.add(new HinhAnh(R.drawable.imgfood8));
        HinhAnhAdapter hinhAnhAdapter = new HinhAnhAdapter(list);
        viewPager2.setAdapter(hinhAnhAdapter);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
    }

    private void AnhXa(View view) {
        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        imgDoAn = view.findViewById(R.id.imgDoAn);
        imgDoUong = view.findViewById(R.id.imgDoUong);
        imgVanChuyen = view.findViewById(R.id.imgVanChuyen);
        imgGiaoHang = view.findViewById(R.id.imgGiaoHang);
        imgKhuyenMai = view.findViewById(R.id.imgKhuyenMai);
        imgCuaHang = view.findViewById(R.id.imgCuaHang);
        viewPager2 = view.findViewById(R.id.viewPager2);
        circleIndicator3 = view.findViewById(R.id.circle_indicator_3);
        dsSanPhamMoiNhat = view.findViewById(R.id.dsSanPhamMoiNhat);
        listLoaiSanPham = new ArrayList<>();
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(getActivity(), listLoaiSanPham);

        listSanPhamNew = new ArrayList<>();
        sanPhamMoiNhatAdapter = new SanPhamMoiNhatAdapter(getActivity());
        sanPhamMoiNhatAdapter.setData(listSanPhamNew);
        dsSanPhamMoiNhat.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dsSanPhamMoiNhat.setHasFixedSize(true);
        dsSanPhamMoiNhat.setAdapter(sanPhamMoiNhatAdapter);
    }
}