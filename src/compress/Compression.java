package compress;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
import java.util.concurrent.TimeUnit;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

public class Compression {





public JWindow processWindow;
public void initProcessWindow() {processWindow=new JWindow();
String ic=Resources.getIcon("loading1.gif");
ImageIcon icon=	new ImageIcon(ic);
processWindow.getContentPane().add(new JLabel("Processing", icon, SwingConstants.CENTER));
processWindow.setBounds(500, 200, 200, 200);}
public void showProcessWindow() {
processWindow.setVisible(true);
processWindow.setAlwaysOnTop(true);
processWindow.setLocationRelativeTo(null);}
public void closeProcessWindow() {processWindow.setVisible(false);processWindow.dispose();}


public JWindow processWindow_;
public void initProcessWindow_() {processWindow_=new JWindow();
String ic=Resources.getIcon("loading2.gif");
ImageIcon icon=	new ImageIcon(ic);
processWindow_.getContentPane().add(new JLabel("Processing", icon, SwingConstants.CENTER));
processWindow_.setBounds(500, 200, 200, 200);}
public void showProcessWindow_() {
processWindow_.setVisible(true);
processWindow_.setAlwaysOnTop(true);
processWindow_.setLocationRelativeTo(null);}
public void closeProcessWindow_() {processWindow_.setVisible(false);processWindow_.dispose();}

//public JWindow progressWindow;
//public JLabel progressLabel;
//public void initProgressWindow() {
//progressWindow=new JWindow();
//progressWindow.setSize(400, 200);
//Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
//progressLabel = new JLabel("", icon, JLabel.LEFT);progressWindow.getContentPane().add(progressLabel);
//progressWindow.getContentPane().setBackground(java.awt.Color.WHITE);}
//public void showProgressWindow(int progress) throws HeadlessException {
//progressLabel.setText("Processed: "+progress +" %");
//progressWindow.setVisible(true);
//progressWindow.setAlwaysOnTop(true);
//progressWindow.setLocationRelativeTo(null);}
//public void closeProgressWindow() {progressWindow.setVisible(false);progressWindow.dispose();}


//public JWindow progressWindow_;
//public JLabel progressLabel_;
//public void initProgressWindow_() {
//progressWindow_=new JWindow();
//progressWindow_.setSize(400, 200);
//Icon icon =new ImageIcon(Resources.getIcon("decompress.gif"));
//progressLabel_ = new JLabel("", icon, JLabel.LEFT);progressWindow_.getContentPane().add(progressLabel_);
//progressWindow_.getContentPane().setBackground(java.awt.Color.WHITE);}
//public void showProgressWindow_(int progress) throws HeadlessException {
//progressLabel_.setText("Decompressed: "+progress +" %");
//progressWindow_.setVisible(true);
//progressWindow_.setAlwaysOnTop(true);
//progressWindow_.setLocationRelativeTo(null);}
//public void closeProgressWindow_() {progressWindow_.setVisible(false);progressWindow_.dispose();}



public JWindow progressWindow;
public JLabel progressLabel;
public void initProgressWindow() {
progressWindow=new JWindow();
progressWindow.setSize(400, 200);
Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
progressWindow.getContentPane().setLayout(null);
progressLabel = new JLabel("", icon, JLabel.LEFT);
progressLabel.setBounds(0, 0, 369, 200);progressWindow.getContentPane().add(progressLabel);
JButton clo = new JButton("");
clo.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
try {killtasks();
progressWindow.setVisible(false);
progressWindow.dispose();
} catch (InterruptedException e1) {e1.printStackTrace();}}});
clo.setIcon(new ImageIcon(Resources.getIcon("close.png")));
clo.setBounds(381, 0, 16, 16);
clo.setOpaque(false);
clo.setContentAreaFilled(false);
clo.setBorderPainted(false);
progressWindow.getContentPane().add(clo);}
public void showProgressWindow(int progress) throws HeadlessException {
progressLabel.setText("Processed: "+progress +" %");
progressWindow.setVisible(true);
progressWindow.setAlwaysOnTop(true);
progressWindow.toFront();
progressWindow.requestFocus();
progressWindow.setAlwaysOnTop(false);
progressWindow.setLocationRelativeTo(null);}
public void closeProgressWindow() {progressWindow.setVisible(false);progressWindow.dispose();}


