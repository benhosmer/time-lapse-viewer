<div class = "modal" id = "searchBookmarkDialog">
	<div class = "modal-dialog">
 		<div class = "modal-content">
			<div class = "modal-body">
				<a id = "searchBookmarkHref">This</a> URL captures ALL of the current search parameters. Keep what you want and delete the rest. Then, bookmark it!
			</div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
			</div>
 		</div>
	</div>
</div>


<div class = "modal" id = "searchDialog">
	<div class = "modal-dialog modal-lg">
		<div class = "modal-content">
			<div class = "modal-header"><h4>Search Parameters</h4></div>
			<div class = "modal-body">
				<div class = "row">
					<div align = "right" class = "col-md-3">Location:</div>
					<div class = "col-md-6">
						<input id = "searchTabLocationDiv" type = "text" value = "28.12540769577, -80.687456130982">
						&nbsp;within&nbsp;
						<select id = "searchTabRadiusSelect">
							<g:each in = "${[1, 10, 50, 100, 500,1000, 5000, 10000]}">
								<option value = ${it}>${it}</option>
							</g:each>
						</select> meter(s)
					</div>
				</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Start Date:</div>
					<div class = "col-md-6">
						<div class = "input-group date" id = "searchTabStartDateTimePicker">
							<input type = "text">
							<span class = "input-group-addon">
								<span class = "glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">End Date:</div>
					<div class = "col-md-6">
						<div class = "input-group date" id = "searchTabEndDateTimePicker">
							<input type = "text">
							<span class = "input-group-addon">
								<span class = "glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Sensor:</div>
         				<div class = "col-md-6">
						<div class = "btn-group" data-toggle = "buttons">
							<label class = "btn btn-primary" id = "searchTabSensorAllLabel" onchange = "javascript:librarySensorCheck()">
								<input id = "searchTabSensorAllCheckbox" type = "checkbox">ALL
							</label>
							<g:each in = "${params.availableResources.sensors}">
								<label class = "btn btn-primary" id = "searchTabSensor${it.name.capitalize()}Label">
									<input id = "searchTabSensor${it.name.capitalize()}Checkbox" type = "checkbox">${it.name.toUpperCase()}
								</label>
							</g:each>
						</div>
					</div>
        			</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Min. NIIRS:</div>
                			<div class = "col-md-6">
						<input id = "searchTabMinNiirsInput" max = "9" min = "0" step = "0.1" type = "number">
					</div>
        			</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Max. Cloud Cover (%):</div>
					<div class = "col-md-6">
						<input id = "searchTabMaxCloudCoverInput" max = "100" min = "0" step = "1" type = "number">
					</div>
				</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Max. Results:</div>
					<div class = "col-md-6">
						<select id = "searchTabMaxResultsSelect">
							<g:each in = "${[5, 10, 25, 50, 75, 100, 250, 500]}">
								<option value = ${it}>${it}</option>
							</g:each>
						</select>
					</div>
				</div>
				<div class = "row">
					<div align = "right" class = "col-md-3">Library:</div>
					<div class = "col-md-6">
						<div class = "btn-group" data-toggle = "buttons">
							<g:each in = "${params.availableResources.libraries}">
								<label class = "btn btn-primary" id = "searchTabLibrary${it.capitalize()}Label" onchange = "javascript:librarySensorCheck()">
									<input id = "searchTabLibrary${it.capitalize()}Checkbox" type = "checkbox">${it.toUpperCase()}
								</label>
							</g:each>
						</div>
					</div>
				</div>
			</div>
                        <div class = "modal-footer">
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "beginSearch()">Search</button>
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "bookmarkSearchParams()">Bookmark It!</button>
                                <button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
                        </div>
		</div>
	</div>
</div> 
