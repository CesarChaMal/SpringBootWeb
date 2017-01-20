<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>SpringBoot</title>
	</head>
    
	<body>
		<p>Hello ${name} from a JSP page</p>
		<script src="../webjars/jquery/2.2.4/jquery.min.js"></script>
		<script src="../resources/js/rest.js"></script>
		<script type="text/javascript">

			var jsonObj = {
				name : "Cesar Chavez"
			};

			var url = "http://localhost:8080/SpringBootWeb/post/hello";
			restRequest(url, jsonObj, POST, response)

			url = "http://localhost:8080/SpringBootWeb/get/hello?name=Cesar";
			restRequest(url, jsonObj, GET, response)
			    
			url = "http://localhost:8080/SpringBootWeb/put/hello",
			restRequest(url, jsonObj, PUT, response)

			url = "http://localhost:8080/SpringBootWeb/delete/hello?name=Cesar";
			restRequest(url, jsonObj, DELETE, response)
			
		</script>
	</body>
</html>