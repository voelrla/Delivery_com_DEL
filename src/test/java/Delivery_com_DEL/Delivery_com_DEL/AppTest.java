package Delivery_com_DEL.Delivery_com_DEL;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

public class AppTest extends login_admin {

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		WebDriverRunner.setWebDriver(driver);

		// 브라우저 오픈
		open("http://test.wemakeprice.com");

		try {
			File f2 = new File("login.txt");
			FileReader fr = new FileReader(f2);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer str = new StringTokenizer(line, ";");
				while (str.hasMoreTokens()) {
					String name = str.nextToken();
					String value = str.nextToken();
					String domain = str.nextToken();
					String path = str.nextToken();
					Date expiry = null;
					String dt;
					if (!(dt = str.nextToken()).equals("null")) {
						// expiry = new Date(dt);
					}
					boolean isSecure = new Boolean(str.nextToken()).booleanValue();
					Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
					driver.manage().addCookie(ck);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String getNumber(String code) {
		return code.replaceAll("[^0-9]", "");
	}

	public String[] getnumber() {

		String[] On_list = new String[10];
		open("https://front-qa.wemakeprice.com/mypage/orders?page=1");
		for (int i = 1; i < 6; i++) {
			String Ordernumber = $(
					By.xpath("//div[@id='_contents']/div/div/div[2]/div[4]/div[2]/table/tbody/tr[" + i + "]/td/div"))
							.getText();
			String smsnumber = getNumber(Ordernumber); // 받아온 문자에서 숫자만 추출
			On_list[i] = smsnumber.substring(8, 17); // 뒷 구매번호만 추출
//			System.out.println(On_list[i]);
		}
		return On_list;
	}

	@Test
	public void del_com() throws Exception {
		String[] Ordernumber = getnumber();
		login();
		for (int i = 1; i < 6; i++) {
			System.out.println(Ordernumber[i]);

			Pnumber(Ordernumber[i]);
			// Pnumber("100003357");
			orderC();
		}

	}

	@AfterMethod
	public void end() {

	}

}
