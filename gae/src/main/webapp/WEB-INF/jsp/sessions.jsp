<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Sessions</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.css" rel="stylesheet">


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	
	<div class="container">
		<label>Sessions</label><br/>
		<c:forEach items="${sessions}" var="session">
		<a href="/session/${session.id}?user=${user}">${session.teacher.username} -> ${session.student.username}</a>	<br/>	  			
		</c:forEach>
	</div>
	<!-- /container -->

	<script src="../js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		
		
			
	</script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>