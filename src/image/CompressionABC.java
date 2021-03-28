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

public class CompressionABC {
public static void main(String args[]) throws Exception {

CompressionABC i=new CompressionABC();

//File image=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.png");
//i.imageInit(image);
//i.compressImage(i.inp);
//i.decompressImage(i.com);

//long start=System.currentTimeMillis();
//File video=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.mp4");i.videoInit(video);
//i.compressVideo(i.inp,i.com);System.out.println("video Compressed");
//i.decompressVideo(i.com,i.dec);System.out.println("video DECompressed");
//i.decompressVideo_(i.com,i.dec);System.out.println("video DECompressed");
//long end=System.currentTimeMillis();long diff=end-start;
//System.out.println("Execution Time: "+diff);


File inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sam\\cat.mp4");
File com=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sam\\cat__.bin");
i.compress(inp);
i.decompress(com);

}


public void compress(File file) throws ImageReadException, IOException {
String IMAGE=".jpg .jpeg .png";
String VIDEO=".mp4 .avi .wmv";
String filename,ext;
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
     if(IMAGE.contains(ext.toLowerCase())){imageInit(file);compressImage(inp);}
else if(VIDEO.contains(ext.toLowerCase())){videoInit(file);compressVideo(inp,com);} }



public void decompress(File file) throws ImageReadException, IOException {
String filename;
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
     if(filename.contains("__.bin")){String out=filename.substring(0,filename.lastIndexOf('_'));decompressVideo(file,new File(file.getParentFile()+"\\"+out+".mp4"));}
else if(filename.contains("_.bin")||filename.contains(".bin")){decompressImage(file);} }

public CompressionABC() {
par=null; filename="";name="";ext="";
inp=null;com=null;dec=null;}

BufferedImage img;
String par; String filename;String name;String ext;
public File inp;
public File com;
public File dec;
long size;
int dim;
int dpi;
double sdr;

public void imageInit(File file) {
if(file.isFile()) {
par=file.getParent();
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
     if(ext.toLowerCase().contains("png")) {com=new File(par+"\\"+name+"_.bin");}
else if(ext.toLowerCase().contains("jpg")){com=new File(par+"\\"+name+".bin");}}
else if(file.isDirectory()) {
par=file.toString();
filename=file.getName();
inp=new File(par+"\\");
com=new File(par+"\\_"+filename+".zip");}}




public FileFilter imgFileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};

public void compressImage(File fi) throws ImageReadException, IOException {
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

public void decompressImage(File fi) throws ImageReadException, IOException{
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


public void decompressImage_(File fi) throws ImageReadException, IOException{
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


//****************************************************************************************************************************************************************************************************
public  static float factor=0.7f;
//public void videoInit(File file) {
//par=file.getParent();
//filename=file.getName();
//ext=filename.substring(filename.lastIndexOf('.'),filename.length());
//name=filename.substring(0,filename.lastIndexOf('.')); 
//inp=new File(par+"\\"+name+ext);
//com=new File(par+"\\"+name+".bin");
//dec=new File(par+"\\"+name+"_.mp4");}

public void videoInit(File file) {
par=file.getParent();
filename=file.getName(); 
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
com=new File(par+"\\"+name+"__.bin");
dec=new File(par+"\\"+name+"_.mp4");}

public  static void processVideo(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
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

public   void compressVideo(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=output.getParent();String filename=output.getName();String trans="";
String name=filename.substring(0,filename.lastIndexOf('.'));
trans=par+"\\_"+name+".mp4";
trans=par+"\\"+name+"_.h265";
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream audio = probeResult.getStreams().get(0);
FFmpegStream video = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video fact_rate: "+factor);
System.out.println("\nCOMPRESSING VIDEO");
//long ABR=(long)(video.bit_rate*factor);
double CRF=24;
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
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();

trans=par+"\\"+name+"_.h265";
FileInputStream fis = null;
FileOutputStream fos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(new File(trans));
fos = new FileOutputStream(output);
zos = new GZIPOutputStream(fos);
IOUtils.copy(fis, zos);} 
finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(zos);new File(trans).delete();}}





public   void decompressVideo(File input, File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=input.getParent();String filename=input.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String trans=par+"\\__"+name+".mp4";
FileInputStream fis=null;
InputStream is=null;
OutputStream os=null;
try {
fis = new FileInputStream(input);
is = new GZIPInputStream(new BufferedInputStream(fis));
os = new FileOutputStream(trans);
IOUtils.copy(is, os);} 
finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}
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
double CRF=0;
long ABR=(long)(bitrate/factor);
     if(framerate>=25) {CRF=22;}
else if(framerate>=23 &&framerate<25) {CRF=24;}
System.out.println("FrameRate:"+ framerate);
System.out.println("CRF:"+ CRF);
System.out.println("BitRate:"+bitrate);
System.out.println("ABR:"+ ABR);

FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx264")
.setConstantRateFactor(CRF)
//.setVideoBitRate(bitrate)
.done();
FFmpegJob job = executor.createJob(builder, new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration = video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();new File(trans).delete();
}

public   void decompressVideo_(File input, File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=input.getParent();String filename=input.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String trans=par+"\\__"+name+".mp4";
output=new File(par+"\\"+name+"__.mp4");
FileInputStream fis=null;
InputStream is=null;
OutputStream os=null;
try {
fis = new FileInputStream(input);
is = new GZIPInputStream(new BufferedInputStream(fis));
os = new FileOutputStream(trans);IOUtils.copy(is, os);} 
finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}
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
//long ABR=(long)(bitrate/factor);
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
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
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();new File(trans).delete();}

//public  static void decompressVideo_(File input, File output) throws IOException {
//input=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.txt");
//FileInputStream fis=null;
//InputStream is=null;
//OutputStream os=null;
//try {
//fis = new FileInputStream(input);
//is = new GZIPInputStream(new BufferedInputStream(fis));
//os = new FileOutputStream(output);
//IOUtils.copy(is, os);} 
//finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}}


}

