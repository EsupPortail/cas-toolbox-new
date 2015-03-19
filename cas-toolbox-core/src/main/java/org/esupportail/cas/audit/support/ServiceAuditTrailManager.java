/**
 *  Copyright 2007 Rutgers, the State University of New Jersey
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.esupportail.cas.audit.support;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.inspektr.audit.AuditTrailManager;
import com.github.inspektr.audit.AuditActionContext;


public final class ServiceAuditTrailManager implements AuditTrailManager {
    
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    public void record(final AuditActionContext auditActionContext) {
    	if(("SERVICE_TICKET_CREATED").equals(auditActionContext.getActionPerformed())) {    	
    		String resourceOperatedUpon = auditActionContext.getResourceOperatedUpon();
    		if (resourceOperatedUpon.contains(" for ")) {
    			String parts[] = auditActionContext.getResourceOperatedUpon().split(" for ");
    			
    			String ticket = parts[0];    		
        		String service = parts[1];
        		
        		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        	    HttpServletRequest req = sra.getRequest();
        	    String useragent = req.getHeader("User-Agent");
        	    
        		log.info(
        				"[" + auditActionContext.getWhenActionWasPerformed() + "] " + 
        				"[IP:" + auditActionContext.getClientIpAddress() + "] " + 
        				"[ID:" + auditActionContext.getPrincipal() + "] " + 
        			    "[TICKET:" + ticket + "] "+
        			    "[SERVICE:" + service + "] "+
        			    "[USER-AGENT:" + useragent +"]");
    		}
    		
    		
    		
    		
    	}
    	
    	
    }
}
