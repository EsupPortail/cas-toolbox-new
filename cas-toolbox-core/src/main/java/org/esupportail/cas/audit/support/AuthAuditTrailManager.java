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
package org.esupportail.cas.audit.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.AuditActionContext;


public final class AuthAuditTrailManager implements AuditTrailManager {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	public void record(final AuditActionContext auditActionContext) {
		if(("AUTHENTICATION_SUCCESS").equals(auditActionContext.getActionPerformed()) || ("AUTHENTICATION_FAILED").equals(auditActionContext.getActionPerformed())) {
			if(auditActionContext.getPrincipal().startsWith("[username:")) {
				log.info(auditActionContext.getWhenActionWasPerformed() + " - " + auditActionContext.getActionPerformed() + " for '"+auditActionContext.getPrincipal() + "' from '" + auditActionContext.getClientIpAddress() + "'");
			}
		}
			log.info(auditActionContext.getWhenActionWasPerformed() + " - " + auditActionContext.getActionPerformed() + " for '"+auditActionContext.getPrincipal() + "' from '" + auditActionContext.getClientIpAddress() + "'");
	}
}
