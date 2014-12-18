/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.esupportail.cas.services.blockAttack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.esupportail.cas.services.blockAttack.AccountLockingService;
import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;


@Aspect
public final class BlockAttackAspect{
	protected static Logger log = LoggerFactory.getLogger(BlockAttackAspect.class);
	
	private static final String CODE = "error.authentication.credentials.locked";
	private AccountLockingService accountLockingService ;
	
	public boolean activeWait=false;
	public boolean enabled=false;

	@Pointcut("execution(* org.jasig.cas.authentication.AuthenticationManager.authenticate(..))")
	public void inExecute() { }	
	
	
	@Around("inExecute() && args(credentials,..)")
	public Object AroundExecute(ProceedingJoinPoint pjp, UsernamePasswordCredentials credentials) throws Throwable {
		if(!enabled) {
			return pjp.proceed();
		}
		
		String username = credentials.getUsername();
		
		if(accountLockingService.isAccountLocked(username)) {
			log.error("Account \""+username+"\" is locked for : "+accountLockingService.getLockTime()/1000+" s");			
			makeWait();
			throw new BadCredentialsAuthenticationException(CODE);
		}
		
		try {
			return pjp.proceed();
		} catch (BadCredentialsAuthenticationException ex) {
			accountLockingService.incrementAttempts(username);
			if(accountLockingService.isAccountLocked(username)) {
				makeWait();
			}
			throw ex;
		} catch(Exception e) {
			throw e;
		}
	}	
	
	
	private void makeWait(){
		if(!isActiveWait()) return;
		
		try {
			Thread.sleep(accountLockingService.getLockTime());
		} catch (InterruptedException e) {
		}	
	}
	
	public void setAccountLockingService(AccountLockingService accountLockingService) {
		this.accountLockingService = accountLockingService;
	}

	public boolean isActiveWait() {
		return activeWait;
	}

	public void setActiveWait(boolean activeWait) {
		this.activeWait = activeWait;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
