package com.mosbach.ld.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.TreeMap;

import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;


public class DHBWScheduleService {

	//dhbw interface for ics data
	String host_url = "http://ics.mosbach.dhbw.de/ics";
	String all_entries = "/calendars.list";
	
	TreeMap<String, String> courses;
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		DHBWScheduleService service = new DHBWScheduleService();
		service.courses = service.getAllCourses();
		//System.out.println(service.loadFileFromURL(service.courses.get("WI19A")));
		//service.interpretCalendar(new ByteArrayInputStream(service.loadFileFromURL(service.courses.get("WI19A")).getBytes(Charset.forName("UTF-8"))));
		//service.interpretCalendar(new URL("http://ics.mosbach.dhbw.de/ics/wi19a.ics").openStream());
		ICalendar c = service.readCalendar(service.loadFileFromURL(service.courses.get("WI19A")));
		System.out.println(c);
		for(VEvent e : c.getEvents()) {
			//TODO NullPointerException possible
			System.out.println(e.getSummary().getValue());
			System.out.println(e.getDateStart().getValue());
		}
	}
	
	public DHBWScheduleService() {
		courses = new TreeMap<>();
	}
	
	public TreeMap<String, String> getAllCourses() throws MalformedURLException, IOException  {
		TreeMap<String, String> result = new TreeMap<>();
		String data = loadFileFromURL(host_url+all_entries);
		String[] pair;
		for(String i : data.split("\n")) {
			pair = i.strip().split(";");
			//System.out.println(pair[0]+""+pair[1]);
			result.put(pair[0], pair[1]);
		}
		//System.out.println(result);
		return result;
	}
	
	public DHBWSchedule getScheduleByCourse(String course) throws MalformedURLException, IOException{
		//TODO
		InputStream ins = new URL(host_url+"").openStream();
		
		/*
		
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(ins);
		ComponentList<CalendarComponent> cs = calendar.getComponents();
		System.out.println(calendar);
		for (Component c: cs) {
		    if (c instanceof VEvent) {
		     System.out.println(c);
		        for (Property p : c.getProperties()) {
		        	Iterator<Parameter> i = p.getParameters().iterator();
		            while(i.hasNext()) {
		            	System.out.println(i.next());
		            }
		        }
		    }
		}
		*/
		
		
		return null;
	}
	
	private ICalendar readCalendar(String cal) {
		try {
			return Biweekly.parse(new URL("http://ics.mosbach.dhbw.de/ics/wi19a.ics").openStream()).first();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void interpretCalendar(InputStream inputStream) {
		/*CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar;
		try {
			calendar = builder.build(inputStream);
			ComponentList<CalendarComponent> cs = calendar.getComponents();
			System.out.println(calendar);
			for (Component c: cs) {
			    if (c instanceof VEvent) {
			     System.out.println(c);
			        for (Property p : c.getProperties()) {
			        	Iterator<Parameter> i = p.getParameters().iterator();
			            while(i.hasNext()) {
			            	System.out.println(i.next());
			            }
			        }
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	private String loadFileFromURL(String url) throws MalformedURLException, IOException {
		StringBuilder sb = new StringBuilder();
		InputStream ins = new URL(url).openStream();
		for(int a = 0; a != -1; a = ins.read()) {
			sb.append((char) a);
		}
		return sb.toString().replace("\n", "\r\n");
	}
	
}