public JWindow progressWindow_;
public JLabel progressLabel_;
public void initProgressWindow_() {
progressWindow_=new JWindow();
progressWindow_.setSize(400, 200);
Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
progressWindow_.getContentPane().setLayout(null);
progressLabel_ = new JLabel("", icon, JLabel.LEFT);
progressLabel_.setBounds(0, 0, 369, 200);progressWindow_.getContentPane().add(progressLabel_);
JButton clo = new JButton("");
clo.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
try {killtasks();
progressWindow_.setVisible(false);
progressWindow_.dispose();
} catch (InterruptedException e1) {e1.printStackTrace();}}});
clo.setIcon(new ImageIcon(Resources.getIcon("close.png")));
clo.setBounds(381, 0, 16, 16);
clo.setOpaque(false);
clo.setContentAreaFilled(false);
clo.setBorderPainted(false);
progressWindow_.getContentPane().add(clo);}
public void showProgressWindow_(int progress) throws HeadlessException {
progressLabel_.setText("Processed: "+progress +" %");
progressWindow_.setVisible(true);
progressWindow_.setAlwaysOnTop(true);
progressWindow_.toFront();
progressWindow_.requestFocus();
progressWindow_.setAlwaysOnTop(false);
progressWindow_.setLocationRelativeTo(null);}
public void closeProgressWindow_() {progressWindow_.setVisible(false);progressWindow_.dispose();}



//public void initWindow() {
//String gif=Resources.getIcon("loading1.gif");
//window=new JWindow();ImageIcon icon=	new ImageIcon(gif);
//window.getContentPane().add(new JLabel("", icon, SwingConstants.CENTER));
//window.setBounds(500, 200, 200, 200);}
//public void showWindow() {
//window.setVisible(true);
//window.setAlwaysOnTop(true);
//window.toFront();
//window.requestFocus();
//window.setAlwaysOnTop(false);
//window.setLocationRelativeTo(null);}
//public void closeWindow() {window.setVisible(false);window.dispose();}
//public void initWindow_() {window_=new JWindow();
//String gif=Resources.getIcon("decompress.gif");
//ImageIcon icon=	new ImageIcon(gif);
//window_.getContentPane().add(new JLabel("", icon, SwingConstants.CENTER));
//window_.setBounds(500, 200, 200, 200);}
//public void showWindow_() {
//window_.setVisible(true);
//window_.setAlwaysOnTop(true);
//window_.toFront();
//window_.requestFocus();
//window_.setAlwaysOnTop(false);
//window_.setLocationRelativeTo(null);}
//public void closeWindow_() {window_.setVisible(false);window_.dispose();}

public FileFilter imgFileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};

@SuppressWarnings("unused")
public void killtasks() throws InterruptedException {
//String terminate[]={"taskkill","/F","/IM","ffmpeg.jar"};
//Process process=null;
//try {
//System.out.println("Terminating process");
//ProcessBuilder ter = new ProcessBuilder(terminate).inheritIO();
//process=ter.start();
//}catch (IOException e) { e.printStackTrace();}

try{
String[] cmd = new String[3];
cmd[0] = "cmd.exe";
cmd[1] = "/C";
cmd[2] = "taskkill /F /IM media.jar";
Process closeProcess= Runtime.getRuntime().exec(cmd);
System.out.println("Processing Closed");}
catch (Exception ex) {ex.printStackTrace();}
}


public File inp;
public File com;
public File dec;
String par; String filename;String name;String ext;
String res;

BufferedImage img;
long size;
int dim;
int dpi;
double sdr;

public Compression() {
par=null; filename="";name="";ext="";res="";
inp=null;com=null;dec=null;}



public void compress(File file) throws ImageReadException, IOException {

if(file.isFile()) {
String IMAGE=".jpg .jpeg .png .bmp";
String VIDEO=".mp4 .avi .wmv";
String filename,ext;
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
     if(IMAGE.contains(ext.toLowerCase())){compressImage(file);}
else if(VIDEO.contains(ext.toLowerCase())){compressVideo(file);}}
				   if(file.isDirectory()) {compressDir(file);}
				   

}



public void decompress(File file) throws ImageReadException, IOException {
String filename;
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
     if(filename.contains("__.bin") ||filename.contains("__.bin")){decompressVideo(file);}
else if(filename.contains("_.bin")  ||filename.contains(".bin"))  {decompressImage(file);}
else if(filename.contains(".zip")   ||filename.contains(".zip"))  {decompressDir(file);}}

public void decompress_(File file) throws ImageReadException, IOException {
String filename;
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
     if(filename.contains("__.bin")|| filename.contains("__.bin")){decompressVideo_(file);}
else if(filename.contains("_.bin") || filename.contains(".bin"))  {decompressImage_(file);}
else if(filename.contains(".zip")  || filename.contains(".zip"))  {decompressDir_(file);}}






