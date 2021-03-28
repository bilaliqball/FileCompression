package xcompres;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfImageObject;

public class Conversion {
	
public void ConvertFile(String file, String format) throws Exception {
File fi=new File(file);
String fn=FilenameUtils.removeExtension(fi.getName());
String fe=FilenameUtils.getExtension(fi.getName());
File src,dest;
     if(fe.toLowerCase().contains("docx") && format.toLowerCase().contains("pdf")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+".pdf");docxToPdf(src, dest);}
else if(fe.toLowerCase().contains("docx") && format.toLowerCase().contains("image")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+"Images");extractImagesfromWord(src,dest);}

else if(fe.toLowerCase().contains("xlsx") && format.toLowerCase().contains("pdf")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+".pdf");xlsxToPdf(src, dest);}
else if(fe.toLowerCase().contains("pptx") && format.toLowerCase().contains("pdf")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+".pdf");pptxToPdf(src, dest);}
else if(fe.toLowerCase().contains("pptx") && format.toLowerCase().contains("image")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+"Images");extractImagesfromSlides(src, dest);}


else if(fe.toLowerCase().contains("pdf") && format.toLowerCase().contains("image")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+"Images");extractImagesfromPdf(src, dest);}
else if(fe.toLowerCase().contains("pdf") && format.toLowerCase().contains("page")){src=new File(file);;dest=new File(src.getParentFile()+"\\"+fn+fe+"Pages");extractPagesfromPdf(src, dest);}
else if(fe.toLowerCase().contains("pdf") && format.toLowerCase().contains("pdf")){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+".pdf");pdfToPdf(src, dest);}

//else if(fe.toLowerCase().contains("") && format.toLowerCase().contains("pdf")){src=new File(file);src=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures");dest=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Sample\\imagesToPdf.pdf");jpgToPdf(src, dest);}
else if((format.toLowerCase().contains("mp3")||format.toLowerCase().contains("audio")) &&(fe.toLowerCase().contains("wmv") || fe.toLowerCase().contains("mp4") ||fe.toLowerCase().contains("flv"))){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+"Audio.mp3");audioConversion(src, dest);}
else if((fe.toLowerCase().contains("wmv") || fe.toLowerCase().contains("mp4") ||fe.toLowerCase().contains("flv")) && (format.toLowerCase().contains("mp4")||format.toLowerCase().contains("video"))){src=new File(file);dest=new File(src.getParentFile()+"\\"+fn+fe+"Converted.mp4");videoConversion(src, dest);}}
	
	
@SuppressWarnings("unused")
public void audioConversion(File src, File dest) {
String ffmpeg="compress.exe";
try {
Process process = new ProcessBuilder(
ffmpeg,"-i",
src.toString(), "-y", 
dest.toString()).start();
}catch (IOException e) { e.printStackTrace();}}

@SuppressWarnings("unused")
public void videoConversion(File source, File dest) {
Process process=null;
String ffmpeg="compress.exe";
try {
process = new ProcessBuilder(
ffmpeg,"-i",
source.toString(), "-y", 
dest.toString()).start();
}catch (IOException e) { e.printStackTrace();}}
	
public static void createPdf(String content, String filename) throws DocumentException, IOException {
Document document = new Document();
FileOutputStream fos=new FileOutputStream(filename);
PdfWriter.getInstance(document, fos);
document.open();
document.add(new Paragraph("Hello World!"));
document.add(new Paragraph(content));
//Image img = Image.getInstance("C:\\Users\\bee\\Pictures\\Sample\\lion.jpg");
//Rectangle siz = new Rectangle(596,842);
//document.setPageSize(siz);
////img.setAbsolutePosition(0, 0);
//document.add(img);
//img.scaleAbsoluteHeight(842/2);
//img.scaleAbsoluteWidth(576);
document.close();}


