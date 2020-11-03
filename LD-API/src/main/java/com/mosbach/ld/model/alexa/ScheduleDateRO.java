package com.mosbach.ld.model.alexa;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = ScheduleDateRO.TYPENAME)
public class ScheduleDateRO {

	protected final static String TYPENAME = "ScheduleDateRO";
	@JsonProperty("name")
	private String name;
	@JsonProperty("value")
	private String value;
	
	@JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
	
	public ScheduleDateRO() {
		super();
	}
	
	public ScheduleDateRO(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
