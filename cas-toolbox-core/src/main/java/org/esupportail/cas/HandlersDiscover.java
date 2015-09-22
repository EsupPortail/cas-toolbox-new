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