public void compressImage(File fi) throws ImageReadException, IOException {
initProcessWindow();
String filename="";
if(fi.isFile()) {
showProcessWindow();
filename=fi.getName();
par=fi.getParent();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);name=name.replace("_", "");
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\"+name+".bin");compressIMG(inp,com);}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\"+name+"_.bin");compressIMG(inp,com);}
if(ext.toLowerCase().contains("bmp")) {com=new File(par+"\\"+name+".bin");compressIMG(inp,com);}
System.out.println("File "+ fi.getName()+" compressed");
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}}
closeProcessWindow();}

public void compressDir(File fi) throws IOException, ImageReadException {
long start=System.currentTimeMillis();initProcessWindow();
if(fi.isDirectory()) {
par=fi.toString();
File files[]=fi.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
System.out.println ("Name:	"+"size	"+"dim	"+"dpi	"+"sdr	");File com=new File(fi.toString()+"\\com");com.mkdir();
for(File file:files) {
showProcessWindow();
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);name=name.replace("_", "");
if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\com\\"+name+".bin");compressIMG(inp,com);}
if(ext.toLowerCase().contains("png")) {com=new File(par+"\\com\\"+name+"_.bin");compressIMG(inp,com);}
size=inp.length()/1024;
img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
dim=img.getWidth();}else {dim=img.getHeight();}
ImageInfo imageInfo = Sanselan.getImageInfo(inp);
dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
sdr=dim/dpi;
System.out.println (name+"	"+size+"	"+dim+ "	"+dpi+"	"+sdr);}
zip(fi);System.out.println((System.currentTimeMillis()-start)/1000+"sec");
closeProcessWindow();
}
}


public void decompressImage(File fi) throws ImageReadException, IOException{
initProcessWindow_();String filename="";
if(fi.getName().toLowerCase().contains(".bin")) {
showProcessWindow_();
filename=fi.getName();
par=fi.getParent();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\"+filename);
if(name.contains("_")) {
dec=new File(par+"\\"+name+".png");decompressIMG(com,dec);}else {
dec=new File(par+"\\"+name+"_.jpg");decompressIMG(com,dec);}
System.out.println ("File "+name+" decompressed..");}
closeProcessWindow_();}

public void decompressDir(File fi) throws ImageReadException, IOException{
initProcessWindow_();
long start=System.currentTimeMillis();
if(fi.getName().toLowerCase().contains(".zip")) {
unzip(fi);
par=fi.getParent();
filename=fi.getName(); 
String dirname=filename.substring(0,filename.lastIndexOf('.'));System.out.println("Dir is: "+dirname);
String decdir=par+"\\"+dirname; //new File(decdir).mkdir();
String comdir=par+"\\"+name+"\\_comp";
File files[]=new File(comdir).listFiles();System.out.println(files.length +" files found!");
for(File file:files) {
showProcessWindow_();
filename=file.getName();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\"+dirname+"\\_comp\\"+filename);
if(name.contains("_")) {
dec=new File(decdir+"\\"+name+".png");decompressIMG(com,dec);}else {
dec=new File(decdir+"\\"+name+"_.jpg");decompressIMG(com,dec);}
System.out.println ("File "+name+" decompressed");}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");
FileUtils.deleteQuietly(new File(comdir));
}closeProcessWindow_();}




//public void compressImage_(File fi) throws ImageReadException, IOException {
//long start=System.currentTimeMillis();String filename="";
//if(fi.isFile()) {
//par=fi.getParent();
//filename=fi.getName();
//ext=filename.substring(filename.lastIndexOf('.'),filename.length());
//name=filename.substring(0,filename.lastIndexOf('.')); 
//inp=new File(par+"\\"+name+ext);
//if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\"+name+".bin");}
//if(ext.toLowerCase().contains("png")) {com=new File(par+"\\"+name+"_.bin");}
//size=inp.length()/1024;
//img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
//dim=img.getWidth();}else {dim=img.getHeight();}compressIMG_(inp,com);
//System.out.println("File "+ fi.getName()+" compressed");}
//if(fi.isDirectory()) {
//par=fi.toString();
//File files[]=fi.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
//System.out.println ("Name:	"+"size	"+"dim	"+"dpi	"+"sdr	");File com=new File(fi.toString()+"\\com");com.mkdir();
//for(File file:files) {filename=file.getName();
//ext=filename.substring(filename.lastIndexOf('.'),filename.length());
//name=filename.substring(0,filename.lastIndexOf('.')); 
//inp=new File(par+"\\"+name+ext);
//if(ext.toLowerCase().contains("jpg")) {com=new File(par+"\\com\\"+name+".bin");}
//if(ext.toLowerCase().contains("png")) {com=new File(par+"\\com\\"+name+"_.bin");}
//size=inp.length()/1024;
//img=ImageIO.read(inp);if(img.getWidth()>img.getHeight()) {
//dim=img.getWidth();}else {dim=img.getHeight();}
//ImageInfo imageInfo = Sanselan.getImageInfo(inp);
//dpi= imageInfo.getPhysicalWidthDpi();if(dpi<=0) {dpi=96;}
//sdr=dim/dpi;compressIMG_(inp,com);
//System.out.println (name+"	"+size+"	"+dim+ "	"+dpi+"	"+sdr);}zip(fi);
//System.out.println((System.currentTimeMillis()-start)/1000+"sec");}}

