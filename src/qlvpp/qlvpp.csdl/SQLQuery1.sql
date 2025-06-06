﻿CREATE DATABASE QuanLyVanPhongPham;
USE QuanLyVanPhongPham;
CREATE TABLE KhachHang (
    MaKH INT PRIMARY KEY ,
    Ho VARCHAR(50) NOT NULL,
    Ten VARCHAR(50) NOT NULL,
    DiaChi TEXT,
    sdt VARCHAR(15) UNIQUE
);
CREATE TABLE NhanVien (
    MaNV INT PRIMARY KEY ,
    Ho VARCHAR(50) NOT NULL,
    Ten VARCHAR(50) NOT NULL,
    LuongThang DECIMAL(10,2) NOT NULL,
	DiaChi VARCHAR(50) NOT NULL
);
  
CREATE TABLE HoaDon (
    MaHD INT PRIMARY KEY ,
    NgayLap DATE NOT NULL,
    MaNV INT,
    MaKH INT,
    TongTien DECIMAL(15,2) DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);
CREATE TABLE SanPham (
    MaSP INT PRIMARY KEY ,
    TenSP VARCHAR(100) NOT NULL,
    SoLuong INT DEFAULT 0,  -- Số lượng còn lại của cửa hàng
    DonGia DECIMAL(15,2) NOT NULL,
    DonViTienTe VARCHAR(10) NOT NULL DEFAULT 'VND',
    MaLoai INT,
	Xuatxu VARCHAR(100) NOT NULL,
    FOREIGN KEY (MaLoai) REFERENCES LoaiSanPham(MaLoai)
);
  
CREATE TABLE LoaiSanPham (
    MaLoai INT PRIMARY KEY ,
    TenLoai VARCHAR(100) NOT NULL,
	Chatlieu VARCHAR(50) NOT NULL
);
  
CREATE TABLE HoaDonChiTiet (
    MaHD INT,
    MaSP INT,
    SoLuong INT NOT NULL,
    DonGia DECIMAL(15,2) NOT NULL,
    ThanhTien DECIMAL(15,2) NOT NULL,
    PRIMARY KEY (MaHD, MaSP),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);
CREATE TABLE ChuongTrinhKhuyenMai (
    MaKM INT PRIMARY KEY,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL
);
CREATE TABLE KhuyenMaiSanPham (
    MaKM INT,
    MaSP INT,
    PhanTramGiam DECIMAL(5,2) CHECK (PhanTramGiam BETWEEN 0 AND 100),
    PRIMARY KEY (MaKM, MaSP),
    FOREIGN KEY (MaKM) REFERENCES ChuongTrinhKhuyenMai(MaKM),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);
CREATE TABLE KhuyenMaiHoaDon (
    MaKM INT,
    SoTienHoaDon DECIMAL(15,2) NOT NULL,
    PhanTramGiam DECIMAL(5,2) CHECK (PhanTramGiam BETWEEN 0 AND 100),
    PRIMARY KEY (MaKM, SoTienHoaDon),
    FOREIGN KEY (MaKM) REFERENCES ChuongTrinhKhuyenMai(MaKM)
);
CREATE TABLE NhaCungCap (
    MaNCC INT PRIMARY KEY ,
    TenNCC VARCHAR(100) NOT NULL,
    DiaChi TEXT,
    sdt VARCHAR(15) UNIQUE
);
CREATE TABLE PhieuNhap (
    MaPN INT PRIMARY KEY ,
    MaNV INT,
    MaNCC INT,
    NgayNhap DATE NOT NULL,
    TongTien DECIMAL(15,2) DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
);
CREATE TABLE ChiTietPhieuNhap (
    MaPN INT,
    MaSP INT,
    SoLuong INT NOT NULL,
    GiaNhap DECIMAL(15,2) NOT NULL,
    ThanhTien DECIMAL(15,2) NOT NULL,
    PRIMARY KEY (MaPN, MaSP),
    FOREIGN KEY (MaPN) REFERENCES PhieuNhap(MaPN),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);
