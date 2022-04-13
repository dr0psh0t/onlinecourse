package com.onlinecourse.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_image")
public class UserImage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "filename")
	private String filename;
	
	@Column(name = "filetype")
	private String filetype;
	
	@Column(name = "file", nullable = true, columnDefinition = "MediumBlob")
	private byte[] file;
	
	@OneToOne(mappedBy = "userImage",
			cascade = {CascadeType.REMOVE,
			CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH
	})
	private User user;
	
	public UserImage() {}
	
	public UserImage(String filename, String filetype, byte[] file) {
		this.filename = filename;
		this.filetype = filetype;
		this.file = file;
	}
	
	public UserImage(int id, String filename, String filetype, byte[] file) {
		this.id = id;
		this.filename = filename;
		this.filetype = filetype;
		this.file = file;
	}
	
	public UserImage id(int id) {
		if (id > 0) {
			this.id = id;
		}
		return this;
	}
	
	public UserImage filename(String filename) {
		this.filename = filename;
		return this;
	}
	
	public UserImage filetype(String filetype) {
		this.filetype = filetype;
		return this;
	}
	
	public UserImage bytes(byte[] bytes) {
		this.file = bytes;
		return this;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFiletype() {
		return filetype;
	}
	
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	public byte[] getFile() {
		return file;
	}
	
	public void setFile(byte[] file) {
		this.file = file;
	}
}
