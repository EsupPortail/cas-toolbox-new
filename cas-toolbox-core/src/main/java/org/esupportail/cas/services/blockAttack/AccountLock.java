/**
 * Licensed to Esup-Portail under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Esup-Portail licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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