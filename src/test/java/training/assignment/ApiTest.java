package training.assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Properties;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiTest {
	@Test
	public void verify() throws IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/main/resources/context.properties"));
		String name1 = "f=" + prop.getProperty("ComputerName");
		Response path1 = given().pathParam("compno1", name1).when()
				.get("http://computer-database.gatling.io/computers?{compno1}");
		String name = path1.htmlPath().getString("html.body.section.table.tbody.tr.td[0].a.@href");
		String param = name.split("/")[2];
		Response path = given().pathParam("compno", param).when()
				.get("http://computer-database.gatling.io/computers/{compno}");

		path.then().body("html.body.section.form.fieldset.div.div[0].input.@value",
				containsString(prop.getProperty("ComputerName")));
		path.then().body("html.body.section.form.fieldset.div.div[1].input.@value",
				containsString(prop.getProperty("IntroducedDate")));
		path.then().body("html.body.section.form.fieldset.div.div[2].input.@value",
				containsString(prop.getProperty("DiscontinuedDate")));
		path.then().body("html.body.section.form.fieldset.div.div[3].select.option[3]",
				containsString(prop.getProperty("Company")));
	}

	@Test
	public void verifyThroughList() throws ParseException, FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/main/resources/context.properties"));
		String name = "f=" + prop.getProperty("ComputerName");
		Response path = given().pathParam("compno", name).when()
				.get("http://computer-database.gatling.io/computers?{compno}");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = prop.getProperty("IntroducedDate");
		String date2 = prop.getProperty("DiscontinuedDate");
		String NEW_FORMAT = "dd MMM yyyy";
		Date d1 = formatter.parse(date1);
		Date d2 = formatter.parse(date2);
		formatter.applyPattern(NEW_FORMAT);
		path.then().body("html.body.section.table.tbody.tr.td[0].a", containsString(prop.getProperty("ComputerName")));
		path.then().body("html.body.section.table.tbody.tr.td[1]", containsString(formatter.format(d1)));
		path.then().body("html.body.section.table.tbody.tr.td[2]", containsString(formatter.format(d2)));
		path.then().body("html.body.section.table.tbody.tr.td[3]", containsString(prop.getProperty("Company")));

	}

}
