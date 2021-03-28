package compress;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.PluginTransfer;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Comparison {
public Shell shell;
public Label infoLable;
public Display display;


File original=null;
File decompress=null;

public void setOriginal(String org) {original=new File(org);}
public void setDecompress(String dec) {decompress=new File(dec);}

public void Compare() {
try {open();} catch (Exception e) {e.printStackTrace();}}

public static void main(String args[]) {Comparison comparison=new Comparison();comparison.Compare();}


public void compareImage(File file1, File file2) throws IOException, ImageReadException {
//String ffplay=Resources.getFplay();
//String fil1=file1.toString().replaceAll("\\\\", "/");
//String fil2=file2.toString().replaceAll("\\\\", "/");
//String fi1=fil1.replaceAll(":", "\\\\\\\\:");
//String fi2=fil2.replaceAll(":", "\\\\\\\\:");
//String dif="";
//dif="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]vstack\"";
//try {
//String cmd[]= {ffplay,"-f","lavfi",dif};
//ProcessBuilder p = new ProcessBuilder(cmd);
//p.start();}catch (IOException e) { e.printStackTrace();}


BufferedImage img1,img2;
int width1,width2;
int height1, height2;
ImageInfo info1,info2;
long size1,size2;
int dim1,dim2;
int dpi1,dpi2;
int bitdepth1, bitdepth2;

size1=file1.length()/1024;
img1=ImageIO.read(file1);
width1 = img1.getWidth();
height1 = img1.getHeight();
if(img1.getWidth()>img1.getHeight()) {
dim1=img1.getWidth();}else {dim1=img1.getHeight();}
info1 = Sanselan.getImageInfo(file1);
dpi1= info1.getPhysicalWidthDpi();if(dpi1<=0) {dpi1=96;}
bitdepth1=info1.getBitsPerPixel();

size2=file2.length()/1024;
img2=ImageIO.read(file2);
width2 = img2.getWidth();
height2 = img2.getHeight();

if(img2.getWidth()>img2.getHeight()) {
dim2=img2.getWidth();}else {dim2=img2.getHeight();}
info2 = Sanselan.getImageInfo(file2);
dpi2= info2.getPhysicalWidthDpi();if(dpi2<=0) {dpi2=96;}
bitdepth2=info2.getBitsPerPixel();


long den=1;long nom=1;
if(size1>size2) {den=size1;nom=size2;}
if(size1<size2) {den=size2;nom=size1;}
double difference=(double)((nom*100)/(den));
String name1=String.format("%1$"+16+ "s", file1.getName());
String name2=String.format("%1$"+16+ "s", file2.getName());
String name0=String.format("%1$"+16+ "s", "Image:");

String res="";
res+=name0+"	"+"Size	"+"Dim	"+"Resolution	"+"BitDepth \n";
res+=name1+"	"+size1+"	"+dim1+ "	"+width1+"x"+height1+"		"+bitdepth1+"\n";
res+=name2+"	"+size2+"	"+dim2+ "	"+width2+"x"+height2+"		"+bitdepth2+"\n";

int THRESHOLD=3;if(file1.getName().toLowerCase().contains(".png")) {THRESHOLD=2*THRESHOLD;}
if ((width1 != width2) || (height1 != height2)) {System.err.println("Error: Images dimensions mismatch");System.exit(1);}
int rgb1=0;int rgb2=0; int r1=0;int r2=0;int g1=0;int g2=0;int b1=0;int b2=0;
BufferedImage outputImage =null;

if(file1.getName().toLowerCase().contains(".jpg")|| bitdepth1==24) {outputImage=new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);}
if(file1.getName().toLowerCase().contains(".png")&& bitdepth1==32) {outputImage=new BufferedImage(width1, height1, BufferedImage.TYPE_INT_ARGB);}


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
outputImage.setRGB(j, i,rgb); }
else if(diff<=THRESHOLD) {outputImage.setRGB(j, i,rgb1);}}}


res+="Different pixels: "+diffPix+" Total Pixels: "+ totalPix+"\n";
res+="Pixel difference: "+ (double)((diffPix*100)/(totalPix))+"% Size difference: "+(100-difference)+"% Average Difference: "+((double)((diffPix*100)/(totalPix))+(100-difference))/2 +"%";
System.out.println(res);infoLable.setText(res);

String filename=file1.getName();String name=filename.substring(0,filename.lastIndexOf('.')); 

if(filename.toLowerCase().contains("jpg")|| bitdepth1==24) {ImageIO.write(outputImage,"jpg",new File(file1.getParentFile()+"\\"+name+"Dif.png"));}

//else if(filename.toLowerCase().contains("png")) {
//outputImage.createGraphics().drawImage(outputImage, 0, 0, Color.WHITE, null);
//ImageIO.write(outputImage, "png", new File(file1.getParentFile()+"\\"+name+"Dif.png"));}

