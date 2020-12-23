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
</style>
</head>
<body>

<tiles:insertAttribute name="header"/>

<tiles:insertAttribute name="body"/>

<tiles:insertAttribute name="footer"/>

</body>
</html>