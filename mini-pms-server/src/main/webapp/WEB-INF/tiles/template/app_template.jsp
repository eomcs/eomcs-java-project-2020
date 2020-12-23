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
  
  #sidebar {
    border: 1px solid black;
    float: left;
    width: 130px;
    min-height: 400px;
    background-color: yellow;
  }
  
  #main {
    border: 1px solid black;
    min-height: 400px;
    margin-left: 130px;
    padding: 10px;
  }
</style>
</head>
<body>

<div id="content">

<tiles:insertAttribute name="header"/>

<tiles:insertAttribute name="sidebar"/>

<div id="main">
<tiles:insertAttribute name="body"/>
</div>

<tiles:insertAttribute name="footer"/>

</div>

</body>
</html>