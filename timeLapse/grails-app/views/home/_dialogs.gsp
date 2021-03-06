<div class = "modal" id = "loadingDialog">
	<div class = "modal-dialog">
		<div class = "modal-content">
			<div class = "modal-header"><h4>Please wait...</h4></div>
			<div class = "modal-body">
				<div id = "loadingDialogMessageDiv"></div>
				<br>
				<div class = "progress">
					<div class = "progress-bar progress-bar-striped" role = "progressbar" style = "width: 100%"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<g:render template = "timeLapseDialogs"/>
<g:render template = "menus/exportMenuDialogs"/>
<g:render template = "menus/imagePropertiesMenuDialogs"/>
<g:render template = "menus/searchMenuDialogs"/>
<g:render template = "menus/timeLapseMenuFunctionsDialogs"/>

<div class = "modal" id = "helpDialog">
	<div class = "modal-dialog">
		<div class = "modal-content">
			<div class = "modal-header"><h4>Help!</h4></div>
			<div class = "modal-body"><g:render plugin = "networkSpecific" template = "helpDialog/helpDialog"/></div>
			<div class = "modal-footer"><button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button></div>
		</div>
	</div>
</div>
