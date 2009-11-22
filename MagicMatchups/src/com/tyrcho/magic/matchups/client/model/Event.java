package com.tyrcho.magic.matchups.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Event implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Date date;
	@Persistent
	private String place;
	@Persistent
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Persistent
	private Integer attendance;
	@Persistent
	private Integer rounds;
	@Persistent
	private Boolean top8;
	@Persistent
	private EventLevel level;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public Integer getRounds() {
		return rounds;
	}

	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}

	public Boolean getTop8() {
		return top8;
	}

	public void setTop8(Boolean top8) {
		this.top8 = top8;
	}

	public EventLevel getLevel() {
		return level;
	}

	public void setLevel(EventLevel level) {
		this.level = level;
	}
}
