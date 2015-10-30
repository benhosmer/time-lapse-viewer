<div class = "modal" id = "helpDialog">
	<div class = "modal-dialog">
		<div class = "modal-content">
			<div class = "modal-header"><h4>Help!</h4></div>
			<div class = "modal-body">
				<g:render plugin = "networkSpecific" template = "/home/helpDialog/helpDialog"/>

				<% def searchHelp = new File("grails-app/views/home/helpDialog/_search.gsp") %>
				<% def pluginSearchHelp = new File("../plugins/networkSpecific/grails-app/views/home/helpDialog/_search.gsp") %>
				<g:if test = "${searchHelp.exists() || pluginSearchHelp.exists()}"><h6>Search</h6></g:if>
				<g:if test = "${searchHelp.exists()}"><g:render template = "helpDialog/search"/></g:if>
				<g:if test = "${pluginSearchHelp.exists()}"><br><g:render plugin = "networkSpecific" template = "/home/helpDialog/search"/></g:if>	
			</div>
			<div class = "modal-footer"><button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button></div>
		</div>
	</div>
</div>
