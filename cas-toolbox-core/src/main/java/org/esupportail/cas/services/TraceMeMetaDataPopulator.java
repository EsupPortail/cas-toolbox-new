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
package org.esupportail.cas.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.AuthenticationBuilder;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.Credential;
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
	public void populateAttributes(AuthenticationBuilder builder, Credential credential) {
		if(enabled) {
			String id = traceMeUniqueIdGenerator.getNewTicketId("TRACE");
			String principalId = builder.getPrincipal().getId();
			
			ExternalContext externalContext = ExternalContextHolder.getExternalContext();
			ServletExternalContext servletExternalContext = (ServletExternalContext) externalContext;
			 
			HttpServletRequest request = (HttpServletRequest) servletExternalContext.getNativeRequest();
			HttpServletResponse response = (HttpServletResponse) servletExternalContext.getNativeResponse();
			
			this.traceMeCookieGenerator.addCookie(request, response, id);

			log.info(id + ":" + principalId);
		}
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
