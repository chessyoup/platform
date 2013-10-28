<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@
page session="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Training session</title>

<meta http-equiv="X-UA-Compatible" content="chrome=1" />

<link rel="canonical" href="${room_link}" />

<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script src="../js/main.js"></script>
<script src="../js/adapter.js"></script>
<link rel="stylesheet" href="../css/main.css" />

</head>
<body>

	<script type="text/javascript">
	  var errorMessages = [];
	  var channelToken = '${token}';
	  var me = '${me}';
	  var roomKey = '${room_key}';
	  var roomLink = '${room_link}';
	  var initiator = ${initiator};
	  var pcConfig = {"iceServers": [{"url": "stun:stun.l.google.com:19302"}]};
	  var pcConstraints = {"optional": [{"DtlsSrtpKeyAgreement": true}]};
	  var offerConstraints = {"optional": [], "mandatory": {}};
	  var mediaConstraints = {"audio": true, "video": true};
	  var turnUrl = 'https://computeengineondemand.appspot.com/turn?username=64997429&key=4080218913';
	  var stereo = false;
	  var audio_send_codec = '';
	  var audio_receive_codec = 'opus/48000';
	  setTimeout(initialize, 1);
	</script>
			
	
<div id="container" ondblclick="enterFullScreen()">
  <div id="card">
    <div id="local">
      <video id="localVideo" autoplay="autoplay" muted="true"/>
    </div>
    <div id="remote">
      <video id="remoteVideo" autoplay="autoplay">
      </video>
      <div id="mini">
        <video id="miniVideo" autoplay="autoplay" muted="true"/>
      </div>
    </div>
  </div>
</div>
</body>
<footer id="status">
</footer>
<div id="infoDiv"></div>
</html>