public  void extractImagesfromWord(File src,File dest) throws IOException{
String parent=src.getParent();
String filename=src.getName();
File dir=new File(parent+"\\"+filename+"toImages");
dir.mkdir();
FileInputStream fs;
XWPFDocument docx = null;
try{
fs=new FileInputStream(src);
docx=new XWPFDocument(fs);
List<XWPFPictureData> piclist=docx.getAllPictures();
Iterator<XWPFPictureData> iterator=piclist.iterator();
int i=0;
while(iterator.hasNext()){
XWPFPictureData pic=iterator.next();
byte[] bytepic=pic.getData();
BufferedImage img=ImageIO.read(new ByteArrayInputStream(bytepic));
ImageIO.write(img, "jpg", new File(dir+"\\"+i+".jpg"));
i++;}}
catch(Exception e){System.exit(-1);}
finally{docx.close();}
}
	 

@SuppressWarnings("unused")
public void extractImagesfromSlides(File src,File dest) throws InvalidFormatException, IOException {
String parent=src.getParent();
String filename=src.getName();
File dir=new File(parent+"\\"+filename+"toImages");
dir.mkdir();
FileInputStream inputStream = new FileInputStream(src);
XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
inputStream.close();
Dimension pgsize = ppt.getPageSize();
float scale = 1;
int width = (int) (pgsize.width * scale);
int height = (int) (pgsize.height * scale);
int i = 1;
int totalSlides = ppt.getSlides().size();
for (XSLFSlide slide : ppt.getSlides()) {
BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,
BufferedImage.TYPE_INT_RGB);
Graphics2D graphics = img.createGraphics();
graphics.setPaint(Color.white);
graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,pgsize.height));
graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
graphics.setColor(Color.white);
graphics.clearRect(0, 0, width, height);
graphics.scale(scale, scale);
slide.draw(graphics);
FileOutputStream out = new FileOutputStream(dir+"\\"+i+".png");
javax.imageio.ImageIO.write(img, "png", out);
out.close();ppt.close();
i++;}}


public  void extractImagesfromPdf(File src,File dest){
String parent=src.getParent();
String filename=src.getName();
File dir=new File(parent+"\\"+filename+"toImages");
dir.mkdir();
try{
PdfReader pr=new PdfReader(src.toString());
PRStream pst;
PdfImageObject pio;
PdfObject po;
int n=pr.getXrefSize(); //number of objects in pdf document
for(int i=0;i<n;i++){
po=pr.getPdfObject(i); //get the object at the index i in the objects collection
if(po==null || !po.isStream()) //object not found so continue
continue;
pst=(PRStream)po; //cast object to stream
PdfObject type=pst.get(PdfName.SUBTYPE); //get the object type
//check if the object is the image type object
if(type!=null && type.toString().equals(PdfName.IMAGE.toString())){
pio=new PdfImageObject(pst); //get the image  
BufferedImage bi=pio.getBufferedImage(); //convert the image to buffered image
ImageIO.write(bi, "jpg", new File(dir+"\\"+i+".jpg"));}}}
catch(Exception e){e.printStackTrace();}}


public  void extractPagesfromPdf(File src,File dest) throws IOException {
String parent=src.getParent();
String filename=src.getName();
File dir=new File(parent+"\\"+filename+"toPage");
dir.mkdir();
PDDocument document = PDDocument.load(src);
PDFRenderer renderer = new PDFRenderer(document);
int pages=document.getNumberOfPages();
BufferedImage image;
for(int i=0;i<pages;i++) {
image = renderer.renderImage(i);
ImageIO.write(image, "JPEG", new File(dir+"\\"+i+".jpg"));}
document.close();}


public static void extractImage() throws Exception{
final String OUTPUT_DIR = "C:\\Users\\bee\\Pictures\\FILES\\Sample\\";
try (final PDDocument document = PDDocument.load(new File("C:\\Users\\bee\\Pictures\\FILES\\Sample\\stories.pdf"))){
PDPageTree list = document.getPages();
for (PDPage page : list) {
PDResources pdResources = page.getResources();
int i = 1;
for (COSName name : pdResources.getXObjectNames()) {
PDXObject o = pdResources.getXObject(name);
if (o instanceof PDImageXObject) {
PDImageXObject image = (PDImageXObject)o;
String filename = OUTPUT_DIR + "axtracted-image-" + i + ".png";
ImageIO.write(image.getImage(), "png", new File(filename));
i++;}}}} 
catch (IOException e){System.err.println("Exception while trying to create pdf document - " + e);}}
   

