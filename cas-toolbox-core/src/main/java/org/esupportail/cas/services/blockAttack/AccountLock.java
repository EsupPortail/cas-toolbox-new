package org.esupportail.cas.services.blockAttack;

public class AccountLock {

	private String login;
	private long time = System.currentTimeMillis();
	private int attempts;
	
	/**
	 * Constructeur
	 */
	public AccountLock() {
	}

	/**
	 * Retourne le login
	 * @return
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Positionne le login
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Retourne l'heure
	 * @return
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Positionne l'heure
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Retourne le nb de tentatives
	 * @return the attempts
	 */
	public int getAttempts() {
		return attempts;
	}

	/**
	 * Positionne le nb de tentatives
	 * @param attempts the attempts to set
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
}