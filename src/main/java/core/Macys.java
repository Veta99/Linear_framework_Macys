package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Macys {
	public static void main(String[] args) throws IOException {
		String url = "http://www.macys.com";
		// String url = "http://www1.qa8codemacys.fds.com";

		String csvFile = "C:/Workspace/Linear_framework_Macys/src/main/resources/csv.txt";
		BufferedReader br = null;
		String line = null;
		String text_case_id = null;
		String id = null;
		String actual_id;

		br = new BufferedReader(new FileReader(csvFile));
		WebDriver driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		while ((line = br.readLine()) != null) {
			String[] csv = line.split("\\^");
			text_case_id = csv[0];
			id = csv[1];

			driver.findElement(By.id("globalSearchInputField")).sendKeys(id);
			driver.findElement(By.id("subnavSearchSubmit")).click();

			actual_id = driver
					.findElement(
							By.className("productID"))
					.getText().replace("Web ID:", "").trim(); // Web ID:

			List<WebElement> sizes = driver
					.findElements(By
							.xpath("//div[1]/div/div[1]/div[1]/div/div[1]/ul/li"));
			List<String> ls = new ArrayList<String>();
			for (WebElement size : sizes) {
				ls.add(size.getText());
			} // [King, Queen, California King, Full]

			if (id.equals(actual_id)
					&& ls.toString().equals(expected_sizes(ls).toString())) {

				System.out.println("Test Case ID: \t\t" + text_case_id
						+ " - PASSED");
				System.out.println("ID Expected/Actual: \t" + id + "/"
						+ actual_id);
				System.out.println("Size Actual: \t\t"
						+ ls.toString().replace("[", "").replace("]", ""));
				System.out.println("Size Expected: \t\t"
						+ expected_sizes(ls).toString().replace("[", "")
								.replace("]", ""));
				System.out.println("");
			} else {
				System.out.println("Test Case ID: \t\t" + text_case_id
						+ " - FAILED");
				System.out.println("ID Expected/Actual: \t" + id + "/"
						+ actual_id);
				System.out.println("Size Actual: \t\t"
						+ ls.toString().replace("[", "").replace("]", ""));
				System.out.println("Size Expected: \t\t"
						+ expected_sizes(ls).toString().replace("[", "")
								.replace("]", ""));
				System.out.println("");
			}// else

		}// while
		driver.close();
		br.close();
	}// main

	public static List<String> expected_sizes(List<String> ls) {
		List<String> new_sizes = new ArrayList<String>();
		List<String> sizes = ls;
		if (sizes.contains("Twin")) {
			new_sizes.add("Twin");
		}
		if (sizes.contains("Twin XL")) {
			new_sizes.add("Twin XL");
		}
		if (sizes.contains("Full")) {
			new_sizes.add("Full");
		}
		if (sizes.contains("Full/Queen")) {
			new_sizes.add("Full/Queen");
		}
		if (sizes.contains("Queen")) {
			new_sizes.add("Queen");
		}
		if (sizes.contains("King")) {
			new_sizes.add("King");
		}
		if (sizes.contains("California King")) {
			new_sizes.add("California King");
		}
		List<String> expected_sizes = new_sizes;
		return expected_sizes;

	}// List

}// class
