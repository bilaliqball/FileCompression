package image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
public class DifferencesIMG {
    
 
public static void main(String[] args) throws IOException {
String image1="C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\rabbit.jpg";
String image2="C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\rabbit__.jpg";
String image3="C:\\Users\\bilal.iqbal\\Pictures\\FILES\\sample\\rabbit1.png";
			
ImageIO.write(
getDifferenceImage(
ImageIO.read(new File(image1)),
ImageIO.read(new File(image2))),
"png",new File(image3));
	
	
//double p = getDifferencePercent(
//ImageIO.read(new File(image1)), 
//ImageIO.read(new File(image2)));
//System.out.println("diff percent: " + p);
//System.out.println("diff percent: " + p*100);

}
 

 



public static BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
int width1 = img1.getWidth(); int THRESHOLD=3;
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
return outImg;}


public static BufferedImage getDifferenceImage2(BufferedImage img1, BufferedImage img2) {
final int w = img1.getWidth(),
h = img1.getHeight(), 
highlight = Color.MAGENTA.getRGB();
final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
for (int i = 0; i < p1.length; i++) {
System.out.println("P1 "+p1[i]+" P2: "+p2[i]);
if (p1[i] != p2[i]) {p1[i] = highlight;}}
final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
out.setRGB(0, 0, w, h, p1, 0, w);
return out;}

@SuppressWarnings("unused")
private static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
int width = img1.getWidth();
int height = img1.getHeight();
int width2 = img2.getWidth();
int height2 = img2.getHeight();
if (width != width2 || height != height2) {
throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));}
 long diff = 0;int rgb1=0;int rgb2=0;
int r1,g1,b1,r2,g2,b2;
for (int y = 0; y < height; y++) {
for (int x = 0; x < width; x++) {
rgb1=img1.getRGB(x, y);rgb2= img2.getRGB(x, y);
r1 = (rgb1 >> 16) & 0xff;
g1 = (rgb1 >>  8) & 0xff;
b1 =  rgb1        & 0xff;
r2 = (rgb2 >> 16) & 0xff;
g2 = (rgb2 >>  8) & 0xff;
b2 =  rgb2        & 0xff;
diff += Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);}}
long maxDiff = 3L * 255 * width * height;
return 100.0 * diff / maxDiff;}


}