public void decompressImage_(File fi) throws ImageReadException, IOException{
initProcessWindow_();
if(fi.getName().toLowerCase().contains(".bin")) {
showProcessWindow_();
filename=fi.getName();
par=fi.getParent();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\"+filename);
if(name.contains("_")) {
dec=new File(par+"\\"+name+".png");}else {
dec=new File(par+"\\"+name+"__.jpg");}
transformIMG(com,dec);
System.out.println ("File "+name+" decompressed_");}
closeProcessWindow_();}

public void decompressDir_(File fi) throws ImageReadException, IOException{
initProcessWindow_();
long start=System.currentTimeMillis();
if(fi.getName().toLowerCase().contains(".zip")) {
unzip_(fi);
par=fi.getParent();
filename=fi.getName(); 
String dirname=filename.substring(0,filename.lastIndexOf('.'));System.out.println("Dir is: "+dirname);
String decdir=par+"\\_"+dirname; //new File(decdir).mkdir();
String comdir=par+"\\_"+name+"\\_comp";
File files[]=new File(comdir).listFiles();System.out.println(files.length +" files found!");
for(File file:files) {
showProcessWindow_();
filename=file.getName();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(par+"\\_"+dirname+"\\_comp\\"+filename);
if(name.contains("_")) {
dec=new File(decdir+"\\"+name+".png");transformIMG(com,dec);}else {
dec=new File(decdir+"\\"+name+"_.jpg");transformIMG(com,dec);}
System.out.println ("Directory "+name+" decompressed");}
System.out.println((System.currentTimeMillis()-start)/1000+"sec");
FileUtils.deleteQuietly(new File(comdir));
//if(new File(comdir).exists()==true) {FileUtils.deleteQuietly(comdiretory);}
//if(comdir.exists()==true) {FileUtils.deleteQuietly(comdiretory);}
}closeProcessWindow_();}


//public  void compressIMG(File file, File dest) throws IOException {
//float fact=0.7f;
//if(file.getName().toLowerCase().contains(".jpg")) {fact=0.75f;}
//if(file.getName().toLowerCase().contains(".png")) {fact=0.65f;}
//if(file.getName().toLowerCase().contains(".bmp")) {fact=0.80f;}
//BufferedImage image = ImageIO.read(file);
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
//ImageWriter writer = writers.next();
//ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(fact);
//ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
//writer.setOutput(ios);
//writer.write(null, new IIOImage(image, null, null), param);
//byte[] uncompressbytes = baos.toByteArray();writer.dispose();
//FileOutputStream fos=new FileOutputStream(dest);
//byte[] compressBytes= new byte[]{};
//try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
//GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
//gzipOS.write(uncompressbytes );
//gzipOS.close();
//compressBytes = bos.toByteArray();bos.close();
//fos.write(compressBytes);fos.close();} 
//catch (IOException e) {e.printStackTrace();}}
//
//
//public  void decompressIMG(File file, File dest) throws IOException {
//float fact=0.70f;
//FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
//if(file.getName().contains("_")) {
//BufferedImage bufferedImage=null;try {
//bufferedImage = ImageIO.read(is);
//int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_ARGB;
//BufferedImage newBufferedImage = new BufferedImage(width,height,type);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//ImageIO.write(newBufferedImage, "png", dest);} 
//catch (IOException e) { e.printStackTrace();}}
//else {
//BufferedImage image = ImageIO.read(is);
//if(image.getWidth()>image.getHeight()) {dim=image.getWidth();}else {dim=image.getHeight();}
//     if(dim>=960  && dim<1200) {fact=0.95f;}
//else if(dim>=2000 && dim<4000) {fact=1.00f;}
//else if(dim==5184 && dim<6000) {fact=1.00f;}
//else if(dim>=4000 && dim<8000) {fact=0.90f;}
//else 						   {fact=0.95f;}
//OutputStream os =new FileOutputStream(dest);
//Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
//ImageWriter writer = (ImageWriter) writers.next();
//ImageOutputStream ios = ImageIO.createImageOutputStream(os);
//writer.setOutput(ios);
//ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(fact);
//writer.write(null, new IIOImage(image, null, null), param);
//os.close();ios.close();writer.dispose();}}


