package compress;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JWindow;

public class SendAs {

	FileSend send;


	

	
	
public void send() {
send=new FileSend();
initshareasWindow();
showshareasWindow();}
	
	
public JWindow shareasWindow;
public void initshareasWindow() {
shareasWindow=new JWindow();
shareasWindow.setSize(800, 150);
shareasWindow.getContentPane().setBackground(java.awt.Color.WHITE);
shareasWindow.getContentPane().setLayout(null);

JButton onedrive = new JButton("");
onedrive.setToolTipText("Share file via OneDrive");
onedrive.setIcon(new ImageIcon(Resources.getIcon("onedrive.png")));
onedrive.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
System.out.println("One Drive");try {
send.sendFileViaOnedrive();} catch (IOException e1) {e1.printStackTrace();}
closeshareasWindow();closeshareasWindow();}});
onedrive.setBounds(3, 11, 120, 120);
onedrive.setOpaque(false);
onedrive.setContentAreaFilled(false);
onedrive.setBorderPainted(false);
shareasWindow.getContentPane().add(onedrive);



JButton gdrive = new JButton("");
gdrive.setToolTipText("Share File Via GDrive");
gdrive.setIcon(new ImageIcon(Resources.getIcon("gdrive.png")));
gdrive.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent arg0) {
System.out.println("G Drive");
closeshareasWindow();}});
gdrive.setBounds(124, 11, 120, 120);
gdrive.setOpaque(false);
gdrive.setContentAreaFilled(false);
gdrive.setBorderPainted(false);
shareasWindow.getContentPane().add(gdrive);



JButton outlook = new JButton("");
outlook.setToolTipText("Share file via Outlook");
outlook.setIcon(new ImageIcon(Resources.getIcon("outlook.png")));
outlook.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
System.out.println("Outlook Mail");
send.sendFileViaOutlook();
closeshareasWindow();}});
outlook.setBounds(254, 11, 120, 120);
outlook.setOpaque(false);
outlook.setContentAreaFilled(false);
outlook.setBorderPainted(false);
shareasWindow.getContentPane().add(outlook);

JButton gmail = new JButton("");
gmail.setToolTipText("Share File Via Gmail");
gmail.setIcon(new ImageIcon(Resources.getIcon("gmail.png")));
gmail.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
System.out.println("G mail");
send.sendFileViaGmail();
closeshareasWindow();}});
gmail.setBounds(384, 11, 120, 120);
gmail.setOpaque(false);
gmail.setContentAreaFilled(false);
gmail.setBorderPainted(false);
shareasWindow.getContentPane().add(gmail);



JButton dropbox = new JButton("");
dropbox.setToolTipText("Share file via dropbox");
dropbox.setIcon(new ImageIcon(Resources.getIcon("dropbox.png")));
dropbox.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
System.out.println("dropBox");
try {send.sendFiledropbox();} catch (IOException e1) {e1.printStackTrace();}
closeshareasWindow();}});
dropbox.setOpaque(false);
dropbox.setContentAreaFilled(false);
dropbox.setBorderPainted(false);
dropbox.setBounds(523, 11, 120, 120);
shareasWindow.getContentPane().add(dropbox);


JButton wetransfer = new JButton("");
wetransfer.setToolTipText("Share file via WeTransfer");
wetransfer.setIcon(new ImageIcon(Resources.getIcon("wetransfer.png")));
wetransfer.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
System.out.println("We Transfer");
closeshareasWindow();}});
wetransfer.setOpaque(false);
wetransfer.setContentAreaFilled(false);
wetransfer.setBorderPainted(false);
wetransfer.setBounds(653, 11, 120, 120);
shareasWindow.getContentPane().add(wetransfer);


JButton closeButton = new JButton("");
closeButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
closeshareasWindow();}});
closeButton.setIcon(new ImageIcon(Resources.getIcon("close.png")));
closeButton.setBounds(780, 0, 20, 23);
closeButton.setOpaque(false);
closeButton.setContentAreaFilled(false);
closeButton.setBorderPainted(false);
shareasWindow.getContentPane().add(closeButton);}

public void showshareasWindow() throws HeadlessException {
shareasWindow.setVisible(true);
shareasWindow.setAlwaysOnTop(true);
shareasWindow.setLocationRelativeTo(null);}
public void closeshareasWindow() {shareasWindow.setVisible(false);shareasWindow.dispose();}

public static void main(String args[]) {
new SendAs().send();}

}
