<%@ page import="com.bananity.util.serialization.JsonSerializer" %>
<%@ page import="java.util.HashMap" %>
<%
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");

HashMap<String, Object> jsonResponse = new HashMap<String, Object>();

// Always present data
jsonResponse.put("status", request.getAttribute("status") );
jsonResponse.put("data", request.getAttribute("data") );

// Optional data
if ( request.getAttribute("einfo") != null ) jsonResponse.put("einfo", request.getAttribute("einfo") );
if ( request.getAttribute("winfo") != null ) jsonResponse.put("winfo", request.getAttribute("winfo") );

out.print(JsonSerializer.ObjectToJsonString( jsonResponse ));
%>
