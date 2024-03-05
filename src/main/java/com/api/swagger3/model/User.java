package com.api.swagger3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@JacksonXmlRootElement(localName = "user")
@XmlRootElement(name = "user")
@Getter
@Setter
@ToString
public class User {
    @JsonProperty("id")
	@JacksonXmlProperty(localName = "id")
    private Long id;


    @JsonProperty("name")
	@JacksonXmlProperty(localName = "name")
	private String name;

}
