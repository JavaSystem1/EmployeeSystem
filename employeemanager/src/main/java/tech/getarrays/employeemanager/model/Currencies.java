package tech.getarrays.employeemanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Currencies {
    @JsonProperty("success")
    public boolean success;
    @JsonProperty("timestamp")
    public int timestamp;
    @JsonProperty("base")
    public String base;
    @JsonProperty("date")
    public String date;
    @JsonProperty("rates")
    public List<Rate> rates;
}
