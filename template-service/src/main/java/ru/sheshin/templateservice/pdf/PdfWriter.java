package ru.sheshin.templateservice.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import ru.sheshin.templateservice.dto.InspectionInfoDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PdfWriter {
    public static void writePatientsToPdf(List<InspectionInfoDTO> patients) {
        Document document = new Document();
        try {
            com.itextpdf.text.pdf.PdfWriter.getInstance(
                    document,
                    new FileOutputStream("/Пациенты_список.pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        document.open();

        BaseFont baseFont;
        try {
            baseFont = BaseFont.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }

        Font font = new Font(baseFont);

        for (var patient : patients) {
            Paragraph nameChunk = new Paragraph(
                    patient.getPatientDTO().getLastName() + " " +
                            patient.getPatientDTO().getFirstName() + " "+
                            patient.getPatientDTO().getPatronymic(),
                    font);

            Paragraph passportChunk = new Paragraph(
                    "Паспорт: " +
                    patient.getPatientDTO().getPassportSeries() + " " + patient.getPatientDTO().getPassportNumber(),
                    font
            );

            Paragraph snilsChunk = new Paragraph(
                    "Снилс: " +
                    patient.getPatientDTO().getSnilsNumber(),
                    font
            );

            Paragraph omsChunk = new Paragraph(
                    "Полис ОМС: " +
                            patient.getPatientDTO().getOmsPolicy(),
                    font
            );

            Paragraph dateChunk  = new Paragraph(
                    "Дата: " +
                            patient.getNextObservationDate()
            );

            try {
                document.add(nameChunk);
                document.add(passportChunk);
                document.add(snilsChunk);
                document.add(omsChunk);
                document.add(dateChunk);
            } catch (DocumentException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }



        document.close();
    }
}