public  void compressIMG(File file, File dest) throws IOException {
float fact=0.7f;
if(file.getName().toLowerCase().contains(".jpg")) {fact=0.75f;}
if(file.getName().toLowerCase().contains(".png")) {fact=0.70f;}
if(file.getName().toLowerCase().contains(".bmp")) {fact=0.80f;}
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
writer.dispose();


//FileOutputStream fos=new FileOutputStream(dest);
//byte[] uncompressbytes = baos.toByteArray();
//byte[] compressBytes= new byte[]{};
//DeflateCompressorOutputStream dcos=null;
//ByteArrayOutputStream bos=null;
//try {
//bos = new ByteArrayOutputStream(uncompressbytes.length);
//dcos = new DeflateCompressorOutputStream(bos);
//dcos.write(uncompressbytes );
//dcos.close();compressBytes = bos.toByteArray();}
//catch (IOException e) {e.printStackTrace();}
//finally{bos.close();fos.write(compressBytes);fos.close();} 

byte[] uncompressbytes = baos.toByteArray();
FileOutputStream fos=new FileOutputStream(dest);
DeflateCompressorOutputStream dcos=null;

InputStream is=null;try {
is=new ByteArrayInputStream(uncompressbytes);
dcos = new DeflateCompressorOutputStream(fos);
IOUtils.copy(is, dcos);}
finally {dcos.close();is.close();}


}


public  void decompressIMG(File file, File dest) throws IOException {
float fact=0.70f;
FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
InputStream is =new DeflateCompressorInputStream(new BufferedInputStream(fis));

if(file.getName().contains("_")) {
//BufferedImage bufferedImage=null;try {
//bufferedImage = ImageIO.read(is);
//int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_ARGB;
//BufferedImage newBufferedImage = new BufferedImage(width,height,type);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//ImageIO.write(newBufferedImage, "png", dest);} 
//catch (IOException e) { e.printStackTrace();}

try {
BufferedImage img = ImageIO.read(is);
int w = img.getWidth(null);
int h = img.getHeight(null);
BufferedImage bi = new
BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
Graphics g = bi.getGraphics();
g.drawImage(img, 0, 0, null);
float[] scales = { 1f, 1f, 1f, 0.50f };//tarnsparency
float[] offsets = new float[4];
RescaleOp rop = new RescaleOp(scales, offsets, null);
bi.createGraphics().drawImage(bi, rop, 0, 0);
ImageIO.write(bi, "png",dest);}
catch (IOException e) { e.printStackTrace();}

}


else {
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
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();}
}




public  void compressIMG_(File file, File dest) throws IOException {
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

public  void decompressIMG_(File file, File dest) throws IOException {
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
IOUtils.copy(is, os);os.close();}}

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

//public  void transformIMG(File file, File dest) throws IOException {
//FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
//img=ImageIO.read(is);
//ByteArrayOutputStream pngbaos=new ByteArrayOutputStream();
//ImageIO.write(img, "jpg", pngbaos);
//InputStream pngins= new ByteArrayInputStream(pngbaos.toByteArray());
//BufferedImage image=ImageIO.read(pngins);
//ByteArrayOutputStream combaos=new ByteArrayOutputStream();
//Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
//ImageWriter writer = (ImageWriter) writers.next();
//ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
//writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(0.75f);
//writer.write(null, new IIOImage(image, null, null), param);
//InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
//OutputStream comous=new FileOutputStream(dest);
//IOUtils.copy(comins, comous);combaos.flush();combaos.close();ios.close();writer.dispose();
//comous.close();writer.dispose();}


//public  void transformIMG(File file, File dest) throws IOException {
//FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
//if(file.getName().contains("_")) {
//BufferedImage bufferedImage;try {
//bufferedImage = ImageIO.read(is);
//int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
//BufferedImage newBufferedImage = new BufferedImage(width,height,type);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//ImageIO.write(newBufferedImage, "jpg", dest);} 
//catch (IOException e) { e.printStackTrace();}}
//else {
//OutputStream os=new FileOutputStream(dest);
//IOUtils.copy(is, os);os.close();}}






