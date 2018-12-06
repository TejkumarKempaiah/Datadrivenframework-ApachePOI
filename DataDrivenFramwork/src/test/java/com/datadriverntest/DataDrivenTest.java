package com.datadriverntest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.excel.utility.Xls_Reader;
public class DataDrivenTest 
{
	public static WebDriver driver;
	
	static
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Tejkumar\\Desktop\\JanBask-Batch\\Softwares\\chromedriver.exe");
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Xls_Reader reader = new Xls_Reader("C:\\Users\\Tejkumar\\Desktop\\DataDrivenFramework\\contactform.xlsx");
		int row_count = reader.getRowCount("contactform");
		
		System.out.println("Row Count: "+row_count);
		int column_count = reader.getColumnCount("contactform");
		
		System.out.println("Column Count: "+column_count);	
		
		String city = reader.getCellData("contactform","city",4);
		System.out.println(city);		
		
		
		//Using config.properties file values
		
		Properties p1 = new Properties();
		
		InputStream i1 = new FileInputStream("C:\\Users\\Tejkumar\\Desktop\\JanBask-Batch\\WORKSPACE-NOV18\\DataDrivenFramwork\\config.properties");
		
		p1.load(i1);
		
		String url = p1.getProperty("url");
		String email = p1.getProperty("email");
		String pwd = p1.getProperty("pwd");
		
		
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		System.out.println("Login page title: "+driver.getTitle());
		
		WebElement email_textbox = driver.findElement(By.xpath("//input[@placeholder='Email']"));
		WebElement pwd_textbox = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement signIn_button = driver.findElement(By.xpath("//button[@class='btn palette-Light-Blue-800 bg']"));
		
		email_textbox.sendKeys(email);
		pwd_textbox.sendKeys(pwd);
		signIn_button.click();
		Thread.sleep(4000);
		
		System.out.println("Page title after login: "+driver.getTitle());				
		
		//Click on Employee hyperlink
		WebElement emp = driver.findElement(By.xpath("//a[contains(text(),'Employees')]"));
		Thread.sleep(4000);
		
		emp.click();
		
		System.out.println(driver.getTitle());
		
		//Create one user
		
		driver.findElement(By.xpath("//span[contains(text(),'New')]")).click();
		driver.findElement(By.xpath("//a[@class='text-button palette-Blue-300 bg']")).click();
		Thread.sleep(4000);
		
		//Upload profile pic
		
			WebElement selectFile = driver.findElement(By.xpath("//input[@id='image-file']"));
			selectFile.click();
			
			Thread.sleep(2000);
			
			Runtime.getRuntime().exec("C:\\Users\\Tejkumar\\Desktop\\fileupload\\fileupload.exe");
			
			driver.findElement(By.xpath("//button[contains(text(),'Crop')]")).click();
			
			Thread.sleep(2000);
			
		driver.close();	
		
	}
}
