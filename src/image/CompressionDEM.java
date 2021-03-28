package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base32OutputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class CompressionDEM {
public static void main(String args[]) throws Exception {
CompressionDEM b=new CompressionDEM(); 
File dir=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\");
File files[]=dir.listFiles();
for(File file:files) {String filename=file.getName();b.init(filename);
b.compress();b.decompress();}

File inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File bin=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bin.bin");
File dec=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec.bin");
File hex=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.bin");
File btt=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\btt.bin");
File bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.bin");
File ltn=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\ltn.bin");
toBIN(inp,bin);
toDEC(inp,dec);
toHEX(inp,hex);
toBTT(inp,btt);
toBSF(inp,bsf);
toLTN(inp,ltn);

System.out.println("...");

}

BufferedImage img;
public File inp;
public File com;
public File dec;
String name;
String ext;
long size;
int dim;
int dpi;
double sdr;

public void init(String filename) throws IOException, ImageReadException {
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\"+name+ext);
com=new File(inp.getParent()+"\\"+name+".bin");
dec=new File(inp.getParent()+"\\"+name+"_"+ext);
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
ImageInfo imageInfo = Sanselan.getImageInfo(inp);
dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
sdr=dim/dpi;}



public void compress() throws  IOException {
     if(ext.toLowerCase().contains("jpg")){compressJPG(inp,com,"jpg");}
else if(ext.toLowerCase().contains("png")){compressPNG(inp,com,0.65f);}}

public void decompress()throws IOException{
if(ext.toLowerCase().contains("jpg")){
     if(dim>=960  && dim<1200) {decompressJPG(com,dec,0.95f);}
else if(dim>=2000 && dim<4000) {decompressJPG(com,dec,1.00f);}
else if(dim==5184 && dim<6000) {decompressJPG(com,dec,1.00f);}
else if(dim>=4000 && dim<8000) {decompressJPG(com,dec,0.90f);}
else 						   {decompressJPG(com,dec,0.95f);}}
else if(ext.toLowerCase().contains("png")){decompressPNG(com,dec,"png");}}

public static void compressJPG(File file, File dest,String formatt) throws IOException {
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
public static void compressPNG(File file, File dest,float fact) throws IOException {
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
public static void decompressJPG(File file, File dest,float fact) throws IOException {
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
public static void decompressPNG(File file, File dest,String formatt) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(is);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, formatt, dest);} 
catch (IOException e) { e.printStackTrace();}}


public static void toBIN(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
byte[] bytes = IOUtils.toByteArray(fis);
StringBuilder sb = new StringBuilder();
for (byte b : bytes) {sb.append(String.format("%8s", Integer.toBinaryString(b&0xFF)).replace(' ', '0'));sb.append(" ");}
try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true))) {
bw.write(sb.toString());
bw.close();
} catch (IOException e) {e.printStackTrace();}}


public static void toDEC(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
byte[] bytes = IOUtils.toByteArray(fis);
StringBuilder sb = new StringBuilder();
for (byte b : bytes) {sb.append(Math.abs((int)b));sb.append(" ");}
try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true))) {
bw.write(sb.toString());
bw.close();
} catch (IOException e) {e.printStackTrace();}}

public static void toHEX(File originalFile, File outFile) throws IOException {
FileInputStream fis=new FileInputStream(originalFile);
byte[] bytes = IOUtils.toByteArray(fis);
String hex=org.apache.commons.codec.binary.Hex.encodeHexString(bytes);
try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true))) {
bw.write(hex);
bw.close();
} catch (IOException e) {e.printStackTrace();}}


public static void toBTT(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base32OutputStream bos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base32OutputStream(fos);
zos = new GZIPOutputStream(bos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}




public static void toBSF(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
zos = new GZIPOutputStream(bos);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}


public static void toLTN(File input,File output){
try {
FileInputStream fis=new FileInputStream(input);
byte[] bytes = IOUtils.toByteArray(fis);
String s=new String(bytes,StandardCharsets.UTF_16);
Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_16));
out.append(s);
out.flush();
out.close();} 
catch (UnsupportedEncodingException e) {System.out.println(e.getMessage());} 
catch (IOException e) {System.out.println(e.getMessage());}
catch (Exception e){System.out.println(e.getMessage());} }


}

