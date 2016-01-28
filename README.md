# ESUP CAS


This package (esup cas-toolbox-new v3 and v4) is used to deploy and customize a CAS server (v3.4.1x or v4.0.x)
You must have JDK (1.6 or greater), Maven 3 install (and Tomcat 7 or greater)

 - Deploying a CAS server into an existing Tomcat installation
 - Simplifying and centralizing CAS configuration
     * config.properties file and Authentication “HandlersDiscover” functionality
 - Esup pre-settings and add-ons
     * Esup Theme, LDAP, Logging, TraceMe, Stats, BlockAttack, SAML 1.1…
     * Configured modules that user can activate : Memcached, Rest…
 - token manager addon integration


-------------------------------------
1. Install
-------------------------------------

 - git clone https://github.com/EsupPortail/cas-toolbox-new.git  
 - git checkout v4.0.x
 - Rename config.sample.properties to config.properties 
 - Set the deploy.path property in config.properties
 - Configure config.properties
 - (Set customizations in the cas-toolbox-custom directory : deployerConfigContext.xml for example )
 - Initialize and deploy in an existing Tomcat by running 
	mvn clean package
 - Start Tomcat and browse 
	http://localhost:8080/cas
	http://localhost:8080/cas-management 

For the usage of the Token Manager addon
(method without maven repository for the token manager, when a repository will be created the installation method will changed)

- git clone https://github.com/Hakall/esup-cas-tokenmanager.git
- copy the esup-cas-tokenmanager/cas-addon-ticket-management/src/main/java/org/esupportail/cas/addon folder into cas-toolbox-new/cas-toolbox-core/src/main/java/org/esupportail/cas/
- configure the *.properties files of both cas-addon-ticket-management and cas-ticket-management-webapp folders to match your installation
- Deploy esup-cas-tokenmanager/cas-ticket-management-webapp in your Tomcat
- Start Tomcat and browse 
	http://localhost:8080/cas-ticket-management/


-------------------------------------
2. Directories Structure
-------------------------------------

An integrator can easily copy the files from cas-toolbox-core (or the Apereo CAS project) to cas-toolbox-custom and then modify the files.

```
cas-toolbox-core
  |-- src/main/java
  |-- src/main/webapp
  |-- target
  pom.xml
cas-toolbox-custom
  |-- src/main/webapp
  |-- target
  pom.xml
cas-toolbox-management
  |-- src/main/webapp
  |-- target
  pom.xml
etc
config.sample.properties
config.properties
pom.xml
```

 - target : folders where cas server was build before deploy
 - cas-toolbox-core : folder that stores all esup addons and preconfigurations of the cas server
 - cas-toolbox-core/src/main/java : java sources added or updated by the esup packaging
 - cas-toolbox-core/src/main/webbapp : files of webpages, configurations (spring, log, messages...) updated by the esup packaging
 - cas-toolbox-custom : folder where you can put your configurations and cutomizations of the cas server (the same stucture of cas-toolbox-core folder)
 - cas-toolbox-management : folder that stores a default configuration of the services management module of the cas server
 - etc : HOWTo help files, scripts, notice and licence examples...
 - config.sample.properties : esup cas configuration example
 - config.properties : esup cas configuration file (with University )


-------------------------------------
3. Statistics, Monitoring and Logs
-------------------------------------

- Esup Stats Page: /cas/stats.jsp
```
SERVICE_TICKET_CREATED : 1
TICKET_GRANTING_TICKET_CREATED : 1
AUTHENTICATION_SUCCESS : 1
AUTHENTICATION_FAILED : 1
SERVICE_TICKET_VALIDATED : 1
TICKET_GRANTING_TICKET_NOT_CREATED : 1
```

- Apereo Monitoring Page: /cas/status
```
Health: OK
	1.MemoryMonitor: OK - 418,07MB free, 623,54MB total.
	2.SessionMonitor: OK - 1 sessions. 0 service tickets.
```

- Esup User and Service Stats Logs: serviceStats.log
```
[Sun May 31 16:01:15 CEST 2015] [IP:127.0.0.1] [ID:admin] [TICKET:ST-1-ZW74nIKOVEECbowbB0BT-localhost] [SERVICE:http://localhost:8080/cas-management/j_spring_cas_security_check] [USER-AGENT:Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0]
```

