package compress;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import org.apache.commons.io.IOUtils;



public class Normalize {
public int found=0; public int count=0;
public long total=0;public final long avail=25*1024*1024;
String par; String filename;String name;String ext;
public File inp;
public File com;
public File dec;
public ArrayList<file> fileList;


public JWindow window;
public JLabel label;
public void initWindow() {window=new JWindow();
String ic=Resources.getIcon("loading1.gif");
ImageIcon icon=	new ImageIcon(ic);
window.getContentPane().add(new JLabel("Processing", icon, SwingConstants.CENTER));
window.setBounds(500, 200, 200, 200);}
public void showWindow() {
window.setVisible(true);
window.setAlwaysOnTop(true);
//window.toFront();
//window.requestFocus();
//window.setAlwaysOnTop(false);
window.setLocationRelativeTo(null);}
public void closeWindow() {window.setVisible(false);window.dispose();}

public Normalize() {}

public class file{
public file() {}
public String name="";
public long size=0;
public void setName(String n) {this.name=n;} public String getName() {return this.name;}
public void setSize(long s) {this.size=s;} public long getSize() {return this.size;}
public file(String n, long s) {this.name=n;this.size=s;}
public  Comparator<file> sizeComparator = new Comparator<file>() {
public int compare(file f1, file f2) {
long file1 = f1.getSize();
long file2 = f2.getSize();
if(file1<file2) {return -1;}
if(file2<file1) {return 1;}
else {return 0;}}};}


public static void main(String srgs[]) throws IOException {
Normalize s=new Normalize();
s.normalizeFiles("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures");}



public FileFilter imgFileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg jpeg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};


public  String normalizeIMG(File file, File dest,float fact) throws IOException {
BufferedImage image = ImageIO.read(file);int dim=0;
if(image.getWidth()>image.getHeight()) {dim=image.getWidth();}else {dim=image.getHeight();}
     if(dim>=240  && dim<960)  {fact=0.85f;}     
else if(dim>=960  && dim<1280) {fact=0.80f;}
else if(dim>=1280 && dim<2500) {fact=0.70f;}
else if(dim>=2500 && dim<4000) {fact=0.60f;}
else if(dim>=4000 && dim<5000) {fact=0.50f;}
else if(dim>=5000 && dim<8000) {fact=0.40f;}
ByteArrayOutputStream combaos=new ByteArrayOutputStream();
Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
InputStream ins= new ByteArrayInputStream(combaos.toByteArray());
if(file.getName().toLowerCase().contains(".png")) {
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(ins);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, "jpg", dest);} 
catch (IOException e) { e.printStackTrace();}}
else if(file.getName().toLowerCase().contains(".jpg")) { 
OutputStream os=new FileOutputStream(dest);
IOUtils.copy(ins, os);}
double gain=(double)file.length()/dest.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";
return txt;}





public void normalizeFiles(String di) throws IOException {
fileList=new ArrayList<file>() ;initWindow();
File dir=new File(di);
String par=dir.toString();
File src=null, des=null;
filename=dir.getName();
File files[]=dir.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
File comdir=new File(dir.getParent()+"\\"+filename+"--");comdir.mkdir();
for(File file:files) {
	showWindow();
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
src=new File(par+"\\"+name+ext);
des=new File(comdir+"\\"+name+"-"+ext);
try {
@SuppressWarnings("unused")
String res=normalizeIMG(src,des,1.0f);System.out.println(""+name+" compressed.");
file f=new file(des.toString(),des.length());
fileList.add(f);} 
catch (IOException e1) {e1.printStackTrace();}}
Collections.sort(fileList,new file().sizeComparator);
ArrayList<File> imglist=new ArrayList<>();
for(file fi:fileList) {imglist.add(new File(fi.name));}
bucketizeFiles(imglist);

//if(comdir.exists()==true) {FileUtils.deleteQuietly(comdir);}
//if(comdir.exists()==true) {FileUtils.deleteQuietly(comdir);}
//if(comdir.exists()==true) {FileUtils.deleteQuietly(comdir);}
closeWindow();}


public ArrayList<File> elementfileList;
public ArrayList<File> bucketfileList;
public ArrayList<File> zipfileList;
public void bucketizeFiles(ArrayList<File> fl) throws IOException {
elementfileList=fl;
bucketfileList=new ArrayList<File>();
int i;for(i=0;i<fl.size();) {
if(total<avail && (total+fl.get(i).length())<avail ) {fillBucket(i);i++;} 
else {zipBucket(i);}}
zipBucket(i);}
public void fillBucket(int i) throws IOException {
long size=0L;
File file=elementfileList.get(i);size=file.length();
total+=size;bucketfileList.add(file);}
public void zipBucket(int i) throws IOException {
zipFiles(bucketfileList,i);
bucketfileList=new ArrayList<File>();total=0L;}
public void zipFiles(ArrayList<File> res,int i) throws IOException{
showWindow();
ArrayList<File> fileList=res;String dir=fileList.get(0).getParent();
String zipOutputFile = dir+i+".zip";
byte[] buffer = new byte[1024];  	
try{
FileOutputStream fos = new FileOutputStream(zipOutputFile);
ZipOutputStream zos = new ZipOutputStream(fos);
for(File file : fileList){
ZipEntry ze= new ZipEntry(file.getName());
zos.putNextEntry(ze);
FileInputStream in = new FileInputStream(file.toString());
int len;
while ((len = in.read(buffer)) > 0) {zos.write(buffer, 0, len);}
in.close();}
zos.closeEntry();
zos.close();}
catch(IOException ex){ex.printStackTrace();}}


}
