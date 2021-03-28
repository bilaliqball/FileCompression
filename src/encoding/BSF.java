package encoding;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.IOUtils;

public class BSF {
public static void main(String args[]) throws IOException{
BSF b=new BSF();
b.init("file");
b.compress();
b.decompress();}

public File input;
public File pixel;
public File compress;
public File decompress;
public File output;
public int he;public int wi;
public BSF() {input=null;pixel=null;compress=null;decompress=null;he=0;wi=0;} 

public void init(String filename) {
input=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\"+filename+".png");
pixel=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\pixel.txt");
compress=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\compress.txt");
decompress= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decompress.txt");
output= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\"+filename+"_.png");
BufferedImage img = null;
try{img = ImageIO.read(input);}catch(IOException e){System.out.println(e);}
wi=img.getWidth();he=img.getHeight();}

public void compress() throws IOException {
extractHexPixels(); compressFile(pixel,compress);}

public void decompress() throws IOException {
decompressFile(compress,decompress);drawHexPixels();decompress.delete();}

public char encodeCharacter(char c) {
	char enc = 0;
	  if(c=='0') {enc='0';}
 else if(c=='1') {enc='A';}
 else if(c=='2') {enc='B';}
 else if(c=='3') {enc='C';}
 else if(c=='4') {enc='D';}
 else if(c=='5') {enc='E';}
 else if(c=='6') {enc='F';}
 else if(c=='7') {enc='G';}
 else if(c=='8') {enc='H';}
 else if(c=='9') {enc='I';}
 else if(c=='a') {enc='J';}
 else if(c=='b') {enc='K';}
 else if(c=='c') {enc='L';}
 else if(c=='d') {enc='M';}
 else if(c=='e') {enc='N';}
 else if(c=='f') {enc='O';}
return enc;}
public char decodeCharacter(char c) {
	char dec = 0;
      if(c=='0') {dec='0';}
 else if(c=='A') {dec='1';}
 else if(c=='B') {dec='2';}
 else if(c=='C') {dec='3';}
 else if(c=='D') {dec='4';}
 else if(c=='E') {dec='5';}
 else if(c=='F') {dec='6';}
 else if(c=='G') {dec='7';}
 else if(c=='H') {dec='8';}
 else if(c=='I') {dec='9';}
 else if(c=='J') {dec='a';}
 else if(c=='K') {dec='b';}
 else if(c=='L') {dec='c';}
 else if(c=='M') {dec='d';}
 else if(c=='N') {dec='e';}
 else if(c=='O') {dec='f';}
return dec;}
public void extractHexPixels()throws IOException{
BufferedImage img = null;
try{img = ImageIO.read(input);}catch(IOException e){System.out.println(e);}
int width = img.getWidth();int height = img.getHeight();int p;
System.out.println("Width: "+width);System.out.println("Height: "+height);
String trim="";char r,rr,g,gg,b,bb;
try (BufferedWriter bw = new BufferedWriter(new FileWriter(pixel,true))) {
for(int y = 0; y < height; y++){
for(int x = 0; x < width; x++){
p = img.getRGB(x,y);
String hex=Integer.toHexString(p);
r=encodeCharacter(hex.charAt(2));rr=encodeCharacter(hex.charAt(3));
g=encodeCharacter(hex.charAt(4));gg=encodeCharacter(hex.charAt(5));
b=encodeCharacter(hex.charAt(6));bb=encodeCharacter(hex.charAt(7));
trim=Character.toString(r)+Character.toString(rr)+
     Character.toString(g)+Character.toString(gg)+
     Character.toString(b)+Character.toString(bb);
bw.write(trim);}bw.newLine();}bw.close();} 
catch (IOException e) {e.printStackTrace();}}
public void compressFile(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
FileOutputStream fos = new FileOutputStream(outFile);
Base64OutputStream base64Out = new Base64OutputStream(fos);
zos = new GZIPOutputStream(base64Out);
IOUtils.copy(fis, zos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}





public void decompressFile(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
OutputStreamWriter osw = null;
try {
FileInputStream fis = new FileInputStream(inZippedFile);
Base64InputStream base64In = new Base64InputStream(fis);
zis = new GZIPInputStream(base64In);
FileOutputStream fos = new FileOutputStream(outUnzippedFile);
osw = new OutputStreamWriter(fos, "UTF-8");
IOUtils.copy(zis, osw);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(osw);}}
public void drawHexPixels()throws IOException{
BufferedImage img = null;
int width =wi;
int height =he;
img= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
String line;int p;String hex="";char r,rr,g,gg,b,bb;
String pixel;int start=0;int end=6;
try (BufferedReader br = new BufferedReader(new FileReader(decompress))) {
for(int y = 0; y < height; y++){
line = br.readLine();
if(line!=null) {
for(int x=0;x<width;x++) {
pixel=line.substring(start,end);start=end;end=end+6;
r=decodeCharacter(pixel.charAt(0));rr=decodeCharacter(pixel.charAt(1));
g=decodeCharacter(pixel.charAt(2));gg=decodeCharacter(pixel.charAt(3));
b=decodeCharacter(pixel.charAt(4));bb=decodeCharacter(pixel.charAt(5));
hex="ff"+Character.toString(r)+Character.toString(rr)+
		 Character.toString(g)+Character.toString(gg)+
		 Character.toString(b)+Character.toString(bb);
BigInteger bi = new BigInteger(hex, 16);
p=bi.intValue();
img.setRGB(x,y,p);
}start=0;end=6;}}}
catch (IOException e) {e.printStackTrace();}
try{ImageIO.write(img, "png", output);}
catch(IOException e){System.out.println(e);}}    



    

}
