function addLayersToTheMap() {
	$.each(
		tlv.layers,
		function(i, x) {
			var params = {
				BANDS: x.bands || "2,1,0",
				BRIGHTNESS: x.brightness || 0,
				CONTRAST: x.contrast || 0,
				DRA: x.dra || "auto",
				DRA_AREA: x.draArea || "viewport",
				DRA_SIGMA: x.draSigma || 1,
				FORMAT: "image/jpeg",
				IDENTIFIER: new Date().getTime(),
				IMAGE_ID: x.imageId,
				INTEPOLATION: x.interpolation || "bilinear",
				LAYERS: x.indexId,
				LIBRARY: x.library,
				OFFSET_LAT: x.offsetLat || 0,
				OFFSET_LON: x.offsetLon || 0,
				ROTATE: x.rotate || 0,
				SHARPNESS: x.sharpness || 0,
				TRANSPARENT: "false",
				VERSION: "1.1.1"
			};

			var image = new ol.layer.Image({
				source: new ol.source.ImageWMS({
					params: params,
					ratio: 1,
					url: "/timeLapse/home/wms"
				}),
				visible: i == 0 ? true : false
			});

			x.mapLayer = image;
			
			tlv.map.addLayer(x.mapLayer);
		}
	);
}

function buildSummaryTable() {
	var table = $("#timeLapseSummaryTable")[0];
	
	for (var i = table.rows.length - 1; i >= 0; i--) { table.rows[i].delete(); }

	var row = table.insertRow(0);
	var cell = row.insertCell(row.cells.length);
	$.each(
		tlv.layers[0].metadata,
		function(key, value) {
			var cell = row.insertCell(row.cells.length);
			$(cell).append(key.capitalize().replace(/([A-Z])/g, " $1"));
		}
	);

	$.each(
		tlv.layers,
		function(i, x) {
			row = table.insertRow(table.rows.length);
			cell = row.insertCell(row.cells.length);
			$(cell).append(i + 1);
			$.each(
				x.metadata,
				function(key, value) {
					cell = row.insertCell(row.cells.length);
					$(cell).append(value);
				}
			);
		}
	);
}

function changeFrame(param) {
	tlv.layers[tlv.currentLayer].mapLayer.setVisible(false);

	if (param === "fastForward") { tlv.currentLayer = getNextFrameIndex(); }
	else if (param === "rewind") { tlv.currentLayer = getPreviousFrameIndex(); }
	else if (typeof param === "number") { tlv.currentLayer = param; }

	tlv.layers[tlv.currentLayer].mapLayer.setVisible(true);

	tlv.map.renderSync();

	updateScreenText();
}

function createMousePOsitionControl() {
	var mousePositionControl = new ol.control.MousePosition({ projection: "EPSG:4326" });

	mousePositionControl.coordinateDisplayFormat = 0;
	mousePositionControl.updateHTML_ = function(pixel) {
		var html = this.undefinedHTML_;
		if (pixel && this.mapProjection_) {
			if (!this.transform_) {
				var projection = this.getProjection();
				if (projection) {
					this.transform_ = ol.proj.getTransformFromProjections(this.mapProjection_, projection);
				} 
				else { this.transform_ = ol.proj.identityTransform; }
			}
			var map = this.getMap();
			var coordinate = map.getCoordinateFromPixel(pixel);
			if (coordinate) {
				this.transform_(coordinate, coordinate);
				var lat = coordinate[1];
				var lon = coordinate[0];
				var coordConvert = new CoordinateConversion()
				switch(this.coordinateDisplayFormat) {
					case 0: html = coordinate[1].toFixed(6) + ", " + coordinate[0].toFixed(6); break;
					case 1: html = coordConvert.ddToDms(lat, "lat") + " " + coordConvert.ddToDms(lon, "lon"); break;
					case 2: html = coordConvert.ddToMgrs(lat, lon); break; 
				}
			}
		}
		if (!this.renderedHTML_ || html != this.renderedHTML_) {
			this.element.innerHTML = html;
			this.renderedHTML_ = html;
		}		
	}

	$(mousePositionControl.element).click(function() {
		mousePositionControl.coordinateDisplayFormat++;
		if (mousePositionControl.coordinateDisplayFormat >= 3) { mousePositionControl.coordinateDisplayFormat = 0; }
	});


	return mousePositionControl;
}

function deleteFrame() {
	changeFrame("rewind");

	var nextFrameIndex = getNextFrameIndex();
	var spliceIndex = nextFrameIndex == 0 ? 0 : nextFrameIndex;
	tlv.layers.splice(spliceIndex, 1);
	if (tlv.currentLayer > tlv.layers.length - 1) { tlv.currentLayer = tlv.layers.length - 1; } 

	changeFrame("fastForward");
}