else if(filename.toLowerCase().contains("png")&& bitdepth1==32) {
try {
BufferedImage img = outputImage;
int w = img.getWidth(null);
int h = img.getHeight(null);
BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
Graphics g = bi.getGraphics();
g.drawImage(img, 0, 0, null);
float[] scales = { 1f, 1f, 1f, 0.50f };//tarnsparency
float[] offsets = new float[4];
RescaleOp rop = new RescaleOp(scales, offsets, null);
bi.createGraphics().drawImage(bi, rop, 0, 0);
ImageIO.write(bi, "png",new File(file1.getParentFile()+"\\"+name+"Dif.png"));}
catch (IOException e) { e.printStackTrace();}}



String file=file1.getParentFile()+"\\"+name+"Dif.png";
try {
Desktop desktop = null;
if (Desktop.isDesktopSupported()) {
desktop = Desktop.getDesktop();}
desktop.open(new File(file));} 
catch (IOException ioe) {ioe.printStackTrace();}}






@SuppressWarnings("unused")
public void compareVideo(File file1, File file2) throws InterruptedException, IOException{
String ffmpegPath=Resources.getFmpeg();
String ffprobePath=Resources.getFprob();
String ffplayPath=Resources.getFplay();

String fil1=file1.toString().replaceAll("\\\\", "/");
String fil2=file2.toString().replaceAll("\\\\", "/");
String fi1=fil1.replaceAll(":", "\\\\\\\\:");
String fi2=fil2.replaceAll(":", "\\\\\\\\:");
String split=null; String difference=null;String compare="";//overlay, difference, screen

split="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]hstack\"";
compare="\"movie="+fi1+"[a];"+"movie="+fi2+"[b];"+"[a][b]blend=all_mode=difference:all_opacity=1.0\"";
difference="\"ssim;[0:v][1:v]psnr\"";difference="psnr";

String spt[]={ffplayPath,"-f","lavfi",split};
//String cmp[]={ffplayPath,"-f","lavfi",compare};
//String dif[]={ffmpegPath,"-i",fil1,"-i",fil2,"-lavfi",difference,"-f", "null", "-"};

Process process=null;
try {
ProcessBuilder comp = new ProcessBuilder(spt).inheritIO();
process=comp.start();process.waitFor();process.destroy();
}catch (IOException e) { e.printStackTrace();}


try {
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);

FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult1 = ffprobe.probe(file1.toString());
FFmpegProbeResult probeResult2 = ffprobe.probe(file2.toString());

FFmpegStream video1=null;FFmpegStream video2=null;
if(probeResult1.getStreams().get(0).bit_rate>probeResult1.getStreams().get(1).bit_rate) {
video1 = probeResult1.getStreams().get(0);}else {video1 = probeResult1.getStreams().get(1);}
if(probeResult2.getStreams().get(0).bit_rate>probeResult2.getStreams().get(1).bit_rate) {
video2 = probeResult2.getStreams().get(0);}else {video2 = probeResult2.getStreams().get(1);}

String name1=String.format("%1$"+16+ "s", file1.getName());
String name2=String.format("%1$"+16+ "s", file2.getName());
String name0=String.format("%1$"+16+ "s", "Video:");
String res="";
res+=name0+"	"+"Size	"+"Bitrate	"+"Duration	"+"Resolution	"+"Framerate	\n";
res+=name1+"	"+file1.length()/1024+"	"+video1.bit_rate/1024+"	"+video1.duration+"	"+video1.height+"x"+video1.width+"	"+video1.nb_frames/video1.duration+"\n";
res+=name2+"	"+file2.length()/1024+"	"+video2.bit_rate/1024+"	"+video2.duration+"	"+video2.height+"x"+video2.width+"	"+video2.nb_frames/video1.duration+"\n";



long den=0;long nom=0;long size1=file1.length();long size2=file2.length();
if(size1>size2) {den=size1;nom=size2;}
if(size1<=size2) {den=size2;nom=size1;}
double szdif=(double)(100-((nom*100)/(den)));
double brdif=(double)(100-((video2.bit_rate/1024)*100)/(video1.bit_rate/1024));

res+="Size Difference: "+szdif+"%  Bitrate Difference: "+Math.abs(brdif)+"%  Average Difference: "+(szdif+Math.abs(brdif))/2+"%";
System.out.println(res);infoLable.setText(res);

}catch (IOException e) { e.printStackTrace();}


