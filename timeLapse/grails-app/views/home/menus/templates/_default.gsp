<table class = "table">
	<g:each in = "${(1..6)}">
		<tr>
			<td align = "right">Line ${it}:</td>
			<td><input id = "exportFrameLine${it}Input" type = "text"></td>
		</tr>
	</g:each>
	<tr>
		<td align = "right">Logo:</td>
		<td>
			<% def logos = [] %>
			<% new File("grails-app/assets/images/logos").eachFile() { logos << it.name.find(/[^\\.]+/) } %>
			<select id = "exportFrameLogoSelect">
				<g:each in = "${logos}">
					<option value = "${it}">${it.toUpperCase()}</option>
				</g:each>
			</select>
		</td>
	</tr>
</table>
