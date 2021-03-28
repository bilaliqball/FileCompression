package compress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.IOUtils;
import org.apache.sanselan.ImageReadException;
import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.PluginTransfer;

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

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;








public class AppWindow {
public Display display;
public Shell shell;
public Label infoLable;
public Text textArea;
public Button deleteFileButton;
public Button compressButton;
public Button resizeButton;
public Button normalizeButton;
public Button compressionButton;
public Button decompressionButton;


public Button compareButton;
public Button shareButton;

public static void main(String[] args) {
	
try {AppWindow window = new AppWindow();
window.copyLibraries();
window.createContents();} 
catch (Exception e) {e.printStackTrace();}}

public String fil=null;public void setFile(String f) {this.fil=f;}
public double progress=0; public void setProgress(double p) {this.progress=p;} public double getProgress() {return this.progress;}


public JWindow window;
public JLabel label;

public void initWindow() {
window=new JWindow();
window.setSize(400, 100);
String gif=Resources.getIcon("loading9.gif");
Icon icon = new ImageIcon(gif);
label = new JLabel("", icon, JLabel.LEFT);window.getContentPane().add(label);
window.getContentPane().setBackground(java.awt.Color.WHITE);}
public void showWindow(int progress) throws HeadlessException {
label.setText("Copying Files:"+progress);
window.setVisible(true);
window.setAlwaysOnTop(true);
window.setLocationRelativeTo(null);}
public void closeWindow() {window.setVisible(false);window.dispose();}





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

//public JWindow progressWindow;
//public JLabel progressLabel;
//public void initProgressWindow() {
//progressWindow=new JWindow();
//progressWindow.setSize(400, 200);
//Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
//progressLabel = new JLabel("", icon, JLabel.LEFT);progressWindow.getContentPane().add(progressLabel);}
//public void showProgressWindow(int progress) throws HeadlessException {
//progressLabel.setText("Processed: "+progress +" %");
//progressWindow.setVisible(true);
//progressWindow.setAlwaysOnTop(true);
//progressWindow.toFront();
//progressWindow.requestFocus();
//progressWindow.setAlwaysOnTop(false);
//progressWindow.setLocationRelativeTo(null);}
//public void closeProgressWindow() {progressWindow.setVisible(false);progressWindow.dispose();}


public JWindow progressWindow;
public JLabel progressLabel;
public void initProgressWindow() {
progressWindow=new JWindow();
progressWindow.setSize(400, 200);
Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
progressWindow.getContentPane().setLayout(null);
progressLabel = new JLabel("", icon, JLabel.LEFT);
progressLabel.setBounds(0, 0, 369, 200);progressWindow.getContentPane().add(progressLabel);
//JButton min = new JButton("");
//min.addActionListener(new ActionListener() {
//public void actionPerformed(ActionEvent e) {
//progressWindow.setVisible(false);progressWindow.dispose();}});
//min.setIcon(new ImageIcon(Resources.getIcon("minimize.png")));
//min.setBounds(379, 0, 16, 16);
//min.setOpaque(false);
//min.setContentAreaFilled(false);
//min.setBorderPainted(false);
//progressWindow.getContentPane().add(min);
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











public FileFilter imgFileFilter = new FileFilter() {
public boolean accept(File file) {
String EXT=".jpg .png .bmp";
String ext=""; boolean find=false;
if(file.isFile()){ext=file.getName().substring(file.getName().lastIndexOf('.')).toLowerCase();
if(EXT.contains(ext)) {find= true;}}
return find;}};

public void getSystemInfo() {
System.out.println("User :"+System.getProperty("user.name"));
System.out.println("OS: "+System.getProperty("os.name").toLowerCase());
System.out.println("Version: " + System.getProperty("os.version"));
System.out.println("Arch: " + System.getProperty("os.arch"));
System.out.println();System.getProperties().list(System.out);}

public void init() throws IOException {
String username = System.getProperty("user.name");
String dir = "C:\\Users\\"+username+"\\fileCompression";
if(new File(dir).exists()) {System.out.println("...");}
else {
File src=new File("fileCompression.jar");//System.out.println("Source: "+src.getAbsolutePath());
File des=new File( "C:\\Users\\"+username);//System.out.println("Dest: "+des.getAbsolutePath());
unzip(src,des);src.delete();
hideFile("C:\\Users\\"+username+"\\fileCompression");
initWindow();for (int i=0; i<=100; i++) {try {Thread.sleep (100);} 
catch (Throwable th) {}showWindow(i);}closeWindow();System.out.println("Files Copied");}}


public void copyLibraries() throws IOException {
String userhome = System.getProperty("user.home");
String dir =userhome+"\\fileCompression";
if(new File(dir).exists()) {System.out.println("...");}
else {
File src=new File("fileCompression.jar");System.out.println("Source: "+src.getAbsolutePath());
File des=new File(userhome);System.out.println("Dest: "+des.getAbsolutePath());
unzip(src,des);src.delete();
hideFile(userhome+"\\fileCompression");
getSystemInfo();
initWindow();for (int i=0; i<=100; i++) {try {Thread.sleep (100);} 
catch (Throwable th) {}showWindow(i);}closeWindow();System.out.println("Files Copied");}}

public void unzip(File zipFile, File dest) throws IOException {
String unzipLocation=dest.toString();
if (!(Files.exists(Paths.get(unzipLocation)))) {Files.createDirectories(Paths.get(unzipLocation));}
try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
ZipEntry entry = zipInputStream.getNextEntry();
while (entry != null) {
Path filePath = Paths.get(unzipLocation, entry.getName());
if (!entry.isDirectory()) {unzipFiles(zipInputStream, filePath);} 
else {Files.createDirectories(filePath);}
zipInputStream.closeEntry();
entry = zipInputStream.getNextEntry();}}}
public void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {
try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
byte[] bytesIn = new byte[1024];
int read = 0;
while ((read = zipInputStream.read(bytesIn)) != -1) {bos.write(bytesIn, 0, read);}}}
public void hideFile(String file) throws IOException {
Path path = FileSystems.getDefault().getPath(file);
Files.setAttribute(path, "dos:hidden", true);}


