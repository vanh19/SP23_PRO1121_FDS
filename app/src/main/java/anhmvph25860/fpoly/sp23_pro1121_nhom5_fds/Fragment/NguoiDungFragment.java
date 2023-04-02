package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;

public class NguoiDungFragment extends Fragment {

    public NguoiDungFragment() {
        // Required empty public constructor
    }


    public static NguoiDungFragment newInstance() {
        NguoiDungFragment fragment = new NguoiDungFragment();
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
        return inflater.inflate(R.layout.b_nguoi_dung_fragment, container, false);
    }
}