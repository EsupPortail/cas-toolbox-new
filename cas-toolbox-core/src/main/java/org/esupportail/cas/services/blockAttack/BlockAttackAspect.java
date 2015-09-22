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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.esupportail.cas.services.blockAttack.AccountLockingService;
import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;
import org.jasig.cas.authentication.UsernamePasswordCredential;


@Aspect
public final class BlockAttackAspect{
	protected static Logger log = LoggerFactory.getLogger(BlockAttackAspect.class);
	
	private static final String CODE = "error.authentication.credentials.locked";
	private AccountLockingService accountLockingService ;
	
	public boolean activeWait=false;
	public boolean enabled=false;

	@Pointcut("execution(* org.jasig.cas.authentication.AuthenticationManager.authenticate(..))")
	public void inExecute() { }	
	
	
	@Around("inExecute() && args(credential,..)")
	public Object AroundExecute(ProceedingJoinPoint pjp, UsernamePasswordCredential credential) throws Throwable {
		if(!enabled) {
			return pjp.proceed();
		}
		
		String username = credential.getUsername();
		
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
