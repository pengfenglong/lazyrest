package com.smartdp.lazyrest.security.domain;

import org.springframework.stereotype.Component;

/** This is domain DAO to access users. Kinda fake here. */
@Component
public class UserDao {

	public User getByLogin(String login) {
		if("admin".equals(login)){
			return new User("admin", "Administrator", "admin", "ADMIN");
		}else if("special".equals(login)){
			return new User("special", "Special Expert", "special", "ROLE_SPECIAL");
		}else if("user1".equals(login)){
			return new User("user1", "User Uno", "user1");
		}else if("Aladdin".equals(login)){
			return new User("Aladdin", "Aladdin", "open sesame");
		}else{
			return null;
		}
	}
}
