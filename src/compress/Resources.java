package compress;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


public class Resources {


//public static String username = System.getProperty("user.name");
//public static String userpath="C:\\Users\\"+username;
//public static String defaultpath="C:\\Program Files";
//public String tempPath="";

public static String userhome= System.getProperty("user.home");
public static String curtpath= System.getProperty("user.dir");
	
	
public static String getIcon(String icon) {
String res=userhome+"\\fileCompression\\ico\\"+icon;return res;}

public static String getTemp() {
String tempPath=userhome+"\\fileCompression\\tem";return tempPath;}

public static String getFdocs() {
String ffdocsPath=userhome+"\\fileCompression\\jar\\docs.jar";return ffdocsPath;}

public static String getFmpeg() {
String ffmpegPath=userhome+"\\fileCompression\\jar\\media.jar";return ffmpegPath;}

public static String getFprob() {
String ffprobePath=userhome+"\\fileCompression\\jar\\info.jar";return ffprobePath;}
	
public static String getFplay() {
String ffplayPath=userhome+"\\fileCompression\\jar\\play.jar";return ffplayPath;}



//public static String getIcon(String icon) {
//String res=curtpath+"\\fileCompression\\ico\\"+icon;return res;}
//
//public static String getTemp() {
//String tempPath=curtpath+"\\fileCompression\\tem";return tempPath;}
//
//public static String getFdocs() {
//String ffdocsPath=curtpath+"\\fileCompression\\mac\\docs.jar";return ffdocsPath;}
//
//public static String getFmpeg() {
//String ffmpegPath=curtpath+"\\fileCompression\\mac\\ffmpeg";return ffmpegPath;}
//
//public static String getFprob() {
//String ffprobePath=curtpath+"\\fileCompression\\mac\\ffprobe";return ffprobePath;}
//	
//public static String getFplay() {
//String ffplayPath=curtpath+"\\fileCompression\\mac\\ffplay";return ffplayPath;}
	

public static Color getColor(int systemColorID) {
Display display = Display.getCurrent();
return display.getSystemColor(systemColorID);}












//public static final int TOP_LEFT = 1;
//public static final int TOP_RIGHT = 2;
//public static final int BOTTOM_LEFT = 3;
//public static final int BOTTOM_RIGHT = 4;
//protected static final int LAST_CORNER_KEY = 5;

//@SuppressWarnings("unchecked")
//private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = new Map[LAST_CORNER_KEY];

//public static Image decorateImage(Image baseImage, Image decorator) {
//return decorateImage(baseImage, decorator, BOTTOM_RIGHT);}
//
//public static Image decorateImage(final Image baseImage, final Image decorator, final int corner) {
//if (corner <= 0 || corner >= LAST_CORNER_KEY) {
//throw new IllegalArgumentException("Wrong decorate corner");}
//Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[corner];
//if (cornerDecoratedImageMap == null) {
//cornerDecoratedImageMap = new HashMap<Image, Map<Image, Image>>();
//m_decoratedImageMap[corner] = cornerDecoratedImageMap;}
//
//Map<Image, Image> decoratedMap = cornerDecoratedImageMap.get(baseImage);
//if (decoratedMap == null) {
//decoratedMap = new HashMap<Image, Image>();
//cornerDecoratedImageMap.put(baseImage, decoratedMap);}
//
//Image result = decoratedMap.get(decorator);
//if (result == null) {
//Rectangle bib = baseImage.getBounds();
//Rectangle dib = decorator.getBounds();
//result = new Image(Display.getCurrent(), bib.width, bib.height);
//GC gc = new GC(result);
//gc.drawImage(baseImage, 0, 0);
//     if (corner == TOP_LEFT) {gc.drawImage(decorator, 0, 0);} 
//else if (corner == TOP_RIGHT) {gc.drawImage(decorator, bib.width - dib.width, 0);}
//else if (corner == BOTTOM_LEFT) {gc.drawImage(decorator, 0, bib.height - dib.height);} 
//else if (corner == BOTTOM_RIGHT) {gc.drawImage(decorator, bib.width - dib.width, bib.height - dib.height);}
//gc.dispose();
//decoratedMap.put(decorator, result);}
//return result;}
//
//public static void disposeImages() {
//{
//for (Image image : m_imageMap.values()) {image.dispose();}
//m_imageMap.clear();}
//
//for (int i = 0; i < m_decoratedImageMap.length; i++) {
//Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[i];
//if (cornerDecoratedImageMap != null) {
//for (Map<Image, Image> decoratedMap : cornerDecoratedImageMap.values()) {
//for (Image image : decoratedMap.values()) {
//image.dispose();}
//decoratedMap.clear();}
//cornerDecoratedImageMap.clear();}}}
//
//private static Map<String, Font> m_fontMap = new HashMap<String, Font>();
//
//private static Map<Font, Font> m_fontToBoldFontMap = new HashMap<Font, Font>();
//
//public static Font getFont(String name, int height, int style) {
//return getFont(name, height, style, false, false);}
//
//public static Font getFont(String name, int size, int style, boolean strikeout, boolean underline) {
//String fontName = name + '|' + size + '|' + style + '|' + strikeout + '|' + underline;
//Font font = m_fontMap.get(fontName);
//if (font == null) {
//FontData fontData = new FontData(name, size, style);
//if (strikeout || underline) {
//try {
//Class<?> logFontClass = Class.forName("org.eclipse.swt.internal.win32.LOGFONT"); //$NON-NLS-1$
//Object logFont = FontData.class.getField("data").get(fontData); //$NON-NLS-1$
//if (logFont != null && logFontClass != null) {
//if (strikeout) {
//logFontClass.getField("lfStrikeOut").set(logFont, Byte.valueOf((byte) 1)); //$NON-NLS-1$
//}
//if (underline) {
//logFontClass.getField("lfUnderline").set(logFont, Byte.valueOf((byte) 1)); //$NON-NLS-1$
//}}
//} catch (Throwable e) {
//System.err.println("Unable to set underline or strikeout" + " (probably on a non-Windows platform). " + e); //$NON-NLS-1$ //$NON-NLS-2$
//}
//}
//font = new Font(Display.getCurrent(), fontData);
//m_fontMap.put(fontName, font);
//}
//return font;
//}
//
//public static Font getBoldFont(Font baseFont) {
//Font font = m_fontToBoldFontMap.get(baseFont);
//if (font == null) {
//FontData fontDatas[] = baseFont.getFontData();
//FontData data = fontDatas[0];
//font = new Font(Display.getCurrent(), data.getName(), data.getHeight(), SWT.BOLD);
//m_fontToBoldFontMap.put(baseFont, font);}
//return font;}
//
//public static void disposeFonts() {
//for (Font font : m_fontMap.values()) {
//font.dispose();}
//m_fontMap.clear();
//for (Font font : m_fontToBoldFontMap.values()) {
//font.dispose();}
//m_fontToBoldFontMap.clear();}
//
//private static Map<Integer, Cursor> m_idToCursorMap = new HashMap<Integer, Cursor>();
//
//public static Cursor getCursor(int id) {
//Integer key = Integer.valueOf(id);
//Cursor cursor = m_idToCursorMap.get(key);
//if (cursor == null) {
//cursor = new Cursor(Display.getDefault(), id);
//m_idToCursorMap.put(key, cursor);}
//return cursor;}
//
//public static void disposeCursors() {
//for (Cursor cursor : m_idToCursorMap.values()) {
//cursor.dispose();}
//m_idToCursorMap.clear();}
//
//public static void dispose() {
//disposeColors();
//disposeImages();
//disposeFonts();
//disposeCursors();}
}