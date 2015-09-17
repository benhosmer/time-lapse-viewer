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
				visible: false
			});

			x.mapLayer = image;
			
			tlv.map.addLayer(x.mapLayer);
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

	updateImageId();
	updateAcquisitionDate();
	updateTlvLayerCount();
}

function deleteFrame() {
	changeFrame("rewind");

	var nextFrameIndex = getNextFrameIndex();
	var spliceIndex = nextFrameIndex == 0 ? 0 : nextFrameIndex;
	tlv.layers.splice(spliceIndex, 1);
	tlv.currentLayer = tlv.currentLayer > tlv.layers.length - 1 && tlv.layers.length - 1; 

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

function getNextFrameIndex() { return tlv.currentLayer >= tlv.layers.length - 1 ? 0 : tlv.currentLayer + 1; }

function getPreviousFrameIndex() { return tlv.currentLayer <= 0 ? tlv.layers.length - 1 : tlv.currentLayer - 1; }

var pageLoadTimeLapse = pageLoad;
pageLoad = function() {
	pageLoadTimeLapse();
	setupMap();
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

	addLayersToTheMap();

	tlv.map.getView().fit(tlv.bbox, tlv.map.getSize());

	// register map listeners
	tlv.map.on("moveend", theMapHasMoved);

	// cycle through the layers to begin their chip downloads
	tlv.currentLayer = 0;
	for (var i = 0; i < tlv.layers.length; i++) { changeFrame("fastForward"); }
}

function stopTimeLapse() { clearTimeout(tlv.timeLapseAdvance); }

function theMapHasMoved() { /* place holder to be overriden by other functions */ }

function updateAcquisitionDate() { $("#acquisitionDateDiv").html(tlv.layers[tlv.currentLayer].metadata.acquisitionDate); }

function updateImageId() { $("#imageIdDiv").html(tlv.layers[tlv.currentLayer].imageId); }

function updateTlvLayerCount() {
	var currentCount = tlv.currentLayer + 1;
	$("#tlvLayerCountSpan").html(currentCount + "/" + tlv.layers.length);
}
