<div class = "modal" id = "imagePropertiesDialog">
	<div class = "modal-dialog">
 		<div class = "modal-content">
			<div class = "modal-header"><h4>Image Properties</h4></div>
			<div class = "modal-body">
				<div class = "row">
					<div class = "col-md-2">Bands:</div>
					<div class = "col-md-10">
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
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">Brightness:</div>
					<div class = "col-md-10">
						<input data-slider-id = "brightnessSlider" id = "brightnessSliderInput" style = "width: 100%" type = "text"/>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">Contrast:</div>
					<div class = "col-md-10">
						<input data-slider-id = "contrastSlider" id = "contrastSliderInput" style = "width: 100%" type = "text"/>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">DRA:</div>
					<div class = "col-md-10">
						<select id = "draSelect">
							<g:each in = "${["auto", "sigma", "none"]}"><option value = ${it}>${it}</option></g:each>
						</select>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">DRA Area:</div>
					<div class = "col-md-10">
						<select id = "draAreaSelect">
							<g:each in = "${["global", "viewport"]}"><option value = ${it}>${it}</option></g:each>
						</select>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">DRA Sigma:</div>
					<div class = "col-md-10">
						<select id = "draSigmaSelect">
							<g:each in = "${1..3}"><option value = ${it}>${it}</option></g:each>
						</select>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">Interpolation:</div>
					<div class = "col-md-10">
						<select id = "interpolationSelect">
							<g:each in = "${["bilinear", "nearest neighbor", "cubic", "sinc"]}"><option value = ${it}>${it}</option></g:each>
						</select>
					</div>
				</div>
				<div class = "row">
					<div class = "col-md-2">Sharpness:</div>
					<div class = "col-md-10">
						<input data-slider-id = "sharpnessSlider" id = "sharpnessSliderInput" style = "width: 100%" type = "text"/>
					</div>
				</div>
			</div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "changeImageProperties()">Apply</button>
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = "changeImageProperties('all')">Apply All</button>
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
 			</div>
		</div>
	</div>
</div>
