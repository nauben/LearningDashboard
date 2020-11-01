package com.mosbach.ld.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.dataManager.DHBWScheduleDataManager;
import com.mosbach.ld.model.dhbwSchedule.DHBWCourses;
import com.mosbach.ld.model.dhbwSchedule.DHBWLecture;
import com.mosbach.ld.model.dhbwSchedule.DHBWSchedule;
import com.mosbach.ld.services.DHBWScheduleService;

@RestController
@RequestMapping("/api/v0.1a")
public class ScheduleController {
	
	private DHBWScheduleService dhbwScheduleService;
	private DHBWScheduleDataManager dataManager;
	
	@Autowired
	public ScheduleController(
				DHBWScheduleService dhbwScheduleService,
				@Qualifier("s-postgres") DHBWScheduleDataManager dataManager
			) {
		
		this.dhbwScheduleService = dhbwScheduleService;
		this.dataManager = dataManager;
	}

	@GetMapping("/dhbw-schedule/courses")
    public DHBWCourses getAllCourses() {
		return new DHBWCourses(dhbwScheduleService.loadAllCourses());
    }
	
	@GetMapping("/dhbw-schedule/courses/selected")
    public String getCourse() {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		return dataManager.getCourseOf(id);
    }
	
	@PutMapping( 
			path = "/dhbw-schedule/courses/{course}")
    public void setCourse(@PathVariable("course") String course) {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		dataManager.setCourseOf(id, course);
    }
	
	@GetMapping("/dhbw-schedule")
    public DHBWSchedule getAllLecturesByUser() {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		String course = dataManager.getCourseOf(id);
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
        return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
	@GetMapping("/dhbw-schedule/{course}")
    public DHBWSchedule getAllLecturesByCourse(@PathVariable("course") String course) {
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
        return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
	@GetMapping("/dhbw-schedule/upcomingdays")
    public DHBWSchedule getUpcomingDaysLecturesByUser(@RequestParam(value = "days", defaultValue = "3") int days) {
		UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
		String course = dataManager.getCourseOf(id);
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
		Predicate<DHBWLecture> streamsPredicate = 
				lecture -> lecture.getEnd().isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)) 
					&& lecture.getStart().isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(days));
		 
	    lectures = lectures.stream()
	      .filter(streamsPredicate)
	      .collect(Collectors.toList());
		
	    return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
	@GetMapping("/dhbw-schedule/{course}/upcomingdays")
    public DHBWSchedule getUpcomingDaysLecturesByCourse(@PathVariable("course") String course, @RequestParam(value = "days", defaultValue = "3") int days) {
		
		Collection<DHBWLecture> lectures = dhbwScheduleService.loadAllLecturesOfCourse(course);
		Predicate<DHBWLecture> streamsPredicate = 
				lecture -> lecture.getEnd().isAfter(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)) 
					&& lecture.getStart().isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(days));
		 
	    lectures = lectures.stream()
	      .filter(streamsPredicate)
	      .collect(Collectors.toList());
		
	    return new DHBWSchedule(course, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), lectures);
    }
	
}
