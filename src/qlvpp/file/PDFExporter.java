package qlvpp.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import qlvpp.model.PhieuNhap;
import qlvpp.model.ChiTietPhieuNhap;

public class PDFExporter {

    public static File exportPDF(String documentType, String maPN) throws IOException {
        File pdfFile = new File("D:/QuanLyVanPhongPham/src/qlvpp/file/export_phieunhap.pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Load font Unicode (Arial.ttf hoặc DejaVuSans.ttf)
            PDType0Font font = PDType0Font.load(document, new File("D:\\fonts/Arial.ttf"));

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);

            if ("Phiếu nhập".equals(documentType)) {
                // Dữ liệu mẫu
                PhieuNhap phieuNhap = new PhieuNhap(1, 101, 201, new java.sql.Date(new java.util.Date().getTime()), 5000000.0);
                List<ChiTietPhieuNhap> chiTiets = new ArrayList<>();
                chiTiets.add(new ChiTietPhieuNhap(1, 1001, 10, 50000.0, 500000.0));
                chiTiets.add(new ChiTietPhieuNhap(1, 1002, 20, 60000.0, 1200000.0));

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("PHIẾU NHẬP");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã phiếu nhập: " + maPN);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ngày nhập: " + phieuNhap.getNgayNhap());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã nhân viên: " + phieuNhap.getMaNV());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã nhà cung cấp: " + phieuNhap.getMaNCC());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tổng tiền: " + String.format("%,.2f", phieuNhap.getTongTien()) + " VNĐ");
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Chi tiết phiếu nhập:");
                contentStream.newLineAtOffset(0, -20);

                for (ChiTietPhieuNhap ct : chiTiets) {
                    String line = String.format("- Mã SP: %d | SL: %d | Giá: %,.2f VNĐ | Thành tiền: %,.2f VNĐ",
                            ct.getMaSP(), ct.getSoLuong(), ct.getGiaNhap(), ct.getThanhTien());
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -20);
                }

                contentStream.endText(); // Kết thúc đoạn text
            } else if ("Hóa đơn".equals(documentType)) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("HÓA ĐƠN");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã phiếu nhập: " + maPN);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Chức năng chưa triển khai!");
                contentStream.endText();
            }

            contentStream.close();
            document.save(pdfFile);
        }

        return pdfFile;
    }
}
