package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO.SanPham;
import anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.R;

public class DoAnAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<SanPham> list;
    private ArrayList<SanPham> listOld;

    public DoAnAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.listOld = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String timKiem = charSequence.toString();
                if (timKiem.isEmpty()) {
                    list = listOld;
                } else {
                    ArrayList<SanPham> listSP = new ArrayList<>();
                    for (SanPham obj : listOld) {
                        if (obj.getTenSp().toLowerCase().contains(timKiem.toLowerCase())) {
                            listSP.add(obj);
                        }
                    }
                    list = listSP;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder {
        ImageView img;
        TextView tvTen, tvGia, tvDiaChi;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder bau = null;
        if (view == null) {
            bau = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_do_an, null);
            bau.img = view.findViewById(R.id.imgDoAnOneRow);
            bau.tvTen = view.findViewById(R.id.tvTenDoAn);
            bau.tvGia = view.findViewById(R.id.tvGiaDoAn);
            bau.tvDiaChi = view.findViewById(R.id.tvDiaChiDoAn);
            view.setTag(bau);
        } else {
            bau = (ViewHolder) view.getTag();
        }
        SanPham obj = list.get(i);
        bau.tvTen.setText(obj.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        bau.tvGia.setText(decimalFormat.format(obj.getGia()) + " đ");
        bau.tvDiaChi.setMaxLines(1);
        bau.tvDiaChi.setEllipsize(TextUtils.TruncateAt.END);
        bau.tvDiaChi.setText(obj.getDiaChi());
        Picasso.get().load(obj.getHinhAnh())
                .placeholder(R.drawable.home)
                .error(R.drawable.ic_baseline_error_24)
                .into(bau.img);
        return view;
    }
}
