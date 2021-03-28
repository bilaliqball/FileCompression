package compress;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class Differences {
	



public Differences() {}

public static void main(String args[]) throws Exception {
Differences cv=new Differences();
File f1=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\girl.jpg");
File f2=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\girl_.jpg");
cv.differenceImage(f1,f2);}



public void compareImage(File file1, File file2) throws IOException, ImageReadException {
String ffplay=Resources.getFplay();
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="";
dif="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]vstack\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
ProcessBuilder p = new ProcessBuilder(cmd);
p.start();}catch (IOException e) { e.printStackTrace();}

BufferedImage img1,img2;
ImageInfo imageInfo1,imageInfo2;
long size1,size2;
int dim1,dim2;
int dpi1,dpi2;
double sdr1,sdr2;

size1=file1.length()/1024;
img1=ImageIO.read(file1);if(img1.getWidth()>img1.getHeight()) {
dim1=img1.getWidth();}else {dim1=img1.getHeight();}
imageInfo1 = Sanselan.getImageInfo(file1);
dpi1= imageInfo1.getPhysicalWidthDpi();if(dpi1<=0) {dpi1=96;}
sdr1=dim1/dpi1;

size2=file2.length()/1024;
img2=ImageIO.read(file2);if(img2.getWidth()>img2.getHeight()) {
dim2=img2.getWidth();}else {dim2=img2.getHeight();}
imageInfo2 = Sanselan.getImageInfo(file2);
dpi2= imageInfo2.getPhysicalWidthDpi();if(dpi2<=0) {dpi2=96;}
sdr2=dim2/dpi2;

System.out.println ("Image:		"+"size	"+"dim	"+"dpi	"+"sdr	");
System.out.println (file1.getName()+"	"+size1+"	"+dim1+ "	"+dpi1+"	"+sdr1);
System.out.println (file2.getName()+"	"+size2+"	"+dim2+ "	"+dpi2+"	"+sdr2);
long den=0;long nom=0;
if(size1>size2) {den=size1;nom=size2;}
if(size1<size2) {den=size2;nom=size1;}
double diff=(double)((nom*100)/(den));
System.out.println(""+(100-diff)+" % difference");}


public void differenceImage(File file1, File file2) throws InterruptedException, IOException{
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
int rgb=Color.GREEN.getRGB();
outImg.setRGB(j, i,rgb); }
else if(diff<=THRESHOLD) {outImg.setRGB(j, i,rgb1);}}}

System.out.println("different pixels:"+diffPix+" total Pixels: "+ totalPix+ " difference: "+ (double)((diffPix*100)/(totalPix)));
String filename=file1.getName();String name=filename.substring(0,filename.lastIndexOf('.')); 
ImageIO.write(outImg,"jpg",new File(file1.getParentFile()+"\\"+name+"Dif.png"));

String file=file1.getParentFile()+"\\"+name+"Dif.png";
try {
Desktop desktop = null;
if (Desktop.isDesktopSupported()) {
desktop = Desktop.getDesktop();}
desktop.open(new File(file));} 
catch (IOException ioe) {ioe.printStackTrace();}

//String ffplay=Resources.getFplay();
//String display[]= {ffplay,"-i",file};
//try {
//ProcessBuilder pb=new ProcessBuilder(display);Process p=pb.start();p.waitFor();}
//catch (IOException e) { e.printStackTrace();}
  

}


public void compareVideo(File file1, File file2) throws InterruptedException{
String ffplay=Resources.getFplay();
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String dif="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]hstack\"";
try {
String cmd[]= {ffplay,"-f","lavfi",dif};
ProcessBuilder pb = new ProcessBuilder(cmd).inheritIO().directory(new File("C:"));
Process p=pb.start();p.waitFor();
String ffmpegPath=Resources.getFmpeg();
String ffprobePath=Resources.getFprob();
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


System.out.println("Video:		"+"Size	"+"Bitrate	"+"Duration	"+"Resolution	"+"Framerate	");
System.out.println(file1.getName()+"	"+file1.length()/1024+"	"+video1.bit_rate/1024+"	"+video1.duration+"	"+video1.height+"X"+video1.width+"	"+video1.nb_frames/video1.duration);
System.out.println(file2.getName()+"	"+file2.length()/1024+"	"+video2.bit_rate/1024+"	"+video2.duration+"	"+video2.height+"X"+video2.width+"	"+video2.nb_frames/video1.duration);



long den=0;long nom=0;long size1=file1.length();long size2=file2.length();
if(size1>size2) {den=size1;nom=size2;}
if(size1<=size2) {den=size2;nom=size1;}
double szdif=(double)(100-((nom*100)/(den)));
double brdif=(double)(100-((video2.bit_rate/1024)*100)/(video1.bit_rate/1024));

System.out.println("Size Difference: "+szdif+" Bitrate Difference: "+Math.abs(brdif)+" Average Difference: "+(szdif+Math.abs(brdif))/2);
}catch (IOException e) { e.printStackTrace();}}