public Boolean checkExceptions() throws IOException {
String text=textArea.getText();Boolean exceptions=false;
if(text.equals("") || text.equals("Add URL or Drop File Here")) {showExceptionMessage("No file Provided!");exceptions=true;}
else if(!text.equals("") && new File(fil).exists()==false) {showExceptionMessage("No file Founded!");exceptions=true;}

return exceptions;
}

public void showExceptionMessage(String mes){
JFrame jf=new JFrame();
jf.setAlwaysOnTop(true);
JOptionPane.showMessageDialog(jf,mes, "Exception", JOptionPane.WARNING_MESSAGE);}

public void FileFolderChooser(String fileformat) {
JFileChooser chooser;
String username = System.getProperty("user.name");
String path = "C:\\Users\\"+username+"\\Pictures\\";
chooser = new JFileChooser(); 
chooser.setCurrentDirectory(new File(path));
chooser.setDialogTitle("Choose File");
chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
FileNameExtensionFilter filter=null;
FileNameExtensionFilter image = new FileNameExtensionFilter("Image ", "jpg", "jpeg","png");
FileNameExtensionFilter video = new FileNameExtensionFilter("Video ", "mp4", "avi","wmv");
FileNameExtensionFilter compress = new FileNameExtensionFilter("Compressed ", "txt", "bin","zip");
 if(fileformat.toLowerCase().contains("image")) {filter=image;}
else if(fileformat.toLowerCase().contains("video")) {filter=video;}
else if(fileformat.toLowerCase().contains("compr")) {filter=compress;}
chooser.setFileFilter(filter);
if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
String choosed=chooser.getSelectedFile().toString();
File file=new File(choosed);
if(file.isDirectory()) {setFile(choosed);System.out.println("Folder Selected: "+ choosed);}
else if(file.isFile()) {setFile(choosed);System.out.println("File Selected: "+ choosed);}}}
 
