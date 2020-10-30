package com.mosbach.ld.dataManager;

import java.util.UUID;

public interface DHBWScheduleDataManager {

	public boolean setCourseOf(UUID id, String course);
	
	public String getCourseOf(UUID id);
	
}
