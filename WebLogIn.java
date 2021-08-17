import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.fortuna.ical4j.validate.ValidationException;

public class WebLogIn {
private WebDriver driver;
private WebDriverWait wait;
private static String webURL = "https://sjsu.instructure.com/";
private List<WebElement> classList;
private List<WebElement> childList;
private List<AssignmentInfo> assignmentList = new ArrayList<>();
private AssignmentInfo assignmentInfo;
	
	public WebLogIn() {
		this.driver = new SafariDriver();
		this.wait = new WebDriverWait(driver, 15);
		
		login();
		
	}
	
	public void login() {
		driver.get(webURL);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		
		WebElement userName = driver.findElement(By.id("okta-signin-username"));
		userName.clear();
		//User ID Here
		userName.sendKeys("UserID");
		
		WebElement passWord = driver.findElement(By.id("okta-signin-password"));
		//Password here
		passWord.sendKeys("Password");
		
		WebElement submitLogIn = driver.findElement(By.id("okta-signin-submit"));
		submitLogIn.click();
		
	}
	
	public void getClassName() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("PlannerItem-styles__type")));
		
		classList = driver.findElements(By.className("PlannerItem-styles__type"));

	}
	
	public void filterAssignments() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("PlannerItem-styles__title")));
		
		childList = driver.findElements(By.className("PlannerItem-styles__title"));

	}
	
	public void createAssignmentClass() {
		for (int i = 0; i < childList.size(); i++) {
			if (childList.get(i).getText().contains("due")) {
				System.out.println(childList.get(i).getText());
				
				String[] strList = new String[2];
				assignmentInfo = new AssignmentInfo();
				
				assignmentInfo.setClassName(classList.get(i).getText());
				String assignmentName = parseAssignmentName(childList.get(i).getText());
				assignmentInfo.setAssignmentName(assignmentName);
				strList = parseAssignmentDue(childList.get(i).getText());
				assignmentInfo.setDueDate(strList[0]);
				assignmentInfo.setDueTime(strList[1]);
				
				assignmentList.add(assignmentInfo);
			}
		}
	}
	
	public String parseAssignmentName(String str) {
		int index1 = str.indexOf(' ') + 1;
		int index2 = str.indexOf(',');
		String assignmentName = str.substring(index1,index2);
		return assignmentName;
	}
	
	public String[] parseAssignmentDue(String str) {
		int index1 = str.indexOf("due") + 4;
		int index2 = str.indexOf("2021");
		int index3 = str.indexOf('.');
		
		String dueDate = str.substring(index1, index2 + 4);
		String dueTime = str.substring(index2 + 5, index3);
		
		String[] strList = new String[2];
		strList[0] = dueDate;
		strList[1] = dueTime;
		
		return strList;
 	}
	
	public void createICSFile() throws ValidationException, IOException {
		ICS ics = new ICS(assignmentList);
	}
	
	public void exitDriver() {
		driver.quit();
	}
}
