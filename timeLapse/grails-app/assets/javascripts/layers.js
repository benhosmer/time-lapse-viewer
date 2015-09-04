function crossHairToggleButtonClick(desiredStatus) {
	var button = $("#layersCrossHairButton");
	if (desiredStatus) { button.html(desiredStatus); }
	else { button.html(button.html() == "ON" ? "OFF" : "ON"); }

	if (button.html() == "ON") {
		displayCrossHairLayer();
		button.removeClass("btn-danger");
		button.addClass("btn-success");
	}
	else {
		hideCrossHairLayer();
		button.removeClass("btn-success");
		button.addClass("btn-danger");
	}
}

function displayCrossHairLayer() {
	if (!tlv.crossHairLayer) {
		tlv.crossHairLayer = new ol.layer.Vector({
			source: new ol.source.Vector(),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "rgba(255, 255, 0, 1)",
					width: 2
				})
			})
		});
		tlv.map.addLayer(tlv.crossHairLayer);
	}
	else { tlv.crossHairLayer.setVisible(true); }
	refreshCrossHairLayer();
}

function hideCrossHairLayer() { tlv.crossHairLayer.setVisible(false); }

function refreshCrossHairLayer() {
	var mapCenter = tlv.map.getView().getCenter();

	var centerPixel = tlv.map.getPixelFromCoordinate(mapCenter);
	var deltaXPixel = [centerPixel[0] + 10, centerPixel[1]];
	var deltaYPixel = [centerPixel[0], centerPixel[1] + 10];
	
	var deltaXDegrees = tlv.map.getCoordinateFromPixel(deltaXPixel)[0] - mapCenter[0];
	var deltaYDegrees = tlv.map.getCoordinateFromPixel(deltaYPixel)[1] - mapCenter[1];

	var horizontalLinePoints = [
		[mapCenter[0] - deltaXDegrees, mapCenter[1]],
		[mapCenter[0] + deltaXDegrees, mapCenter[1]]
	];
	var horizontalLineGeometry = new ol.geom.LineString(horizontalLinePoints);
	var horizontalLineFeature = new ol.Feature(horizontalLineGeometry);

	var verticalLinePoints = [
		[mapCenter[0], mapCenter[1] - deltaYDegrees],
		[mapCenter[0], mapCenter[1] + deltaYDegrees]
	];
	var verticalLineGeometry = new ol.geom.LineString(verticalLinePoints);
	var verticalLineFeature = new ol.Feature(verticalLineGeometry);
	
	var source = tlv.crossHairLayer.getSource();
        $.each(source.getFeatures(), function(i, x) { source.removeFeature(x); });
	source.addFeatures([horizontalLineFeature, verticalLineFeature]);
}

var theMapHasMovedLayers = theMapHasMoved;
theMapHasMoved = function() {
	theMapHasMovedLayers();

	var crossHairButton = $("#layersCrossHairButton");
	if (crossHairButton.html() == "ON") { refreshCrossHairLayer(); }
}

var setupTimeLapseLayers = setupTimeLapse;
setupTimeLapse = function() {
	setupTimeLapseLayers();

	tlv.crossHairLayer = null;
	if (tlv.crossHairLayerEnabled == "true") { 
		/* there is an error if the layer is enabled immediately after the map has been created */
		setTimeout(function() { crossHairToggleButtonClick("ON"); }, 1); 
	}
}	
