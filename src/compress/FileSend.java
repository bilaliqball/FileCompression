package compress;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;



public class FileSend {
public void sendFileViaGmail() {
long start=System.currentTimeMillis();
final String username = "";//enter gmail id
final String password = "";//enter gmail password
Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");
Session session = Session.getInstance(props,new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}});

try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(username));
message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("bilaliqbal038@gmail.com"));
message.setSubject("Testing Subject");
BodyPart messageBodyPart = new MimeBodyPart();
messageBodyPart.setText("Please find the attachment");
Multipart multipart = new MimeMultipart();
multipart.addBodyPart(messageBodyPart);
messageBodyPart = new MimeBodyPart();
String filename = "hello.txt";
DataSource source = new FileDataSource(filename);
messageBodyPart.setDataHandler(new DataHandler(source));
messageBodyPart.setFileName(filename);
multipart.addBodyPart(messageBodyPart);
message.setContent(multipart );
Transport.send(message);
System.out.println("File Shared via Gmail");
} catch (MessagingException e) {throw new RuntimeException(e);}
long end=System.currentTimeMillis();
long diff=(end-start)/1000; System.out.println("execution time: "+diff +" sec");}


public void sendFileViaOutlook() {
long start=System.currentTimeMillis();
final String username = "";//enter outlook id
final String password = "";//enter outlook password
Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.host", "outlook.office365.com");
props.put("mail.smtp.port", "587");
Session session = Session.getInstance(props,new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}});
try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(username));
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bilal.iqbal@curexa.com.pk"));
message.setSubject("Testing Subject");
BodyPart messageBodyPart = new MimeBodyPart();
messageBodyPart.setText("Please find the attachment");
Multipart multipart = new MimeMultipart();
multipart.addBodyPart(messageBodyPart);
messageBodyPart = new MimeBodyPart();
String filename = "hello.txt";
DataSource source = new FileDataSource(filename);
messageBodyPart.setDataHandler(new DataHandler(source));
messageBodyPart.setFileName(filename);
multipart.addBodyPart(messageBodyPart);
message.setContent(multipart );
Transport.send(message);
System.out.println("File Shared via Outlook");
} catch (MessagingException e) {throw new RuntimeException(e);}
long end=System.currentTimeMillis();
long diff=(end-start)/1000; System.out.println("execution time: "+diff +" sec");}


public void sendFileViaOnedrive() throws IOException {
File input=new File("C:\\Users\\bilal.iqbal\\Compression\\Compression\\sheep.jpg");
String output=Resources.userhome+"\\OneDrive - Highnoon Laboratories Limited\\"+input.getName();
File dest=new File(output);
FileUtils.copyFile(input, dest);
System.out.println("File Shared via Onedrive");}

public void sendFiledropbox() throws IOException {
File input=new File("C:\\Users\\bilal.iqbal\\Compression\\Compression\\sheep.jpg");
String output=Resources.userhome+"\\Dropbox\\"+input.getName();
File dest=new File(output);
FileUtils.copyFile(input, dest);
System.out.println("File Shared Via Dropbox");}


//public  void sendFiledropbox1() throws Exception {
//String APP_KEY = "z8op1ir2h58e8u9";
//String APP_SECRET = "gsoffym1em2wge5";
//DbxRequestConfig config = new DbxRequestConfig("berry120_dropbox_example"); //Client name can be whatever you like
//DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
//DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);
//DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();
//String url = webAuth.authorize(webAuthRequest);
//Desktop.getDesktop().browse(new URL(url).toURI());
//String code = JOptionPane.showInputDialog("Please click \"allow\" then enter your access code:");
////code="qVjHlds6X3AAAAAAAAAAO-yP-sR-RrVz7z-PHaVwtX8";
//DbxAuthFinish authFinish = webAuth.finishFromCode(code);
//String accessToken = authFinish.getAccessToken(); //Store this for future use
//DbxClientV2 client = new DbxClientV2(config, accessToken);
//String fileContents = "Hello World!";
//ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
//client.files().uploadBuilder("/Files/testing.txt").uploadAndFinish(inputStream);}

}