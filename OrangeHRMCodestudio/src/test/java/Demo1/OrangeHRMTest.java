package Demo1;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class OrangeHRMTest {
	
	public String BaseURL="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		
		System.out.println("Before execution");
		
		driver=new ChromeDriver();
		
		
		driver.get(BaseURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}
	
	@Test(priority=2)
	public void loginTest() 
	{
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[.=' Login ']")).submit();
		
		
		String pageTitle = driver.getTitle();
		if(pageTitle.equals("OrangeHRM")) {
			System.out.println("Login successful");
		}
		else
		{
			System.out.println("Login not successful");
		}
		
	}
	
	@Test(priority=1, enabled=false)
	public void loginTestWithInvalidCredentials() throws InterruptedException
	{
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin1");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin12");
		driver.findElement(By.xpath("//button[.=' Login ']")).submit();
		
	String message_expected="Invalid credentials";
	
	String actual_message = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
	Assert.assertEquals(message_expected, actual_message);
	Thread.sleep(2000);
		
	}
	
	@Test(priority = 3)
	public void addEmployee() {
		
		
		driver.findElement(By.xpath("//span[.='PIM']")).click();
		driver.findElement(By.xpath("//a[.='Add Employee']")).click();
		
		driver.findElement(By.xpath("//input[@class='oxd-input oxd-input--active orangehrm-firstname']")).sendKeys("Ramya");
		driver.findElement(By.xpath("//input[@class='oxd-input oxd-input--active orangehrm-middlename']")).sendKeys("s");
		driver.findElement(By.xpath("//input[@class='oxd-input oxd-input--active orangehrm-lastname']")).sendKeys("s");
		
		driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")).click();
		
		String confirmation_acmessage = driver.findElement(By.xpath("//h6[.='Personal Details']")).getText();		
		String confirmation_exemessage = "Personal Details";
		
		Assert.assertEquals(confirmation_exemessage, confirmation_acmessage);
	}
	
	@Test(priority=4)
	public void searchEmployee() {
		
		
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[.='Employee List']")).click();
		
		driver.findElements(By.xpath("//input[@placeholder='Type for hints...']")).get(0).sendKeys("Ramya");
	}
	
	@Test(priority = 5, enabled=false)
	public void deleteEmployee() {
		
		driver.findElement(By.xpath("//a[.='PIM']")).click();
		driver.findElement(By.xpath("//a[.='Employee List']")).click();
		driver.findElements(By.xpath("//input[@placeholder='Type for hints...']")).get(0).sendKeys("Ramya");
		driver.findElement(By.xpath("//button[.=' Search ']")).click();
		
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();
		driver.findElement(By.xpath("//button[.=' Yes, Delete ']")).click();
		
		String msg = driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();
		
		Assert.assertEquals(msg, "No Records Found");
	}
	
	@Test(priority = 6, enabled=true)
	public void listEmployee() {
		driver.findElement(By.xpath("//a[.='PIM']")).click();
		driver.findElement(By.xpath("//a[.='Employee List']")).click();
		
		List<WebElement> totallinkselement = driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"));
		int totallinks = totallinkselement.size();
		
		for(int i=0;i<totallinks;i++) {
			String currentLinks=totallinkselement.get(i).getText();
			
				try {
					int page=Integer.parseInt(currentLinks);
					totallinkselement.get(i).click();
				}
				catch(Exception e) 
				{
					System.out.println("Not a number");
				}
		}
	}
	@Test(priority = 7, enabled=true)
	public void applyLeave() throws InterruptedException {
		
		driver.findElement(By.xpath("//span[.='Leave']")).click();
		
		driver.findElement(By.xpath("//a[.='Apply']")).click();
		
		
		
		Thread.sleep(2000);
		
		logoutTest();
	}
	
	public void logoutTest() throws InterruptedException {
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		//driver.findElement(By.xpath("//a[.='Logout']")).submit();
		List<WebElement> elementList = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		for(int i=0;i<elementList.size();i++) {
			Thread.sleep(2000);
			System.out.println(i + ":" + elementList.get(i).getText());
		}
		elementList.get(3).getText();
	}
	
	
	@AfterTest
	public void tearDown() throws InterruptedException {
		
		Thread.sleep(2000);
		driver.close();
		driver.quit();
	}

}
