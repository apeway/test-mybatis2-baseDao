package org.liws.mybatis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
	private int id;
	private String name;
	private String phone;
	private String postcode;
	
	private User user; // user_id

	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", phone=" + phone + ", postcode=" + postcode + ", user=" + user
				+ "]";
	}

	public Address(String name, String phone, String postcode) {
		super();
		this.name = name;
		this.phone = phone;
		this.postcode = postcode;
	}
	public Address() {
		super();
	}
}
