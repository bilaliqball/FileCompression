package video;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class DifferencesVID {
	



public DifferencesVID() {}

public static void main(String args[]) throws Exception {
DifferencesVID cv=new DifferencesVID();
File f1=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sam\\rabit.jpg");
File f2=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sam\\rabit-.jpg");
cv.compareImage(f1,f2);

}


public void playVideo(File source, File des) {
String ffplay="\\fileCompression\\bin\\ffplay.exe";
try {
@SuppressWarnings("unused")
Process process = new ProcessBuilder(
ffplay,"-i",
source.toString()).start();
}catch (IOException e) { e.printStackTrace();}}

public void overlayVideo(File file1, File file2){
String ffplay="\\fileCompression\\bin\\ffplay.exe";
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="";//overlay, difference, screen
dif="\"movie="+fi1+"[a];"
	+ "movie="+fi2+"[b];"
	+ "[a][b]blend=all_mode=screen:all_opacity=1.0\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
//ProcessBuilder p = new ProcessBuilder(cmd);process=p.start();
ProcessBuilder pb = new ProcessBuilder(cmd)
.inheritIO()
.directory(new File("C:"));
System.out.println(pb.command().toString());
pb.start();

}catch (IOException e) { e.printStackTrace();}}
public void splitVideo(File file1, File file2){
String ffplay="\\fileCompression\\bin\\ffplay.exe";
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="";
dif="\"movie="+fi1+"[a];"
	+ "movie="+fi2+"[b];"
	+ "[a][b]hstack\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
//ProcessBuilder p = new ProcessBuilder(cmd);process=p.start();
ProcessBuilder pb = new ProcessBuilder(cmd)
.inheritIO()
.directory(new File("C:"));
System.out.println(pb.command().toString());
pb.start();
}catch (IOException e) { e.printStackTrace();}}

public void compareVideo(File file1, File file2){
String ffplay="\\fileCompression\\bin\\ffplay.exe";
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="";
dif="\"movie="+fi1+"[a];"
	+ "movie="+fi2+"[b];"
	+ "[a][b]hstack\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
//ProcessBuilder p = new ProcessBuilder(cmd);process=p.start();
ProcessBuilder pb = new ProcessBuilder(cmd).inheritIO().directory(new File("C:"));pb.start();


String ffmpegPath="\\fileCompression\\bin\\ffmpeg.exe";
String ffprobePath="\\fileCompression\\bin\\ffprobe.exe";
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
@SuppressWarnings("unused")
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult1 = ffprobe.probe(file1.toString());
FFmpegProbeResult probeResult2 = ffprobe.probe(file2.toString());

FFmpegStream video1=null;FFmpegStream video2=null;
if(probeResult1.getStreams().get(0).bit_rate>probeResult1.getStreams().get(1).bit_rate) {
video1 = probeResult1.getStreams().get(0);}else {video1 = probeResult1.getStreams().get(1);}
if(probeResult2.getStreams().get(0).bit_rate>probeResult2.getStreams().get(1).bit_rate) {
video2 = probeResult2.getStreams().get(0);}else {video2 = probeResult2.getStreams().get(1);}


System.out.println("video1-	"+video1.bit_rate/1024);
System.out.println("video2-	"+video2.bit_rate/1024);
double diff=((video2.bit_rate/1024)*100)/(video1.bit_rate/1024);
System.out.println(100-diff+"% difference");

}catch (IOException e) { e.printStackTrace();}}

public void differenceVideo(File file1, File file2) throws InterruptedException{
String ffmpeg="\\fileCompression\\bin\\ffmpeg.exe";

String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String dif="\"ssim;[0:v][1:v]psnr\"";

String cmd1[]= {ffmpeg,"-i",fil1,"-i",fil2,"-lavfi",dif,"-f", "null", "-"};
try {
ProcessBuilder pb = new ProcessBuilder(cmd1)
.inheritIO()
.directory(new File("C:"));
System.out.println(pb.command().toString());
pb.start();
}catch (IOException e) { e.printStackTrace();}}
public void splitImage(File file1, File file2){
String ffplay="\\fileCompression\\bin\\ffplay.exe";
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="";
dif="\"movie="+fi1+"[a];"
	+ "movie="+fi2+"[b];"
	+ "[a][b]vstack\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
ProcessBuilder p = new ProcessBuilder(cmd);
p.start();
}catch (IOException e) { e.printStackTrace();}}

