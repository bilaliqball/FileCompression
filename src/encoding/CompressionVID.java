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

public class CompressionVID {
public static void main(String args[]) throws IOException{
CompressionVID b=new CompressionVID();
b.init("cat");


b.IMGtoBSF(b.inp,b.bsf);
b.encode(b.bsf, b.com);
b.decode(b.com, b.dec);
b.BSFtoIMG(b.bsf, b.out);
System.out.println("...");

}
public File inp;
public File txt;
public File bsf;
public File rgb;
public File bsf_;
public File rgb_;
public File com;
public File dec;
public File out;
public int hei;public int wid;
public CompressionVID() {inp=null;txt=null;bsf=null;rgb=null;com=null;dec=null;out=null;hei=0;wid=0;} 

public void init(String filename) {
inp=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\"+filename+".mp4");
txt=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\txt.txt");
bsf=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf.txt");
rgb=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\rgb.txt");
bsf_=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\bsf_.txt");
rgb_=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\rgb_.txt");
com=new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\compress.png");
dec= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\decompress.txt");
out= new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\"+filename+"_.mp4");
wid=0; hei=0;}

public  void IMGtoBSF(File originalFile, File outFile) throws IOException {
INPtoTXT(inp,txt);TXTtoBSF();}

public  void INPtoTXT(File originalFile, File outFile) throws IOException {
FileInputStream fis = null;
FileOutputStream fos=null;
Base64OutputStream bos=null;
GZIPOutputStream zos = null;
try {
fis = new FileInputStream(originalFile);
fos = new FileOutputStream(outFile);
bos = new Base64OutputStream(fos);
IOUtils.copy(fis, bos);} 
finally {
IOUtils.closeQuietly(fis);
IOUtils.closeQuietly(zos);}}

public void tolines(int lta,int rows)throws IOException{
String line = null;
try (BufferedReader br = new BufferedReader(new FileReader(txt))) {
for(int y=0;y<rows+1;y++) {
line = br.readLine();
if(line!=null) {
for(int i=0;i<lta;i++) {line+=br.readLine();}writeline(line);}}
}catch (IOException e) {e.printStackTrace();}}
public void writeline(String content) {
try (BufferedWriter bw = new BufferedWriter(new FileWriter(bsf,true))) {
bw.write(content);bw.newLine();
bw.close();
} catch (IOException e) {e.printStackTrace();}}
@SuppressWarnings("unused")
public int getLineCount()throws IOException{
String line;int c = 0;
try (BufferedReader br = new BufferedReader(new FileReader(txt))) {
while((line = br.readLine())!=null) {c++;}br.close();}
catch (IOException e) {e.printStackTrace();}
return c;}

public int getCharCount()throws IOException{
String line;int c = 0;
try (BufferedReader br = new BufferedReader(new FileReader(txt))) {
while((line = br.readLine())!=null) {c+=line.length();}br.close();}
catch (IOException e) {e.printStackTrace();}
return c;}

public void TXTtoBSF()throws IOException{
int tlc=getLineCount();//total line count
int tc=getCharCount();//total line count
int tcc=76*tlc;//total char count
int cpr=(int)Math.sqrt((double)tcc);//char per row
int lta= (int)(Math.sqrt((double)tcc)/76);//lines to append

int dlc=0;//dist line count
     if((lta+0)%3==0) {dlc=lta+0;}
else if((lta+1)%3==0) {dlc=lta+1;}
else if((lta+2)%3==0) {dlc=lta+2;}
tolines(dlc,cpr); 

this.wid=cpr/3;this.hei=cpr/3;
System.out.println("tlc: "+tlc+"tc "+tc+" tcc: "+tcc+" cpr: "+cpr+" lta: "+lta+" dlc: "+dlc+ " wid: "+wid+" hei: "+hei);}

public String encodeString(String e) {
String enc="";char c;
for(int i=0;i<3;i++) {
c=e.charAt(i);
     if(c=='A') {enc+="00";}
else if(c=='B') {enc+="01";}
else if(c=='C') {enc+="02";}
else if(c=='D') {enc+="03";}
else if(c=='E') {enc+="04";}
else if(c=='F') {enc+="05";}
else if(c=='G') {enc+="06";}
else if(c=='H') {enc+="07";}
else if(c=='I') {enc+="08";}
else if(c=='J') {enc+="09";}
else if(c=='K') {enc+="10";}
else if(c=='L') {enc+="11";}
else if(c=='M') {enc+="12";}
else if(c=='N') {enc+="13";}
else if(c=='O') {enc+="14";}
else if(c=='P') {enc+="15";}
else if(c=='Q') {enc+="16";}
else if(c=='R') {enc+="17";}
else if(c=='S') {enc+="18";}
else if(c=='T') {enc+="19";}
else if(c=='U') {enc+="20";}
else if(c=='V') {enc+="21";}
else if(c=='W') {enc+="22";}
else if(c=='X') {enc+="23";}
else if(c=='Y') {enc+="24";}
else if(c=='Z') {enc+="25";}
else if(c=='a') {enc+="26";}
else if(c=='b') {enc+="27";}
else if(c=='c') {enc+="28";}
else if(c=='d') {enc+="29";}
else if(c=='e') {enc+="30";}
else if(c=='f') {enc+="31";}
else if(c=='g') {enc+="32";}
else if(c=='h') {enc+="33";}
else if(c=='i') {enc+="34";}
else if(c=='j') {enc+="35";}
else if(c=='k') {enc+="36";}
else if(c=='l') {enc+="37";}
else if(c=='m') {enc+="38";}
else if(c=='n') {enc+="39";}
else if(c=='o') {enc+="40";}
else if(c=='p') {enc+="41";}
else if(c=='q') {enc+="42";}
else if(c=='r') {enc+="43";}
else if(c=='s') {enc+="44";}
else if(c=='t') {enc+="45";}
else if(c=='u') {enc+="46";}
else if(c=='v') {enc+="47";}
else if(c=='w') {enc+="48";}
else if(c=='x') {enc+="49";}
else if(c=='y') {enc+="50";}
else if(c=='z') {enc+="51";}
else if(c=='0') {enc+="52";}
else if(c=='1') {enc+="53";}
else if(c=='2') {enc+="54";}
else if(c=='3') {enc+="55";}
else if(c=='4') {enc+="56";}
else if(c=='5') {enc+="57";}
else if(c=='6') {enc+="58";}
else if(c=='7') {enc+="59";}
else if(c=='8') {enc+="60";}
else if(c=='9') {enc+="61";}
else if(c=='+') {enc+="62";}
else if(c=='/') {enc+="63";}}
return enc;}
public String decodeString(String d) {
String dec="";String str;
for(int i=0;i<5;i=i+2) {
str=Character.toString(d.charAt(i))+Character.toString(d.charAt(i+1));
     if(str.equals("00")) {dec+="A";}
else if(str.equals("01")) {dec+="B";}
else if(str.equals("02")) {dec+="C";}
else if(str.equals("03")) {dec+="D";}
else if(str.equals("04")) {dec+="E";}
else if(str.equals("05")) {dec+="F";}
else if(str.equals("06")) {dec+="G";}
else if(str.equals("07")) {dec+="H";}
else if(str.equals("08")) {dec+="I";}
else if(str.equals("09")) {dec+="J";}
else if(str.equals("10")) {dec+="K";}
else if(str.equals("11")) {dec+="L";}
else if(str.equals("12")) {dec+="M";}
else if(str.equals("13")) {dec+="N";}
else if(str.equals("14")) {dec+="O";}
else if(str.equals("15")) {dec+="P";}
else if(str.equals("16")) {dec+="Q";}
else if(str.equals("17")) {dec+="R";}
else if(str.equals("18")) {dec+="S";}
else if(str.equals("19")) {dec+="T";}
else if(str.equals("20")) {dec+="U";}
else if(str.equals("21")) {dec+="V";}
else if(str.equals("22")) {dec+="W";}
else if(str.equals("23")) {dec+="X";}
else if(str.equals("24")) {dec+="Y";}
else if(str.equals("25")) {dec+="Z";}
else if(str.equals("26")) {dec+="a";}
else if(str.equals("27")) {dec+="b";}
else if(str.equals("28")) {dec+="c";}
else if(str.equals("29")) {dec+="d";}
else if(str.equals("30")) {dec+="e";}
else if(str.equals("31")) {dec+="f";}
else if(str.equals("32")) {dec+="g";}
else if(str.equals("33")) {dec+="h";}
else if(str.equals("34")) {dec+="i";}
else if(str.equals("35")) {dec+="j";}
else if(str.equals("36")) {dec+="k";}
else if(str.equals("37")) {dec+="l";}
else if(str.equals("38")) {dec+="m";}
else if(str.equals("39")) {dec+="n";}
else if(str.equals("40")) {dec+="o";}
else if(str.equals("41")) {dec+="p";}
else if(str.equals("42")) {dec+="q";}
else if(str.equals("43")) {dec+="r";}
else if(str.equals("44")) {dec+="s";}
else if(str.equals("45")) {dec+="t";}
else if(str.equals("46")) {dec+="u";}
else if(str.equals("47")) {dec+="v";}
else if(str.equals("48")) {dec+="w";}
else if(str.equals("49")) {dec+="x";}
else if(str.equals("50")) {dec+="y";}
else if(str.equals("51")) {dec+="z";}
else if(str.equals("52")) {dec+="0";}
else if(str.equals("53")) {dec+="1";}
else if(str.equals("54")) {dec+="2";}
else if(str.equals("55")) {dec+="3";}
else if(str.equals("56")) {dec+="4";}
else if(str.equals("57")) {dec+="5";}
else if(str.equals("58")) {dec+="6";}
else if(str.equals("59")) {dec+="7";}
else if(str.equals("60")) {dec+="8";}
else if(str.equals("61")) {dec+="9";}
else if(str.equals("62")) {dec+="+";}
else if(str.equals("63")) {dec+="/";}
else {dec+="o";}     }
System.out.println("str-->"+d + " decode: "+dec);
return dec;}

 



public void encode(File input, File output)throws IOException{
BufferedImage img = null;int width =this.wid;int height =this.hei;
img= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
int pv;int start=0;int end=3;
String line;String str;String enc;
int rc=wid;
try (BufferedReader br = new BufferedReader(new FileReader(bsf))) {
for(int y=0;y<height;y++) {
line = br.readLine();
if(line!=null) {
for(int x=0;x<rc;x++) {
str=line.substring(start,end);start=end;end=end+3; 
enc=encodeString(str);
enc="-1"+enc;BigInteger bi=new BigInteger(enc,10);pv=bi.intValue();


img.setRGB(y, x,pv);}
start=0;end=3;line="";str="";}}
}catch (IOException e) {e.printStackTrace();}
try{ImageIO.write(img, "jpg", com);}
catch(IOException e){System.out.println(e);}} 

public void decode(File input, File output)throws IOException{
BufferedImage img = null;String str;String decode;
try{img = ImageIO.read(com);}catch(IOException e){System.out.println(e);}
BufferedImage img2 = null;
try{img2 = ImageIO.read(com);}catch(IOException e){System.out.println(e);}
int width = img.getWidth();int height = img.getHeight();int p;
try (BufferedWriter bw = new BufferedWriter(new FileWriter(dec,true))) {
for(int y = 0; y < height; y++){
for(int x = 0; x < width; x++){
p = img.getRGB(x,y);
str=Integer.toString(Math.abs(p));
str=str.substring(1, str.length());
     if(str.length()==5) {str="000"+str;} else if(str.length()==4) {str="0000"+str;}else if(str.length()==3) {str="00000"+str;}
else if(str.length()==2) {str="000000"+str;}else if(str.length()==1) {str="0000000"+str;}else if(str.length()==0) {str="00000000"+str;}
decode=decodeString(str);//System.out.println("rgb-->"+p+" str-->"+str+" dec-->"+decode);
bw.write(decode);}bw.newLine();}bw.close();} 
catch (IOException e) {e.printStackTrace();}
try{ImageIO.write(img2, "jpg", new File("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\dec.png"));}
catch(IOException e){System.out.println(e);}} 

public void BSFtoIMG(File inZippedFile, File outUnzippedFile) throws IOException {
GZIPInputStream zis = null;
OutputStreamWriter osw = null;
FileInputStream fis=null;
Base64InputStream bis;
FileOutputStream fos=null;
try {
fis = new FileInputStream(inZippedFile);
fos = new FileOutputStream(outUnzippedFile);
bis = new Base64InputStream(fis);
IOUtils.copy(bis, fos);} 
finally {
IOUtils.closeQuietly(zis);
IOUtils.closeQuietly(osw);}}


}
