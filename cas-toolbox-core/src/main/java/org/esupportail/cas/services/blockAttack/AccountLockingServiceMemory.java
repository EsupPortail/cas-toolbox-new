package org.esupportail.cas.services.blockAttack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.esupportail.cas.services.blockAttack.AccountLock;

public class AccountLockingServiceMemory implements AccountLockingService, Runnable {

	public long lockTime = 5 * 1000;
	public long cleanTimeout = 30 * 1000;
	public long cleanExecution = 60 * 1000;
	public int attemptsAllowed = 3;
	
	protected static Log log = LogFactory.getLog(AccountLockingService.class);
	
	private Thread t = null;
	private boolean running = true;
	
	private Map<String, AccountLock> accounts = Collections.synchronizedMap(new HashMap<String, AccountLock>());
	
	public boolean isAccountLocked(String login) {
		
		long time = System.currentTimeMillis();
		
		// Le compte n'est pas dans la liste
		if(!accounts.containsKey(login)) {
			// On crée le compte
			AccountLock a = new AccountLock();
			a.setLogin(login);
			a.setAttempts(0);
			log.debug("AccountLockingService::isAccountLocked() : [" + login + "] added to user list");
			accounts.put(login, a);
			return false;
		}
		AccountLock a = accounts.get(login);
		if(a.getAttempts() >= attemptsAllowed) {
			if(a.getAttempts() == attemptsAllowed) {
				log.debug("AccountLockingService::isAccountLocked() : [" + login + "] locked");
			}
			if((time - a.getTime()) <= lockTime) {
				a.setTime(time);
				return true;
			}
		}
		a.setTime(time);
		return false;
	}

	public void run() {
			
		while(running) {
			long currentTime = System.currentTimeMillis();
			
			// Parcours des comptes présents en mémoire
			for(Iterator<String> i = accounts.keySet().iterator();i.hasNext();) {
				AccountLock a = accounts.get(i.next());
				if((currentTime - a.getTime()) > cleanTimeout) {
					// Suppression des anciens comptes
					log.debug("AccountLockingService::run() : [" + a.getLogin() + "] removed from user list");
					i.remove();
				}
			}
			
			try { 
				Thread.sleep(cleanExecution);
			} catch (InterruptedException iex) {
				running=false;
			}
		}
	}
	
	public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}

	public long getLockTime() {
		return lockTime;
	}

	public void setCleanTimeout(long cleanTimeout) {
		this.cleanTimeout = cleanTimeout;
	}

	public void setCleanExecution(long cleanExecution) {
		this.cleanExecution = cleanExecution;
	}

	/**
	 * @return the attemptsAllowed
	 */
	public int getAttemptsAllowed() {
		return attemptsAllowed;
	}

	/**
	 * @param attemptsAllowed the attemptsAllowed to set
	 */
	public void setAttemptsAllowed(int attemptsAllowed) {
		this.attemptsAllowed = attemptsAllowed;
	}

	/**
	 * @param login
	 */
	public void incrementAttempts(String login) {
		// Le compte n'est pas dans la liste
		long time = System.currentTimeMillis();
		if(!accounts.containsKey(login)) {
			// On crée le compte : ne devrait pas se produire
			log.debug("AccountLockingService::incrementAttempts() : [" + login + "] added to user list");
			AccountLock a = new AccountLock();
			a.setLogin(login);
			a.setTime(time);
			a.setAttempts(0);
			accounts.put(login, a);
		}
		AccountLock a = accounts.get(login);
		a.setTime(time);
		a.setAttempts(a.getAttempts() + 1);
		log.debug("AccountLockingService::incrementAttempts() : [" + login + "] - number of attempts : " + a.getAttempts() + " of " + attemptsAllowed);
	}

	public void init() {
		t = new Thread(this);
		log.debug("AccountLockingService::init() : Starting Thread");
		t.start();
	}
	
	public void destroy() {
		log.debug("AccountLockingService::destroy() : Stopping Thread");
		running = false;
		t.interrupt();
	}
}