- Apereo Performance Statistics Logs: perfStats.log
```
Performance Statistics   2015-05-31 16:01:00 - 2015-05-31 16:02:00
Tag                                                  Avg(ms)         Min         Max     Std Dev       Count
AUTHENTICATE                                            21,0          21          21         0,0           1
CREATE_TICKET_GRANTING_TICKET                           25,0          25          25         0,0           1
GRANT_SERVICE_TICKET                                     3,0           3           3         0,0           1
VALIDATE_SERVICE_TICKET                                  1,0           1           1         0,0           1
```
- Tickets: tickets.log
```
2015-05-31 16:01:15,606 INFO [org.jasig.cas.CentralAuthenticationServiceImpl] - Granted service ticket [ST-1-ZW74nIKOVEECbowbB0BT-localhost] for service [http://localhost:8080/cas-management/j_spring_cas_security_check] for user [admin]
```

- Success and failed authentications, blockAttack: auth.log (works with CAS 3)
```
Thu Dec 18 15:47:00 CET 2014 - AUTHENTICATION_FAILED for '[username: admin]' from '127.0.0.1'
AccountLockingService::incrementAttempts() : [admin] - number of attempts : 3 of 3AccountLockingService::isAccountLocked() : [admin] added to user list
AccountLockingService::isAccountLocked() : [admin] locked
Account "admin" is locked for : 3 s
AccountLockingService::run() : [admin] removed from user list
Thu Dec 18 15:48:00 CET 2014 - AUTHENTICATION_SUCCESS for '[username: admin]' from '127.0.0.1‘
```

- Rejected Services: rejectedServices.log
```
2015-05-31 16:02:31,091 WARN [org.jasig.cas.web.flow.ServiceAuthorizationCheck] - ServiceManagement: Unauthorized Service Access. Service [http://loc:8080/cas-management/j_spring_cas_security_check] is not found in service registry.
```

- CAS management Services: cas-management.log
```
2015-05-31 16:01:15,606 INFO [org.jasig.cas.CentralAuthenticationServiceImpl] - Granted service ticket [ST-1-ZW74nIKOVEECbowbB0BT-localhost] for service [http://localhost:8080/cas-management/j_spring_cas_security_check] for user [admin]
```

- Trace (used by Esup AGIMUS-ng: Indicators and Usage): trace.log
```
TRACE-1-70RFFjeWwSqCfoqwHxUoimyMBELrhdlqAjhtlhoIpChkFnuG5f-localhost:admin
```

-------------------------------------
4. Authentication handlers "Discover"
-------------------------------------

- Set Handlers to use in config.properties (reported in esup.properties file)
```
	# Handler to use (cf cas\WEB-INF\*.auth.xml to find id)
	# - ldapFastBindHandler : make a fast bind in ldap (AD or direct bind configuration)
	# - ldapFullBindHandler : make a search after a bind with find dn (OpenLdap or anonymous)
	# - fileEncAuthHandler : use a flat encoded file
	# - filePlainAuthHandler : use a flat plaintext file
	authHandlers=fileEncAuthHandler
	# IF USE ldapFullBindHandler
	#authHandlers=fileEncAuthHandler,ldapFullBindHandler
	
	authResolvers=primaryPrincipalResolver
	# IF USE ldapFullBindHandler
	#authResolvers=primaryPrincipalResolver,ldapPrincipalResolver
	defaultResolver=primaryPrincipalResolver
```

- HandlersDiscover JAVA class: org.esupportail.cas.HandlersDiscover.java
	* Browse the list of authentication Handlers, the list of authentication Resolvers
	* Put each couple <AuthHandler, AuthResolver> within the HandlerMap used by Authentication Manager bean

- Handlers to discover within src\main\webapp\WEB-INF\auth-configuration\*-auth.xml

- HandlerDiscover bean defined in src\main\webapp\WEB-INF\deployerConfigContext.xml
```
<bean id="authenticationManager" class="org.jasig.cas.authentication.PolicyBasedAuthenticationManager">
	<constructor-arg ref="handlerMap"/>
	...
	<property name="authenticationPolicy">
		<bean class="org.jasig.cas.authentication.AnyAuthenticationPolicy" />
	</property>
</bean>

<bean id="handlerMap" class="org.springframework.beans.factory.config.MapFactoryBean">
	<property name="sourceMap">
	<map>
		<entry key-ref="proxyAuthenticationHandler" value-ref="proxyPrincipalResolver" />
	</map>
	</property>
</bean>

<bean id="handlerDiscover" class="org.esupportail.cas.HandlersDiscover">
	<property name="handlersId" value="${cas.authHandlers}"/>
	<property name="resolversId" value="${cas.authResolvers}"/>
	<property name="defaultResolverId" value="${cas.defaultResolver}"/>
	<property name="mapToAdd" ref="handlerMap"/>
</bean>
```

-------------------------------------
5. Apereo CAS Documentation
-------------------------------------

http://jasig.github.io/cas/4.0.x/
