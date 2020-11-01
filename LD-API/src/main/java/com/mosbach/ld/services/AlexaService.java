package com.mosbach.ld.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.mosbach.ld.model.alexa.AlexaRO;
import com.mosbach.ld.model.alexa.OutputSpeechRO;
import com.mosbach.ld.model.alexa.ResponseRO;
import com.mosbach.ld.model.task.Task;
import com.mosbach.ld.model.task.TaskList;

@Service
public class AlexaService {

	//TODO as account linking not yet possible, hardcoded user
	private final UUID USER_ID = UUID.randomUUID();

	public AlexaService() {
		
	}
	
	/*
	private final String CONNECT_URL = "https://www.amazon.com/ap/oa?client_id="+CLIENT_ID+"&scope=alexa::skills:"
			+ "account_linking&response_type=code&redirect_uri="+REDIRECT_URL+"&state="+STATE;
	*/
	
	public void connectToAlexa() {
		throw new UnsupportedOperationException("No OAuth/Account Linking possible yet!");
	}
	
	public AlexaRO processRequest(AlexaRO alexaRO) {
		if(alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")) {
			return processLaunchRequest(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskToDoReadIntent"))) {
			return processTaskToDoReadIntend(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskWIPReadIntent"))) {
			return processTaskWIPReadIntent(alexaRO);
		}else if(alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
                (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskFinishedReadIntent"))) {
			return processTaskFinishedReadIntent(alexaRO);
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
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "";
		}
		return prepareResponse(request, outText, false);
	}
	
	private AlexaRO processTaskToDoReadIntend(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider ist beim Lesen deiner Aufgaben ein Fehler aufgetreten. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processTaskWIPReadIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider ist beim Lesen deiner Aufgaben ein Fehler aufgetreten. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processTaskFinishedReadIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider ist beim Lesen deiner Aufgaben ein Fehler aufgetreten. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processReadSummaryIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider konnte auf deine Daten nicht zugegriffen werden. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processCreateToDoTaskIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider konnte keine neue Aufgabe angelegt werden. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
	}
	
	private AlexaRO processScheduleReadIntent(AlexaRO request) {
		String outText = "";
		try {
			outText += "Willkommen beim Learning Dashboard.";
		}catch(Exception e) {
			outText = "Leider konnte nicht auf den Vorlesungsplan zugegriffen werden. Probiere es einfach später erneut.";
		}
		return prepareResponse(request, outText, true);
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
