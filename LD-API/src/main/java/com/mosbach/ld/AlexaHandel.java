package com.mosbach.demo;

import org.springframework.web.bind.annotation.RequestBody;

import com.mosbach.demo.model.alexa.AlexaRO;
import com.mosbach.demo.model.alexa.OutputSpeechRO;
import com.mosbach.demo.model.alexa.ResponseRO;
import com.mosbach.demo.model.task.Task;
import com.mosbach.demo.model.task.TaskList;

public class AlexaHandel {

	 public AlexaRO getTasks(@RequestBody AlexaRO alexaRO) {

	        String outText = "";

	        if (alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")) {
	            outText = outText + "Welcome to the Mosbach Task Organizer. ";
	        }
	        else {
	            if (alexaRO.getRequest().getType().equalsIgnoreCase("IntentRequest") &&
	                    (alexaRO.getRequest().getIntent().getName().equalsIgnoreCase("TaskReadIntent"))) {
	                try
	                {
	                    TaskList taskList = new TaskList(
	                            new Student("me", "ignore")
	                    );
	                    taskList.setTasks();

	                    outText = outText + "You have to do the following tasks. ";
	                    int i = 1;
	                    for (Task temp : taskList.getTasks()) {
	                        outText = outText + "Number " + i + " . ";
	                        outText = outText + temp.getName() + ", priority " + temp.getPriority() + " . ";
	                        i++;
	                    }
	                }
	                catch(Exception e) {
	                    outText = "Unfortunately, we cannot reach heroku. Our REST server is not responding. ";
	                }
	            }
	        }

	        return
	                prepareResponse(alexaRO, outText, true);
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
