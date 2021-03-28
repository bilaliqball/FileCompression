package compress;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.IOUtils;

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

public class OperationVID {
	

public  static float factor=0.7f;
public OperationVID(int fact) {}


public static void main(String args[]) throws Exception {
OperationVID cv=new OperationVID(5);long start=System.currentTimeMillis();
	File fi=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bigbuck.mp4");
String par=null; String filename="";String name="";String ext="";
File inp=null;File com=null;File dec=null;

//if(fi.isDirectory()) {par=fi.toString();
//FileFilter fileFilter = new FileFilter() {
//public boolean accept(File file) {
//String EXT=".mp4";
//String ext=""; boolean find=false;
//if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
//if(EXT.contains(ext)) {find= true;}}
//return find;}};
//File files[]=fi.listFiles(fileFilter);System.out.println(files.length +" files found!");
//for(File file:files) {
//filename=file.getName();
//ext=filename.substring(filename.lastIndexOf('.'),filename.length());
//name=filename.substring(0,filename.lastIndexOf('.')); 
//inp=new File(par+"\\"+name+".mp4");
//com=new File(par+"\\"+name+".bin");
//dec=new File(par+"\\"+name+"_.mp4");
//cv.compressVideo(inp,com);System.out.println("video Compressed");
//cv.decompressVideo(com,dec);System.out.println("video DECompressed");}}

par=fi.getParent();
filename=fi.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
inp=new File(par+"\\"+name+ext);
com=new File(par+"\\"+name+".bin");
dec=new File(par+"\\"+name+"_.mp4");


//vidtoIMG(inp,com);System.out.println("VIDEO SPLITTED");
//compressImages();System.out.println("iMAGES COMPRESSED");
//imgtoVID(inp,dec);System.out.println("VIDEO CONSTRUCTED");


cv.compressVideo(inp,com);System.out.println("video Compressed");
cv.decompressVideo(com,dec);System.out.println("video DECompressed");
long end=System.currentTimeMillis();long diff=end-start;
System.out.println("Execution Time: "+diff/1000 +"sec");
}

public static int i=0;
public static void compressPNG(File input, File compress) throws IOException {
BufferedImage image = ImageIO.read(input);
OutputStream os =new FileOutputStream(compress);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(0.30f);i=i+1;;System.out.println("Compressing Frame"+i);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();}
	
public  static void vidtoIMG(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=output.getParent();String filename=output.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String out=par+"\\_"+name+".mp4";
String dir=par+"\\original\\";
new File(dir).mkdir();
out=dir+name+"%0d.png";
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream audio = probeResult.getStreams().get(0);
FFmpegStream video = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("\nSPLITING VIDEO");
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(out)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();}

public  static void extractFrames(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=output.getParent();String filename=output.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String out=par+"\\_"+name+".mp4";
String dir=par+"\\compressed\\";
new File(dir).mkdir();
out=dir+name+"%0d.jpg";
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream audio = probeResult.getStreams().get(0);
FFmpegStream video = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("\nSPLITING VIDEO");
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(out)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();}



public static void compressImages() throws IOException {
File dir=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\original");
File files[]=dir.listFiles();System.out.println(files.length +" files found!");
File comdir=new File(dir.getParent()+"\\compressed");comdir.mkdir();
File com=null;
String name=null; String filename="";

for(File file:files) {
filename=file.getName();
name=filename.substring(0,filename.lastIndexOf('.')); 
com=new File(comdir.toString()+"\\"+name+".jpg");
compressPNG(file,com);}}


public  static void imgtoVID(File input,File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");

String filename=input.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String inp="C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\compressed\\"+name+"%0d.jpg";
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream video = probeResult.getStreams().get(1);
System.out.println("\nCONSTRUCTING VIDEO");
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(inp)
.overrideOutputFiles(true)
.addOutput(output.toString())
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();}




public  static void info(File input) throws IOException {
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
FFmpegStream audio = probeResult.getStreams().get(1);
FFmpegStream video = probeResult.getStreams().get(0);
System.out.format("%nCodec: '%s' ; Duration: %.2fsec ;  Width: %dpx ; Height: %dpx; Bitrate: %dkbps; frames: %.2ffps\n",
video.codec_name,video.duration,video.width,video.height,video.bit_rate,video.nb_frames/video.duration);
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);}	

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
String par=output.getParent();String filename=output.getName();String out="";
String name=filename.substring(0,filename.lastIndexOf('.'));
out=par+"\\_"+name+".mp4";
out=par+"\\"+name+"_.h265";
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
.addOutput(out)
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
out=par+"\\"+name+"_.h265";String inp="";
inp=par+"\\"+name+"_.txt";
FileInputStream fis = null;
FileOutputStream fos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(new File(out));
fos = new FileOutputStream(new File(inp));
zos = new GZIPOutputStream(fos);
IOUtils.copy(fis, zos);} 
finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(zos);new File(out).delete();}}





public   void decompressVideo(File input, File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=input.getParent();String filename=input.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String out=par+"\\__"+name+".mp4";
input=new File(par+"\\"+name+"_.txt");
FileInputStream fis=null;
InputStream is=null;
OutputStream os=null;
try {
fis = new FileInputStream(input);
is = new GZIPInputStream(new BufferedInputStream(fis));
os = new FileOutputStream(out);
IOUtils.copy(is, os);} 
finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(out);
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
job.run();new File(out).delete();
}

public   void decompressVideo_(File input, File output) throws IOException {
FFmpeg ffmpeg = new FFmpeg("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe");
FFprobe ffprobe = new FFprobe("C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe");
String par=input.getParent();String filename=input.getName();
String name=filename.substring(0,filename.lastIndexOf('.'));
String out=par+"\\__"+name+".mp4";
input=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.txt");
output=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file__.mp4");
FileInputStream fis=null;
InputStream is=null;
OutputStream os=null;
try {
fis = new FileInputStream(input);
is = new GZIPInputStream(new BufferedInputStream(fis));
os = new FileOutputStream(out);
IOUtils.copy(is, os);} 

finally {IOUtils.closeQuietly(fis);IOUtils.closeQuietly(is);IOUtils.closeQuietly(os);}
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(out);
FFmpegStream video = probeResult.getStreams().get(0);
FFmpegStream audio = probeResult.getStreams().get(1);
System.out.println("video resolution: "+ video.height);
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);long bitrate=(long)(video.bit_rate/(factor));
System.out.println("video fact_rate: "+factor);
System.out.println("video bit_rate: "+bitrate);

System.out.format("%nCodec: '%s' ; Duration: %.2fsec ;  Width: %dpx ; Height: %dpx; Bitrate: %dkbps; frames: %.2ffps",
video.codec_name,video.duration,video.width,video.height,video.bit_rate/1024,video.nb_frames/video.duration);
System.out.println("\nDECOMPRESSING VIDEO");
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx264")

//.setVideoBitRate(bitrate)
.done();
FFmpegJob job = executor.createJob(builder, new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration = video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));}});
job.run();//new File(out).delete();
}

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
