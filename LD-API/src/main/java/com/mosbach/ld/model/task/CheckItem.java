package com.mosbach.ld.model.task;

import java.util.UUID;

public class CheckItem {
	
	private UUID id;
	private String description;
	private boolean checked;
	
	public CheckItem() {
		super();
	}

	public CheckItem(UUID id, String description, boolean checked) {
		super();
		this.id = id;
		this.description = description;
		this.checked = checked;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