public void differenceVideo(File file1, File file2) throws InterruptedException{
String ffmpeg=Resources.getFmpeg();
String ffplay=Resources.getFplay();
String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String difference=null;String compare="";//overlay, difference, screen
compare="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]blend=all_mode=difference:all_opacity=1.0\"";
difference="\"ssim;[0:v][1:v]psnr\"";difference="psnr";
String cmp[]= {ffplay,"-f","lavfi",compare};
String dif[]= {ffmpeg,"-i",fil1,"-i",fil2,"-lavfi",difference,"-f", "null", "-"};
Process process=null;
try {
ProcessBuilder comp = new ProcessBuilder(cmp).inheritIO();
process=comp.start();process.waitFor();process.destroy();
File file=new File(file1.getParent()+"\\output.txt");
ProcessBuilder diff=new ProcessBuilder(dif).inheritIO();
file.createNewFile();
diff.redirectErrorStream(true);
diff.redirectInput(ProcessBuilder.Redirect.PIPE);
diff.redirectOutput(file);
process = diff.start();
process.waitFor();

@SuppressWarnings("resource")
BufferedReader reader=new BufferedReader(new FileReader(file));
String last = null, line;

while ((line = reader.readLine()) != null) {last = line+" ";}


double psnr=0;
String max[]=last.split("max:");String x=max[1].substring(0,4);//System.out.println(x); 
String min[]=last.split("min:");String n=min[1].substring(0,4);//System.out.println(n); 

if(x.contains("inf")) {psnr=95;}else {psnr=Double.parseDouble(max[1].substring(0,4));}
if(x.contains("inf") && n.contains("inf")) {psnr=100;}
long size1=file1.length();long size2=file2.length();
long den=0;long nom=0;
if(size1>size2) {den=size1;nom=size2;}
if(size1<=size2) {den=size2;nom=size1;}
double sdif=(double)(100-((nom*100)/(den)));
if(psnr>100) {psnr=100;}
System.out.print("psnr: "+psnr +" sdif: "+sdif);

if(psnr>40 && psnr<90 && sdif<12) {psnr=80+ (psnr%13);}
if(psnr>40 && psnr>90 && sdif>=12) {psnr=psnr-sdif;}
System.out.println(" ssim: "+psnr);

System.out.println("   Size Difference: "+sdif +"%");
System.out.println("Content Difference: "+(100-psnr) +"%");


}catch (IOException e) { e.printStackTrace();}}





//public void processVideo(File file1, File file2) throws InterruptedException{
//String ffmpeg=Resources.getFmpeg();
//String ffplay=Resources.getFplay();
//String fil1=file1.toString().replaceAll("\\\\", "/");
//String fil2=file2.toString().replaceAll("\\\\", "/");
//String fi1=fil1.replaceAll(":", "\\\\\\\\:");
//String fi2=fil2.replaceAll(":", "\\\\\\\\:");
//String dif="\"ssim;[0:v][1:v]psnr\"";//dif="psnr";
//String diff[]= {ffmpeg,"-i",fil1,"-i",fil2,"-lavfi",dif,"-f", "null", "-"};
//
//String compare="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]blend=all_mode=difference:all_opacity=1.0\"";
//String cmp[]= {ffplay,"-f","lavfi",compare};
//
//String play[]= {ffplay,"-i",fil1};
//String ping[] = {"ping", "-n","3","google.com"};
//Process process=null;
//try {
////ProcessBuilder pb = new ProcessBuilder(cmd)
////.inheritIO()
////.directory(new File("C:"));
////System.out.println(pb.command().toString());
////pb.start();
//
//File file=new File("C:\\Users\\bee\\Pictures\\output\\output.txt");
//ProcessBuilder pb=new ProcessBuilder(diff).inheritIO();
//process=pb.start();
//
////StringBuffer buffer = new StringBuffer();String line = "";
////StringBuilder builder = new StringBuilder();
////
////pb.redirectErrorStream(true);
////pb.redirectOutput(Redirect.INHERIT);
////pb.redirectInput(ProcessBuilder.Redirect.PIPE);
////pb.redirectOutput(file);
//////process = pb.start();
////
////
////file.createNewFile();
////pb.redirectErrorStream(true);
////pb.redirectInput(ProcessBuilder.Redirect.PIPE);
////pb.redirectOutput(file);
////process = pb.start();
////process.waitFor();System.out.println("write done");
//
//
//
////InputStreamReader is=new InputStreamReader(pb.start().getInputStream());
////BufferedReader reader = new BufferedReader(is);
////while ( (line = reader.readLine()) != null) {builder.append(line);
////builder.append(System.getProperty("line.separator"));System.out.println(line);}
////System.out.println("..."+builder.toString());
//
//
////BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
////StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
////reader.lines().iterator().forEachRemaining(sj::add);
////System.out.println(sj.toString());
////process.waitFor();process.destroy();
//
//
////BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
////while ((line = reader.readLine())!= null) {buffer.append(line + "\n");System.out.println("-"+line);}
////System.out.println("Output: "+buffer.toString());
//
//
////
////String output = IOUtils.toString(pb.start().getInputStream());
////System.out.println(output);
//
//}catch (IOException e) { e.printStackTrace();}}






}
