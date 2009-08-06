package com.tyrcho.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Streams {
	public static String readStream(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder=new StringBuilder();
		while(reader.ready()) {
			builder.append(reader.readLine());
			builder.append("\r\n");
		}
		return builder.toString();
	}
}
