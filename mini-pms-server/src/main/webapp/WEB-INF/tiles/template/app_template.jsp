<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PMS</title>
<style>
  #menubar {
    background-color:gray;
    color:white;
    height: 30px;
    padding: 5px;
  }
  #menubar a {
    color:white; 
    text-decoration: none;
  }
  #menubar a:visited {
    color:white; 
  }
  #menubar a:hover {
    text-decoration: underline;
  }
  
  footer {
    background-color: navy;
    color: white;
    text-align: center;
  }
  
  #content {
    border: 1px solid black;
    width: 600px;
    margin: 0px auto;
  }
</style>
</head>
<body>

<div id="content">

<tiles:insertAttribute name="header"/>

<div id="main">
<tiles:insertAttribute name="body"/>
</div>

<tiles:insertAttribute name="footer"/>

</div>

</body>
</html>