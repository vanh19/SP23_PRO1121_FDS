package anhmvph25860.fpoly.sp23_pro1121_nhom5_fds.DTO;

public class DonHang {
    private String tenNguoiDung;
    private String soDienThoai;
    private String diaChiNhan;
    private String thucDon;
    private int tongTien;
    private String thanhToan;
    private String ngayDatMua;

    public DonHang() {
    }

    public DonHang(String tenNguoiDung, String soDienThoai, String diaChiNhan, String thucDon, int tongTien, String thanhToan, String ngayDatMua) {
        this.tenNguoiDung = tenNguoiDung;
        this.soDienThoai = soDienThoai;
        this.diaChiNhan = diaChiNhan;
        this.thucDon = thucDon;
        this.tongTien = tongTien;
        this.thanhToan = thanhToan;
        this.ngayDatMua = ngayDatMua;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getThucDon() {
        return thucDon;
    }

    public void setThucDon(String thucDon) {
        this.thucDon = thucDon;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getNgayDatMua() {
        return ngayDatMua;
    }

    public void setNgayDatMua(String ngayDatMua) {
        this.ngayDatMua = ngayDatMua;
    }
}

