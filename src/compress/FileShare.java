package compress;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.apache.commons.io.FileUtils;

public class FileShare {


public void copyFile(File src, File dst) throws IOException {
FileUtils.copyFile(src, dst);System.out.println("File Copied");}


//public void sendAttachmail() throws UnsupportedEncodingException {
//Desktop desktop = Desktop.getDesktop();
//String file="File:///C:/Users/bilal.iqbal/Pictures/FILES/hello.txt";
//file = URLEncoder.encode(file, "UTF-8");
//String uri=new File(file).toURI().toString();
//String mailData = "mailto:bilal.iqbal@curexa.com.pk?subject=Document&attachment="+file;
//System.out.println(mailData);
//try {
//desktop.mail(new URI(mailData));} 
//catch (IOException e1) {e1.printStackTrace();} 
//catch (URISyntaxException e1) {e1.printStackTrace();}}




public void openOutlook() throws IOException {
Desktop desktop = Desktop.getDesktop();
String message = "mailto:bilal.iqbal@curexa.com.pk?subject=First%20Email";
URI uri = URI.create(message);desktop.mail(uri);}

public void openGmail() {
try{
String link="https://mail.google.com/mail/?view=cm&fs=1&tf=1&to=bilal.iqbal@curexa.com.pk";
URI uri = new URI(link);
Desktop dt = Desktop.getDesktop();
dt.browse(uri);}
catch(Exception ex){ex.printStackTrace();}}


public void openOnedrive() {
try{
String link="https://hnl-my.sharepoint.com/personal/bilal_iqbal_curexa_com_pk/_layouts/15/onedrive.aspx";
URI uri = new URI(link);
Desktop dt = Desktop.getDesktop();
dt.browse(uri);}
catch(Exception ex){ex.printStackTrace();}}

public void openGdrive() {
try{
String link="https://drive.google.com/drive/my-drive";
URI uri = new URI(link);
Desktop dt = Desktop.getDesktop();
dt.browse(uri);}
catch(Exception ex){ex.printStackTrace();}}


public void openDropbox() {
try{
String link="https://www.dropbox.com/home";
URI uri = new URI(link);
Desktop dt = Desktop.getDesktop();
dt.browse(uri);}
catch(Exception ex){ex.printStackTrace();}}

public void openWetransfer() {
try{
String link="https://wetransfer.com";
URI uri = new URI(link);
Desktop dt = Desktop.getDesktop();
dt.browse(uri);}
catch(Exception ex){ex.printStackTrace();}}

public void shareFileViaOnedrive() throws IOException {
File source=new File("C:\\Users\\bilal.iqbal\\Compression\\Compression\\hello.txt");
File dest=new File("C:\\Users\\bilal.iqbal\\OneDrive - Highnoon Laboratories Limited\\hello.txt");
FileUtils.copyFile(source, dest);
System.out.println("File Shared via Onedrive");}

public void sendFiledropbox() throws IOException {
File source=new File("C:\\Users\\bilal.iqbal\\Compression\\Compression\\rabbit.jpg");
File dest=new File("C:\\Users\\bilal.iqbal\\Dropbox\\rabbit.jpg");
FileUtils.copyFile(source, dest);
System.out.println("File Shared Via Dropbox");}

}