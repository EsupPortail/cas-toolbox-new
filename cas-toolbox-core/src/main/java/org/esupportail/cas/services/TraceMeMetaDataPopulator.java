package org.esupportail.cas.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;

public final class TraceMeMetaDataPopulator implements
    AuthenticationMetaDataPopulator {

	protected final static Logger log = LoggerFactory.getLogger(TraceMeMetaDataPopulator.class);
	
	@NotNull
	private CookieRetrievingCookieGenerator traceMeCookieGenerator;
	
	@NotNull
	private UniqueTicketIdGenerator traceMeUniqueIdGenerator;
	
	private boolean enabled = false;
	
	@Override
	public Authentication populateAttributes(Authentication authentication, Credentials credentials) {
		if(enabled) {
			String id = traceMeUniqueIdGenerator.getNewTicketId("TRACE");
			String principalId = authentication.getPrincipal().getId();
			
			ExternalContext externalContext = ExternalContextHolder.getExternalContext();
			ServletExternalContext servletExternalContext = (ServletExternalContext) externalContext;
			 
			HttpServletRequest request = (HttpServletRequest) servletExternalContext.getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) servletExternalContext.getNativeResponse();
			
			this.traceMeCookieGenerator.addCookie(request, response, id);

			log.info(id + ":" + principalId);
		}
		return authentication;
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
