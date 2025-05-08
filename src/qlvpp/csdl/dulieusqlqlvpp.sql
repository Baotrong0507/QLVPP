USE QuanLyVanPhongPham
-- Bảng KhachHang
INSERT INTO KhachHang VALUES (1, 'Nguyen', 'An', '123 Le Loi, Q1', '0909123456');
INSERT INTO KhachHang VALUES (2, 'Tran', 'Binh', '456 Nguyen Trai, Q5', '0911223344');

-- Bảng NhanVien
INSERT INTO NhanVien (MaNV, Ho, Ten, LuongThang, DiaChi) 
VALUES (1, 'Le', 'Hoa', 10000000, '123 D1, Binh Thanh');
INSERT INTO NhanVien (MaNV, Ho, Ten, LuongThang, DiaChi) 
VALUES (2, 'Pham', 'Hieu', 12000000, '789 D2, Go Vap');

-- Bảng LoaiSanPham
INSERT INTO LoaiSanPham VALUES (1, 'Văn phòng phẩm', 'Giấy');
INSERT INTO LoaiSanPham VALUES (2, 'Đồ dùng học tập', 'Nhựa');

-- Bảng SanPham
INSERT INTO SanPham VALUES (1, 'Bút bi Thiên Long', 100, 3500, 'VND', 1, 'Việt Nam');
INSERT INTO SanPham VALUES (2, 'Vở ô ly 200 trang', 50, 12000, 'VND', 1, 'Việt Nam');

-- Bảng NhaCungCap
INSERT INTO NhaCungCap VALUES (1, 'Công ty Thiên Long', '01 Quang Trung, Q12', '0987654321');
INSERT INTO NhaCungCap VALUES (2, 'Công ty Hồng Hà', '88 Pasteur, Q3', '0977888999');

-- Bảng PhieuNhap
INSERT INTO PhieuNhap VALUES (1, 1, 1, '2025-04-01', 350000);
INSERT INTO PhieuNhap VALUES (2, 2, 2, '2025-04-02', 240000);

-- Bảng ChiTietPhieuNhap
INSERT INTO ChiTietPhieuNhap VALUES (1, 1, 100, 3500, 350000);
INSERT INTO ChiTietPhieuNhap VALUES (2, 2, 20, 12000, 240000);

-- Bảng HoaDon
INSERT INTO HoaDon VALUES (1, '2025-04-05', 1, 1, 50000);
INSERT INTO HoaDon VALUES (2, '2025-04-06', 2, 2, 24000);

-- Bảng HoaDonChiTiet
INSERT INTO HoaDonChiTiet VALUES (1, 1, 10, 3500, 35000);
INSERT INTO HoaDonChiTiet VALUES (2, 2, 2, 12000, 24000);

-- Bảng ChuongTrinhKhuyenMai
INSERT INTO ChuongTrinhKhuyenMai VALUES (1, '2025-04-01', '2025-04-10');
INSERT INTO ChuongTrinhKhuyenMai VALUES (2, '2025-04-15', '2025-04-30');

-- Bảng KhuyenMaiSanPham
INSERT INTO KhuyenMaiSanPham VALUES (1, 1, 10);
INSERT INTO KhuyenMaiSanPham VALUES (2, 2, 5);

-- Bảng KhuyenMaiHoaDon
INSERT INTO KhuyenMaiHoaDon VALUES (1, 100000, 15);
INSERT INTO KhuyenMaiHoaDon VALUES (2, 200000, 20);
