package pl.event.base;

import javax.persistence.Entity;

@Entity
public class User extends Base {
	
	private String name;
	
	private String password;

}
