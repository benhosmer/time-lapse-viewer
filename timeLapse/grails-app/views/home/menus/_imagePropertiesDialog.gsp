<div class = "modal" id = "imagePropertiesDialog">
	<div class = "modal-dialog">
 		<div class = "modal-content">
			<div class = "modal-header"><h1>Image Properties</h1></div>
			<div class = "modal-body">
				<table class = "table table-nolines">
					<tr>
						<td align = "right">Bands:</td>
						<td>
							<div class = "row">
								<g:each in = "${["red", "green", "blue"]}">
									<div align = "center" class = "col-md-4">${it.capitalize()}</div>
								</g:each>
							</div>
							<div class = "row">
								<g:each in = "${["red", "green", "blue"]}">
									<div align = "center" class = "col-md-4">
										<select id = "${it}GunSelect">
											<g:each in = "${0..2}"><option value = ${it}>${it}</option></g:each>
										</select>
									</div>
								</g:each>
							</div>
						</td>
					</tr>
					<tr>
						<td align = "right">Brightness:</td>
						<td>
							<input data-slider-id = "brightnessSlider" id = "brightnessSliderInput" style = "width: 100%" type = "text"/>
						</td>
					</tr>
					<tr>
						<td align = "right">Contrast:</td>
						<td>
							<input data-slider-id = "contrastSlider" id = "contrastSliderInput" style = "width: 100%" type = "text"/>
						</td>
					<tr>
						<td align = "right">DRA:</td>
						<td>
							<select id = "draSelect">
								<g:each in = "${["auto", "sigma", "none"]}">
									<option value = ${it}>${it}</option>
								</g:each>
							</select>
						</td>
					</tr>
					<tr>
						<td align = "right">DRA Area:</td>
						<td>
							<select id = "draAreaSelect">
								<g:each in = "${["global", "viewport"]}">
									<option value = ${it}>${it}</option>
								</g:each>
							</select>
						</td>
					</tr>	
					<tr>
						<td align = "right">DRA Sigma:</td>
						<td>
							<select id = "draSigmaSelect">
								<g:each in = "${1..3}">
									<option value = ${it}>${it}</option>
								</g:each>
							</select>
						</td>
					</tr>
					<tr>				
						<td align = "right">Interpolation:</td>
						<td>
							<select id = "interpolationSelect">
								<g:each in = "${["bilinear", "nearest neighbor", "cubic", "sinc"]}">
									<option value = ${it}>${it}</option>
								</g:each>
							</select>
						</td>
					</tr>
					<tr>
						<td align = "right">Sharpness:</td>
						<td> 
							<input data-slider-id = "sharpnessSlider" id = "sharpnessSliderInput" style = "width: 100%" type = "text"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "changeImageProperties()">Apply</button>
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "changeImageProperties('all')">Apply All</button>
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
 			</div>
		</div>
	</div>
</div>
