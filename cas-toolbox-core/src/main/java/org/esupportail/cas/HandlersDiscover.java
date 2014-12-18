package org.esupportail.cas;

import java.util.List;

import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HandlersDiscover implements ApplicationContextAware,InitializingBean {
	private String[] handlersId;
	private List<AuthenticationHandler> listToAdd;
	private ApplicationContext context;
	
	public HandlersDiscover() {
	}

	public void setHandlersId(String[] handlersId) {
		this.handlersId = handlersId;
	}


	public void setListToAdd(List<AuthenticationHandler> listToAdd) {
		this.listToAdd = listToAdd;
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public void afterPropertiesSet() throws Exception {
		for (int i =0; i<handlersId.length; i++){
			String beanId = handlersId[i];
			AuthenticationHandler handlerBean = (AuthenticationHandler) context.getBean(beanId);
			listToAdd.add(handlerBean);
		}
	}

}