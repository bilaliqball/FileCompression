package image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class CompressionACD {
BufferedImage img;
public File inp;
public File com;
public File dec;
public String par;
String name;
String ext;
long size;
int dim;
int dpi;
double sdr;
public static void main(String args[]) throws Exception {

CompressionACD b=new CompressionACD();
File file=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
//String par=file.getParent();

b.compressDecompressLossless(file);
b.compressDecompressLossy(file);


}

public void compressDecompressLossy(File fi) throws ImageReadException, IOException {
long start=System.currentTimeMillis();String filename="";
if(fi.isFile()) {par=fi.getParent();filename=fi.getName();setCompressDecompressLossy(filename);}
else if(fi.isDirectory()) {par=fi.toString();
FileFilter fileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};
File files[]=fi.listFiles(fileFilter);System.out.println(files.length +" files found!");
for(File file:files) {filename=file.getName();setCompressDecompressLossy(filename);
System.out.println (name+" compressed.");}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");}}

public void setCompressDecompressLossy(String filename) throws IOException, ImageReadException {
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
com=new File(par+"\\"+name+"__.bin");
dec=new File(par+"\\"+name+"__"+ext);
size=inp.length()/1024;
img=ImageIO.read(inp);
ByteArrayOutputStream filbaos=new ByteArrayOutputStream();
ImageIO.write(img, "jpg", filbaos);OutputStream filous=new FileOutputStream(com);
InputStream filins= new ByteArrayInputStream(filbaos.toByteArray());
IOUtils.copy(filins,filous);filous.close();filbaos.flush();filbaos.close();

int width=img.getWidth();int height=img.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
ByteArrayOutputStream jpgbaos=new ByteArrayOutputStream();
ImageIO.write(newBufferedImage, "jpg", jpgbaos);
InputStream jpgins= new ByteArrayInputStream(jpgbaos.toByteArray());
BufferedImage image=ImageIO.read(jpgins);
ByteArrayOutputStream combaos=new ByteArrayOutputStream();
Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.20f);
writer.write(null, new IIOImage(image, null, null), param);
InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
combaos.flush();combaos.close();ios.close();writer.dispose();

BufferedImage decimage=ImageIO.read(comins);
ByteArrayOutputStream decbaos=new ByteArrayOutputStream();
Iterator<ImageWriter>decwriters=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter decwriter = (ImageWriter) decwriters.next();
ImageOutputStream dios = ImageIO.createImageOutputStream(decbaos);
decwriter.setOutput(dios);ImageWriteParam decparam = decwriter.getDefaultWriteParam();
decparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
decparam.setCompressionQuality(0.95f);
decwriter.write(null, new IIOImage(decimage, null, null), decparam);
OutputStream decous=new FileOutputStream(dec);
InputStream decins= new ByteArrayInputStream(decbaos.toByteArray());
IOUtils.copy(decins, decous);decous.close();decbaos.flush();decbaos.close();
dios.close();decwriter.dispose();

//ByteArrayOutputStream jpgbaos=new ByteArrayOutputStream();
//ImageIO.write(image, "jpg", jpgbaos);
//InputStream jpgins= new ByteArrayInputStream(jpgbaos.toByteArray());
//IOUtils.copy(jpgins,jpgous);jpgous.close();jpgbaos.flush();jpgbaos.close();
//
//ByteArrayOutputStream combaos=new ByteArrayOutputStream();
//Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
//ImageWriter writer = (ImageWriter) writers.next();
//ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
//writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(0.25f);
//writer.write(null, new IIOImage(image, null, null), param);
//InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
//
//combaos.flush();combaos.close();ios.close();writer.dispose();
//BufferedImage decimage=ImageIO.read(comins);
//ByteArrayOutputStream decbaos=new ByteArrayOutputStream();
//Iterator<ImageWriter>decwriters=ImageIO.getImageWritersByFormatName("JPEG");
//ImageWriter decwriter = (ImageWriter) decwriters.next();
//ImageOutputStream dios = ImageIO.createImageOutputStream(decbaos);
//decwriter.setOutput(dios);ImageWriteParam decparam = decwriter.getDefaultWriteParam();
//decparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//decparam.setCompressionQuality(0.95f);
//decwriter.write(null, new IIOImage(decimage, null, null), decparam);
//OutputStream decous=new FileOutputStream(dec);
//InputStream decins= new ByteArrayInputStream(decbaos.toByteArray());
//IOUtils.copy(decins, decous);decous.close();decbaos.flush();decbaos.close();
//dios.close();decwriter.dispose();

}


public void setCompressDecompress(String filename) throws IOException, ImageReadException {
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
com=new File(par+"\\"+name+"_"+ext);
dec=new File(par+"\\"+name+"__"+ext);
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
BufferedImage image=ImageIO.read(inp);//ImageIO.write(image, "jpg", dec);
//OutputStream jpgous=new FileOutputStream(dec);
//ByteArrayOutputStream jpgbaos=new ByteArrayOutputStream();
//ImageIO.write(image, "jpg", jpgbaos);
//InputStream jpgins= new ByteArrayInputStream(jpgbaos.toByteArray());
//IOUtils.copy(jpgins,jpgous);jpgous.close();jpgbaos.flush();jpgbaos.close();

ByteArrayOutputStream combaos=new ByteArrayOutputStream();
Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.50f);
writer.write(null, new IIOImage(image, null, null), param);
//OutputStream comous=new FileOutputStream(new File(file.getParentFile()+"\\filecom.jpg"));
//InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
//IOUtils.copy(comins, comous);comous.close();combaos.flush();combaos.close();
ios.close();writer.dispose();}