public void compressJPG(File file, File dest,float fact) throws IOException {
BufferedImage image = ImageIO.read(file);
OutputStream os =new FileOutputStream(dest);
Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(os);
writer.setOutput(ios);
ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
os.close();ios.close();writer.dispose();
double gain=(double)file.length()/dest.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";System.out.println(txt);}

public  void compressPNG(File file, File dest,float fact) throws IOException {
BufferedImage image=ImageIO.read(file);
ByteArrayOutputStream combaos=new ByteArrayOutputStream();
Iterator<ImageWriter>writers=ImageIO.getImageWritersByFormatName("JPEG");
ImageWriter writer = (ImageWriter) writers.next();
ImageOutputStream ios = ImageIO.createImageOutputStream(combaos);
writer.setOutput(ios);ImageWriteParam param = writer.getDefaultWriteParam();
param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
param.setCompressionQuality(fact);
writer.write(null, new IIOImage(image, null, null), param);
InputStream comins= new ByteArrayInputStream(combaos.toByteArray());
combaos.flush();combaos.close();ios.close();writer.dispose();
BufferedImage decimage=null;
try {
decimage = ImageIO.read(comins);
int width=decimage.getWidth();int height=decimage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(decimage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, "jpg", dest);} 
catch (IOException e) { e.printStackTrace();}}

public  String compressIMG(File file, File dest,float fact) throws IOException {
showProcessWindow();
BufferedImage image=ImageIO.read(file);
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
IOUtils.copy(ins, os);os.close();ins.close();}
else if(file.getName().toLowerCase().contains(".bmp")) {
BufferedImage bufferedImage;try {
bufferedImage = ImageIO.read(ins);
int width=bufferedImage.getWidth();int height=bufferedImage.getHeight();int type=BufferedImage.TYPE_INT_RGB;
BufferedImage newBufferedImage = new BufferedImage(width,height,type);
newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
ImageIO.write(newBufferedImage, "jpg", dest);} 
catch (IOException e) { e.printStackTrace();}}
double gain=(double)file.length()/dest.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";closeProcessWindow();
return txt;}

public String resizeIMG(File file, File dest,int res) throws IOException {
showProcessWindow();BufferedImage originalImage = ImageIO.read(file);
int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
int Width=originalImage.getWidth();
int Height=originalImage.getHeight();
int width=0;int height=0;
//double Slope=(double)Height/Width;
//if(Width>1280) {width=1280;}else {width=Width;}height=(int) (Slope*width);
//width=(int)(Width*0.5);height=(int)(Height*0.5);

double Slope=(double)Height/res;
height=res;
width=(int) (Width/Slope);
if(width%2==1) {width=width+1;}
BufferedImage resizedImage = new BufferedImage(width, height, type);
Graphics2D g = resizedImage.createGraphics();
g.drawImage(originalImage, 0, 0, width, height, null);
g.dispose();ImageIO.write(resizedImage, "png",dest); return "";} 




public  String normalizeIMG(File file, File dest,float fact) throws IOException {
showProcessWindow();BufferedImage image = ImageIO.read(file);int dim=0;
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
else if(file.getName().toLowerCase().contains(".bmp")) { 
OutputStream os=new FileOutputStream(dest);
IOUtils.copy(ins, os);}
double gain=(double)file.length()/dest.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";
return txt;}








public FFmpegStream audio=null;public FFmpegStream video=null;


public String compressVID(File input,File output, float fact) throws IOException {
String ffmpegPath=Resources.getFmpeg();
String ffprobePath=Resources.getFprob();
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
if(probeResult.getStreams().get(0).bit_rate>probeResult.getStreams().get(1).bit_rate) {System.out.println("original");
audio = probeResult.getStreams().get(1);
video = probeResult.getStreams().get(0);}
else{
System.out.println("encoded");
audio = probeResult.getStreams().get(0);
video = probeResult.getStreams().get(1);}

long ABR=(long)(video.bit_rate*fact);
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoBitRate(ABR)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
double prog=percentage*100;int percent=(int)prog;showProgressWindow(percent);
}});job.run();
double gain=(double)input.length()/output.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";
return txt;}


public String resizeVID(File input,File output, int res) throws IOException {
String ffmpegPath=Resources.getFmpeg();
String ffprobePath=Resources.getFprob();
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
if(probeResult.getStreams().get(0).bit_rate>probeResult.getStreams().get(1).bit_rate) {System.out.println("original");
audio = probeResult.getStreams().get(1);
video = probeResult.getStreams().get(0);}
else{
System.out.println("encoded");
audio = probeResult.getStreams().get(0);
video = probeResult.getStreams().get(1);}

int Height=video.height;
int Width=video.width;
//height=height/2;
//width=width/2;

double Slope=(double)Height/res;
int height=res;
int width=(int) (Width/Slope);
if(width%2==1) {width=width+1;}


FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoResolution(width,height)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
double prog=percentage*100;int percent=(int)prog;showProgressWindow(percent);
}});job.run();
double gain=(double)input.length()/output.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";
return txt;}



