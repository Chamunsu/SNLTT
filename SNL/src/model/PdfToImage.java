package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;



public class PdfToImage {

  /**
   *
   * @param sourceFile
   * @param resolution
   * @param password
   * @return 이미지 형태의 PDF Page 추출결과를 반환
   */
  public static int extractPagesAsImage(File sourceFile, String sourcePath, int resolution, String password) {
        
         boolean result = false;
        
         String imageFormat = "png";
         int endOfPages = 0;
        
         PDDocument pDDocument = null;
         try {
                 System.out.println("[MinjuneL] (PdfToImage) sourceFile path = "+sourceFile);
                 System.out.println("[MinjuneL] (PdfToImage) sourceDirectory path = "+sourcePath);
                 pDDocument = PDDocument.load(sourceFile);
                
                 endOfPages = pDDocument.getNumberOfPages();
                
         } catch (IOException ioe) {
                
                 System.out.println("PDFToImage-extractPagesAsImage ERROR : " + ioe.getMessage());
         }
        
         String destFilePrefix = sourcePath;
        
         PDFImageWriter imageWriter = new PDFImageWriter();
        
         try {
                 result = imageWriter.writeImage(pDDocument, imageFormat, password, 1, endOfPages, destFilePrefix, BufferedImage.TYPE_INT_RGB, resolution);
                
         } catch (IOException ioe) {
                
                 System.out.println("PDFToImage-extractPagesAsImage ERROR : " + ioe.getMessage());
         }
        
         return endOfPages;
  }
 
  public static String destFilePrefix(String sourceFile) {
        
         String result = "";
         int lastSeparatorIndex = sourceFile.lastIndexOf("/") + 1;
         int lastCommaIndex =  sourceFile.lastIndexOf(".");
        
         result = sourceFile.substring(0, lastSeparatorIndex);
         result = result + (sourceFile.substring(lastSeparatorIndex,lastCommaIndex));
        
         return result;
        
  }
}
