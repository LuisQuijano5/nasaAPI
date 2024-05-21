package comprehensive.project.nasaapi.reports;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.util.List;
import comprehensive.project.nasaapi.models.APOD;
import org.slf4j.IMarkerFactory;

public class ApodReportGenerator {
    public void createReport(List<APOD> apodList, String dest) throws IOException {
        Thread reportThread = new Thread(() -> {
           try {
               PdfWriter writer = new PdfWriter(dest);
               PdfDocument pdf = new PdfDocument(writer);
               Document document = new Document(pdf);

               for (APOD apod : apodList) {
                   document.add(new Paragraph("Date: " + apod.getDate()));
                   document.add(new Paragraph("Title: " + apod.getTitle()));

                   if (apod.getUrl() != null && apod.getUrl().endsWith(".jpg")) {
                       Image image = new Image(ImageDataFactory.create(apod.getUrl()));
                       image.setHeight(300);
                       image.setWidth(500);
                       document.add(image);
                   }

                   document.add(new Paragraph("URL: " + apod.getUrl()));
                   if (apod.getCopyright() != null) {
                       document.add(new Paragraph("Copyright: " + apod.getCopyright()));
                   }
                   document.add(new Paragraph("Description: " + apod.getExplanation()));
                   document.add(new Paragraph("----------------------------------------------------------------------"));
               }

               document.close();
           }catch (Exception e){
               e.printStackTrace();
           }
        });
        reportThread.start();
    }

}
