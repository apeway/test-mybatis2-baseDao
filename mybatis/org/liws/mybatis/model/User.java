package org.liws.mybatis.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int type;	// 默认为0表示普通用户、1表示管理员
	
	private List<Address> addresses; 
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", type=" + type + ", addresses=" + addresses + "]";
	}

	public User(int id) {
		super();
		this.id = id;
	}
	public User() {
		super();
	}
}