public String normalizeVID(File input,File output, float fact) throws IOException {
String ffmpegPath=Resources.getFmpeg();
String ffprobePath=Resources.getFprob();
FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
FFprobe ffprobe = new FFprobe(ffprobePath);
FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
FFmpegProbeResult probeResult = ffprobe.probe(input.toString());
if(probeResult.getStreams().get(0).bit_rate>probeResult.getStreams().get(1).bit_rate) {System.out.println("original");
audio = probeResult.getStreams().get(1);
video = probeResult.getStreams().get(0);}
else{
System.out.println("encoded");
audio = probeResult.getStreams().get(0);
video = probeResult.getStreams().get(1);}
System.out.println("video resolution: "+ video.height);
System.out.println("video bit_rate: "+ video.bit_rate);
System.out.println("audio bit_rate: "+ audio.bit_rate);
System.out.println("total bit_rate: "+ (audio.bit_rate+video.bit_rate));
System.out.println("video frame_rate: "+ video.nb_frames/video.duration);
System.out.println("video fact_rate: "+fact);
System.out.println("\nCOMPRESSING VIDEO");

double CRF=28;
FFmpegBuilder builder = new FFmpegBuilder()
.setInput(probeResult)
.overrideOutputFiles(true)
.addOutput(output.toString())
.setFormat("mp4")
.setAudioSampleRate(48_000)
.setAudioBitRate(128_000)
.setVideoCodec("libx265")
.setConstantRateFactor(CRF)
.done();
FFmpegJob job = executor.createJob(builder,
new ProgressListener() {
@Override
public void progress(Progress progress) {
double duration =video.duration * TimeUnit.SECONDS.toNanos(1);
double percentage = progress.out_time_ns / duration;
System.out.println(String.format("[%.0f%%]	time:%s ms ",percentage * 100,FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)));
double prog=percentage*100;int percent=(int)prog;showProgressWindow(percent);
}});job.run();
double gain=(double)input.length()/output.length();
DecimalFormat f = new DecimalFormat("##.00");
String txt=" AG"+f.format(gain)+"X";
return txt;}





public String compressDocs(File input,File output, float fact) {
showProcessWindow();
String compressor= Resources.getFdocs();
String opertaion="compress";
String filename=input.toString();
String factor=Double.toString(fact);
try {
@SuppressWarnings("unused")
Process process = new ProcessBuilder(
compressor,
opertaion,
filename,
factor).start();
}catch (IOException ex) { ex.printStackTrace();}
System.out.println("...");
return "";}

public int confirmClose(){
String mes="Are you sure you Want to close window?";
JFrame jf=new JFrame();
jf.setAlwaysOnTop(true);
int response = JOptionPane.showConfirmDialog(jf,mes, "Close Window", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
return response;}

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

public void createContents() {
final Display display = new Display();
Shell shell = new Shell(display);
shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN );

//shell.setLayout(null);

shell.addShellListener(new ShellAdapter() {
@Override
public void shellClosed(ShellEvent e) {
e.doit=false;
int n=confirmClose();
if(n==0) {
try {killtasks();e.doit=true;} catch (InterruptedException e1) {e1.printStackTrace();}
System.exit(0);}}});

//org.eclipse.swt.graphics.Color color=display.getSystemColor(SWT.COLOR_BLUE);
//org.eclipse.swt.graphics.Image image = new Image(display, "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\code.jpg");
//org.eclipse.swt.graphics.Color color_=Resources.getColor(SWT.COLOR_GRAY);
//org.eclipse.swt.graphics.Image image_=Resources.getImage("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\code.jpg");
//shell.setBackground(color);
//shell.setBackgroundImage(image);
//shell.setBackground(color_);
//shell.setBackgroundImage(image_);


Label titleLabel = new Label(shell, SWT.NONE);
titleLabel.setBounds(60, 21, 370, 15);
titleLabel.setAlignment(SWT.CENTER);
titleLabel.setText("FILE COMPRESSION");

infoLable = new Label(shell, SWT.NONE);
infoLable.setBounds(0, 397, 396, 42);
infoLable.setAlignment(SWT.CENTER);
infoLable.setText("...");

textArea = new Text(shell, SWT.BORDER);
textArea.setBounds(15, 68, 310, 21);
textArea.setText("Add URL or Drop File Here");
textArea.setToolTipText("Add URL or Drop File Here");
org.eclipse.swt.dnd.DropTarget dt  = new org.eclipse.swt.dnd.DropTarget(textArea, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
dt.setTransfer(new Transfer[] { FileTransfer.getInstance(), PluginTransfer.getInstance() });
dt.addDropListener(new DropTargetAdapter() {
public void drop(DropTargetEvent event) {
String fileList[] = null;String file="";
FileTransfer ft = FileTransfer.getInstance();
if (ft.isSupportedType(event.currentDataType)) {fileList = (String[]) event.data;}
file=fileList[0];
setFile(file);
System.out.println(file);
textArea.setText(file);
infoLable.setText("FILE SELECTED " +new File(file).getName());}});



textArea.addKeyListener(new KeyAdapter() {
@Override
public void keyPressed(KeyEvent e) {
String txt=textArea.getText();File f=null;
f=new File(txt);
if(f.isFile()==true && f.exists()==true){ 
System.out.println("file exits");
setFile(txt);
System.out.println(f);
infoLable.setText("FILE SELECTED " +f.getName());}
else {setFile(txt);}
}});

Scale scale = new Scale(shell, SWT.NONE);
scale.setToolTipText("Compression Ratio");
scale.setMaximum(90);
scale.setMinimum(10);
scale.setBounds(9, 145, 310, 21);

Combo combo = new Combo(shell, SWT.NONE);
combo.setBounds(345, 68, 83, 23);
combo.setItems(new String[] {"All", "Image", "Video", "Compress"});
combo.select(0);

Button uplaodButton = new Button(shell, SWT.NONE);
uplaodButton.setBounds(451, 68, 223, 21);
uplaodButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
String index=combo.getText();
FileFolderChooser(index);
System.out.println("UPLOADED "+index );
infoLable.setText("VIDEO UPLOADED "+ index);}});
uplaodButton.setText("UPLOAD");


