package org.jasig.cas.services;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.services.RegisteredServiceImpl;

import java.util.regex.*;

public class RegRegisteredServiceImpl extends RegisteredServiceImpl {
	private static final long serialVersionUID = -5136718302682868276L;
	protected final Log logger = LogFactory.getLog(this.getClass());
    
    public boolean matches(final Service service) {
    	
    	if(service != null ) {
    		Pattern p = Pattern.compile(getServiceId(),Pattern.CASE_INSENSITIVE);
    		Matcher m = p.matcher(service.getId());
    		boolean result = m.find();
    		        	
    		if(result)
    			return true;
    	}
    	return false;
    }
}
