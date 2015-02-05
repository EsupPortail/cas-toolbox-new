package org.esupportail.cas;

import java.util.Map;

import javax.validation.constraints.NotNull;


import org.jasig.cas.authentication.AuthenticationHandler;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HandlersDiscover implements ApplicationContextAware,InitializingBean {
	@NotNull
	private String[] handlersId;
	@NotNull
	private String[] resolversId;
	@NotNull
	private String defaultResolverId;
	@NotNull
	private Map<AuthenticationHandler,PrincipalResolver> mapToAdd;

	private ApplicationContext context;

	
	public HandlersDiscover() {
	}

	public void setHandlersId(String[] handlersId) {
		this.handlersId = handlersId;
	}

	public void setResolversId(String[] resolversId) {
		this.resolversId = resolversId;
	}
	
	public void setDefaultResolverId(String defaultResolverId) {
		this.defaultResolverId = defaultResolverId;
	}
	
	public void setMapToAdd(Map<AuthenticationHandler,PrincipalResolver> mapToAdd) {
		this.mapToAdd = mapToAdd;
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public void afterPropertiesSet() throws Exception {
		for (int i =0 ; i< handlersId.length ; i++) {
			String handlerBeanId = handlersId[i];
			String resolverBeanId;
			if(i > resolversId.length) {
				resolverBeanId = defaultResolverId;
			} else {
				resolverBeanId = resolversId[i];
			}
			AuthenticationHandler handlerBean = (AuthenticationHandler) context.getBean(handlerBeanId);
			PrincipalResolver resolverBean = (PrincipalResolver) context.getBean(resolverBeanId);
			mapToAdd.put(handlerBean, resolverBean);
		}
	}

}