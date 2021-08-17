import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

public class ICS {
private List<AssignmentInfo> assignmentList;
private Calendar calendar;
private net.fortuna.ical4j.model.Calendar exportCal = new net.fortuna.ical4j.model.Calendar();
private int day;

	public ICS(List<AssignmentInfo> assignmentList) throws ValidationException, IOException {
		String file = "calendar.ics";
		this.assignmentList = assignmentList;
		this.calendar = Calendar.getInstance();
		
		parseAssignmentList();
		System.out.println(exportCal);
		outputICS();
	}
	
	public void parseAssignmentList() {
		for (AssignmentInfo assignment: assignmentList) {
			String[] split = assignment.getDueDate().split(" ");
			day = Integer.parseInt(split[2].substring(0, split[2].length() - 1)) - 1;
			createEvent(day, assignment.getClassName(), assignment.getAssignmentName(), assignment.getDueTime());
		}
	}
	
	public void createEvent(int currentday, String className, String assignmentName, String time) {
		calendar.set(Calendar.DAY_OF_MONTH, currentday);
		VEvent assignment = new VEvent(new Date(calendar.getTime()), className);
		
		UidGenerator ug = new RandomUidGenerator();
		assignment.getProperties().add(new Uid("" + ug.generateUid()));
		
		assignment.getProperties().add(new Description(assignmentName + " " + time));
		exportCal.getComponents().add(assignment);
		
	}
	
	public void outputICS() throws ValidationException, IOException {
		FileOutputStream fout = new FileOutputStream("myAssignmentsCal.ics");
		CalendarOutputter outputter = new CalendarOutputter();
		outputter.setValidating(false);
		outputter.output(exportCal, fout);
	}
}
