package image;

import java.awt.Color;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class CompressionIMG {
public static void main(String args[]) throws Exception {

CompressionIMG b=new CompressionIMG();
File file=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File com=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.bin");

b.compressLossless(file);
b.decompressLossless(com);

//b.compressLossy(file);
//b.decompressLossy(com);

}

BufferedImage img;
public File inp;
public File com;
public File dec;
public String par;
public String name;
public String ext;
long size;
int dim;
int dpi;
double sdr;


public FileFilter imgFileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};

public void compressLossless(File fi) throws ImageReadException, IOException {
long start=System.currentTimeMillis();String filename="";
if(fi.isFile()) {compressFileLossless(fi);}
if(fi.isDirectory()) {
par=fi.toString();
File files[]=fi.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
System.out.println ("Name:	"+"size	"+"dim	"+"dpi	"+"sdr	");File com=new File(fi.toString()+"\\com");com.mkdir();
for(File file:files) {
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\com\\"+name+".bin");compressPNG(inp,com,0.75f);}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\com\\"+name+"_.bin");compressPNG(inp,com,0.65f);}
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
ImageInfo imageInfo = Sanselan.getImageInfo(inp);
dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
sdr=dim/dpi;
System.out.println (name+"	"+size+"	"+dim+ "	"+dpi+"	"+sdr);}zip(fi);
System.out.println((System.currentTimeMillis()-start)/1000+"sec");}}

public void compressFileLossless(File file) throws IOException {
String filename=file.getName();
par=file.getParent();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\"+name+".bin");compressPNG(inp,com,0.75f);}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\"+name+"_.bin");compressPNG(inp,com,0.65f);}
System.out.println("File "+ file.getName()+" compressed");
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}}

public void decompressLossless(File fi) throws ImageReadException, IOException{
long start=System.currentTimeMillis();
if(fi.getName().toLowerCase().contains(".bin")) {decompressFileLossless(fi);}
if(fi.getName().toLowerCase().contains(".zip")) {
unzip(fi);
name=fi.getName(); 
par=fi.getParent();
String decdir=par+"\\"+name.substring(0,name.lastIndexOf('.')); new File(decdir).mkdir();
String comdir=par+"\\_comp";
String filename="";
File files[]=new File(comdir).listFiles();System.out.println(files.length +" files found!");
for(File file:files) {
filename=file.getName();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\_comp\\"+filename);
if(name.contains("_")) {
dec=new File(decdir+"\\"+name+".png");decompressPNG(com,dec,"png");}else {
dec=new File(decdir+"\\"+name+"_.jpg");decompressJPG(com,dec,0.95f);}
System.out.println ("File "+name+" decompressed");}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");
FileUtils.deleteQuietly(new File(comdir));}}

public void decompressFileLossless(File file) throws IOException {
String filename=file.getName();
par=file.getParent();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\"+filename);
if(name.contains("_")) {
dec=new File(par+"\\"+name+".png");decompressPNG(com,dec,"png");}else {
dec=new File(par+"\\"+name+"_.jpg");decompressJPG(com,dec,0.95f);}
System.out.println ("File "+name+" decompressed..");}




public void compressLossy(File fi) throws ImageReadException, IOException {
long start=System.currentTimeMillis();String filename="";
if(fi.isFile()) {compressFileLossy(fi);}
if(fi.isDirectory()) {
par=fi.toString();
File files[]=fi.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
System.out.println ("Name:	"+"size	"+"dim	"+"dpi	"+"sdr	");File com=new File(fi.toString()+"\\com");com.mkdir();
for(File file:files) {filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\com\\"+name+".bin");}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\com\\"+name+"_.bin");}
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
ImageInfo imageInfo = Sanselan.getImageInfo(inp);
dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
sdr=dim/dpi;compressIMG(inp,com);
System.out.println (name+"	"+size+"	"+dim+ "	"+dpi+"	"+sdr);}zip(fi);
System.out.println((System.currentTimeMillis()-start)/1000+"sec");}}

public void compressFileLossy(File file) throws IOException {
par=file.getParent();
String filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\"+name+".bin");}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\"+name+"_.bin");}
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}compressIMG(inp,com);
System.out.println("File "+ file.getName()+" compressed");}


public void decompressLossy(File fi) throws ImageReadException, IOException{
long start=System.currentTimeMillis();
if(fi.getName().toLowerCase().contains(".bin")) {decompressFileLossy(fi);}
if(fi.getName().toLowerCase().contains(".zip")) {
unzip(fi);
name=fi.getName(); 
par=fi.getParent();
String decdir=par+"\\"+name.substring(0,name.lastIndexOf('.')); new File(decdir).mkdir();
String comdir=par+"\\_comp";
String filename="";
File files[]=new File(comdir).listFiles();System.out.println(files.length +" files found!");
for(File file:files) {
filename=file.getName();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\_comp\\"+filename);
if(name.contains("_")) {
dec=new File(decdir+"\\"+name+".png");}else {
dec=new File(decdir+"\\"+name+"_.jpg");}
System.out.println ("File "+name+" decompressed");transformIMG(com,dec);}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");
FileUtils.deleteQuietly(new File(comdir));}}