//************************************************************************************************************************************************************************************************
public void compressDecompressLossless(File fi) throws ImageReadException, IOException {
long start=System.currentTimeMillis();String filename="";
if(fi.isFile()) {par=fi.getParent();filename=fi.getName();setCompressDecompressLossless(filename);compress();decompress();}
else if(fi.isDirectory()) {par=fi.toString();
FileFilter fileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};
File files[]=fi.listFiles(fileFilter);System.out.println(files.length +" files found!");
System.out.println ("Name:	"+"size	"+"dim	"+"dpi	"+"sdr	");
for(File file:files) {filename=file.getName();setCompressDecompressLossless(filename);compress();decompress();
System.out.println (name+"	"+size+"	"+dim+ "	"+dpi+"	"+sdr);}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");}}
public void setCompressDecompressLossless(String filename) throws IOException, ImageReadException {
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
com=new File(par+"\\"+name+"_.bin");
dec=new File(par+"\\"+name+"_"+ext);
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
ImageInfo imageInfo = Sanselan.getImageInfo(inp);
dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
sdr=dim/dpi;}

public void compress() throws  IOException {
     if(ext.toLowerCase().contains("jpg")){compressPNG(inp,com,0.75f);}//compressJPG(inp,com,"jpg");
else if(ext.toLowerCase().contains("png")){compressPNG(inp,com,0.65f);}}

public void decompress()throws IOException{
if(ext.toLowerCase().contains("jpg")){
   if(dim>=960  && dim<1200)   {decompressJPG(com,dec,0.95f);}
else if(dim>=2000 && dim<4000) {decompressJPG(com,dec,1.00f);}
else if(dim==5184 && dim<6000) {decompressJPG(com,dec,1.00f);}
else if(dim>=4000 && dim<8000) {decompressJPG(com,dec,0.90f);}
else 						   {decompressJPG(com,dec,0.95f);}}
else if(ext.toLowerCase().contains("png")){decompressPNG(com,dec,"png");}}

public  void compressJPG(File file, File dest,String formatt) throws IOException {
BufferedImage image=ImageIO.read(file);
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ImageIO.write( image, formatt, baos );
baos.flush();
byte[] uncompressbytes = baos.toByteArray();baos.close();
FileOutputStream fos=new FileOutputStream(dest);
byte[] compressBytes= new byte[]{};
try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
gzipOS.write(uncompressbytes );
gzipOS.close();
compressBytes = bos.toByteArray();
fos.write(compressBytes);fos.close();}}

public  void compressPNG(File file, File dest,float fact) throws IOException {
BufferedImage image = ImageIO.read(file);
ByteArrayOutputStream baos = new ByteArrayOutputStream();
Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
ImageWriter writer = writers.next();
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
writer.setOutput(ios);
writer.write(null, new IIOImage(image, null, null), param);
byte[] uncompressbytes = baos.toByteArray();writer.dispose();
FileOutputStream fos=new FileOutputStream(dest);
byte[] compressBytes= new byte[]{};
try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
gzipOS.write(uncompressbytes );
gzipOS.close();
compressBytes = bos.toByteArray();
fos.write(compressBytes);fos.close();} 
catch (IOException e) {e.printStackTrace();}}

public  void decompressJPG(File file, File dest,float fact) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
BufferedImage image = ImageIO.read(is);
OutputStream os =new FileOutputStream(dest);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();}

public  void decompressPNG(File file, File dest,String formatt) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(is);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_ARGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.OPAQUE, type, null);
ImageIO.write(newBufferedImage, formatt, dest);} 
catch (IOException e) { e.printStackTrace();}}


public void compressIMG(File file, File dest,float fact) throws IOException {
BufferedImage image = ImageIO.read(file);
OutputStream os =new FileOutputStream(dest);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
//JPEGImageWriteParam param = new JPEGImageWriteParam(null);
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();}
public void writeIMG(File file, File dest,String formatt) {
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(file);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.OPAQUE, type, null);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
ImageIO.write(newBufferedImage, formatt, dest);} 
catch (IOException e) { e.printStackTrace();}}
public void resizeIMG(File source, File dest, float ratio) throws IOException {
BufferedImage sourceImage = ImageIO.read(new FileInputStream(source));
int Width=sourceImage.getWidth();
int Height=sourceImage.getHeight();
//double ratio = (double) Width/Height;
//int width = (int) (height * ratio + 0.4); 
//int height = (int) (width /ratio + 0.4);
//double Slope=(double)Height/Width;
//int width=(int)(Width/2);
//int height=(int) (Slope*width);
int width=Width;int height=Height;
Image scaled = sourceImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
BufferedImage bufferedScaled = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
Graphics2D g2d = bufferedScaled.createGraphics();
g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g2d.drawImage(scaled, 0, 0, width, height, null);
ImageIO.write(bufferedScaled, "jpg", dest);}
public void transformIMG(File input,File output,String format) throws IOException{
BufferedImage originalImage= ImageIO.read(input);
int width=originalImage.getWidth();int height=originalImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage resizedImage = new BufferedImage(width, height, type);
Graphics2D g = resizedImage.createGraphics();//g.drawImage(originalImage, 0, 0, width, height, null);
g.drawImage(originalImage, 0, 0, Color.WHITE, null);
g.setComposite(AlphaComposite.Src);
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g.dispose();
ImageIO.write(resizedImage, format, output);}

}
