package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity.ChiTietSanPhamActivity;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.DoAnAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.SanPham;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.CheckConection;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Ultil.Server;

public class DoUongFragment extends Fragment {
    private SearchView searchView;
    private ListView lvDs;
    private ArrayList<SanPham> list;
    private DoAnAdapter adapter;
    private int id = 0, page = 1;
    private View footerView;
    private boolean isLoading = false, limitData = false;
    private DoUongFragment.MyHandler myHandler;
    public DoUongFragment() {
        // Required empty public constructor
    }

    public static DoUongFragment newInstance() {
        DoUongFragment fragment = new DoUongFragment();
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
        return inflater.inflate(R.layout.b_do_uong_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa(view);
        if (CheckConection.HaveConnection(getActivity())){
            GetData(page);
            LoadMoreData();
        }else{
            CheckConection.ShowToast(getActivity(), "Không có kết nối mạng");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.timKiemMenu).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Tìm kiếm thông tin...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void LoadMoreData() {
        lvDs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", list.get(i));
                startActivity(intent);
            }
        });

        lvDs.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    DoUongFragment.ThreadData threadData = new DoUongFragment.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String link = Server.linkSanPham + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tensp = "";
                int giasp = 0;
                String diachi = "";
                String mota = "";
                String hinhanh = "";
                int idloaisp = 0;
                if (response != null && response.length() != 2){
                    lvDs.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            diachi = jsonObject.getString("diachi");
                            mota = jsonObject.getString("mota");
                            hinhanh = jsonObject.getString("hinhanh");
                            idloaisp = jsonObject.getInt("idloaisp");
                            list.add(new SanPham(id, tensp, giasp, diachi, mota, hinhanh, idloaisp));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitData = true;
                    lvDs.removeFooterView(footerView);
                    CheckConection.ShowToast(getActivity(), "Hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.ShowToast(getActivity(), error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("idloaisanpham", id+"");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }


    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvDs.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.handleMessage(message);
            super.run();
        }
    }

    private void AnhXa(View view) {
        lvDs = view.findViewById(R.id.lvDsDoUong);
        list = new ArrayList<>();
        adapter = new DoAnAdapter(getActivity(), list);
        lvDs.setAdapter(adapter);
        Bundle bd = this.getArguments();
//        if (bd != null){
//            id = bd.getInt("idloaisp", -1);
//        }
        id = 2;
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.layout_progress_bar, null);
        myHandler = new MyHandler();
    }
}