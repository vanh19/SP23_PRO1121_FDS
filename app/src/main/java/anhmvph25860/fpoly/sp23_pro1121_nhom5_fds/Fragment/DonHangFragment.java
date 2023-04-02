package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Activity.MainActivity;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter.DonHangAdapter;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;

public class DonHangFragment extends Fragment {
    private TextView tvThongBao;
    private RecyclerView rvDs;
    public DonHangFragment() {
        // Required empty public constructor
    }

    public static DonHangFragment newInstance() {
        DonHangFragment fragment = new DonHangFragment();
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
        return inflater.inflate(R.layout.b_don_hang_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThongBao = view.findViewById(R.id.tvThongBaoDonHang);
        rvDs = view.findViewById(R.id.rvDsDonHang);
        if (MainActivity.listDonHang.size() > 0){
            tvThongBao.setVisibility(View.INVISIBLE);
            rvDs.setVisibility(View.VISIBLE);
            DonHangAdapter donHangAdapter = new DonHangAdapter(getActivity(), MainActivity.listDonHang);
            rvDs.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvDs.setAdapter(donHangAdapter);
        }else{
            tvThongBao.setVisibility(View.VISIBLE);
            rvDs.setVisibility(View.INVISIBLE);
        }
    }
}