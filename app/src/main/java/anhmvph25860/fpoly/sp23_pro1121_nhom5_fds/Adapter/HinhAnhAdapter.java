package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.HinhAnh;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;

public class HinhAnhAdapter extends RecyclerView.Adapter<HinhAnhAdapter.BauHolder> {
    private ArrayList<HinhAnh> list;

    public HinhAnhAdapter(ArrayList<HinhAnh> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hinh_anh, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        HinhAnh obj = list.get(position);
        if (obj == null) {
            return;
        }
        holder.imgHinhAnh.setImageResource(obj.getIdImg());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private ImageView imgHinhAnh;

        public BauHolder(@NonNull View itemView) {
            super(itemView);

            imgHinhAnh = itemView.findViewById(R.id.imgHinhAnh);
        }
    }
}

