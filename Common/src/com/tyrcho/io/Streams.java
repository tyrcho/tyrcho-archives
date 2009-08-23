package com.tyrcho.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public final class Streams {
	private static final int BUFFER_SIZE = 1024;

	private Streams() {
	}

	public static String readFile(String filename)
			throws FileNotFoundException, IOException {
		return readStream(new FileInputStream(filename));
	}

	/**
	 * Construit une nouvelle archive contenant une liste de fichiers.
	 * 
	 * @param jarFile
	 *            le fichier archive à créer
	 * @param files
	 *            un tableau de fichiers à y insérer; les répertoires ne sont
	 *            pas supportés
	 */
	public static void addFilesToJar(File jarFile, File[] files)
			throws FileNotFoundException, IOException {
		JarOutputStream jarOutputStream = new JarOutputStream(
				new FileOutputStream(jarFile));
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			FileInputStream fileInputStream = new FileInputStream(file);
			JarEntry jarEntry = new JarEntry(file.getName());
			jarOutputStream.putNextEntry(jarEntry);
			streamCopy(fileInputStream, jarOutputStream);
			fileInputStream.close();
		}
		jarOutputStream.flush();
		jarOutputStream.close();
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		File localFolder = new File("d:/dev/temp/xml");
		File[] listFiles = localFolder.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.isFile();
			}

		});
		addFilesToJar(new File("d:/dev/temp/test.jar"), listFiles);
	}

	public static void streamCopy(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int read = 0;
		while (read >= 0) {// -1 indique EOF
			read = in.read(buffer);
			if (read > 0) {
				out.write(buffer, 0, read);
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		out.flush();
	}

	public static void readerCopy(Reader in, Writer out) throws IOException {
		char[] buffer = new char[BUFFER_SIZE];
		int read = 0;
		while (read >= 0) {// -1 indique EOF
			read = in.read(buffer);
			if (read > 0) {
				out.write(buffer, 0, read);
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		out.flush();
	}

	public static void writeToFile(byte[] data, File file) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(data);
		OutputStream outputStream = new FileOutputStream(file);
		streamCopy(inputStream, outputStream);
		outputStream.close();
	}

	public static void writeToFile(String data, File file) throws IOException {
		Reader reader = new StringReader(data);
		Writer writer = new FileWriter(file);
		readerCopy(reader, writer);
	}

	public static byte[] readFromFile(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		byte[] data = readFromStream(inputStream);
		inputStream.close();
		return data;
	}

	public static byte[] readFromStream(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		streamCopy(inputStream, outputStream);
		inputStream.close();
		return outputStream.toByteArray();
	}

	public static String getExtension(File file) {
		String[] fileParts = file.getName().split("[.]");
		return fileParts[fileParts.length - 1];
	}

	public static void defaultOpenFile(File file) throws IOException {
		Runtime.getRuntime().exec(
				("rundll32 url.dll,FileProtocolHandler " + file
						.getAbsolutePath()));
	}

	public static String readStream(InputStream inputStream) throws IOException {
		StringWriter stringWriter = new StringWriter();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		readerCopy(inputStreamReader, stringWriter);
		return stringWriter.toString();
	}

	public static String readTextResource(String resourceName, Class<?> context)
			throws IOException {
		InputStream resourceAsStream = context
				.getResourceAsStream(resourceName);
		if (resourceAsStream == null) {
			throw new IOException("Resource not found : " + resourceName);
		}
		return readStream(resourceAsStream);
	}

	public static String readTextFile(String file)
			throws FileNotFoundException, IOException {
		return readStream(new FileInputStream(file));
	}

	public static InputStream inputStream(String inputXml) throws IOException {
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream inputStream = new PipedInputStream(out);
		readerCopy(new StringReader(inputXml), new OutputStreamWriter(out));
		out.close();
		return inputStream;
	}
}
