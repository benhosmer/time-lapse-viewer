function addLayersToTheMap() {
	$.each(
		tlv.layers,
		function(i, x) {
			var params = {
				BANDS: x.bands ? x.bands : "2,1,0",
				BRIGHTNESS: x.brightness ? x.brightness : 0,
				CONTRAST: x.contrast ? x.contrast : 1,
				DRA: x.dra ? x.dra : "auto",
				DRA_AREA: x.draArea ? x.draArea : "viewport",
				DRA_SIGMA: x.draSigma ? x.draSigma : 1,
				FORMAT: "image/jpeg",
				IDENTIFIER: new Date().getTime(),
				IMAGE_ID: x.imageId,
				INTEPOLATION: x.interpolation ? x.interpolation : "bilinear",
				LAYERS: x.indexId,
				LIBRARY: x.library,
				OFFSET_LAT: x.offsetLat ? x.offsetLat : 0,
				OFFSET_LON: x.offsetLon ? x.offsetLon : 0,
				ROTATE: x.rotate ? x.rotate : 0,
				SHARPNESS: x.sharpness ? x.sharpness : 0,
				TRANSPARENT: "false",
				VERSION: "1.1.1"
			};

			var image = new ol.layer.Image({
				source: new ol.source.ImageWMS({
					params: params,
					ratio: 1,
					url: "/timeLapse/home/wms"
				})
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
}

function getNextFrameIndex() { return tlv.currentLayer >= tlv.layers.length - 1 ? 0 : tlv.currentLayer + 1; }

function getPreviousFrameIndex() { return tlv.currentLayer <= 0 ? tlv.layers.length - 1 : tlv.currentLayer - 1; }

function setupTimeLapse() {
	$("#tlvTabs a[href='#timeLapseTab']").tab("show");

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
		view: new ol.View({
			projection: "EPSG:4326"
		})
	});

	addLayersToTheMap();

	tlv.map.getView().fit(tlv.bbox, tlv.map.getSize());

	tlv.currentLayer = 0;
	for (var i = 0; i < tlv.layers.length; i++) { changeFrame("fastForward"); }
}
