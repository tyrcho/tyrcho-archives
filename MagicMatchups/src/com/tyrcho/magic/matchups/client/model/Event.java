package com.tyrcho.magic.matchups.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
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
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;

	@Persistent
	private Date date;
	@Persistent
	private String place;
	@Persistent
	private String name;
	@Persistent
	private Integer attendance;

	@Persistent
	private Integer rounds;
	
	@Persistent
	private Boolean top8;

	@Persistent
	private EventLevel level;
	public Integer getAttendance() {
		return attendance;
	}
	public Date getDate() {
		return date;
	}
	public String getId() {
		return id;
	}

	public EventLevel getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public String getPlace() {
		return place;
	}

	public Integer getRounds() {
		return rounds;
	}

	public Boolean getTop8() {
		return top8;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLevel(EventLevel level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setRounds(Integer rounds) {
		this.rounds = rounds;
	}

	public void setTop8(Boolean top8) {
		this.top8 = top8;
	}

	@Override
	public String toString() {
		return name;
	}
}
