package encoding;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class CompressionBSF {

public static void main(String args[]) throws IOException, DecoderException {
//decodeImage();
//inptoHex();
	
	init();
	
}
	
public static void init2() throws IOException, DecoderException {
jpgtoHex();
hextoImg();
imgtoHex();
hextoJpg();	
}	

public static void init() throws IOException, DecoderException {
File inp= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
File txt= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt");
File bsf= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf_.txt");
File dec= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file1_.jpg");
inptoBsf(inp,txt);
bsftohex();
hextoImg();
imgtoHex();
hextoBsf();
bsftoOut(bsf,dec);
System.out.println("...");}
public static void inptoBsf(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
IOUtils.copy(fis, bos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(bos);}}
public static void bsftohex() throws DecoderException, IOException {
File bsf = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.txt");
File hex = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt");
FileInputStream fis=new FileInputStream(bsf);//String content=readContent(fis);
String content=IOUtils.toString(fis, StandardCharsets.UTF_8.name());
byte[] decoded = Base64.decodeBase64(content);
String hexString = Hex.encodeHexString(decoded);
write(hex,hexString);}
public static void hextoImg()throws IOException{
File hexfile = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt");
File imgfile = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\img.png");
BufferedImage img = null;
long size=(long)hexfile.length();
int thp=(int)(hexfile.length()/8);//total hex pairs
int dim=(int)Math.sqrt((double)thp);
int width =dim;
int height =(int)((thp/dim)+0.4);
System.out.println("siz:"+size+" dim:"+dim+" thp:"+thp+" wid:"+width);
img= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
String line;int p;String hex="";
int start=0;int end=8;
try (BufferedReader br = new BufferedReader(new FileReader(hexfile))) {
line = br.readLine();
if(line!=null) {
for(int y=0;y<height;y++){
for(int x=0;x<width;x++) {
hex=line.substring(start,end);start=end;end=end+8;
BigInteger bi = new BigInteger(hex, 16);
p=bi.intValue();
img.setRGB(x,y,p);
}}}}
catch (IOException e) {e.printStackTrace();}
try{ImageIO.write(img, "png", imgfile);}
catch(IOException e){System.out.println(e);}}
public static void imgtoHex()throws IOException{
File imgfile = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\img.png");
File hexfile = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex_.txt");
BufferedImage img = null;
try{img = ImageIO.read(imgfile);}catch(IOException e){System.out.println(e);}
int width = img.getWidth();int height = img.getHeight();int p;
try (BufferedWriter bw = new BufferedWriter(new FileWriter(hexfile,true))) {
for(int y = 0; y < height; y++){
for(int x = 0; x < width; x++){
p = img.getRGB(x,y);
String hex=Integer.toHexString(p);
     if(hex.length()==8) {hex=hex+"";}
else if(hex.length()==7) {hex="0"+hex;}
else if(hex.length()==6) {hex="00"+hex;}
else if(hex.length()==5) {hex="000"+hex;}
else if(hex.length()==4) {hex="0000"+hex;}
else if(hex.length()==3) {hex="00000"+hex;}
else if(hex.length()==2) {hex="000000"+hex;}
else if(hex.length()==1) {hex="0000000"+hex;}
else if(hex.length()==0) {hex="00000000";}
bw.write(hex);}bw.newLine();}bw.close();} 
catch (IOException e) {e.printStackTrace();}}
public static void hextoBsf() throws DecoderException, IOException {
File hex = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex_.txt");
File bsf = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf_.txt");
String hexString=read(hex);
byte[] decodedHex = Hex.decodeHex(hexString);
byte[] encodedHexB64 = Base64.encodeBase64(decodedHex);
String decstr=new String(encodedHexB64);
write(bsf,decstr);}
public static void bsftoOut(File input, File output) throws IOException {
FileInputStream fis=null;
Base64InputStream bis = null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(input);
fos = new FileOutputStream(output);
bis = new Base64InputStream(fis);
IOUtils.copy(bis, fos);} 
finally {
IOUtils.closeQuietly(bis);
IOUtils.closeQuietly(fos);}}
public static String read(File file)throws IOException{
String line;
StringBuilder sb = new StringBuilder();
try (BufferedReader br = new BufferedReader(new FileReader(file))) {
while((line = br.readLine())!=null) {
sb.append(line);}br.close();}
catch (IOException e) {e.printStackTrace();}
return sb.toString();}
public static String readContent(FileInputStream fis) throws IOException{
try( BufferedReader br =new BufferedReader( new InputStreamReader(fis))){
StringBuilder sb = new StringBuilder();String line;
while(( line = br.readLine()) != null ) {
sb.append( line );}
return sb.toString();}}
public static void write(File file,String content) {
try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
bw.write(content);
bw.close();
} catch (IOException e) {e.printStackTrace();}}
public static void writeCOntent(File file, String content) throws IOException {
FileWriter writer = new FileWriter(file);
try(PrintWriter printWriter =new PrintWriter(writer)){
printWriter.write(content);}}


