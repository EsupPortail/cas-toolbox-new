/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.esupportail.cas.web.flow;

import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


public final class TraceMeAction extends AbstractAction {
	protected static Log log = LogFactory.getLog(TraceMeAction.class);
	
	private UniqueTicketIdGenerator traceMeUniqueIdGenerator;
	private boolean enabled = false;
	private boolean cookieSecure = true;
	private int cookieMaxAge = 432000;
	private String cookieName = "CASTRACEME";
	private String cookiePath = "/";
	private String cookieDomain = ".univ.fr";
	
	protected Event doExecute(final RequestContext context) {
		if(enabled) {
			String id = traceMeUniqueIdGenerator.getNewTicketId("TRACE");
			UsernamePasswordCredentials userCreds = (UsernamePasswordCredentials) context.getFlowScope().get("credentials");
			
			Cookie traceCookie = new Cookie(cookieName,id);
			traceCookie.setMaxAge(cookieMaxAge);
			traceCookie.setDomain(cookieDomain);
			traceCookie.setSecure(cookieSecure);
			traceCookie.setPath(cookiePath);
			
			WebUtils.getHttpServletResponse(context).addCookie(traceCookie);
			
			log.info(id + ":" + userCreds.getUsername());
		}
		return success();
	}
	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setCookieSecure(boolean cookieSecure) {
		this.cookieSecure = cookieSecure;
	}


	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}


	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}


	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}


	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}


	public void setTraceMeUniqueIdGenerator(final UniqueTicketIdGenerator uniqueTicketIdGenerator) {
	        this.traceMeUniqueIdGenerator = uniqueTicketIdGenerator;
	    }
}
