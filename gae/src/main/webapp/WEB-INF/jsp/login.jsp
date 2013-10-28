<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>${name} Signin</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../css/signin.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<form class="form-signin">
			<h2 class="form-signin-heading">${name} sign in</h2>
			<input type="text" class="form-control" id="username" placeholder="Username"
				autofocus> <input type="password" id="password" class="form-control"
				placeholder="Password"> <label class="checkbox"> <input
				type="checkbox" value="remember-me"> Remember me
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="button"
				id="login">Sign in</button>
		</form>

	</div>
	<!-- /container -->

	<script src="../js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		
		$( "#login" ).on( "click", function( event ) {						
  			$.ajax({
			  	url: "/api/authentication/${name}",
			  	beforeSend: function (request)
	            {
	                request.setRequestHeader("Accept", "Application/json");
	            },
	            processData: false,
				type : "POST",
				dataType: "json",  
			 	contentType:"application/json; charset=utf-8",
			  	data: '{"username": "'+$("#username").val()+'" , "password" : "'+$( "#password").val()+'" }',
				   
				success: function( data ) {
					console.log(data);										
					window.location.href = '../sessions?user='+$("#username").val();												
				},
				
				statusCode: {
				  401: function() {
				    alert( "Access denied!" );
				  }
				}			  
			});
		});
			
	</script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>