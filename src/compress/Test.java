package compress;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Test {
public static void main(String args[]) throws IOException {
	File inp=new File("C:\\Users\\bilal.iqbal\\FileCompressions\\Compression\\fileCompression.zip");
	File out=new File("C:\\Users\\bilal.iqbal\\FileCompressions\\Compression\\fileCompression.jar");
	FileUtils.copyFile(inp, out);
}
}
