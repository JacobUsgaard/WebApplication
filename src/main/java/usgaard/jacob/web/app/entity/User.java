
package usgaard.jacob.web.app.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import usgaard.jacob.web.app.controller.data.UserDataController;
import usgaard.jacob.web.app.repository.UserRepository;
import usgaard.jacob.web.app.service.UserService;

/**
 * Mapping for user.
 * 
 * @see UserDataController
 * @see UserService
 * @see UserRepository
 * 
 * @author Jacob Usgaard
 *
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "USER_T";

	public static final String COLUMN_NAME_USER_ID = "USER_ID";
	public static final String COLUMN_NAME_USERNAME = "USERNAME";
	public static final String COLUMN_NAME_PASSWORD = "PASSWORD";
	public static final String COLUMN_NAME_SALT = "SALT";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = COLUMN_NAME_USER_ID, insertable = false, updatable = false)
	private BigInteger userId;

	@Column(name = COLUMN_NAME_USERNAME)
	private String username;

	@Column(name = COLUMN_NAME_PASSWORD)
	@JsonIgnore
	private String password;

	@Column(name = COLUMN_NAME_SALT)
	private String salt;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", salt=" + salt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 *            The hashed and salted password.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return The hashed and salted password.
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}