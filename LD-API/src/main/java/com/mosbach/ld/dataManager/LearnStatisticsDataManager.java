package com.mosbach.ld.dataManager;

import java.util.Collection;
import java.util.UUID;

import com.mosbach.ld.model.learnStatistics.Subject;

public interface LearnStatisticsDataManager {
	
	public boolean createNewSubject(Subject subject, UUID userId);
	
	public boolean deleteSubject(UUID id);
	
	public boolean updateSubjectName(Subject subject);
	
	public boolean incrementSubjectTime(UUID id, int seconds);
	
	public boolean resetSubjectTime(UUID id);
	
	public Subject getSubjectById(UUID id);
	
	public Collection<Subject> getAllSubjectsOf(UUID userId);
	
}
