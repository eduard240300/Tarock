<%--
  Created by IntelliJ IDEA.
  User: forest
  Date: 4/29/2021
  Time: 12:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.eduard240300.TarockWebSite.Domain.PublishingHouse" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>

<% ArrayList<PublishingHouse> publishingHouses = (ArrayList) request.getAttribute("publishingHouses"); %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
        .book-name {
            background-color: cornflowerblue;
            border-right: solid 1px black;
        }
    </style>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>
</head>
<body>

<table border="" cellspacing="2" cellpadding="2">

    <tr><th>ID</th><th>Name</th><th>Url</th><th>Number of books</th></tr>
    <%
        // Iterating through subjectList

        if(request.getAttribute("publishingHouses") != null)  // Null check for the object
        {
            Iterator<PublishingHouse> iterator = publishingHouses.iterator();  // Iterator interface

            while(iterator.hasNext())  // iterate through all the data until the last record
            {
                PublishingHouse details = iterator.next(); //assign individual employee record to the employee class object
    %>
    <tr><td><%=details.getId()%></td>
        <td><%=details.getName()%></td>
        <td><%=details.getUrl()%></td>
        <td><%=details.getNumberOfBooks()%></td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>