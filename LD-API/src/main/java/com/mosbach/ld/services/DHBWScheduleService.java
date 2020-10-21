package com.mosbach.ld.services;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mosbach.ld.model.dhbwSchedule.DHBWCourses;
import com.mosbach.ld.model.dhbwSchedule.DHBWLecture;
import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;
import com.sun.el.parser.ParseException;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

@Service
public class DHBWScheduleService {

	public static final String COURSES_URI = "http://ics.mosbach.dhbw.de/ics/calendars.list";
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	
	public Collection<String> loadAllCourses(){
		Collection<String> result = new ArrayList<>();
		Map<String, String> courses = loadAllCoursesWithURL();
		if(courses != null)
			courses.forEach((key, value) -> {
				if(key.length() > 0)
					result.add(key);
			});
		return result;
	}
	
	@Cacheable(value="dhbwCourses", unless="#result == null")
	private Map<String, String> loadAllCoursesWithURL() {
		try {
			Thread.sleep(1000);
			System.out.println("fetching new data from server");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TreeMap<String, String> result = new TreeMap<>();
		try {
			   URL url = new URL(COURSES_URI);
			   Scanner scanner = new Scanner(url.openStream());
			   
			   // read from your scanner
			   scanner.forEachRemaining((String line) -> {
				   String[] pair = line.split(";");
				   if(pair.length == 2 && pair[0].length() > 0)
					   result.put(pair[0], pair[1]);
			   });
		}
		catch(IOException ex) {
		   // there was some connection problem, or the file did not exist on the server,
		   // or your URL was not in the right format.
		   // think about what to do now, and put it here.
		   ex.printStackTrace(); // for now, simply output it.
		   return null;
		}
		return result;
	}
	
	public static void main(String[] args) {
		DHBWScheduleService s = new DHBWScheduleService();
		s.loadAllLecturesOfCourse("WI19A");
	}
	
	@Cacheable(value="dhbwLectures", key="#course", unless="#result == null")
	public Collection<DHBWLecture> loadAllLecturesOfCourse(String course) {
		//TODO might throw nullpointer
		
		Collection<DHBWLecture> result = new ArrayList<>();
		String uri = loadAllCoursesWithURL().get(course);
		if(uri != null && uri.length() > 0) {
			try {
				ICalendar calendar = Biweekly.parse(new URL(uri).openStream()).first();
				for(VEvent e : calendar.getEvents()) {
					DHBWLecture lecture = parseVEventToDHBWSchedule(e);
					if(lecture != null) 
						result.add(lecture);
				}
			} catch (IOException e) {
				// there was some connection problem, or the file did not exist on the server,
			   // or your URL was not in the right format.
			   // think about what to do now, and put it here.
				e.printStackTrace();
				return null;
			}
			return result;
		}
		return null;
	}
	private DHBWLecture parseVEventToDHBWSchedule(VEvent vEvent) {
		//TODO abfrage ob wichtige werte null
		try {
			String location = "";
			String lecturer = "";
			
			if(vEvent.getLocation() != null)
				location = vEvent.getLocation().getValue().toString();
			
			if(vEvent.getDescription() != null)
				lecturer = vEvent.getDescription().getValue().toString();
			
			/*
			System.out.println(vEvent.getSummary().getValue());
			System.out.println(lecturer);
			System.out.println(location);
			System.out.println(LocalDateTime.parse(vEvent.getDateStart().getValue().toString(), formatter));
			System.out.println(LocalDateTime.parse(vEvent.getDateEnd().getValue().toString(), formatter));
			*/
			
			return new DHBWLecture(
					vEvent.getSummary().getValue(), 
					lecturer, 
					location, 
					LocalDateTime.parse(vEvent.getDateStart().getValue().toString(), formatter), 
					LocalDateTime.parse(vEvent.getDateEnd().getValue().toString(), formatter)
				);
			
			} catch(Exception e) {}
		return null;
	}
	
}