@SuppressWarnings("unused")
public static void extractObjects() throws IOException{
int imageNumber = 1;
PDDocument document = null;
String fileName = "C:\\Users\\bee\\Pictures\\FILES\\Sample\\stories.pdf";
try{
document = PDDocument.load( new File(fileName) );
int pageNum = 0;
for( PDPage page : document.getPages() ){
pageNum++;
//System.out.println( "Processing page: " + pageNum );
PDResources pdResources = page.getResources();
for (COSName objectName : pdResources.getXObjectNames()) {
PDXObject xobject = pdResources.getXObject(objectName);
if( xobject instanceof PDImageXObject){
PDImageXObject image = (PDImageXObject)xobject;
int imageWidth = image.getWidth();
int imageHeight = image.getHeight();
BufferedImage bImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
bImage = image.getImage();
ImageIO.write(bImage,"PNG",new File("C:\\Users\\bee\\Pictures\\FILES\\Sample\\a"+imageNumber+".png"));
//System.out.println("Image saved.");
imageNumber++;}}}}
catch(IOException e) {e.printStackTrace();}
finally{if( document != null ){document.close();}}System.out.println("...");}



public void txtToPdf(File src, File dest) throws FileNotFoundException, DocumentException {
File inputfile=src;
File outputfile=dest;
XWPFDocument inputdocument;
Document outputdocument;
String res="";
try {
FileInputStream fis = new FileInputStream(inputfile.getAbsolutePath());
inputdocument = new XWPFDocument(fis);
List<XWPFParagraph> paragraphs = inputdocument.getParagraphs();
for (XWPFParagraph para : paragraphs) {
res= res + "\n" + para.getText() + "\n";}
fis.close();
FileOutputStream fos = new FileOutputStream(outputfile.getAbsolutePath());
outputdocument = new Document();
PdfWriter.getInstance(outputdocument, fos);
outputdocument.addAuthor("bee");
outputdocument.addTitle("My Converted PDF");
outputdocument.open();
String[] splitter = res.split("\\n");
for (int i = 0; i < splitter.length; i++) {
Chunk chunk = new Chunk(splitter[i]);
Font font = new Font();
font.setStyle(Font.UNDERLINE);
font.setStyle(Font.ITALIC);
chunk.setFont(font);
outputdocument.add(chunk);
Paragraph paragraph = new Paragraph();
paragraph.add("");
outputdocument.add(paragraph);}
outputdocument.close();} 
catch (Exception e) {e.printStackTrace();}}


@SuppressWarnings("rawtypes")
public void docxToPdf(File src, File dest) throws IOException{
FileInputStream fs;
XWPFDocument doc = null;
try{
fs=new FileInputStream(src);
doc=new XWPFDocument(fs); 
Document pdfdoc=new Document(PageSize.A4,72/2,72/2,72/2,72/2);
PdfWriter pwriter=PdfWriter.getInstance(pdfdoc, new FileOutputStream(dest));
pwriter.setInitialLeading(20);
List paragraphlist=doc.getParagraphs();
pdfdoc.open();
for (int i = 0; i < paragraphlist.size(); i++) {
XWPFParagraph pa = (XWPFParagraph) paragraphlist.get(i);
List runs = pa.getRuns();
for (int j = 0; j < runs.size(); j++) {       
XWPFRun run=(XWPFRun) runs.get(j);
List picturelist=run.getEmbeddedPictures();
Iterator iterator=picturelist.iterator();
while(iterator.hasNext()){
XWPFPicture pic=(XWPFPicture) iterator.next();
XWPFPictureData picdata=pic.getPictureData();
byte[] bytepic=picdata.getData(); 
Image imag=Image.getInstance(bytepic);
pdfdoc.add(imag);
pdfdoc.setPageSize(imag);}
int color;
String code=run.getColor();
int colorCode;
if(code!=null) colorCode=Long.decode("0x"+code).intValue();
else colorCode=Long.decode("0x000000").intValue();color=colorCode;
Font font=null;
     if(run.isBold() && run.isItalic()) font=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLDITALIC, new BaseColor(color));
else if(run.isBold()) font=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLD, new BaseColor(color));
else if(run.isItalic()) font=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.ITALIC, new BaseColor(color));
else font=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.NORMAL, new BaseColor(color));
String text=run.getText(-1);
byte[] bs;
if (text!=null){
bs=text.getBytes();
String str=new String(bs,"UTF-8");
Chunk chObj1=new Chunk(str,font);
pdfdoc.add(chObj1);}}
pdfdoc.add(new Chunk(Chunk.NEWLINE));}
pdfdoc.close();}
catch(Exception e){e.printStackTrace();}
finally {doc.close();}
}



