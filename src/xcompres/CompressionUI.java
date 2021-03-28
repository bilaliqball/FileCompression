package xcompres;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.apache.commons.io.FileUtils;


public class CompressionUI extends JFrame {
public static void main(String args[]) throws IOException{CompressionUI i =new CompressionUI();}
    



public String file=null;public void setFile(String f) {this.file=f;}
public String fact=null;public void setFact(String f) {this.fact=f;}
public String form=null;public void setForm(String f) {this.form=f;}




public JLabel label;
public JButton upload;
public JButton compress;
public JButton convert;
public JComboBox factor;
public JComboBox format;

public JPanel motherPanel;
public JPanel buttonPanel;

public void init() throws IOException {
String username = System.getProperty("user.name");
String dir = "C:\\Users\\"+username+"\\compress.exe";
String fil="C:\\Users\\"+username+"\\compression.exe";
if(new File(dir).exists()&& new File(fil).exists()) {System.out.println("exists");}
else {
FileUtils.copyFile(new File("resources/compression.exe"),new File( "C:\\Users\\"+username+"\\compression.exe"));
FileUtils.copyFile(new File("resources/compress.exe"),new File( "C:\\Users\\"+username+"\\compress.exe"));
}}

public CompressionUI () throws IOException {
	init();
String factors[]= {"Low","Normal","High"};
String formats[]= {"Audio","Video","MP3","MP4","Images","Pages"};
upload = new JButton("Upload File");
compress = new JButton("Compress File");
convert = new JButton("Convert File");
factor=new JComboBox(factors);
format=new JComboBox(formats);


label=new JLabel("...");
label.setLocation(0,0);
label.setSize(100, 30);
label.setHorizontalAlignment(0);


motherPanel = new JPanel();
motherPanel.setLayout(new BoxLayout(motherPanel, BoxLayout.Y_AXIS));



buttonPanel = new JPanel();buttonPanel.setPreferredSize(new Dimension(160, 35));
buttonPanel.add(upload);
buttonPanel.add(factor);buttonPanel.add(compress);
buttonPanel.add(format);buttonPanel.add(convert);
buttonPanel.add(label);



UploadButton up = new UploadButton();upload.addActionListener(up);
FactorMenu fa=new FactorMenu();factor.addActionListener(fa);
CompressButton cm=new CompressButton();compress.addActionListener(cm);
FormatMenu fo=new FormatMenu();format.addActionListener(fo);
ConvertButton cn=new ConvertButton();convert.addActionListener(cn);


motherPanel.add(buttonPanel);

add(motherPanel);
setTitle("Compression");
setSize(500, 400);
setLocationByPlatform(true);
setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
setVisible(true);}



public void FileChooser(){
String username = System.getProperty("user.name");
String path = "C:\\Users\\"+username;
JFileChooser fileChooser = new JFileChooser();
File dir=new File(path);
fileChooser.setCurrentDirectory(dir);
int result = fileChooser.showOpenDialog(this);
if (result == JFileChooser.APPROVE_OPTION) {
File selectedFile = fileChooser.getSelectedFile();
setFile(selectedFile.getAbsolutePath());}}
    




public class UploadButton implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) { 
FileChooser();label.setText("Uploaded");
}}


public class FactorMenu implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) { 
String fa=(String)factor.getSelectedItem();
setFact(fa.toLowerCase());
label.setText("Facotor: "+ fa);
}}

public class FormatMenu implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) { 
String fo=(String)format.getSelectedItem();
setForm(fo.toLowerCase());
label.setText("Format: "+fo);
}}

public class CompressButton implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) {
String username = System.getProperty("user.name");
String compressor= "C:\\Users\\"+username+"\\compression.exe";
String opertaion="compress";
String filename=file;
String factor="normal";
try {
Process process = new ProcessBuilder(
compressor,
opertaion,
filename,
factor).start();
}catch (IOException ex) { ex.printStackTrace();}
label.setText("Compressed");
}}

public class ConvertButton implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) { 
String username = System.getProperty("user.name");
String convertor= "C:\\Users\\"+username+"\\compression.exe";
String opertaion="convert";
String filename=file;
String format=form;
try {
Process process = new ProcessBuilder(
convertor,
opertaion,
filename,
format).start();
}catch (IOException ex) { ex.printStackTrace();}
	label.setText("converted");
}}

}
