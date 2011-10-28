package org.esupportail.cas.services.blockAttack;

public interface AccountLockingService {
		
	public boolean isAccountLocked(String login);
	
	public long getLockTime();

	public void incrementAttempts(String login);
}
