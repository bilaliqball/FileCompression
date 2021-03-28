package compress;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;


public class Window {


//public JWindow window;
//public JLabel label;
//public void initWindow() {window=new JWindow();
//String gif=Resources.getIcon("loading5.gif");
//ImageIcon icon=	new ImageIcon(gif);
//window.getContentPane().add(new JLabel("Processing", icon, SwingConstants.CENTER));
//window.setBounds(400, 200, 200, 200);}
//public void showWindow(int i) {
//window.setVisible(true);
//window.setAlwaysOnTop(true);
//window.toFront();
//window.requestFocus();
//window.setAlwaysOnTop(false);
//window.setLocationRelativeTo(null);}
//public void closeWindow() {window.setVisible(false);window.dispose();}


//public JWindow window;
//public JLabel label;
//public void initWindow() {
//window=new JWindow();
//window.setSize(400, 100);
//String gif=Resources.getIcon("loading9.gif");
//Icon icon = new ImageIcon(gif);
//label = new JLabel("", icon, JLabel.LEFT);window.getContentPane().add(label);
//window.getContentPane().setBackground(java.awt.Color.WHITE);}
//public void showWindow(int progress) throws HeadlessException {
//label.setText("Processed:"+progress);
//window.setVisible(true);
//window.setAlwaysOnTop(true);
////window.toFront();
////window.requestFocus();
////window.setAlwaysOnTop(false);
//window.setLocationRelativeTo(null);}
//public void closeWindow() {window.setVisible(false);window.dispose();}
	
	@SuppressWarnings("unused")
	public void killtasks() throws InterruptedException {
//		String terminate[]={"taskkill","/F","/IM","ffmpeg.jar"};
//		Process process=null;
//		try {
//		System.out.println("Terminating process");
//		ProcessBuilder ter = new ProcessBuilder(terminate).inheritIO();
//		process=ter.start();
//		}catch (IOException e) { e.printStackTrace();}

try{
	String[] cmd = new String[3];
	cmd[0] = "cmd.exe";
cmd[1] = "/C";
cmd[2] = "taskkill /F /IM ffmpeg.jar";
Process closeProcess= Runtime.getRuntime().exec(cmd);
System.out.println("Processing Closed");}
catch (Exception ex) {ex.printStackTrace();}
		}
	
	
public JWindow progressWindow;
public JLabel progressLabel;
public void initProgressWindow() {
progressWindow=new JWindow();
progressWindow.setSize(400, 200);
Icon icon =new ImageIcon(Resources.getIcon("compress.gif"));
progressWindow.getContentPane().setLayout(null);
progressLabel = new JLabel("", icon, JLabel.LEFT);
progressLabel.setBounds(0, 0, 369, 200);progressWindow.getContentPane().add(progressLabel);
JButton min = new JButton("");
min.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
progressWindow.setVisible(false);}});
min.setIcon(new ImageIcon(Resources.getIcon("minimize.png")));
min.setBounds(379, 0, 16, 16);
min.setOpaque(false);
min.setContentAreaFilled(false);
min.setBorderPainted(false);
progressWindow.getContentPane().add(min);
clo = new JButton("");
clo.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
try {killtasks();} catch (InterruptedException e1) {e1.printStackTrace();}}});
clo.setIcon(new ImageIcon(Resources.getIcon("close.png")));
clo.setBounds(351, 0, 16, 16);
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
System.out.println("One Drive");
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
System.out.println("");
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


//public JWindow window;
//public JProgressBar bar;    
//public void initWindow() {
//window=new JWindow();
//window.getContentPane().setForeground(Color.WHITE);
//JLabel label=new JLabel("PROCESSING");
//label.setForeground(Color.WHITE);
//label.setText("Processing:");
//label.setBounds(207, 63, 100, 20);
//bar=new JProgressBar(0,100);    
//bar.setForeground(SystemColor.activeCaption);
//bar.setBounds(10,22,480,30);         
//bar.setValue(0);    
//bar.setStringPainted(true);    
//window.getContentPane().add(bar);   window.getContentPane().add(label); 
//window.setSize(500,100);    
//window.getContentPane().setLayout(null); 
//window.getContentPane().setBackground(java.awt.Color.DARK_GRAY);}
//public void showWindow(int i){
//window.setVisible(true);
//window.setAlwaysOnTop(true);
////window.toFront();
////window.requestFocus();
////window.setAlwaysOnTop(false);
//window.setLocationRelativeTo(null);  bar.setValue(i); }    
//public void closeWindow() {window.setVisible(false);window.dispose();}


