<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8">
		<meta http-equiv = "X-UA-Compatible" content = "IE=edge">
		<meta name = "viewport" content = "width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

		<title>Time Lapse Viewer (TLV)</title>
		
    		<asset:stylesheet src = "indexBundle.css"/>
		<asset:script>var tlv = ${raw(tlvParams)};</asset:script>
    		<asset:javascript src = "indexBundle.js"/>
	</head>
	<body>
		<div class = "container-fluid" style = "height: 100%">
			<g:render template = "../securityClassificationHeader"/>

			<nav class = "navbar navbar-default navbar-inverse navbar-static-top">
				<div class = "container-fluid">
					<div class = "navbar-header"><a class = "navbar-brand" href = "/timeLapse/home">TLV</a></div>
					<div class = "collapse navbar-collapse">
						<ul class = "nav navbar-nav">
							<g:render template = "menus/searchMenu"/>
							<g:render template = "menus/exportMenu"/>
							<g:render template = "menus/imagePropertiesMenu"/>
							<g:render template = "menus/layersMenu"/>
							<g:render template = "menus/viewMenu"/>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="#">Help</a></li>
						</ul>
					</div>
				</div>		
			</nav>
					
			<g:render template = "timeLapse"/>  

			<g:render template = "dialogs"/>
		</div> 

		<asset:deferredScripts/>
	</body>
</html>
