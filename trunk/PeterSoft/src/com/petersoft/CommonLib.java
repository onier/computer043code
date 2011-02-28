package com.petersoft;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class CommonLib {
	public static byte[] readFileByte(File file, long offset, int size) {
		return readFileByte(file.getAbsolutePath(), offset, size);
	}

	public static byte[] readFileByte(String filepath, long offset, int size) {
		try {
			RandomAccessFile br = new RandomAccessFile(filepath, "r");
			br.seek(offset);
			byte bytes[] = new byte[size];
			br.readFully(bytes);
			br.close();
			return bytes;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Long string2decimal(String s) {
		s = s.trim().toLowerCase();
		if (s.startsWith("0x")) {
			return Long.parseLong(s.substring(2), 16);
		} else {
			return Long.parseLong(s, 10);
		}
	}

	public static long convertFilesize(String filesize) {
		if (filesize.length() == 0) {
			return 0;
		} else if (filesize.length() == 1) {
			return Long.parseLong(filesize);
		} else if (filesize.substring(filesize.length() - 1).toLowerCase().equals("t")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 1)) * 1024 * 1024 * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 2).toLowerCase().equals("tb")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 2)) * 1024 * 1024 * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 1).toLowerCase().equals("g")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 1)) * 1024 * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 2).toLowerCase().equals("gb")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 2)) * 1024 * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 1).toLowerCase().equals("m")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 1)) * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 2).toLowerCase().equals("mb")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 2)) * 1024 * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 1).toLowerCase().equals("k")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 1)) * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else if (filesize.substring(filesize.length() - 2).toLowerCase().equals("kb")) {
			try {
				return Long.parseLong(filesize.substring(0, filesize.length() - 2)) * 1024;
			} catch (Exception ex) {
				return -1;
			}
		} else {
			try {
				return Long.parseLong(filesize);
			} catch (Exception ex) {
				return -1;
			}
		}
	}

	public static void centerDialog(JFrame dialog) {
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = dialog.getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}

		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public static void centerDialog(JDialog dialog) {
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = dialog.getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}

		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public static void centerDialog(JFileChooser chooser) {
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = chooser.getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}

		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		chooser.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	private static char numberOfFile_runningChar[] = { '-', '\\', '|', '/' };

	public static int numberOfFile(File node, boolean isSlient) {
		int x = 0;
		for (File file : node.listFiles()) {
			if (!isSlient) {
				System.out.print("\r" + numberOfFile_runningChar[x % numberOfFile_runningChar.length]);
			}
			if (file.isDirectory()) {
				x += numberOfFile(file, isSlient);
			} else {
				x++;
			}
		}
		System.out.print("\r \r");
		return x;
	}

	public static int getNumberOfFileAndDirectory(String file) {
		return getNumberOfFileAndDirectory(new File(file));
	}

	public static int getNumberOfFileAndDirectory(File file) {
		if (file.isFile()) {
			return -1;
		} else {
			int num = file.listFiles().length;
			for (int x = 0; x < file.listFiles().length; x++) {
				if (file.listFiles()[x].isDirectory()) {
					num += getNumberOfFileAndDirectory(file.listFiles()[x]);
				}
			}
			return num;
		}
	}

	public static int getNumberOfFile(String file) {
		return getNumberOfFile(new File(file));
	}

	public static int getNumberOfFile(File file) {
		if (file.isFile()) {
			return -1;
		} else {
			int num = 0;
			for (int x = 0; x < file.listFiles().length; x++) {
				if (file.listFiles()[x].isDirectory()) {
					num += getNumberOfFile(file.listFiles()[x]);
				} else if (file.listFiles()[x].isFile()) {
					num++;
				}
			}
			return num;
		}
	}

	public static int getNumberOfDirectory(String file) {
		return getNumberOfDirectory(new File(file));
	}

	public static int getNumberOfDirectory(File file) {
		if (file.isFile()) {
			return -1;
		} else {
			int num = 0;
			for (int x = 0; x < file.listFiles().length; x++) {
				if (file.listFiles()[x].isDirectory()) {
					num += getNumberOfDirectory(file.listFiles()[x]);
					num++;
				}
			}
			return num;
		}
	}

	public static byte readFileByte(File file, long offset) {
		return readFileByte(file.getAbsolutePath(), offset);
	}

	public static byte readFileByte(String filepath, long offset) {
		try {
			RandomAccessFile br = new RandomAccessFile(filepath, "r");
			br.seek(offset);
			byte b = br.readByte();
			br.close();
			return b;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static boolean deleteDirectory(String path) {
		if (new File(path).exists()) {
			File[] files = new File(path).listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i].getPath());
				} else {
					files[i].delete();
				}
			}
		}
		return (new File(path).delete());
	}

	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i].getPath());
				} else {
					files[i].delete();
				}
			}
		}
		return path.delete();
	}

	public static byte[] readFile(File file, long offset, int size) {
		return readFile(file.getAbsolutePath(), offset, size);
	}

	public static byte[] readFile(String filepath, long offset, int size) {
		try {
			RandomAccessFile br = new RandomAccessFile(filepath, "r");
			byte data[] = new byte[size];
			br.seek(offset);
			br.read(data);
			br.close();
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Object[] reverseArray(Object objects[]) {
		try {
			List l = Arrays.asList(objects);
			java.util.Collections.reverse(l);
			/** and if you must back to the array again */
			objects = l.toArray();
			return objects;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String convertFilesize(long filesize) {
		if (filesize < 1024) {
			return filesize + " bytes";
		} else if (filesize >= 1024 && filesize < 1024 * 1024) {
			return filesize / 1024 + " KB";
		} else {
			return filesize / 1024 / 1024 + " MB";
		}
	}

	public static void printSecond(long second) {
		if (second > 60 * 60 * 24 * 365) {
			System.out.println(second / (60 * 60 * 24 * 365) + " years, " + second + " seconds");
		} else if (second > 60 * 60 * 24) {
			System.out.println(second / (60 * 60 * 24) + " days, " + second + " seconds");
		} else if (second > 60 * 60) {
			System.out.println(second / (60 * 60) + " hours, " + second + " seconds");
		} else if (second > 60) {
			System.out.println(second / (60) + " minutes, " + second + " seconds");
		} else {
			System.out.println(second + " seconds");
		}
	}

	public static String getRelativePath(File file) {
		return getRelativePath(file.getAbsolutePath());
	}

	public static String getRelativePath(String file) {
		return file.substring(System.getProperty("user.dir").length());
	}

	public static void highlightUsingRegularExpression(JTextComponent textComp, String regularExpression, Color color) {
		removeHighlights(textComp);
		Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(color);

		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			String inputStr = textComp.getText();
			Pattern pattern = Pattern.compile(regularExpression);
			Matcher matcher = pattern.matcher(inputStr);
			boolean matchFound = matcher.find();
			while (matchFound) {
				// System.out.println(matcher.start() + "-" + matcher.end());
				for (int i = 0; i <= matcher.groupCount(); i++) {
					String groupStr = matcher.group(i);
					// System.out.println(i + ":" + groupStr);
				}
				hilite.addHighlight(matcher.start(), matcher.end(), myHighlightPainter);
				if (matcher.end() + 1 <= inputStr.length()) {
					matchFound = matcher.find(matcher.end());
				} else {
					break;
				}
			}
		} catch (BadLocationException e) {
		}
	}

	public static void highlight(JTextComponent textComp, String pattern, Color color) {
		// First remove all old highlights
		removeHighlights(textComp);
		Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(color);

		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			while ((pos = text.indexOf(pattern, pos)) >= 0) {
				// Create highlighter using private painter and apply around
				// pattern
				hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
		} catch (BadLocationException e) {
		}
	}

	// Removes only our private highlights
	public static void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	public static void printFilesize(long fileOffset, long filesize) {
		System.out.print("\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r\r");
		if (fileOffset < 1024) {
			System.out.print(fileOffset + " /" + filesize + " bytes, " + fileOffset * 100 / filesize + "%            ");
		} else if (fileOffset >= 1024 & fileOffset < 1024 * 1024) {
			System.out.print(fileOffset / 1024 + " /" + filesize / 1024 + " KB, " + fileOffset * 100 / filesize + "%          ");
		} else if (fileOffset >= 1024 * 1024) {
			System.out.print(fileOffset / 1024 / 1024 + "MB (" + fileOffset / 1024 + " KB) / " + filesize / 1024 / 1024 + " MB, " + fileOffset * 100 / filesize + "%         ");
		}
	}

	public static String trimByteZero(String str) {
		if (str.indexOf(0) == -1) {
			return str;
		} else {
			return str.substring(0, str.indexOf(0));
		}
	}

	static class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	public static void writeFile(String filepath, String content) {
		try {

			FileWriter fstream = new FileWriter(filepath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void appendFile(String filepath, String content) {
		try {
			FileOutputStream file = new FileOutputStream(filepath, true);
			DataOutputStream out = new DataOutputStream(file);
			out.writeBytes(content);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String empty(Object str) {
		if (str == null) {
			return "";
		} else {
			return str.toString();
		}
	}

	public static String runCommand(String command) {
		StringBuffer sb = new StringBuffer(4096);
		try {
			String s;
			Process p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				sb.append(s);
				sb.append(System.getProperty("line.separator"));
			}
			stdInput.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	public static void captureComponentToJpeg(Component c, File destFile) throws IOException {
		BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
		c.paint(image.createGraphics());
		ImageIO.write(image, "JPEG", destFile);
	}

	public static String getWebpage(String url) {
		String content = "";
		if (!url.trim().toLowerCase().startsWith("http://")) {
			url = "http://" + url;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				content += line + "\n";
			}

			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return content;
	}
}
