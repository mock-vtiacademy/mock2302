package com.vti.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`Group`")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short id;

	@Column(name = "`name`", length = 50, nullable = false, unique = true)
	private String name;

	@Column(name = "totalMember")
	private short totalMember;

	public Group() {
	}
	
	public Group(String name) {
		this.name = name;
	}

	public short getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public short getTotalMember() {
		return totalMember;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTotalMember(short totalMember) {
		this.totalMember = totalMember;
	}
	
}
