package com.mosbach.ld.dataManager;

import java.util.Collection;
import java.util.UUID;

import com.mosbach.ld.model.notification.Notification;

public interface NotificationDataManager {

	public boolean createNewNotificationFor(UUID user, Notification notification);
	
	public Collection<Notification> getAllNotificationsOf(UUID userId);
	
	public boolean deleteNotification(UUID id);
	
}