//	
//public void init() throws IOException {
//String userpath=Resources.userpath;
//String dir=userpath+"\\fileCompression_";
//if(new File(dir).exists()) {System.out.println("exists");}
//else {
//File src=new File("fileCompression_");System.out.println("Source: "+src.getAbsolutePath());
//File des=new File(userpath);System.out.println("Dest: "+des.getAbsolutePath());
//FileUtils.copyDirectoryToDirectory(src,des);System.out.println("Copied");}}	
	
	
	
protected Shell shell;
public Display display;
private JButton clo;

public static void main(String[] args) throws IOException {
Window window = new Window();window.initProgressWindow();

for (int i=0; i<=200; i++) {try {Thread.sleep (100);} catch (Throwable th) {}
window.showProgressWindow(i);}window.closeProgressWindow();
	

//window.initshareasWindow();
//for (int i=0; i<=100; i++) {try {Thread.sleep (100);} catch (Throwable th) {}
//window.showshareasWindow();}window.closeshareasWindow();


//try {window.open();}catch (Exception e) {e.printStackTrace();}

}

public int confirmClose(){
String mes="Are you sure you Want to close window?";
JFrame jf=new JFrame();
jf.setAlwaysOnTop(true);
int response = JOptionPane.showConfirmDialog(jf,mes, "Close Window", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
return response;}




public void open() {
display = Display.getDefault();
shell = new Shell();
shell.addShellListener(new ShellAdapter() {
@Override
public void shellClosed(ShellEvent e) {
e.doit=false;
int n=confirmClose();
if(n==0) {e.doit=true;System.exit(0);}}});

shell.setSize(457, 350);
shell.setText("SWT Application");

//org.eclipse.swt.graphics.Color color=display.getSystemColor(SWT.COLOR_BLUE);
//org.eclipse.swt.graphics.Image image = new Image(display, "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\code.jpg");
//org.eclipse.swt.graphics.Color color_=Resources.getColor(9);
//org.eclipse.swt.graphics.Image image_=Resources.getImage("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\code.jpg");

//shell.setBackground(color);
//shell.setBackgroundImage(image);
//shell.setBackground(color_);
//shell.setBackgroundImage(image_);
Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
int x = (int) ((dimension.getWidth() - 700) / 2);
int y = (int) ((dimension.getHeight() - 480) / 2);
shell.setLocation(x, y);

createContents();
shell.open();shell.layout();
while (!shell.isDisposed()) {
//shell.open();shell.layout();
if (!display.readAndDispatch()) {display.sleep();}
}}


protected void createContents() {
Button btnNewButton = new Button(shell, SWT.NONE);
btnNewButton.addMouseTrackListener(new MouseTrackAdapter() {

@Override
public void mouseEnter(MouseEvent e) {
System.out.println("...");
initshareasWindow();
//for (int i=0; i<=100; i++) {try {Thread.sleep (100);} catch (Throwable th) {} showshareasWindow();}
showshareasWindow();
//closeshareasWindow();
}	

@Override
public void mouseExit(MouseEvent e) {}
});


//btnNewButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
//btnNewButton.setBackgroundImage(SWTResourceManager.getImage("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\test.jpg"));
//btnNewButton.setImage(SWTResourceManager.getImage("C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\flowers.jpg"));

btnNewButton.addSelectionListener(new SelectionAdapter() {
@Override
public void widgetSelected(SelectionEvent e) {
//btnNewButton.setImage(SWTResourceManager.getImage("C:\\Users\\bilal.iqbal\\fileCompression\\res\\loading4.gif"));
 org.eclipse.swt.graphics.Image image = new Image(display, "C:\\Users\\bilal.iqbal\\Pictures\\FILES\\Pictures\\code.jpg");
 btnNewButton.setImage(image);
 

 
}});
//btnNewButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
btnNewButton.setBounds(142, 118, 167, 58);
btnNewButton.setText("New Button");
		
	}
}
