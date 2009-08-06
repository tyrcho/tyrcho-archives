package snippet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

import com.tyrcho.io.Streams;

public class Snippet {
	public static void main(String[] args) throws IOException {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "admin"
						.toCharArray());
			}
		});
		URL url = new URL(
				"http://127.0.0.1:8080/drools-guvnor/org.drools.guvnor.Guvnor/webdav/packages/properties/props.conf");
		
		Properties properties = new Properties();
		properties.load(url.openStream());
		System.out.println(properties);
	}
}
