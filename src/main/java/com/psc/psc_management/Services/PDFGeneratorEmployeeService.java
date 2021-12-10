package com.psc.psc_management.Services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.psc.psc_management.Models.Employees;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGeneratorEmployeeService {
    private List<Employees> listEmployee;

    public PDFGeneratorEmployeeService(List<Employees> listEmployee) {
        this.listEmployee = listEmployee;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(Color.BLACK);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.darkGray);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee E-Mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Branch", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Role", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Employees employee : listEmployee) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getEmployeeName());
            table.addCell(employee.getEmail());
            table.addCell(employee.getBranch().getBranchName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        //Header Font
        Font header = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        header.setSize(16);
        header.setColor(Color.BLUE);

        //Body Font
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        bodyFont.setSize(12);
        bodyFont.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Admin Employee Report", header);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingAfter(3);
        document.add(p);

        p.setAlignment(Paragraph.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 3.5f, 3.0f, 2.5f, 2.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}

