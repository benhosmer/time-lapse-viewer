<li class = "dropdown" role = "presentation">
	<a class = "dropdown-toggle" data-toggle = "dropdown" href = "#">
		Layers <span class = "caret"></span>
	</a>
	<ul class = "dropdown-menu">
		<li>Cross-Hair: <button class = "btn btn-danger" id = "layersCrossHairButton" onclick = "crossHairToggleButtonClick()">OFF</button></li>
		<li class = "divider" role = "separator"></li>
		<li>Offset: 
			<button class = "btn btn-danger" id = "layersOffsetButton" onclick = "offsetToggleButtonClick()">OFF</button>
			<button class = "btn btn-primary" id = "layersOffsetAlignButton" onclick = "alignLayers()">ALIGN</button>
		</li>
		<li class = "divider" role = "separator"></li>
		<li>Search Origin: <button class = "btn btn-danger" id = "layersSearchOriginButton" onclick = "searchOriginToggleButtonClick()">OFF</button></li>
		<li class = "divider" role = "separator"></li>
	</ul>
</li>