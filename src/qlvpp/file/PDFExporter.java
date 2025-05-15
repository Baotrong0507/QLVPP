package qlvpp.file;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import qlvpp.bus.HoaDonBUS;
import qlvpp.model.HoaDon; // Thêm import cho HoaDon
import qlvpp.model.PhieuNhap;
import qlvpp.model.ChiTietPhieuNhap;

public class PDFExporter {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Phiên bản cũ với 2 tham số (cho phiếu nhập)
    public static File exportPDF(String documentType, String maPN) throws IOException {
        return exportPDF(documentType, maPN, null); // Gọi phiên bản với 3 tham số, hd = null
    }

    // Phiên bản mới với 3 tham số (hỗ trợ hóa đơn)
    public static File exportPDF(String documentType, String maPN, HoaDon hd) throws IOException {
        // Tạo tên file động với thời gian
        String timestamp = LocalDateTime.now().format(timeFormatter); // Ví dụ: 20250515_101100
        String fileName = "exports/" + documentType.replace(" ", "") + "_" + maPN + "_" + timestamp + ".pdf";
        File pdfFile = new File(fileName);
        pdfFile.getParentFile().mkdirs(); // Tạo thư mục exports nếu chưa tồn tại

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Load font Unicode (Arial.ttf hoặc DejaVuSans.ttf)
            PDType0Font font = PDType0Font.load(document, new File("D:\\fonts/Arial.ttf"));

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);

            if ("Phiếu nhập".equals(documentType)) {
                // Dữ liệu mẫu cho phiếu nhập (có thể thay bằng dữ liệu từ PhieuNhapBUS)
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
                contentStream.showText("Ngày nhập: " + phieuNhap.getNgayNhap().toLocalDate().format(dateFormatter));
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

                contentStream.endText();
            } else if ("Hóa đơn".equals(documentType) && hd != null) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("HÓA ĐƠN");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã hóa đơn: " + maPN);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã khách hàng: " + hd.getMaKH());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Mã nhân viên: " + hd.getMaNV());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ngày lập: " + hd.getNgayLap().toLocalDate().format(dateFormatter));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tổng tiền: " + String.format("%,.2f", hd.getTongTien()) + " VNĐ");
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Chi tiết hóa đơn:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("(Chi tiết hóa đơn chưa được triển khai)");
                contentStream.endText();
            } else {
                throw new IOException("Không hỗ trợ loại tài liệu hoặc dữ liệu không hợp lệ.");
            }

            contentStream.close();
            document.save(pdfFile);
        }

        return pdfFile;
    }
}