public void xlsxToPdf(File src, File dest) throws IOException, DocumentException {
FileInputStream input_document = new FileInputStream(src);
XSSFWorkbook workbook = new XSSFWorkbook(input_document); 
XSSFSheet worksheet = workbook.getSheetAt(0); 
Iterator<Row> rowIterator = worksheet.iterator();
Document iText_xls_2_pdf = new Document();
PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(dest));
iText_xls_2_pdf.open();
PdfPTable my_table = new PdfPTable(2);
PdfPCell table_cell;
while(rowIterator.hasNext()) {
Row row = rowIterator.next(); 
Iterator<Cell> cellIterator = row.cellIterator();
while(cellIterator.hasNext()) {
Cell cell = cellIterator.next();
if(cell.getCellTypeEnum() == CellType.STRING){
table_cell=new PdfPCell(new Phrase(cell.getStringCellValue()));
my_table.addCell(table_cell);}}}
iText_xls_2_pdf.add(my_table);                       
iText_xls_2_pdf.close();                
input_document.close();
workbook.close();}

@SuppressWarnings("unused")
public void pptxToPdf(File src,File dest) throws InvalidFormatException, IOException, DocumentException {
FileInputStream inputStream = new FileInputStream(src);
String tempdir=src.toString()+"x";
File dir=new File(tempdir);
dir.mkdir();
XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
inputStream.close();
Dimension pgsize = ppt.getPageSize();
float scale = 1;
int width = (int) (pgsize.width * scale);
int height = (int) (pgsize.height * scale);
int i = 1;
int totalSlides = ppt.getSlides().size();
for (XSLFSlide slide : ppt.getSlides()) {
BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,
BufferedImage.TYPE_INT_RGB);
Graphics2D graphics = img.createGraphics();
graphics.setPaint(Color.white);
graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,pgsize.height));
graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
graphics.setColor(Color.white);
graphics.clearRect(0, 0, width, height);
graphics.scale(scale, scale);
slide.draw(graphics);
FileOutputStream out = new FileOutputStream(dir+"\\"+i+".png");
javax.imageio.ImageIO.write(img, "png", out);
out.close();i++;}
File[] files=dir.listFiles();
Image img = Image.getInstance(files[0].toString());
Document document = new Document(img);
PdfWriter.getInstance(document, new FileOutputStream(dest));
document.open();
for (File image : files) {
img = Image.getInstance(image.toString());
document.setPageSize(img);
document.newPage();
img.setAbsolutePosition(0, 0);
document.add(img);}
document.close();ppt.close();
FileUtils.deleteDirectory(dir);}

public void pdfToPdf(File src, File dest) throws IOException {
FileUtils.copyFile(src, dest);}


public void jpgToPdf(File src,File dest) throws Exception {
File root =src;
String files[]=root.list();
Document document = new Document();
PdfWriter.getInstance(document, new FileOutputStream(dest));
document.open();
for (String f : files) {
document.newPage();
Image image = Image.getInstance(new File(root, f).getAbsolutePath());
document.setPageSize(image);
image.setAbsolutePosition(0, 0);
image.setBorderWidth(0);
document.add(image);}
document.close();}
	
}