public void compareImage(File file1, File file2) throws IOException {
BufferedImage img1=ImageIO.read(file1);
BufferedImage img2=ImageIO.read(file2);
	
int width1 = img1.getWidth(); int THRESHOLD=2;
int width2 = img2.getWidth(); 
int height1 = img1.getHeight();
int height2 = img2.getHeight();
if ((width1 != width2) || (height1 != height2)) {System.err.println("Error: Images dimensions mismatch");System.exit(1);}

int rgb1=0;int rgb2=0; int r1=0;int r2=0;int g1=0;int g2=0;int b1=0;int b2=0;
BufferedImage outImg = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
int diff=0;int diffPix=0;int totalPix=width1*height1;THRESHOLD=(THRESHOLD*3)+1;
for (int i = 0; i < height1; i++) {
for (int j = 0; j < width1; j++) {
rgb1 = img1.getRGB(j, i);
rgb2 = img2.getRGB(j, i);
r1 = (rgb1 >> 16) & 0xff;
g1 = (rgb1 >> 8) & 0xff;
b1 = (rgb1) & 0xff;
r2 = (rgb2 >> 16) & 0xff;
g2 = (rgb2 >> 8) & 0xff;
b2 = (rgb2) & 0xff;
diff += Math.abs(r1 - r2); 
diff += Math.abs(g1 - g2);
diff += Math.abs(b1 - b2);
diff /= 3;
if(diff>THRESHOLD) {
diffPix++;
int rgb=Color.RED.getRGB();
outImg.setRGB(j, i,rgb); }
else if(diff<=THRESHOLD) {outImg.setRGB(j, i,rgb1);}}}

//double total_pixels = width1 * height1*3; 
//double avg_different_pixels = diff / total_pixels; 
//double percentage = (avg_different_pixels / 255) * 100; 
//System.out.println("Difference Percentage-->" + percentage);
System.out.println("different pixels:"+diffPix+" total Pixels: "+ totalPix+ " difference: "+ diffPix*100/totalPix);
String filename=file1.getName();

String name=filename.substring(0,filename.lastIndexOf('.')); 
ImageIO.write(outImg,"jpg",new File(file1.getParentFile()+"\\"+name+"Dif.png"));
System.out.println(((diffPix*100)/(totalPix))+"% Difference");


}


public void ping(File f1,File f2) {
String domainName = "google.com";
String command = "ping -n 3 " + domainName;
StringBuffer output = new StringBuffer();
Process p;
try {
p = Runtime.getRuntime().exec(command);
p.waitFor();
BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
String line = "";		
while ((line = reader.readLine())!= null) {output.append(line + "\n");}} 
catch (Exception e) {e.printStackTrace();}
System.out.println(output.toString());}

public void displayVideo(File file1,File file2) {
String ffplay="\\fileCompression\\bin\\ffmpeg.exe";
String fil1=file1.toString().replaceAll("\\\\", "/");
//String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String cmd[]= {ffplay,"-i",fil1};
try {
ProcessBuilder pb = new ProcessBuilder(cmd)
.inheritIO()
.directory(new File("C:"));
System.out.println(pb.command().toString());
pb.start();}
catch (Exception e) {e.printStackTrace();}}

public void outputVideo(File file1, File file2) throws InterruptedException{
String ffplay="C:\\Users\\bilal.iqbal\\fileCompression\\bin\\ffplay.exe";
String cmd[]={ffplay,"-i",file1.toString()};
try {
ProcessBuilder pb = new ProcessBuilder(cmd);
@SuppressWarnings("unused")
Process p=pb.start();}catch (IOException e) { e.printStackTrace();}}

}
