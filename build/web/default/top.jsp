<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
    <base href="<%=basePath%>">
    
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900|Quicksand:400,700|Questrial" rel="stylesheet" />
    <link href="default/websiteTemplate/default.css" rel="stylesheet" type="text/css" media="all" />
    <link href="default/websiteTemplate/fonts.css" rel="stylesheet" type="text/css" media="all" />

    
    
    <!-- JSCook Menu -->
    <script type="text/javascript" src="/default/JSCookMenu.js"></script>
    
    <link rel="stylesheet" href="default/menu/ThemeGray/theme.css" type="text/css"/>
    <script type="text/javascript" src="default/menu/ThemeGray/theme.js"></script>
    <!link rel="stylesheet" href="default/menu/ThemeGray/theme.css" type="text/css"/-->
   
    <meta http-equiv="Content-Language" content="pt-br">
    <title>An Ontology Based Platform for Instructional Design</title>
    <!--link rel="stylesheet" type="text/css" href="default/estilo.css"-->
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

</head>
<body bgcolor="#CCCCCC" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    
    <div id="header-wrapper">
	<div id="header" class="container">
		<div id="logo">
			<h1><a href="#">Instructional Design Authoring Platform</a></h1>
		</div>
	</div>
</div>

<div align="center">
<!--
    <table id="Tabela_01" width="778" height="86" cellpadding="0" cellspacing="0" style="border-left: 1px solid #808080; border-right: 1px solid #808080; padding: 0">
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top01.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top02.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top03.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top04.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top05.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top06.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top07.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="10" colspan="2" background="imgs/top08.jpg"></td>
    </tr>
    <tr>
        <td width="778" height="6" colspan="2" background="imgs/top09.jpg"></td>
    </tr>
</table>
<table id="Tabela_02" width="778" height="47" cellpadding="0" cellspacing="0" style="border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0" bgcolor="#FFFFFF" background="imgs/background.jpg">
    <tr>
        <td width="160" height="10" background="imgs/top10.jpg"></td>
        <td width="618" height="47" rowspan="4" valign="top">
            <p align="right" style="margin-right: 10px; margin-top: 10px"><b>
        <font face="Verdana" size="2">Módulo de Autoria</font></b></td>
    </tr>
    <tr>
        <td width="160" height="10" background="imgs/top11.jpg"></td>
    </tr>
    <tr>
        <td width="160" height="10" background="imgs/top12.jpg"></td>
    </tr>
    <tr>
        <td width="160" height="17" background="imgs/top13.jpg"></td>
    </tr>
</table> -->
<%@ page import = "br.ufal.ic.forbile.autoria.core.*" %>
<%          
    if (session.getAttribute("authenticatedUser") != null) {
        
        if (request.getServletPath().equals("/login.jsp")) {
        
            User userSessionData = (User)session.getAttribute("authenticatedUser");

            switch(userSessionData.getUserType()){
                case 1:
                response.sendRedirect("admin/index.jsf");
                break;
                case 2:
                response.sendRedirect("author/index.jsf");
                break;
            }
        }
%> 
<p align="center">
<table id="Tabela_Menu" width="778" cellpadding="0" cellspacing="0" style="padding: 0" bgcolor="#334467" align="center">
    <tr>
        <td width="778">
<% 
    } else {
        
        if (!request.getServletPath().equals("/login.jsp"))
            response.sendRedirect(request.getContextPath()+"/login.jsf"); 
%>
</p>
<p align="center">
<table id="Tabela_03" width="778" cellpadding="0" cellspacing="0" style="padding: 0" bgcolor="#334467" align="center">
    <tr>
        <td width="778">
</p>
<%
    }
%>