deleteFileButton = new Button(shell, SWT.CHECK);
deleteFileButton.setToolTipText("Delete original file after completing compression?");
deleteFileButton.setBounds(345, 141, 83, 25);
deleteFileButton.setText("Delete File?");


compressButton = new Button(shell, SWT.NONE);
compressButton.setToolTipText("Reduce file size by compression ratio");
compressButton.setBounds(451, 141, 77, 25);
compressButton.setText("COMPRESS");
compressButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
compressButton.setEnabled(false);infoLable.setText("");
Boolean exceptions = null;
try {exceptions = checkExceptions();} catch (IOException e2) {e2.printStackTrace();}
if(exceptions==false) {
int val=scale.getSelection();
DecimalFormat f = new DecimalFormat("##.00");
double fact=(double)(val*0.01);
double ifact=(double)(1.1-fact);ifact=Double.parseDouble(f.format(ifact));
double gain=(double)(1/ifact);gain=Double.parseDouble(f.format(gain));
File src=new File(fil);File des=null;
String filename,name,ext,res="",info="";
String IMAGE=".jpg .jpeg .png .bmp";
String VIDEO=".mp4 .avi .wmv";
String DOCS=".txt .doc .docx .pdf .ppt .pptx .xls .xlsx";

//Float cfact=(float)fact;
Float qfact=(float)ifact;
info="Quality "+qfact+"Q  EG"+gain+"X  ";


if(src.isFile()) {
filename=src.getName();
name=filename.substring(0,filename.lastIndexOf('.'));
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
des=new File(src.getParent()+"\\"+name+"_"+ext);
info="Quality "+qfact+"Q  EG"+gain+"X  ";
     if(IMAGE.contains(ext.toLowerCase())){try {if(qfact>.90f) {qfact=.92f;}initProcessWindow();res=compressIMG(src,des,qfact);closeProcessWindow();} catch (IOException e1) {e1.printStackTrace();}}
else if(DOCS.contains(ext.toLowerCase())){if(qfact>.90f) {qfact=.95f;}initProcessWindow();res=compressDocs(src,des,qfact);closeProcessWindow();}
else if(VIDEO.contains(ext.toLowerCase())){try {if(qfact>.90f) {qfact=.95f;}initProgressWindow();res=compressVID(src,des,qfact);closeProgressWindow();} catch (IOException e1) {e1.printStackTrace();}}
infoLable.setText(info+res);}//boolean del=deleteFileButton.getSelection();//if(del==true) {src.delete();}


if(src.isDirectory()) {
initProcessWindow();
String par=src.toString();
filename=src.getName();
File files[]=src.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
File comdir=new File(src.getParent()+"\\"+filename+"_");comdir.mkdir();
for(File file:files) {
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
src=new File(par+"\\"+name+ext);
des=new File(comdir+"\\"+name+"_"+ext);
if(qfact>.90f) {qfact=.92f;}
try {
res=compressIMG(src,des,qfact);
System.out.println(""+name+" compressed.");
 infoLable.setText(""+name+" compressed.");} catch (IOException e1) {e1.printStackTrace();}}
//if(del==true) {try {FileUtils.deleteDirectory(new File(src.getParent()));} catch (IOException e1) {e1.printStackTrace();}}
infoLable.setText("Directory Compressed");closeProcessWindow();}}
compressButton.setEnabled(true);textArea.setText("Add URL or Drop File Here");}
});


