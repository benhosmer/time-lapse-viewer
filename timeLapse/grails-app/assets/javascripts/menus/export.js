function addLayersToProxyMap() {
	tlv.proxyLayers = [];
	$.each(
		tlv.layers,
		function(i, x) {
			var layer = x.mapLayer;
			if (layer.getVisible()) {
				var source = layer.getSource();
				var params = source.getParams();
				params.proxyUrl = location.origin + source.getUrl();

				var proxyLayer = new ol.layer.Image({
					source: new ol.source.ImageWMS({
						params: params,
						ratio: 1,
						url: "/timeLapse/home/proxyImage"
					})
				});
				tlv.proxyLayers.push({
					layerLoaded: false,
					mapLayer: proxyLayer
				});

				proxyLayer.getSource().on("imageloadend", function(event, proxyLayer) {
					var layerIdentifier = this.getParams().IDENTIFIER;
					$.each(
						tlv.proxyLayers,
						function(i, x) {
							if (layerIdentifier == x.mapLayer.getSource().getParams().IDENTIFIER) { x.layerLoaded = true; }
						}
					);
				});

				tlv.proxyMap.addLayer(proxyLayer);
			}
		}
	);
}

function checkProxyMapLoadStatus(callbackFunction) {
	var theProxyMapHasFinishedLoading = true;
	$.each(
		tlv.proxyLayers,
		function(i, x) {
			if (x.layerLoaded == false) { theProxyMapHasFinishedLoading = false; }
		}
	);

	if (theProxyMapHasFinishedLoading) { getProxyMapCanvasData(callbackFunction); }
	else ( setTimeout(function() { checkProxyMapLoadStatus(callbackFunction); }, 1000) )
}

function createForm() {
	var form = document.createElement("form");
	form.method = "post";
	form.target = "_blank";
	$("body").append(form);

	var input = document.createElement("input");
	input.type = "hidden";
               
	form.appendChild(input);


	return [form, input];
}

function exportImage() {
	setupProxyMap();
	
	var exportParams = getExportImageParams();
	var exportCanvas = function(canvasData) {
		var elements = createForm();
		var form = elements[0];
		var input = elements[1];

		form.action = "/timeLapse/template/exportImage";
		input.name = "imageData";
		input.value = canvasData;

		$.each(
			exportParams,
			function(key, value) {
				var element = document.createElement("input");
				element.name = key;
				element.type = "hidden";
				element.value = value;

				form.appendChild(element);
			}
		);

		form.submit();
		form.remove();
	}

	checkProxyMapLoadStatus(exportCanvas);
}

function exportLink() {
	var tlvInfo = {
		bbox: tlv.map.getView().calculateExtent(tlv.map.getSize()),
		layers: []
	};
	$.each(
		tlv.layers,
		function(i, x) {
			var attributes = getImageProperties(i);
			attributes.metadata = x.metadata;
			tlvInfo.layers.push(attributes);
		}
	);

	$.ajax({
		data: "tlvInfo=" + JSON.stringify(tlvInfo),
		dataType: "text",
		failure: function() { alert("Uh oh, something went wrong when trying to export your link!"); },
		success: function(data) {
			$("#exportLinkHref").attr("href", location.origin + "/timeLapse/home?tlv=" + data);
        		$("#exportLinkDialog").modal("show");
		},
		url: "/timeLapse/export/exportLink"
	});
}

function exportMetadata() {
	var metadataLayers = [];
	$.each(tlv.layers, function(i, x) { metadataLayers.push(x.metadata); });

	var elements = createForm();
	var form = elements[0];
	var input = elements[1];

	form.action = "/timeLapse/export/exportMetadata";          
	input.name = "metadata";
	input.value = JSON.stringify(metadataLayers);

	form.submit();
	form.remove();
}

function exportScreenshot() {		
	setupProxyMap();

	var exportCanvas = function(canvasData) {
		var elements = createForm();
		var form = elements[0];
		var input = elements[1];

		form.action = "/timeLapse/export/exportCanvas";
		input.name = "imageData";
		input.value = canvasData;

		form.submit();
		form.remove();
	}

	checkProxyMapLoadStatus(exportCanvas);
}

function getExportImageParams() {
	var params = {
		date: $("#exportImageDateInput").val(),
		description: $("#exportImageDescriptionInput").val(),
		footerSecurityClassification: $("#exportImageFooterSecurityClassificationInput").val(),
		headerSecurityClassification: $("#exportImageHeaderSecurityClassificationInput").val(),
		location: $("#exportImageLocationInput").val(),
		logo: $("#exportImageLogoSelect").val(),
		northAngle: tlv.map.getView().getRotation(),
		title: $("#exportImageTitleInput").val()
	};
	

	return params;
}

function getImageProperties(layerIndex) {
	var params = tlv.layers[layerIndex].mapLayer.getSource().getParams();
	var properties = {
		bands: params.BANDS,
		brightness: params.BRIGHTNESS,
		contrast: params.CONTRAST,
		dra: params.DRA,
		draArea: params.DRA_AREA,
		draSigma: params.DRA_SIGMA,
		imageId: params.IMAGE_ID,
		interpolation: params.INTEPOLATION,
		indexId: params.LAYERS,
		library: params.LIBRARY,
		offsetLat: params.OFFSET_LAT,
		offsetLon: params.OFFSET_LON,
		rotate: params.ROTATE,
		sharpness: params.SHARPNESS
	};


	return properties;
}

function getProxyMapCanvasData(callbackFunction) {
	tlv.proxyMap.once(
		"postcompose", 
		function(event) {
			var canvasData = event.context.canvas.toDataURL().replace(/\S+,/, ""); 		
			$("#proxyMap").hide();
			callbackFunction(canvasData);
			hideLoadingDialog();
		}
	);
	tlv.proxyMap.renderSync();
}

function prepareExportImageDialog() {
	var layer = tlv.layers[tlv.currentLayer];
	var metadata = layer.metadata;

	$("#exportImageDateInput").val(layer.metadata.acquisitionDate || "N/A");
	$("#exportImageDescriptionInput").val(layer.metadata.countryCode || "N/A");
	$("#exportImageFooterSecurityClassificationInput").val(metadata.securityClassification || "N/A");
	$("#exportImageHeaderSecurityClassificationInput").val(metadata.securityClassification || "N/A");

	var coordinateConversion = new CoordinateConversion();
	var center = tlv.map.getView().getCenter();
	var dms = coordinateConversion.ddToDms(center[1], "lat") + " " + coordinateConversion.ddToDms(center[0], "lon");	
	var mgrs = coordinateConversion.ddToMgrs(center[1], center[0]);
	$("#exportImageLocationInput").val("DMS: " + dms + " MGRS: " + mgrs);

	$("#exportImageTitleInput").val(layer.imageId);
}

function setupProxyMap() {
	displayLoadingDialog("We're taking a snapshot of the map... this may take a sec.");

	var proxyMap = $("#proxyMap");
	proxyMap.show();

	if (tlv.proxyMap) { tlv.proxyMap.setTarget(null); }

	var size = tlv.map.getSize();
	proxyMap.width(size[0]);
	proxyMap.height(size[1]);

	tlv.proxyMap = new ol.Map({
		logo: false,
		target: "proxyMap",
		view: tlv.map.getView()
	});

	addLayersToProxyMap();		
}
