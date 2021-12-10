package com.psc.psc_management.Services;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Models.Employees;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGeneratorManagerService {
    private List<Employees> listEmployee;
    private List<BuyPaddy> listPaddyBuy;

    public PDFGeneratorManagerService(List<Employees> listEmployee, Iterable<BuyPaddy> listPaddyBuy) {
        this.listEmployee = listEmployee;
    }

    private void writeTableHeaderEmployees(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(Color.BLACK);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee E-Mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Role", font));
        table.addCell(cell);
    }

    private void writeTableDataEmployees(PdfPTable table) {
        for (Employees employee : listEmployee) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getEmail());
            table.addCell(employee.getEmployeeName());
            table.addCell(employee.getContact());
            table.addCell(employee.getRole().getRoleName());
        }
    }

    private void writeTableHeaderPaddyBuy(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(Color.BLACK);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Farmer Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Units(KG)", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cost", font));
        table.addCell(cell);
    }

    private void writeTableDataPaddyBuy(PdfPTable table) {
        for (BuyPaddy buyPaddy : listPaddyBuy) {
            table.addCell(String.valueOf(buyPaddy.getId()));
            table.addCell(buyPaddy.getFarmer().getFarmer());
            table.addCell(String.valueOf(buyPaddy.getDate()));
            table.addCell(String.valueOf(buyPaddy.getQuantity()));
            table.addCell(String.valueOf(buyPaddy.getCost()));
        }
    }

    public void export(HttpServletResponse response, Integer id) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        //Header Font
        Font header = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        header.setSize(16);
        header.setColor(Color.BLACK);

        //Sub-Heading Font
        Font subHeading = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        subHeading.setSize(14);
        subHeading.setColor(Color.BLACK);

        //Body Font
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA);
        bodyFont.setSize(12);
        bodyFont.setColor(Color.BLACK);

        //Report Header
        Paragraph h = new Paragraph("Manager Report", header);
        h.setAlignment(Paragraph.ALIGN_CENTER);
        h.setSpacingAfter(3);
        document.add(h);

        //Report Branch Sub-Heading
        Paragraph sh1 = new Paragraph("These Are The Key Details In Your Branch ", subHeading);
        sh1.setSpacingAfter(3);
        document.add(sh1);

        //Report Branch ID
        String branchDetails = "Branch Id : "+id;
        Paragraph p = new Paragraph(branchDetails,bodyFont);
        p.setSpacingAfter(3);
        document.add(p);

        //Report Employee Table Sub-Heading
        Paragraph sh2 = new Paragraph("List of Employees Under Your Branch ", subHeading);
        sh2.setSpacingAfter(3);
        document.add(sh2);

        //Report Employee Table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 4.5f, 3.0f, 2f, 2f});
        table.setSpacingBefore(10);

        writeTableHeaderEmployees(table);
        writeTableDataEmployees(table);
        table.setSpacingAfter(3);
        document.add(table);

        //Report Employee Table Sub-Heading
        Paragraph sh3 = new Paragraph("List of Paddy Buy's in the Branch", subHeading);
        sh3.setSpacingAfter(3);
        document.add(sh3);

        //Report PaddyBuy Table
        PdfPTable table1 = new PdfPTable(5);
        table1.setWidthPercentage(100f);
        table1.setWidths(new float[] {1f, 4f, 3.0f, 2.5f, 2f});
        table1.setSpacingBefore(10);

        writeTableHeaderPaddyBuy(table1);
        writeTableDataPaddyBuy(table1);
        table1.setSpacingAfter(3);
        document.add(table1);

        document.close();
    }
}

