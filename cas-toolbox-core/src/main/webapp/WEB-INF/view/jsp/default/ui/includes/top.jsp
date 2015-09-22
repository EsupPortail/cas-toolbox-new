<%--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <title>CAS &#8211; Central Authentication Service</title>
  
  <spring:theme code="standard.custom.css.file" var="customCssFile" />
  <link type="text/css" rel="stylesheet" href="<c:url value="${customCssFile}" />" />
  <link rel="icon" href="<c:url value="/favicon.ico" />" type="image/x-icon" />
  
  <!--[if lt IE 9]>
    <script src="<c:url value="/js/html5shiv.js" />" type="text/javascript"></script>
  <![endif]-->
</head>
<body id="cas">
  <div id="container">
      <header id="header">
        <h1 id="app-name">Central Authentication Service (CAS)</h1>
      </header>
      <div id="content">
