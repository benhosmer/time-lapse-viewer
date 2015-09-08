<div class = "tab-pane active" id = "searchTab" role = "tabpanel">
	<div class = "row">
		<div class = "col-md-1"></div>
		<div class = "col-md-11">
			<table class = "table search-table">
				<tr>
					<td align = "right">Location:</td>
					<td>
						<input id = "searchTabLocationDiv" type = "text" value = "28.12540769577, -80.687456130982">
						&nbsp;within&nbsp;
						<select id = "searchTabRadiusSelect">
							<g:each in = "${[1, 10, 50, 100, 500,1000, 5000, 10000]}">
								<option value = ${it}>${it}</option>
							</g:each>
						</select> meter(s)
					</td>
				</tr>
				<tr>
					<td align = "right">Start Date:</td>
					<td>
						<div class = "input-group date" id = "searchTabStartDateTimePicker">
							<input type = "text">
							<span class = "input-group-addon">
								<span class = "glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td align = "right">End Date:</td>
					<td>
						<div class = "input-group date" id = "searchTabEndDateTimePicker">
							<input type = "text">
							<span class = "input-group-addon">
								<span class = "glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td align = "right">Sensor:</td>
					<td>
						<div class = "btn-group" data-toggle = "buttons">
							<label class = "btn btn-primary" id = "searchTabSensorAllLabel" onchange = "javascript:librarySensorCheck()">
								<input id = "searchTabSensorAllCheckbox" type = "checkbox">ALL
							</label>
						<g:each in = "${params.availableResources.sensors}">
							<label class = "btn btn-primary" id = "searchTabSensor${it.name.capitalize()}Label">
								<input id = "searchTabSensor${it.name.capitalize()}Checkbox" type = "checkbox">${it.name.toUpperCase()}
							</label>
						</g:each>
					</td>
				</tr>
				<tr>
					<td align = "right">Min. NIIRS:</td>
					<td><input id = "searchTabMinNiirsInput" max = "9" min = "0" step = "0.1" ty"></td>
				</tr>
				<tr>
					<td align = "right">Max. Cloud Cover (%):</td>
					<td><input id = "searchTabMaxCloudCoverInput" max = "100" min = "0" step = "1" type = "number"></td>
				</tr>
				<tr>
					<td align = "right">Max. Results:</td>
					<td>
						<select id = "searchTabMaxResultsSelect">
							<g:each in = "${[5, 10, 25, 50, 75, 100, 250, 500]}">
								<option value = ${it}>${it}</option>
							</g:each>
						</select>
					</td>
				</tr>
				<tr>
					<td align = "right">Library:</td>
					<td>
						 <div class = "btn-group" data-toggle = "buttons">
							<g:each in = "${params.availableResources.libraries}">
								<label class = "btn btn-primary" id = "searchTabLibrary${it.capitalize()}Label" onchange = "javascript:librarySensorCheck()">
									<input id = "searchTabLibrary${it.capitalize()}Checkbox" type = "checkbox">${it.toUpperCase()}
								</label>
							</g:each>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<button class = "btn btn-primary" onclick = "javascript:beginSearch()">Search</button>
</div>