public void decompressFileLossy(File file) throws IOException {
String filename=file.getName();
par=file.getParent();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\"+filename);
if(name.contains("_")) {
dec=new File(par+"\\"+name+".png");}else {
dec=new File(par+"\\"+name+"_.jpg");}
transformIMG(com,dec);
System.out.println ("File "+name+" decompressed");}




public  void compressIMG(File file, File dest) throws IOException {
BufferedImage image = ImageIO.read(file);
ByteArrayOutputStream baos = new ByteArrayOutputStream();
Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
ImageWriter writer = writers.next();
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.70f);
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

public  void decompressIMG(File file, File dest) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
if(file.getName().contains("_")) {
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(is);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, "jpg", dest);} 
catch (IOException e) { e.printStackTrace();}}
else {
OutputStream os=new FileOutputStream(dest);
IOUtils.copy(is, os);}}


public  void transformIMG(File file, File dest) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
img=ImageIO.read(is);
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
param.setCompressionQuality(0.25f);
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
OutputStream decous=new FileOutputStream(dest);
InputStream decins= new ByteArrayInputStream(decbaos.toByteArray());
IOUtils.copy(decins, decous);decous.close();decbaos.flush();decbaos.close();
dios.close();decwriter.dispose();}


public  void transformIMG2(File file, File dest) throws IOException {
img=ImageIO.read(file);
ByteArrayOutputStream pngbaos=new ByteArrayOutputStream();
ImageIO.write(img, "png", pngbaos);ImageIO.write(img, "png", new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file__.jpg"));
InputStream pngins= new ByteArrayInputStream(pngbaos.toByteArray());
BufferedImage image=ImageIO.read(pngins);
ByteArrayOutputStream combaos=new ByteArrayOutputStream();
Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.75f);
writer.write(null, new IIOImage(image, null, null), param);
InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
OutputStream comous=new FileOutputStream(dest);
IOUtils.copy(comins, comous);combaos.flush();combaos.close();ios.close();writer.dispose();
comous.close();writer.dispose();}



public  void zip(File dir) throws IOException {
String filename=dir.getName();
String outputZipFile =dir.toString()+"\\_"+filename+".zip";
File comdir=new File(dir.toString()+"\\com");comdir.mkdir();
byte[] buffer = new byte[1024];
try{
File files[]=new File(dir.toString()+"\\com").listFiles();
FileOutputStream fos = new FileOutputStream(outputZipFile);
ZipOutputStream zos = new ZipOutputStream(fos);
for(File file:files) {
ZipEntry ze= new ZipEntry(file.getName().toString());
zos.putNextEntry(ze);
FileInputStream in = new FileInputStream(file);
int len;while ((len = in.read(buffer)) > 0) {zos.write(buffer, 0, len);}
in.close();}
zos.closeEntry();zos.close();}
catch(IOException ex){ex.printStackTrace();}
FileUtils.deleteDirectory(comdir);}

public  void unzip(File zipFile){
String comdir=zipFile.getParent()+"\\_comp";
new File(comdir).mkdir();
byte[] buffer = new byte[1024];
try{
ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
ZipEntry ze = zis.getNextEntry();FileOutputStream fos = null;
while(ze!=null){
String fileName = ze.getName();
File newFile = new File(comdir+"\\"+fileName);
fos = new FileOutputStream(newFile);             
int len;while ((len = zis.read(buffer)) > 0) {fos.write(buffer, 0, len);}
fos.close();ze = zis.getNextEntry();}
zis.closeEntry();
zis.close();}catch(IOException ex){ex.printStackTrace(); }}




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
compressBytes = bos.toByteArray();bos.close();
fos.write(compressBytes);fos.close();} 
catch (IOException e) {e.printStackTrace();}}

public  void decompressJPG(File file, File dest,float fact) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
BufferedImage image = ImageIO.read(is);
if(image.getWidth()>image.getHeight()) {dim=image.getWidth();}else {dim=image.getHeight();}
     if(dim>=960  && dim<1200) {fact=0.95f;}
else if(dim>=2000 && dim<4000) {fact=1.00f;}
else if(dim==5184 && dim<6000) {fact=1.00f;}
else if(dim>=4000 && dim<8000) {fact=0.90f;}
else 						   {fact=0.95f;}
OutputStream os =new FileOutputStream(dest);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);//System.out.println("dim: "+dim+" fact: "+fact);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();}

public  void decompressPNG(File file, File dest,String formatt) throws IOException {
FileInputStream fis = new FileInputStream(file);
InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(is);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, formatt, dest);} 
catch (IOException e) { e.printStackTrace();}}





}

