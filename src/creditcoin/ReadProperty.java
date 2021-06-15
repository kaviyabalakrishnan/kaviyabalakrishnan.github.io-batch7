package creditcoin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperty {
	InputStream inputStream;
	public static void main(String[] args) throws IOException {
//		ReadProperty property=new ReadProperty();
//		System.out.println(property.getURLDetails());

	}

	public String getURLDetails() throws IOException {
		String ipAddress = "";
		String portNumber = "";
		try {
			Properties prop = new Properties();
			String propFileName = "url.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
//			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			ipAddress = prop.getProperty("ip");
			portNumber = prop.getProperty("port");
			System.out.println(ipAddress);
			System.out.println(portNumber);
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return "http://"+"10.0.0.21"+":"+5000+"/";
	}

}