function disableMapRotation() {
	tlv.map.removeInteraction(tlv.mapInteractions.dragRotate);
	tlv.map.addInteraction(tlv.mapInteractions.dragPan);
}

function enableDisableMapRotation(button) {
	var text = button.innerHTML;
	if (text.contains("Enable")) {
		enableMapRotation();
		text = text.replace("Enable", "Disable");
	}
	else {
		disableMapRotation();
		text = text.replace("Disable", "Enable");
	}
	button.innerHTML = text;
}

function enableMapRotation() {
	tlv.map.removeInteraction(tlv.mapInteractions.dragPan);
	tlv.map.addInteraction(tlv.mapInteractions.dragRotate);
}

function geoJump() {
	var location = $("#geoJumpLocationInput").val();
	var point = convertGeospatialCoordinateFormat(location);
	tlv.map.getView().setCenter(point); 
}

function getNextFrameIndex() { return tlv.currentLayer >= tlv.layers.length - 1 ? 0 : tlv.currentLayer + 1; }

function getPreviousFrameIndex() { return tlv.currentLayer <= 0 ? tlv.layers.length - 1 : tlv.currentLayer - 1; }

var pageLoadTimeLapse = pageLoad;
pageLoad = function() {
	pageLoadTimeLapse();
	setupMap();
	
	if (tlv.layers) {
		$("#searchDialog").modal("hide");
		setupTimeLapse();
	}
}

function playStopTimeLapse(button) { 
	var className = button.className;
	
	$(button).removeClass(className);
	if (className.contains("play")) { 
		playTimeLapse(); 
		className = className.replace("play", "stop");
	}
	else { 
		stopTimeLapse(); 
		className = className.replace("stop", "play");
	}
	$(button).addClass(className);
}

function playTimeLapse() {
	changeFrame("fastForward");
	tlv.timeLapseAdvance = setTimeout("playTimeLapse()", 1000);
}

function setupMap() {
	// if a map already exists, reset it and start from scratch
	if (tlv.map) { tlv.map.setTarget(null); }

	tlv.map = new ol.Map({
		controls: ol.control.defaults().extend([
			createMousePOsitionControl(),
			new ol.control.FullScreen()
		]),
		interactions: ol.interaction.defaults({
			altShiftDragRotate: false,
			dragPan: false
		}).extend([
			new ol.interaction.DragAndDrop({
				formatConstructors: [
					ol.format.GPX,
					ol.format.GeoJSON,
					ol.format.IGC,
					ol.format.KML,
					ol.format.TopoJSON
				]
			})
		]),
		logo: false,
		target: "map",
		view: new ol.View({ projection: "EPSG:4326" })
	});
}

function setupTimeLapse() {
	setupMap();

	// setup interactions so rotation can be controlled independently
	tlv.mapInteractions = {
		altDragRotate: new ol.interaction.DragRotate({ condition: ol.events.condition.altKeyOnly }),
		dragPan: new ol.interaction.DragPan({ condition: ol.events.condition.noModifierKeys }),
		dragRotate: new ol.interaction.DragRotate({ condition: ol.events.condition.always })
	};
	tlv.map.addInteraction(tlv.mapInteractions.altDragRotate);
	tlv.map.addInteraction(tlv.mapInteractions.dragPan);

	if (tlv.reverseChronological == "true") { tlv.layers.reverse(); }
	addLayersToTheMap();

	tlv.map.getView().fit(tlv.bbox, tlv.map.getSize());

	// register map listeners
	tlv.map.on("moveend", theMapHasMoved);

	tlv.currentLayer = 0;

	enableMenuButtons();

	updateScreenText();
}

function stopTimeLapse() { clearTimeout(tlv.timeLapseAdvance); }

function theMapHasMoved() { /* place holder to be overriden by other functions */ }

function updateAcquisitionDate() { $("#acquisitionDateDiv").html(tlv.layers[tlv.currentLayer].metadata.acquisitionDate); }

function updateImageId() { $("#imageIdDiv").html(tlv.layers[tlv.currentLayer].imageId); }

function updateScreenText() {
	updateImageId();
	updateAcquisitionDate();
	updateTlvLayerCount();
}

function updateTlvLayerCount() {
	var currentCount = tlv.currentLayer + 1;
	$("#tlvLayerCountSpan").html(currentCount + "/" + tlv.layers.length);
}
