create database VanPhongPham;
go
use VanPhongPham;
go
CREATE TABLE LoaiSanPham (
    MaLoai INT PRIMARY KEY ,
    TenLoai VARCHAR(100) NOT NULL,
	Chatlieu VARCHAR(50) NOT NULL
);