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
		<div class = "container-fluid">
			<g:render template = "../securityClassificationHeader"/>

			<div class = "row">
				<div class = "col-md-2"></div>
				<div class = "col-md-8">
					<h1 align = "center">Time Lapse Viewer (TLV)</h1>
				</div>
				<div class = "col-md-2"></div>
			</div>
			<ul class = "nav nav-tabs" id = "tlvTabs" role = "tablist">
				<li class = "active" role = "presentation">
					<a data-toggle = "tab" href = "#searchTab" role = "tab">Search</a>
				</li>
				<li role = "presentation">
					<a data-toggle = "tab" href = "#timeLapseTab" role = "tab">Time Lapse</a>
				</li>
				<g:render template = "menus/export"/>
				<g:render template = "menus/imageProperties"/>
				<g:render template = "menus/layers"/>
				<g:render template = "menus/view"/>
			</ul>

  			<div class = "tab-content">
				<g:render template = "searchTab"/>
				<g:render template = "timeLapseTab"/>
			</div>

			<g:render template = "dialogs"/>
		</div>

		<asset:deferredScripts/>
	</body>
</html>
