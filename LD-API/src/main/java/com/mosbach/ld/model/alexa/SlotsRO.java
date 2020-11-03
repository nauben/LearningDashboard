package com.mosbach.ld.model.alexa;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Map;

@JsonTypeName(value = SlotsRO.TYPENAME)
public class SlotsRO
{
    protected final static String TYPENAME = "SlotRO";

    @JsonProperty("scheduleDate")
    private ScheduleDateRO scheduleDate;
    
    @JsonProperty("swimlane")
    private SwimlaneRO swimlane;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public SlotsRO()
    {
        super();
    }
    
    public SlotsRO(ScheduleDateRO scheduleDate, SwimlaneRO swimlane)
    {
        this.scheduleDate = scheduleDate;
        this.swimlane = swimlane;
    }
    
    @JsonProperty("scheduleDate")
    public ScheduleDateRO getScheduleDate() {
		return scheduleDate;
	}

    @JsonProperty("scheduleDate")
	public void setScheduleDate(ScheduleDateRO scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	@JsonProperty("swimlane")
	public SwimlaneRO getSwimlane() {
		return swimlane;
	}

	@JsonProperty("swimlane")
	public void setSwimlane(SwimlaneRO swimlane) {
		this.swimlane = swimlane;
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
