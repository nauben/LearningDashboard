package com.mosbach.ld.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.model.learnStatistics.Subject;

@RestController
@RequestMapping("/api/v0.1a/pomodoro")
public class LearnStatisticsController {

	
	@PostMapping(
            path = "/subjects",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@PutMapping(
            path = "/subjects/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateSubjectName(@PathVariable("id") String id, @RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@PutMapping(
            path = "/subjects/{id}/time",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> updateSubjectTime(@PathVariable("id") String id, @RequestBody Subject subject) {
		//check if valid
		return null;
	}
	
	@GetMapping("/subjects")
	public ResponseEntity<?> getAllSubjects(){
		return null;
	}
	
	@GetMapping("/subjects/{id}")
	public ResponseEntity<?> getSubject(@PathVariable("id") String id){
		return null;
	}
	
	@DeleteMapping(
            path = "/subjects/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public ResponseEntity<?> deleteSubject(@PathVariable("id") String id) {
		
		return null;
	}
}