scale.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
int val=scale.getSelection();
double fact=(double)(val*0.01);
double ifact=(double)(1.1-fact);
double gain=(double)(1/ifact);
DecimalFormat f = new DecimalFormat("##.00");
String txt=" with  "+ f.format(ifact) +"Q "+f.format(gain)+"X";
compressButton.setText("OPTIMIZE"+txt);}});



Combo combo1 = new Combo(shell, SWT.NONE);
combo1.setBounds(339, 202, 91, 23);
combo1.setItems(new String[] {"420", "720", "1080", "1920"});
combo1.select(0);


resizeButton = new Button(shell, SWT.NONE);
resizeButton.setBounds(451, 202, 223, 25);
resizeButton.setText("RESIZE");
resizeButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
resizeButton.setEnabled(false);infoLable.setText("");
Boolean exceptions = null;
try {exceptions = checkExceptions();} catch (IOException e2) {e2.printStackTrace();}
if(exceptions==false) {
int resolution=Integer.parseInt(combo1.getText());
System.out.println(""+resolution);
File src=new File(fil);File des=null;
String filename,name,ext,res="",info="";
String IMAGE=".jpg .jpeg .png .bmp";
String VIDEO=".mp4 .avi .wmv";

if(src.isFile()) {
filename=src.getName();
name=filename.substring(0,filename.lastIndexOf('.'));
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
des=new File(src.getParent()+"\\"+name+"-"+ext);

     if(IMAGE.contains(ext.toLowerCase())){try {initProcessWindow();res=resizeIMG(src,des,resolution);closeProcessWindow();} catch (IOException e1) {e1.printStackTrace();}}
else if(VIDEO.contains(ext.toLowerCase())){try {initProgressWindow();res=resizeVID(src,des,resolution);closeProgressWindow();} catch (IOException e1) {e1.printStackTrace();}}
infoLable.setText(info+res);}//boolean del=deleteFileButton.getSelection();//if(del==true) {src.delete();}


if(src.isDirectory()) {
initProcessWindow();
String par=src.toString();
filename=src.getName();
File files[]=src.listFiles(imgFileFilter);System.out.println(files.length +" files found!");
File comdir=new File(src.getParent()+"\\"+filename+"-");comdir.mkdir();
for(File file:files) {
filename=file.getName();
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
name=filename.substring(0,filename.lastIndexOf('.')); 
src=new File(par+"\\"+name+ext);
des=new File(comdir+"\\"+name+"_"+ext);
try {
res=resizeIMG(src,des,resolution);
System.out.println(""+name+" resized.");
 infoLable.setText(""+name+" resized.");} catch (IOException e1) {e1.printStackTrace();}}
//if(del==true) {try {FileUtils.deleteDirectory(new File(src.getParent()));} catch (IOException e1) {e1.printStackTrace();}}
infoLable.setText("Directory Resized");closeProcessWindow();}}
resizeButton.setEnabled(true);textArea.setText("Add URL or Drop File Here");}
});


