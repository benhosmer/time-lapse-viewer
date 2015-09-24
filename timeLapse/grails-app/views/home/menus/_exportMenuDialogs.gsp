<div class = "modal" id = "exportImageDialog">
	<div class = "modal-dialog">
 		<div class = "modal-content">
			<div class = "modal-header"><h4>Export Image</h4></div>
			<div class = "modal-body">
				<table class = "table">
					<tr>
						<td align = "right">Date:</td>
						<td><input id = "exportImageDateInput" type = "text"></td>
					</tr>
					<tr>
						<td align = "right">Description:</td>
						<td><input id = "exportImageDescriptionInput" type = "text"></td>
					</tr>
					<tr>
						<td align = "right">Footer Security Classification:</td>
						<td><input id = "exportImageFooterSecurityClassificationInput" type = "text"></td>
					</tr>
					<tr>
						<td align = "right">Header Security Classification:</td>
						<td><input id = "exportImageHeaderSecurityClassificationInput" type = "text"></td>
					</tr>
					<tr>
						<td align = "right">Location:</td>
						<td><input id = "exportImageLocationInput" type = "text"></td>
					</tr>
					<tr>
						<td align = "right">Logo:</td>
						<td>
							<% def logos = [] %>
							<% new File("grails-app/assets/images/logos").eachFile() { logos << it.name.find(/[^\\.]+/) } %>
							<select id = "exportImageLogoSelect">
								<g:each in = "${logos}">
									<option value = "${it}">${it.toUpperCase()}</option>
								</g:each>
							</select>
						</td>
					</tr>
					<tr>
						<td align = "right">Title:</td>
						<td><input id = "exportImageTitleInput" type = "text"></td>
					</tr>
				</table>
			</div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "exportImage()">Export</button>
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
 			</div>
		</div>
	</div>
</div>
