
package usgaard.jacob.web.app.entity;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import usgaard.jacob.web.app.controller.data.UserDataController;
import usgaard.jacob.web.app.repository.UserRepository;
import usgaard.jacob.web.app.service.UserService;

/**
 * Mapping for user.
 * 
 * @see UserDataController
 * @see UserService
 * @see UserRepository
 */
@Entity
@Table(
		name = User.TABLE_NAME)
public class User extends BaseEntity {
	public static final String TABLE_NAME = "USER_T";

	public static final String COLUMN_NAME_USER_ID = "USER_ID";
	public static final String COLUMN_NAME_USERNAME = "USERNAME";
	public static final String COLUMN_NAME_PASSWORD = "PASSWORD";
	public static final String COLUMN_NAME_SALT = "SALT";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = COLUMN_NAME_USER_ID, updatable = false)
	private BigInteger userId;

	@Column(name = COLUMN_NAME_USERNAME)
	private String username;

	@Column(name = COLUMN_NAME_PASSWORD)
	private String password;

	@Column(name = COLUMN_NAME_SALT)
	private String salt;

	@Override
	public Object getId() {
		return getUserId();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", salt=" + salt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, salt, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(password, other.password) && Objects.equals(salt, other.salt) && Objects.equals(userId, other.userId) && Objects.equals(username, other.username);
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