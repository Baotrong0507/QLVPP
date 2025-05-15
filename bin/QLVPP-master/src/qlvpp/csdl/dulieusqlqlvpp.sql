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

INSERT INTO NhanVien (MaNV, Ho, Ten, LuongThang, DiaChi) VALUES
(3, 'Nguyen', 'Minh', 11000000, '45 Tran Hung Dao, Q1'),
(4, 'Tran', 'Lan', 9000000, '12 Nguyen Hue, Q1'),
(5, 'Ho', 'Dung', 13000000, '67 Le Lai, Q3'),
(6, 'Pham', 'Tuan', 9500000, '89 Vo Thi Sau, Q3'),
(7, 'Le', 'Mai', 10500000, '23 Cach Mang Thang 8, Q10'),
(8, 'Nguyen', 'Phong', 12500000, '34 Ly Tu Trong, Q1'),
(9, 'Bui', 'Quang', 11500000, '56 Nguyen Thi Minh Khai, Q3'),
(10, 'Tran', 'Nam', 10000000, '78 Pham Ngoc Thach, Q3'),
(11, 'Vo', 'Thao', 12000000, '90 Le Thanh Ton, Q1'),
(12, 'Dang', 'Khoa', 9800000, '123 Ton Duc Thang, Q1'),
(13, 'Nguyen', 'Linh', 13500000, '45 Hai Ba Trung, Q1'),
(14, 'Le', 'Ngoc', 10200000, '67 Dinh Tien Hoang, Binh Thanh'),
(15, 'Pham', 'Hien', 11800000, '89 Le Van Sy, Phu Nhuan'),
(16, 'Hoang', 'Dat', 10700000, '12 Nguyen Van Cu, Q5'),
(17, 'Tran', 'Anh', 12200000, '34 Truong Dinh, Q3');
INSERT INTO KhachHang VALUES
(3, 'Le', 'Thanh', '56 Tran Phu, Q5', '0933456789'),
(4, 'Nguyen', 'Huy', '78 Ly Thuong Kiet, Q10', '0944567890'),
(5, 'Pham', 'Ngoc', '90 Cach Mang Thang 8, Q3', '0955678901'),
(6, 'Tran', 'Mai', '12 Nguyen Thi Minh Khai, Q1', '0966789012'),
(7, 'Ho', 'Vinh', '34 Le Dai Hanh, Q11', '0977890123'),
(8, 'Bui', 'Lan', '45 Vo Van Tan, Q3', '0988901234'),
(9, 'Nguyen', 'Kiet', '67 Nguyen Dinh Chieu, Q3', '0999012345'),
(10, 'Le', 'Phuong', '89 Ton That Tung, Q1', '0900123456'),
(11, 'Tran', 'Quang', '123 Ba Huyen Thanh Quan, Q3', '0911234567'),
(12, 'Pham', 'Tung', '34 Nguyen Van Troi, Phu Nhuan', '0922345678'),
(13, 'Hoang', 'Nhi', '56 Pham Van Dong, Binh Thanh', '0933456780'),
(14, 'Nguyen', 'Bao', '78 Le Van Sy, Q3', '0944567891'),
(15, 'Le', 'Khanh', '90 Nguyen Hue, Q1', '0955678902'),
(16, 'Tran', 'Duy', '12 Tran Hung Dao, Q1', '0966789013'),
(17, 'Pham', 'Tien', '34 Ly Tu Trong, Q1', '0977890124');
INSERT INTO NhaCungCap VALUES
(3, 'Công ty Bút Bi Xanh', '45 Tran Hung Dao, Q1', '0901234567'),
(4, 'Công ty Giấy Vàng', '67 Nguyen Hue, Q1', '0912345678'),
(5, 'Công ty Nhựa Đỏ', '89 Le Lai, Q3', '0923456789'),
(6, 'Công ty Văn Phòng ABC', '12 Vo Thi Sau, Q3', '0934567890'),
(7, 'Công ty Sách XYZ', '34 Cach Mang Thang 8, Q10', '0945678901'),
(8, 'Công ty Dụng Cụ Học Tập 123', '56 Ly Tu Trong, Q1', '0956789012'),
(9, 'Công ty Bút Chì Đen', '78 Nguyen Thi Minh Khai, Q3', '0967890123'),
(10, 'Công ty Giấy Trắng', '90 Pham Ngoc Thach, Q3', '0978901234'),
(11, 'Công ty Nhựa Xanh', '123 Le Thanh Ton, Q1', '0989012345'),
(12, 'Công ty Văn Phòng DEF', '34 Ton Duc Thang, Q1', '0990123456'),
(13, 'Công ty Sách Mới', '45 Hai Ba Trung, Q1', '0901234568'),
(14, 'Công ty Dụng Cụ 456', '67 Dinh Tien Hoang, Binh Thanh', '0912345679'),
(15, 'Công ty Bút Mực Tím', '89 Le Van Sy, Phu Nhuan', '0923456780'),
(16, 'Công ty Giấy Xanh', '12 Nguyen Van Cu, Q5', '0934567891'),
(17, 'Công ty Nhựa Vàng', '34 Truong Dinh, Q3', '0945678902');
INSERT INTO LoaiSanPham VALUES
(3, 'Dụng cụ vẽ', 'Gỗ'),
(4, 'Dụng cụ đo', 'Nhựa'),
(5, 'Sách giáo khoa', 'Giấy'),
(6, 'Bút mực', 'Nhựa'),
(7, 'Tẩy', 'Cao su'),
(8, 'Bút chì', 'Gỗ'),
(9, 'Thước kẻ', 'Nhựa'),
(10, 'Sổ tay', 'Giấy'),
(11, 'Băng keo', 'Nhựa'),
(12, 'Kéo', 'Kim loại');
INSERT INTO SanPham VALUES
(3, 'Tẩy Thiên Long', 200, 2000, 'VND', 7, 'Việt Nam'),
(4, 'Thước kẻ 20cm', 150, 3000, 'VND', 9, 'Việt Nam'),
(5, 'Bút chì 2B', 300, 2500, 'VND', 8, 'Việt Nam'),
(6, 'Sổ tay 100 trang', 80, 10000, 'VND', 10, 'Việt Nam'),
(7, 'Băng keo 1cm', 50, 5000, 'VND', 11, 'Việt Nam'),
(8, 'Kéo nhỏ', 40, 15000, 'VND', 12, 'Việt Nam'),
(9, 'Sách Toán lớp 5', 60, 20000, 'VND', 5, 'Việt Nam'),
(10, 'Bút mực xanh', 120, 4000, 'VND', 6, 'Việt Nam'),
(11, 'Thước đo góc', 90, 6000, 'VND', 4, 'Việt Nam'),
(12, 'Bút dạ quang', 70, 8000, 'VND', 3, 'Việt Nam');
INSERT INTO PhieuNhap VALUES
(3, 3, 3, '2025-04-03', 400000),
(4, 4, 4, '2025-04-04', 450000),
(5, 5, 5, '2025-04-05', 300000),
(6, 6, 6, '2025-04-06', 350000),
(7, 7, 7, '2025-04-07', 500000),
(8, 8, 8, '2025-04-08', 200000),
(9, 9, 9, '2025-04-09', 250000),
(10, 10, 10, '2025-04-10', 600000),
(11, 11, 11, '2025-04-11', 550000),
(12, 12, 12, '2025-04-12', 320000);
INSERT INTO ChiTietPhieuNhap VALUES
(3, 3, 200, 2000, 400000),
(4, 4, 150, 3000, 450000),
(5, 5, 120, 2500, 300000),
(6, 6, 35, 10000, 350000),
(7, 7, 100, 5000, 500000),
(8, 8, 15, 15000, 225000),
(9, 9, 12, 20000, 240000),
(10, 10, 150, 4000, 600000),
(11, 11, 90, 6000, 540000),
(12, 12, 40, 8000, 320000);
INSERT INTO HoaDon VALUES
(3, '2025-04-07', 3, 3, 45000),
(4, '2025-04-08', 4, 4, 60000),
(5, '2025-04-09', 5, 5, 30000),
(6, '2025-04-10', 6, 6, 75000),
(7, '2025-04-11', 7, 7, 40000),
(8, '2025-04-12', 8, 8, 50000),
(9, '2025-04-13', 9, 9, 35000),
(10, '2025-04-14', 10, 10, 80000),
(11, '2025-04-15', 11, 11, 65000),
(12, '2025-04-16', 12, 12, 55000),
(13, '2025-04-17', 13, 13, 70000),
(14, '2025-04-18', 14, 14, 25000),
(15, '2025-04-19', 15, 15, 90000),
(16, '2025-04-20', 1, 1, 45000),
(17, '2025-04-21', 2, 2, 60000),
(18, '2025-04-22', 3, 3, 30000),
(19, '2025-04-23', 4, 4, 75000),
(20, '2025-04-24', 5, 5, 40000),
(21, '2025-04-25', 6, 6, 50000),
(22, '2025-04-26', 7, 7, 35000),
(23, '2025-04-27', 8, 8, 80000),
(24, '2025-04-28', 9, 9, 65000),
(25, '2025-04-29', 10, 10, 55000),
(26, '2025-04-30', 11, 11, 70000),
(27, '2025-05-01', 12, 12, 25000),
(28, '2025-05-02', 13, 13, 90000),
(29, '2025-05-03', 14, 14, 45000),
(30, '2025-05-04', 15, 15, 60000),
(31, '2025-05-05', 1, 1, 30000),
(32, '2025-05-06', 2, 2, 75000);
INSERT INTO HoaDonChiTiet VALUES
(3, 3, 15, 3000, 45000),
(4, 4, 20, 3000, 60000),
(5, 5, 12, 2500, 30000),
(6, 6, 7, 10000, 70000),
(7, 7, 8, 5000, 40000),
(8, 8, 3, 15000, 45000),
(9, 9, 2, 20000, 40000),
(10, 10, 20, 4000, 80000),
(11, 11, 10, 6000, 60000),
(12, 12, 8, 8000, 64000);
INSERT INTO ChuongTrinhKhuyenMai VALUES
(3, '2025-05-01', '2025-05-10'),
(4, '2025-05-15', '2025-05-25'),
(5, '2025-06-01', '2025-06-10'),
(6, '2025-06-15', '2025-06-25'),
(7, '2025-07-01', '2025-07-10'),
(8, '2025-07-15', '2025-07-25'),
(9, '2025-08-01', '2025-08-10'),
(10, '2025-08-15', '2025-08-25'),
(11, '2025-09-01', '2025-09-10'),
(12, '2025-09-15', '2025-09-25');
INSERT INTO KhuyenMaiSanPham VALUES
(3, 3, 8),
(4, 4, 12),
(5, 5, 10),
(6, 6, 15),
(7, 7, 5),
(8, 8, 20),
(9, 9, 7),
(10, 10, 10),
(11, 11, 15),
(12, 12, 5);
INSERT INTO KhuyenMaiHoaDon VALUES
(3, 150000, 10),
(4, 250000, 25),
(5, 300000, 30),
(6, 400000, 15),
(7, 500000, 20),
(8, 600000, 10),
(9, 700000, 25),
(10, 800000, 30),
(11, 900000, 15),
(12, 1000000, 20);
