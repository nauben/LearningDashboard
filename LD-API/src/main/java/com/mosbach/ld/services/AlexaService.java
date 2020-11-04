package com.mosbach.ld.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mosbach.ld.dataManager.DHBWScheduleDataManager;
import com.mosbach.ld.dataManager.TaskDataManager;
import com.mosbach.ld.dataManager.UserDataManager;
import com.mosbach.ld.dataManagerImpl.DHBWScheduleDataManagerImpl;
import com.mosbach.ld.model.alexa.AlexaRO;
import com.mosbach.ld.model.alexa.OutputSpeechRO;
import com.mosbach.ld.model.alexa.ResponseRO;
import com.mosbach.ld.model.dhbwSchedule.DHBWLecture;
import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;

@Service
public class AlexaService {

	//TODO as account linking not yet possible, hardcoded user
	private final UUID USER_ID = UUID.fromString("62c3caa2-44c0-47b5-a9ad-e6dc14774312");
	private DHBWScheduleService scheduleManager;
	private DHBWScheduleDataManager scheduleDataManager;
	private TaskDataManager taskManager;
	
	@Autowired
	public AlexaService(
			DHBWScheduleService scheduleManager,
			@Qualifier("s-postgres") DHBWScheduleDataManager scheduleDataManager,
			@Qualifier("t-postgres") TaskDataManager taskManager) {
		this.scheduleManager = scheduleManager;
		this.scheduleDataManager = scheduleDataManager;
	}
	
	
	public void connectToAlexa() {
		throw new UnsupportedOperationException("No OAuth/Account Linking possible yet!");
	}
	
	public AlexaRO processRequest(AlexaRO alexaRO) {
		if(alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")) {
			return processLaunchRequest(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskReadIntent"))) {
			return processReadIntend(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("ReadSummaryIntent"))) {
			return processReadSummaryIntent(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("CreateToDoTaskIntent"))) {
			return processCreateToDoTaskIntent(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("ScheduleReadIntent"))) {
			return processScheduleReadIntent(alexaRO);
		} 
		return null;
	}
	
	private AlexaRO processLaunchRequest(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard. Wie kann ich dir weiterhelfen?";
		}catch(Exception e) {
			outText = "";
		}
		return prepareResponse(request, outText, false);
	}
	
	private AlexaRO processReadSummaryIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Keine Ahnung, was du unter einer Zusammenfassung alles haben willst. Probier es doch mal mit welche vorlesungen habe ich morgen? oder welche aufgaben sind in arbeit?";
		}catch(Exception e) {
			outText = "Leider ist beim Lesen deiner Aufgaben ein Fehler aufgetreten. Probiere es einfach sp\u00e4ter erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processReadIntend(AlexaRO request) {
		StringBuilder outText = new StringBuilder();
		try {
			String text = request.getRequest().getIntent().getSlots().getSwimlane().getValue();
			int swimlane = getSwimlaneFromText(text);
			Collection<Task> tasks = taskManager.getAllTasksOf(USER_ID);
			tasks = tasks.stream().filter((task)-> task.getSwimlane() == swimlane).collect(Collectors.toList());
			outText.append("Du hast folgende Aufgaben in "+text+": ");
			Iterator<Task> iterator = tasks.iterator();
			while(iterator.hasNext()) {
				Task t = iterator.next();
				outText.append(" "+t.getTitle()+" "+t.getDescription());
				if(t.getDueDate() != null) {
					outText.append(" fällig am "+t.getDueDate().getDayOfMonth()+"."+t.getDueDate().getMonthValue()+"."+t.getDueDate().getYear());
				}
				if(iterator.hasNext())
					outText.append(" und ");
			}
		}catch(Exception e) {
			outText = new StringBuilder();
			outText.append("Leider konnte auf deine Daten nicht zugegriffen werden. Probiere es einfach sp\u00e4ter erneut.");
		}
		return prepareResponse(request, outText.toString(), true);
	}
	private int getSwimlaneFromText(String text) {
		switch(text.toLowerCase()) {
		case "to do": return 0;
		case "in arbeit": return 1;
		case "fertig": return 2;
		default: return -1;
		}
	}
	
	private AlexaRO processCreateToDoTaskIntent(AlexaRO request) {
		String outText = "";
		
		try {
			outText += "Leider kann ich noch keine Aufgaben f\u00fcr dich anlegen, freue dich aber schonmal darauf, dass es bald m\u00f6glich sein wird";
		}catch(Exception e) {
			outText = "Leider konnte keine neue Aufgabe angelegt werden. Probiere es einfach sp\u00e4ter erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	
	private AlexaRO processScheduleReadIntent(AlexaRO request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		StringBuilder outText = new StringBuilder();
		try {
			outText.append("Du hast am ");
			LocalDate date = LocalDate.parse(request.getRequest().getIntent().getSlots().getScheduleDate().getValue(), formatter);
			
			String course = this.scheduleDataManager.getCourseOf(USER_ID);
			Collection<DHBWLecture> lectures = scheduleManager.loadAllLecturesOfCourse(course);
			lectures = lectures.stream().filter((lecture)-> lecture.getStart().toLocalDate().equals(date)).collect(Collectors.toList());
			if(lectures.size() == 0) {
				outText = new StringBuilder();
				outText.append("Du hast am ");
				outText.append(date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear()+" keine Vorlesungen! ");
			}else 
				outText.append(date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear()+" folgende Vorlesungen: ");
			Iterator<DHBWLecture> iterator = lectures.iterator();
			while(iterator.hasNext()) {
				DHBWLecture lecture = iterator.next();
				outText.append(" "+lecture.getTitle()+" bei "+lecture.getLecturer()+" um "+lecture.getStart().getHour()+" Uhr ");
				if(lecture.getStart().getMinute() != 0)
					outText.append(lecture.getStart().getMinute()+" ");
				if(!lecture.getLocation().isEmpty()) {
					outText.append(" im Raum "+lecture.getLocation()+" ");
				}
				if(iterator.hasNext())
					outText.append(" und ");
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			outText = new StringBuilder();
			outText.append("Leider konnte nicht auf den Vorlesungsplan zugegriffen werden. Probiere es einfach sp\u00e4ter erneut.");
		}
		return prepareResponse(request, outText.toString(), true);
	}
	


	private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {
	
	    alexaRO.setRequest(null);
	    alexaRO.setSession(null);
	    alexaRO.setContext(null);
	    OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
	    outputSpeechRO.setType("PlainText");
	    outputSpeechRO.setText(outText);
	    ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
	    alexaRO.setResponse(response);
	    return alexaRO;
	}
}