public static String gethexString0(byte[] bytes) throws IOException {
String    HEXES    = "0123456789ABCDEF";
StringBuilder hex = new StringBuilder(2 * bytes.length);
for (final byte b : bytes) {
hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));}
return hex.toString();}
public static String gethexString1(byte[] bytes) {
String[] hexes = new String[]{
"00","01","02","03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F",
"10","11","12","13","14","15","16","17","18","19","1A","1B","1C","1D","1E","1F",
"20","21","22","23","24","25","26","27","28","29","2A","2B","2C","2D","2E","2F",
"30","31","32","33","34","35","36","37","38","39","3A","3B","3C","3D","3E","3F",
"40","41","42","43","44","45","46","47","48","49","4A","4B","4C","4D","4E","4F",
"50","51","52","53","54","55","56","57","58","59","5A","5B","5C","5D","5E","5F",
"60","61","62","63","64","65","66","67","68","69","6A","6B","6C","6D","6E","6F",
"70","71","72","73","74","75","76","77","78","79","7A","7B","7C","7D","7E","7F",
"80","81","82","83","84","85","86","87","88","89","8A","8B","8C","8D","8E","8F",
"90","91","92","93","94","95","96","97","98","99","9A","9B","9C","9D","9E","9F",
"A0","A1","A2","A3","A4","A5","A6","A7","A8","A9","AA","AB","AC","AD","AE","AF",
"B0","B1","B2","B3","B4","B5","B6","B7","B8","B9","BA","BB","BC","BD","BE","BF",
"C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","CA","CB","CC","CD","CE","CF",
"D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","DA","DB","DC","DD","DE","DF",
"E0","E1","E2","E3","E4","E5","E6","E7","E8","E9","EA","EB","EC","ED","EE","EF",
"F0","F1","F2","F3","F4","F5","F6","F7","F8","F9","FA","FB","FC","FD","FE","FF"};
StringBuilder sb = new StringBuilder();
for (final byte b : bytes) {sb.append(hexes[b&0xFF]);}
return sb.toString();}
public static String gethexString2(byte[] bytes) {
return org.apache.commons.codec.binary.Hex.encodeHexString(bytes);}
public static String gethexString3(byte[] bytes) {
StringBuilder sb = new StringBuilder();
//for (byte b : bytes) {sb.append(String.format("%02X ", b));}
for (byte b : bytes) {sb.append(Integer.toHexString(b&0xFF));}
return sb.toString();}
public static String gethexString4(byte[] bytes) {
char[] HEXARRAY = "0123456789abcdef".toCharArray();
char[] hexChars = new char[bytes.length * 2];
for (int j = 0; j < bytes.length; j++) {
int v = bytes[j] & 0xFF;
hexChars[j * 2] = HEXARRAY[v >>> 4];
hexChars[j * 2 + 1] = HEXARRAY[v & 0x0F];}
return new String(hexChars);}
public static String gethexString5(byte[] bytes) {
StringBuilder sb=new StringBuilder();
for (byte b:bytes){sb.append(String.format("%02X", b));}
return sb.toString();}
public static String gethexString6(byte[] bytes) {
final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
char[] hexChars = new char[bytes.length * 2];
int v;
for ( int j = 0; j < bytes.length; j++ ) {
v = bytes[j] & 0xFF;
hexChars[j * 2] = hexArray[v >>> 4];
hexChars[j * 2 + 1] = hexArray[v & 0x0F];}
return new String(hexChars);}



 

