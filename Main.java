import java.io.IOException;
import java.util.Scanner;

import net.fortuna.ical4j.validate.ValidationException;

public class Main {
private WebLogIn webdriver;

	public Main() throws ValidationException, IOException {
		webdriver = new WebLogIn();
		Menu();
	}
	
	public void Menu() throws ValidationException, IOException {
		printMenu();
		
		Scanner userInput = new Scanner(System.in);
		String input = userInput.nextLine();
		
		while(!input.equals("Quit")) {
			
			if (input.equals("A")) {
				webdriver.getClassName();
				webdriver.filterAssignments();
				webdriver.createAssignmentClass();
			}
			
			else if (input.equals("C")) {
				webdriver.createICSFile();
				
			}
			printMenu();
			input = userInput.nextLine();
		}
		
		webdriver.exitDriver();
	}
	
	public void printMenu() {
		System.out.println("");
		System.out.println("------MENU-----");
		System.out.println("[A] Get Assignment Information");
		System.out.println("[C] Get iCal ICS file of Assignments");
		System.out.println("Quit");
		System.out.println("");
	}
	
	public static void main(String[] args) throws ValidationException, IOException {
		Main test = new Main();
		
	}
}