//try {
//File file=new File(file1.getParent()+"\\output.txt");
//ProcessBuilder diff=new ProcessBuilder(dif).inheritIO();
//file.createNewFile();
//diff.redirectErrorStream(true);
//diff.redirectInput(ProcessBuilder.Redirect.PIPE);
//diff.redirectOutput(file);
//process = diff.start();
//process.waitFor();
//@SuppressWarnings("resource")
//BufferedReader reader=new BufferedReader(new FileReader(file));
//String last=null,line;
//while ((line=reader.readLine()) != null) {last = line+" ";}
//double psnr=0;
//String max[]=last.split("max:");String x=max[1].substring(0,4);//System.out.println(x); 
//String min[]=last.split("min:");String n=min[1].substring(0,4);//System.out.println(n); 
//if(x.contains("inf")) {psnr=95;}else {psnr=Double.parseDouble(max[1].substring(0,4));}
//if(x.contains("inf") && n.contains("inf")) {psnr=100;}
//long size1=file1.length();long size2=file2.length();
//long den=0;long nom=0;
//if(size1>size2) {den=size1;nom=size2;}
//if(size1<=size2) {den=size2;nom=size1;}
//double sdif=(double)(100-((nom*100)/(den)));
//if(psnr>100) {psnr=100;}
//System.out.println("psnr: "+psnr +" sdif: "+sdif);
//if(psnr>40 && psnr<90 && sdif<12) {psnr=80+ (psnr%13);}
//if(psnr>40 && psnr>90 && sdif>=12) {psnr=psnr-sdif;}
//System.out.println(" ssim: "+psnr);
//System.out.println("   Size Difference: "+sdif +"%");
//System.out.println("Content Difference: "+(100-psnr) +"%");
//}catch (IOException e) { e.printStackTrace();}


}



public void open() {
display= Display.getDefault();
createContents();
shell.open();
shell.layout();
while (!shell.isDisposed()) {
if (!display.readAndDispatch()) {display.sleep();}}}

protected void createContents() {
shell = new Shell();
shell.setSize(582, 540);
shell.setText("SWT Application");
shell.setLayout(null);

Label titleLabel = new Label(shell, SWT.NONE);
titleLabel.setBounds(60, 21, 370, 15);
titleLabel.setAlignment(SWT.CENTER);
titleLabel.setText("FILE COMPARISON");

infoLable = new Label(shell, SWT.NONE);
infoLable.setBounds(15, 341, 396, 130);
infoLable.setText("...");

final Text textArea = new Text(shell, SWT.BORDER);
textArea.setBounds(15, 68, 396, 21);
textArea.setText("Add URL or Drop File Here");
textArea.setToolTipText("Add URL or Drop File Here");
org.eclipse.swt.dnd.DropTarget dt  = new org.eclipse.swt.dnd.DropTarget(textArea, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
dt.setTransfer(new Transfer[] { FileTransfer.getInstance(), PluginTransfer.getInstance() });
dt.addDropListener(new DropTargetAdapter() {
public void drop(DropTargetEvent event) {
String fileList[] = null;String file="";
FileTransfer ft = FileTransfer.getInstance();
if (ft.isSupportedType(event.currentDataType)) {fileList = (String[]) event.data;}
file=fileList[0];setOriginal(file);
System.out.println(file);
textArea.setText(file);
infoLable.setText("FILE SELECTED " +new File(file).getName());}});


final Text textArea2 = new Text(shell, SWT.BORDER);
textArea2.setBounds(15, 100, 396, 21);
textArea2.setText("Add URL or Drop File Here");
textArea2.setToolTipText("Add URL or Drop File Here");
org.eclipse.swt.dnd.DropTarget dt2  = new org.eclipse.swt.dnd.DropTarget(textArea2, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
dt2.setTransfer(new Transfer[] { FileTransfer.getInstance(), PluginTransfer.getInstance() });
Button compareButton = new Button(shell, SWT.NONE);
compareButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
String filename1=original.getName();
String filename2=decompress.getName();

String ext1=filename1.substring(filename1.lastIndexOf('.'),filename1.length()).toLowerCase();
String ext2=filename2.substring(filename2.lastIndexOf('.'),filename2.length()).toLowerCase();
String IMAGE=".jpg .jpeg .png .bmp";
String VIDEO=".mp4 .avi .wmv";



if(ext1.equals(ext2)) {
infoLable.setText("Comparing Files ...");
//Differences cv=new Differences();
if(IMAGE.contains(ext1)) {try {compareImage(original, decompress);} catch (IOException e1) {e1.printStackTrace();} catch (ImageReadException e1) {e1.printStackTrace();}}
if(VIDEO.contains(ext1)) {try {compareVideo(original, decompress);} catch (InterruptedException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}}}
else {infoLable.setText("Cant Compare! Both files of different extentions");}}});
compareButton.setBounds(423, 68, 133, 53);
compareButton.setText("COMPARE");
dt2.addDropListener(new DropTargetAdapter() {
public void drop(DropTargetEvent event) {
String fileList[] = null;String file="";
FileTransfer ft = FileTransfer.getInstance();
if (ft.isSupportedType(event.currentDataType)) {fileList = (String[]) event.data;}
file=fileList[0];
setDecompress(file);
System.out.println(file);
textArea2.setText(file);
infoLable.setText("FILE SELECTED " +new File(file).getName());}});

shell.setSize(715, 530);
Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
int x = (int) ((dimension.getWidth() - 700) / 2);
int y = (int) ((dimension.getHeight() - 480) / 2);
shell.setLocation(x, y);
	}
}