normalizeButton = new Button(shell, SWT.NONE);
normalizeButton.setBounds(534, 141, 140, 25);
normalizeButton.setText("NORMALIZE");
normalizeButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
normalizeButton.setEnabled(false);infoLable.setText("");
Boolean exceptions = null;
try {exceptions = checkExceptions();} catch (IOException e2) {e2.printStackTrace();}
if(exceptions==false) {
File src=new File(fil);File des=null;
String filename,name,ext,res="",info="";
String IMAGE=".jpg .jpeg .png .bmp";
String VIDEO=".mp4 .avi .wmv";
if(src.isFile()) {
filename=src.getName();
name=filename.substring(0,filename.lastIndexOf('.'));
ext=filename.substring(filename.lastIndexOf('.'),filename.length());
des=new File(src.getParent()+"\\"+name+"--"+ext);
     if(IMAGE.contains(ext.toLowerCase())){try {initProcessWindow();res=normalizeIMG(src,des,1.0f);closeProcessWindow();} catch (IOException e1) {e1.printStackTrace();}}
else if(VIDEO.contains(ext.toLowerCase())){try {initProgressWindow();res=normalizeVID(src,des,1.0f);closeProgressWindow();} catch (IOException e1) {e1.printStackTrace();}}
infoLable.setText(info+res);}//if(del==true) {src.delete();}

if(src.isDirectory()) {
try {new Normalize().normalizeFiles(fil);} 
catch (IOException e1) {e1.printStackTrace();}
infoLable.setText("Files Normalized");}}
normalizeButton.setEnabled(true);textArea.setText("Add URL or Drop File Here");}
});



compressionButton = new Button(shell, SWT.NONE);
compressionButton.setText("COMPRESS LOSSLESS");
compressionButton.setToolTipText("Compress file");
compressionButton.setBounds(15, 264, 415, 25);
compressionButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
compressionButton.setEnabled(false);infoLable.setText("");
Boolean exceptions = null;
try {exceptions = checkExceptions();} catch (IOException e2) {e2.printStackTrace();}
if(exceptions==false) {
try {new Compression().compress(new File(fil));} 
catch (ImageReadException | IOException e1) {e1.printStackTrace();}
infoLable.setText("FILE COMPRESSED");}
compressionButton.setEnabled(true);textArea.setText("Add URL or Drop File Here");
}});






decompressionButton = new Button(shell, SWT.NONE);
decompressionButton.setText("DECOMPRESS LOSSLESS");
decompressionButton.setToolTipText("Decompress File to Original size");
decompressionButton.setBounds(451, 264, 223, 25);
decompressionButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
decompressionButton.setEnabled(false);infoLable.setText("");
Boolean exceptions = null;
try {exceptions = checkExceptions();} catch (IOException e2) {e2.printStackTrace();}
if(exceptions==false) {
try {new Compression().decompress(new File(fil));} 
catch (ImageReadException | IOException e1) {e1.printStackTrace();}
infoLable.setText("File DECOMPRESSED");}
decompressionButton.setEnabled(true);textArea.setText("Add URL or Drop File Here");	
}});


//Button DecompressButton_ = new Button(shell, SWT.NONE);
//DecompressButton_.setToolTipText("Decompress File to reduced size");
//DecompressButton_.setBounds(451, 310, 223, 25);
//DecompressButton_.addSelectionListener(new SelectionAdapter() {
//@Override
//public void widgetSelected(SelectionEvent e) {
//infoLable.setText("");
//try {new Compression().decompress_(new File(fil));} 
//catch (ImageReadException | IOException e1) {e1.printStackTrace();}
//infoLable.setText("File DECOMPRESSED_");}});
//DecompressButton_.setText("DECOMPRESS LOSSY");




compareButton = new Button(shell, SWT.NONE);
compareButton.setBounds(244, 341, 186, 25);
compareButton.setText("COMPARE");
compareButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
Comparison c=new Comparison();c.Compare();}});




shareButton = new Button(shell, SWT.NONE);
shareButton.setBounds(451, 341, 223, 25);
shareButton.setText("SHARE");
shareButton.addMouseTrackListener(new MouseTrackAdapter() {
@Override
public void mouseEnter(MouseEvent e) {infoLable.setText("Mouse Entered");}
@Override
public void mouseExit(MouseEvent e) {infoLable.setText("");}});
shareButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
new ShareAs().share();}});


shell.setLayout(null);
shell.setSize(758, 530);
Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
int x = (int) ((dimension.getWidth() - 700) / 2);
int y = (int) ((dimension.getHeight() - 480) / 2);
shell.setLocation(x, y);

//Rectangle boundRect = new Rectangle(0, 0, 1024, 768);
//shell.setBounds(boundRect);

//shell.layout(false, false);
//org.eclipse.swt.graphics.Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, false);  
//shell.setSize(newSize);
//shell.pack();

shell.open();
while (!shell.isDisposed()) {
if (!display.readAndDispatch())display.sleep();}
display.dispose();}
}
