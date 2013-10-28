<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@
page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Table</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="../css/chessboard-0.3.0.min.css" />
<script src="../js/chessboard-0.3.0.min.js"></script>
<script src="../js/jquery-1.10.2.min.js"></script>
<script src="../js/chess.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div id="board" style="width: 400px"></div>
	<p>Status: <span id="status"></span></p>
	<p>FEN: <span id="fen"></span></p>
	<p>PGN: <span id="pgn"></span></p>

	<script src="../js/table.js"></script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>