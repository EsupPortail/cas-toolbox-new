/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.esupportail.cas.web.flow;

import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


public final class TraceMeAction extends AbstractAction {

	private static final Logger LOG = LoggerFactory.getLogger(TraceMeAction.class);
	
	@NotNull
	private CookieRetrievingCookieGenerator traceMeCookieGenerator;
	
	@NotNull
	private UniqueTicketIdGenerator traceMeUniqueIdGenerator;
	
	private boolean enabled = false;
	
	@Override
	protected Event doExecute(final RequestContext context) {
		if(enabled) {
			String id = traceMeUniqueIdGenerator.getNewTicketId("TRACE");
			UsernamePasswordCredential userCreds = (UsernamePasswordCredential) context.getFlowScope().get("credential");
			
			this.traceMeCookieGenerator.addCookie(
					WebUtils.getHttpServletRequest(context), 
					WebUtils.getHttpServletResponse(context), id);

			if(userCreds != null) {
				LOG.info(id + ":" + userCreds.getUsername());
			} else {
				LOG.error(id + ":NULL");
			}
				
		}
		return success();
	}
	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setTraceMeCookieGenerator(final CookieRetrievingCookieGenerator traceMeCookieGenerator) {
		this.traceMeCookieGenerator= traceMeCookieGenerator;
	}

	public void setTraceMeUniqueIdGenerator(final UniqueTicketIdGenerator uniqueTicketIdGenerator) {
		this.traceMeUniqueIdGenerator = uniqueTicketIdGenerator;
	}
}
