package Delivery_com_DEL.Delivery_com_DEL;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.open;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selenide;

public class login_admin {
	public static WebDriver driver;

	public static void login() throws Exception {
		open("https://groupware-login.wemakeprice.com/VDI/Login?ReturnURL=https://wadmin-qa.wemakeprice.com/login/action");
		$(By.id("UserID")).setValue("2015080004");
		$(By.id("Password")).setValue("Dl71019324@");
		$(By.xpath("//button[contains(.,'로그인')]")).click();
	}

	public static void Pnumber(String pn) {
		open("https://wadmin-qa.wemakeprice.com/ship/orderInfoDetail?purchaseNo=" + pn
				+ "&mid=16342726&dhxr1548831815470=1");
	}

	public static void orderC() {
		// 주문확인
		$(By.cssSelector("#shipInfoGrid img")).click();
		try {
			$(By.id("confirmOrder")).click();
			confirm("선택하신 주문건에 대해서 주문확인을 진행하시겠습니까?");

			// 송장등록
			$(By.cssSelector("#shipInfoGrid img")).click();
			$(By.id("setInvoiceNo")).click();

			// 송장등록 팝업으로 포커스
			Set<String> set1 = driver.getWindowHandles();
			Iterator<String> win1 = set1.iterator();
			String parent = win1.next();
			String child = win1.next();
			driver.switchTo().window(child);

			$(By.id("shipMethod")).selectOption("기타배송");
			$(By.id("shipEtMessage")).setValue("0000");
			$(By.xpath("//div[2]/table/tbody/tr[2]/td[4]")).click();
			$(By.id("staffSelect")).click();
			confirm("송장을 등록 하시겠습니까?");
			confirm("송장이 등록 되었습니다.");
			driver.switchTo().window(parent);

			// 배송완료 처리
			$(By.cssSelector("#shipInfoGrid img")).click();
			$(By.id("setDeliveryComplete")).click();
			confirm("선택하신 주문건에 대해서 배송완료를 진행하시겠습니까?");

		} catch (Exception e) {
			System.out.println(" 은 '배송완료' 가능상태가 아닙니다.");
		}
		Selenide.sleep(1000);
	}
}