<%-- 
    Document   : jsontest
    Created on : 11 Feb, 2019, 6:24:47 PM
    Author     : Comp001
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.io.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    System.out.println("recieved");
    BufferedReader buff = new BufferedReader(new InputStreamReader(request.getInputStream()));
    String jsonstr="" ;
    if(buff!=null)
    {
        
        jsonstr = buff.readLine();
    }
    
    JSONObject obj ;
    System.out.println(jsonstr);
    try
    {
        obj = new JSONObject(jsonstr);
        System.out.println(obj.get("name"));
          System.out.println(obj.get("age"));
            System.out.println(obj.get("sal"));
            out.println("Yeah We Recieved The data");
    }
    catch(Throwable th)
    {
       th.printStackTrace();
    }
    
    
    %>