//public  void compressJPG(File file, File dest,String formatt) throws IOException {
//BufferedImage image=ImageIO.read(file);
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//ImageIO.write( image, formatt, baos );
//baos.flush();
//byte[] uncompressbytes = baos.toByteArray();baos.close();
//FileOutputStream fos=new FileOutputStream(dest);
//byte[] compressBytes= new byte[]{};
//try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
//GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
//gzipOS.write(uncompressbytes );
//gzipOS.close();
//compressBytes = bos.toByteArray();
//fos.write(compressBytes);fos.close();}}
//
//public  void compressPNG(File file, File dest,float fact) throws IOException {
//BufferedImage image = ImageIO.read(file);
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
//ImageWriter writer = writers.next();
//ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(fact);
//ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
//writer.setOutput(ios);
//writer.write(null, new IIOImage(image, null, null), param);
//byte[] uncompressbytes = baos.toByteArray();writer.dispose();
//FileOutputStream fos=new FileOutputStream(dest);
//byte[] compressBytes= new byte[]{};
//try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressbytes.length);
//GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
//gzipOS.write(uncompressbytes );
//gzipOS.close();
//compressBytes = bos.toByteArray();bos.close();
//fos.write(compressBytes);fos.close();} 
//catch (IOException e) {e.printStackTrace();}}
//
//public  void decompressJPG(File file, File dest,float fact) throws IOException {
//FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream( new BufferedInputStream(fis));
//BufferedImage image = ImageIO.read(is);
//if(image.getWidth()>image.getHeight()) {dim=image.getWidth();}else {dim=image.getHeight();}
//     if(dim>=960  && dim<1200) {fact=0.95f;}
//else if(dim>=2000 && dim<4000) {fact=1.00f;}
//else if(dim==5184 && dim<6000) {fact=1.00f;}
//else if(dim>=4000 && dim<8000) {fact=0.90f;}
//else 						   {fact=0.95f;}
//OutputStream os =new FileOutputStream(dest);
//Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
//ImageWriter writer = (ImageWriter) writers.next();
//ImageOutputStream ios = ImageIO.createImageOutputStream(os);
//writer.setOutput(ios);
//ImageWriteParam param = writer.getDefaultWriteParam();
//param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//param.setCompressionQuality(fact);
//writer.write(null, new IIOImage(image, null, null), param);
//os.close();ios.close();writer.dispose();}
//
//public  void decompressPNG(File file, File dest,String formatt) throws IOException {
//FileInputStream fis = new FileInputStream(file);
//InputStream is = new GZIPInputStream(new BufferedInputStream(fis));
//BufferedImage bufferedImage;try {
//bufferedImage = ImageIO.read(is);
//int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
//BufferedImage newBufferedImage = new BufferedImage(width,height,type);
//newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
//ImageIO.write(newBufferedImage, formatt, dest);} 
//catch (IOException e) { e.printStackTrace();}}