public static void jpgtoHex() throws IOException {
BufferedImage image=ImageIO.read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg"));
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ImageIO.write( image, "jpg", baos );
baos.flush();byte[] imageInByte = baos.toByteArray();baos.close();
String hexString=gethexString2(imageInByte);
write(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt"),hexString);}

public static void inptoHex() throws IOException {
FileInputStream fis=new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.mp4"));
File hex=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex.txt");
byte[] bytes = IOUtils.toByteArray(fis);
String hexString=gethexString5(bytes);
write(hex,hexString);}

public static void hextoJpg() throws IOException, DecoderException {
InputStream is= new FileInputStream(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\hex_.txt"));
String content = IOUtils.toString(is);
//String content=read(new File("C:\\Users\\bee\\Pictures\\sample\\hex_.txt"));
byte[] res=org.apache.commons.codec.binary.Hex.decodeHex(content);
InputStream in = new ByteArrayInputStream(res);
BufferedImage img = ImageIO.read(in);
ImageIO.write(img, "jpg", new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file1_.jpg"));}

public static void writeImage() {
try {
byte[] imageInByte;
BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file1.jpg"));
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ImageIO.write(originalImage, "jpg", baos);
baos.flush();imageInByte = baos.toByteArray();baos.close();
InputStream in = new ByteArrayInputStream(imageInByte);
BufferedImage bImageFromConvert = ImageIO.read(in);
ImageIO.write(bImageFromConvert, "jpg", new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file1_.jpg"));} 
catch (IOException e) {System.out.println(e.getMessage());}}

public static void decodeImage() {
File file = new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file.jpg");
try {
FileInputStream imageInFile = new FileInputStream(file);
byte imageData[] = new byte[(int)file.length()];
imageInFile.read(imageData);
String imageDataString = encodeImage(imageData);
byte[] imageByteArray = decodeImage(imageDataString);
FileOutputStream imageOutFile = new FileOutputStream("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\file_.jpg");
imageOutFile.write(imageByteArray);
imageInFile.close();
imageOutFile.close();
System.out.println("Image Successfully Manipulated!");} 
catch (FileNotFoundException e) {System.out.println("Image not found" + e);} 
catch (IOException ioe) {System.out.println("Exception while reading the Image " + ioe);}}
public static String encodeImage(byte[] imageByteArray){return Base64.encodeBase64URLSafeString(imageByteArray);}
public static byte[] decodeImage(String imageDataString) {return Base64.decodeBase64(imageDataString);}

public static void inptochar() throws IOException {
FileInputStream fis=new FileInputStream(new File("C:\\Users\\bee\\Pictures\\sample\\file.jpg"));
File hex=new File("C:\\Users\\bee\\Pictures\\sample\\bytespace.txt");
byte[] bytes = IOUtils.toByteArray(fis);
StringBuilder sb = new StringBuilder();
for (byte b : bytes) {sb.append((int)b);sb.append(" ");}
write(hex,sb.toString());}
public static void byttoJpg() throws IOException {
InputStream is= new FileInputStream(new File("C:\\Users\\bee\\Pictures\\FILES\\sample\\bytespace.txt"));
byte[] bytes = IOUtils.toByteArray(is);
InputStream in = new ByteArrayInputStream(bytes);
BufferedImage img = ImageIO.read(in);
ImageIO.write(img, "jpg", new File("C:\\Users\\bee\\Pictures\\FILES\\sample\\file_.jpg"));}


//BSF
public static void compressBSIOS(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
IOUtils.copy(fis, bos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(bos);}}
public static void decompressBSIOS(File inZippedFile, File outUnzippedFile) throws IOException {
FileInputStream fis=null;
Base64InputStream bis = null;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
bis = new Base64InputStream(fis);
IOUtils.copy(bis, fos);} 
finally {
IOUtils.closeQuietly(bis);
IOUtils.closeQuietly(fos);}}



}
