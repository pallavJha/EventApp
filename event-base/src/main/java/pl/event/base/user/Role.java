package pl.event.base.user;

import javax.persistence.Entity;

import pl.event.base.Base;

@Entity
public class Role extends Base {

	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
