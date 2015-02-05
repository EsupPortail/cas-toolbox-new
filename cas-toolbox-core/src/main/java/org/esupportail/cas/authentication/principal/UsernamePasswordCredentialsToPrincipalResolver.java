/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.esupportail.cas.authentication.principal;

//import org.jasig.cas.authentication.AbstractPersonDirectoryCredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.PersonDirectoryPrincipalResolver;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.Credential;

/**
 * Implementation of CredentialsToPrincipalResolver for Credential based on
 * UsernamePasswordCredentials when a SimplePrincipal (username only) is
 * sufficient.
 * <p>
 * Implementation extracts the username from the Credential provided and
 * constructs a new SimplePrincipal with the unique id set to the username.
 * </p>
 * 
 * @author Scott Battaglia
 * @author Ludovic Auxepaules
 * @version $Revision: 1.3 $ $Date: 2014/12/19 $
 * @since 3.0
 * @see org.jasig.cas.authentication.principal.SimplePrincipal
 */
public final class UsernamePasswordCredentialsToPrincipalResolver extends
PersonDirectoryPrincipalResolver {

	protected String extractPrincipalId(final Credential credential) {
		final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
		return usernamePasswordCredential.getUsername().toLowerCase().trim();
	}

	/**
	 * Return true if Credential are UsernamePasswordCredential, false
	 * otherwise.
	 */
	public boolean supports(final Credential credential) {
		return credential != null
			&& UsernamePasswordCredential.class.isAssignableFrom(credential.getClass());
	}
}