public  void zip(File dir) throws IOException {
String pardir=dir.getParent();filename=dir.getName();
String inpdir=dir.toString()+"\\com";
String outputZipFile =pardir+"\\_"+filename+".zip";
byte[] buffer = new byte[4096];
try{
File files[]=new File(inpdir).listFiles();
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
FileUtils.deleteDirectory(new File(inpdir));}



public  void unzip(File zipFile) throws IOException{
par=zipFile.getParent();
filename=zipFile.getName(); 
name=filename.substring(0,filename.lastIndexOf('.'));
String comdir=par+"\\"+name+"\\_comp";
new File(comdir).mkdirs();
byte[] buffer = new byte[4096];
ZipInputStream zis=null;
ZipEntry ze=null;
FileOutputStream fos = null;
try{
zis= new ZipInputStream(new FileInputStream(zipFile));
ze= zis.getNextEntry();
while(ze!=null){
String fileName = ze.getName();
File newFile = new File(comdir+"\\"+fileName);
fos = new FileOutputStream(newFile);             
int len;
while ((len = zis.read(buffer)) > 0) {fos.write(buffer, 0, len);}
fos.close();ze = zis.getNextEntry();}}
catch(IOException ex){ex.printStackTrace(); }
finally{fos.close();zis.closeEntry();zis.close();}}


//public void unzip(File zipFile) throws IOException {
//String par=zipFile.getParent();
//String filename=zipFile.getName(); 
//String name=filename.substring(0,filename.lastIndexOf('.'));
//String unzipLocation=	par;
//if (!(Files.exists(Paths.get(unzipLocation)))) {Files.createDirectories(Paths.get(unzipLocation));}
//try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
//ZipEntry entry = zipInputStream.getNextEntry();
//while (entry != null) {
//Path filePath = Paths.get(unzipLocation, entry.getName());
//if (!entry.isDirectory()) {unzipFiles(zipInputStream, filePath);} 
//else {Files.createDirectories(filePath);}
//zipInputStream.closeEntry();
//entry = zipInputStream.getNextEntry();}}}
//public void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {
//try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
//byte[] bytesIn = new byte[1024];
//int read = 0;
//while ((read = zipInputStream.read(bytesIn)) != -1) {bos.write(bytesIn, 0, read);}}}



//public void unzip(File zipFile) {
//String zipFilePath=zipFile.getAbsolutePath();
//par=zipFile.getParent();
//filename=zipFile.getName(); 
//name=filename.substring(0,filename.lastIndexOf('.'));
//String comdir=par+"\\"+name+"\\_comp";
//new File(comdir).mkdirs();
//String destDir=comdir;
//File dir = new File(destDir);
//// create output directory if it doesn't exist
//if(!dir.exists()) dir.mkdirs();
//FileInputStream fis;
////buffer for read and write data to file
//byte[] buffer = new byte[1024];
//try {
//fis = new FileInputStream(zipFilePath);
//ZipInputStream zis = new ZipInputStream(fis);
//ZipEntry ze = zis.getNextEntry();
//while(ze != null){
//String fileName = ze.getName();
//File newFile = new File(destDir + File.separator + fileName);
//System.out.println("Unzipping to "+newFile.getAbsolutePath());
////create directories for sub directories in zip
//new File(newFile.getParent()).mkdirs();
//FileOutputStream fos = new FileOutputStream(newFile);
//int len;
//while ((len = zis.read(buffer)) > 0) {
//fos.write(buffer, 0, len);}
//fos.close();
////close this ZipEntry
//zis.closeEntry();
//ze = zis.getNextEntry();}
////close last ZipEntry
//zis.closeEntry();
//zis.close();
//fis.close();} 
//catch (IOException e) {e.printStackTrace();}}



public  void unzip_(File zipFile){
par=zipFile.getParent();
filename=zipFile.getName(); 
name=filename.substring(0,filename.lastIndexOf('.'));
String comdir=par+"\\_"+name+"\\_comp";
new File(comdir).mkdirs();
byte[] buffer = new byte[4096];try{
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


//****************************************************************************************************************************************************************************************************
//public static void main(String args[]) throws Exception {
//Compression i=new Compression();
//
//
//
//long start=System.currentTimeMillis();
//
//File files[]=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\video").listFiles();
////for(File f:files) {
////String filename=f.getName();
////String name=filename.substring(0,filename.lastIndexOf('.'));
////File inp=f;
////File com=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\video\\"+name+"__.bin");
////i.compressVideo(inp);
////i.decompressVideo(com);}
//
//System.out.println((System.currentTimeMillis()-start)/1000);
//}




public static float factor=0.7f;

public static String ffmpegPath=Resources.getFmpeg();
public static String ffprobePath=Resources.getFprob();

public FFmpegStream audio =null;
public FFmpegStream video = null;


public  void compressVideo(File input) throws IOException {
long start=System.nanoTime();
initProgressWindow();String temp="";
par=input.getParent();
temp=Resources.getTemp();
filename=input.getName(); 
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);String trans="";
com=new File(par+"\\"+name+"__.bin");
dec=new File(par+"\\"+name+"_.mp4");
trans=temp+"\\"+name+"_.h265";
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());

audio = probeResult.getStreams().get(0);
video = probeResult.getStreams().get(1);


if(probeResult.getStreams().get(0).bit_rate>probeResult.getStreams().get(1).bit_rate) {
audio = probeResult.getStreams().get(1);
video = probeResult.getStreams().get(0);}


System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video fact_rate: "+factor);
System.out.println("\nCOMPRESSING VIDEO");
//long ABR=(long)(video.bit_rate*factor);
double CRF=24;if((video.nb_frames/video.duration)>=30) {CRF=26;}
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(trans)
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx265")
.setConstantRateFactor(CRF)
//.setVideoBitRate(ABR)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
double prog=percentage*100;int percent=(int)prog;showProgressWindow(percent);
}});
job.run();



//FileInputStream fis = null;FileOutputStream fos=null;GZIPOutputStream zos = null;try {
//fis = new FileInputStream(new File(trans));fos = new FileOutputStream(com.toString());zos = new GZIPOutputStream(fos);
//IOUtils.copy(fis, zos);} finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(zos);new File(trans).delete();}

DeflateCompressorOutputStream dcos=null;FileInputStream fis=null;try {
fis=new FileInputStream(trans);
dcos = new DeflateCompressorOutputStream(new FileOutputStream(com.toString()));
IOUtils.copy(fis, dcos);}
finally {dcos.close();fis.close();new File(trans).delete();}

closeProgressWindow();
System.out.println(System.nanoTime()-start);

}





public  void decompressVideo(File input) throws IOException {
initProgressWindow_();String temp="";
par=input.getParent();
temp=Resources.getTemp();
filename=input.getName(); 
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
dec=new File(par+"\\"+name+".mp4");String trans= "";
trans=temp+"\\__"+name+".mp4";
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);

