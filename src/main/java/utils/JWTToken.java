package utils;

/**
 * 
 * @author jonathan
 *
 */
public class JWTToken {
	
	private String token = "";
	private long expires;
	
	/**
	 * 
	 * @param pToken
	 */
	public JWTToken(final String pToken) {
		this.token = pToken;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(final String token) {
		this.token = token;
	}
	public long getExpires() {
		return expires;
	}
	public void setExpires(final long expires) {
		this.expires = expires;
	}

}
