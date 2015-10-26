<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8">
		<meta http-equiv = "X-UA-Compatible" content = "IE=edge">
		<meta name = "viewport" content = "width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

		<title>Time Lapse Viewer (TLV)</title>
		<link href = "/timeLapse/assets/tlvicon.ico" rel = "shortcut icon" type = "image/x-icon">

		<link href = "${createLink(action: "openSearch", controller: "home")}" rel = "search" title = "Time-Lapse Viewer" type = "application/opensearchdescription+xml">		
    		<asset:stylesheet src = "indexBundle.css"/>
		<asset:script>var tlv = ${raw(tlvParams)};</asset:script>
    		<asset:javascript src = "indexBundle.js"/>
	</head>
	<body>
		<div class = "container-fluid">
			<g:render template = "../securityClassificationHeader"/>
			<g:render template = "navigationMenu"/>				
			<g:render template = "timeLapse"/>  
			<g:render template = "dialogs"/>
		</div> 

		<asset:deferredScripts/>
	</body>
</html>