//FileInputStream fis=null;InputStream is=null;OutputStream os=null;try {
//fis = new FileInputStream(input);is = new GZIPInputStream(new BufferedInputStream(fis));os = new FileOutputStream(trans);
//IOUtils.copy(is, os);} finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}

DeflateCompressorInputStream dcin=null;FileOutputStream fos=null;try {
dcin = new DeflateCompressorInputStream(new FileInputStream(input));
fos=new FileOutputStream(trans);
IOUtils.copy(dcin, fos);}finally {dcin.close();fos.close();}


FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(trans);
FFmpegStream video = probeResult.getStreams().get(0);
FFmpegStream audio = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("video fact_rate: "+factor);
System.out.println("\nDECOMPRESSING VIDEO");
double framerate =(double)(video.nb_frames/video.duration);
long bitrate=(long)(video.bit_rate);
double CRF=22;
long ABR=(long)(bitrate/factor);
//     if(framerate>=25) {CRF=22;}
//else if(framerate>=23 &&framerate<25) {CRF=24;}
System.out.println("FrameRate:"+ framerate);
System.out.println("CRF:"+ CRF);
System.out.println("BitRate:"+bitrate);
System.out.println("ABR:"+ ABR);

FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(dec.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx264")
.setConstantRateFactor(CRF)
.done();
FFmpegJob job = executor.createJob(builder, new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration = video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
//System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
String processed=FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS);
processed=processed.substring(0,8);

System.out.println(percentage +"..."+ processed);
double prog=percentage*100;int percent=(int)prog;showProgressWindow_(percent);
}});
job.run();new File(trans).delete();closeProgressWindow_();

}



public  void decompressVideo_(File input) throws IOException {
initProgressWindow_();String temp="";
par=input.getParent();
temp=Resources.getTemp();
filename=input.getName(); 
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); String trans="";String out="";
out=par+"\\"+name+"_.mp4";
trans=temp+"\\__"+name+".mp4";
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);


//FileInputStream fis=null;InputStream is=null;OutputStream os=null;try {
//fis =new FileInputStream(input);
//is = new GZIPInputStream(new BufferedInputStream(fis));
//os = new FileOutputStream(trans);IOUtils.copy(is, os);} 
//finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}


DeflateCompressorInputStream dcin=null;FileOutputStream fos=null;try {
dcin = new DeflateCompressorInputStream(new FileInputStream(input));
fos=new FileOutputStream(trans);
IOUtils.copy(dcin, fos);}finally {dcin.close();fos.close();}


FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(trans);
FFmpegStream video = probeResult.getStreams().get(0);
FFmpegStream audio = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("video fact_rate: "+factor);
System.out.println("\nDECOMPRESSING VIDEO");
long bitrate=(long)(video.bit_rate);
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(out)
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx264")
.setVideoBitRate(bitrate)
.done();
FFmpegJob job = executor.createJob(builder, new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration = video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
double prog=percentage*100;int percent=(int)prog;showProgressWindow_(percent);}});
job.run();new File(trans).delete();closeProgressWindow_();}

public  void extractVideo_(File input) throws IOException {

//FileInputStream fis=null;InputStream is=null;OutputStream os=null;try {
//fis = new FileInputStream(input);
//is = new GZIPInputStream(new BufferedInputStream(fis));
//os = new FileOutputStream(output);
//IOUtils.copy(is, os);} 
//finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}
	
par=input.getParent();
filename=input.getName(); 
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
String output=par+"\\"+name+"__.mp4";
DeflateCompressorInputStream dcin=null;FileOutputStream fos=null;try {
dcin = new DeflateCompressorInputStream(new FileInputStream(input));
fos=new FileOutputStream(output);
IOUtils.copy(dcin, fos);}finally {dcin.close();fos.close();}
}

public  void processVideo(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
String par=output.getParent();String filename=output.getName();String out="";
String name=filename.substring(0,filename.lastIndexOf('.'));
out=par+"\\_"+name+"_libcbr7lib264.mp4";
out=par+"\\_"+name+".h265";
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream audio = probeResult.getStreams().get(0);
FFmpegStream video = probeResult.getStreams().get(1);
System.out.format("%nCodec: '%s' ; Duration: %.2fsec ;  Width: %dpx ; Height: %dpx; Bitrate: %dkbps; frames: %.2ffps",
video.codec_name,video.duration,video.width,video.height,video.bit_rate,video.nb_frames/video.duration);
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
long bitrate=(long)(video.bit_rate*factor);
System.out.println("\nCOMPRESSING VIDEO");
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(out)
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx265")
.setVideoBitRate(bitrate)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();}

}

