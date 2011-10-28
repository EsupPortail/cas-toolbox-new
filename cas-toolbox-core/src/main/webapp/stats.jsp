<%@ page import="java.util.*" 
%><%@ page import="org.esupportail.cas.statistics.support.InMemoryStatisticManager"
%>
<html>
<head>
  <title>CAS Stats</title>
</head>


<%

if(InMemoryStatisticManager.isEnabled()) {
	Set<String> ensemble = InMemoryStatisticManager.statsVal.keySet();
	for(String s : ensemble) {
		out.println(s + " : " + InMemoryStatisticManager.statsVal.get(s)+"<BR/>");
	}
}
else {
	out.println("Les statistiques ne sont pas activ&eacute;es<BR/>");
}
%>
