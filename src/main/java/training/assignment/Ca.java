package training.assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public class Ca {
	public By locate(String element) throws IOException {

		Properties prop2 = new Properties();

		prop2.load(new FileInputStream("src/main/resources/element.properties"));
		String abc = prop2.getProperty(element);
		String type = abc.split(":")[0];
		String val = abc.split(":")[1];
		if (type.compareTo("cssSelector") == 0) {
			return By.cssSelector(val);
		}
		if (type.compareTo("id") == 0) {
			return By.id(val);
		}
		if (type.compareTo("xpath") == 0) {
			return By.xpath(val);
		}
		return null;
	}
}
