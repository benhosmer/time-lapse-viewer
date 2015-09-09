var changeFrameImageProperties = changeFrame;
changeFrame = function(params) {
	changeFrameImageProperties(params);
	syncImageProperties();
}

function changeImageProperties(layerIndex) {
	if (typeof layerIndex == "undefined") { layerIndex = tlv.currentLayer; }
	else if ( layerIndex == "all") { 
		$.each(tlv.layers, function(i, x) { changeImageProperties(i); }); 
	

		return;
	}

	tlv.layers[layerIndex].mapLayer.getSource().updateParams({
		BANDS: [$("#redGunSelect").val(), $("#greenGunSelect").val(), $("#blueGunSelect").val()].join(),
		BRIGHTNESS: $("#brightnessSliderInput").slider("getValue"),
		CONTRAST:  $("#contrastSliderInput").slider("getValue"),
		DRA: $("#draSelect").val(),
		DRA_AREA: $("#draAreaSelect").val(),
		DRA_SIGMA: $("#draSigmaSelect").val(),
		INTERPOLATION: $("#interpolationSelect").val(),
		SHARPNESS: $("#sharpnessSliderInput").slider("getValue")
	});
}

function initializeImagePropertySliders() {
	$.each(
		["brightness", "contrast", "sharpness"],
		function(i, x) {
			$("#" + x + "SliderInput").slider({
				max: 1,
				min: -1,
				step: 0.01,
				tooltip: "hide",
				value: 0
			});
		}
	);
}

var pageLoadImageProperties = pageLoad;
pageLoad = function() {  
	pageLoadImageProperties();
	
	initializeImagePropertySliders();
}

function syncImageProperties() {
	var layer = tlv.layers[tlv.currentLayer];
	var params = layer.mapLayer.getSource().getParams();

	var bands = params.BANDS.split(",");
	var numberOfBands = layer.metadata.numberOfBands ? layer.metadata.numberOfBands : 3;
	$.each(
		["red", "green", "blue"], function(i, x) { 
			var select = $("#" + x + "GunSelect");

			select.empty();
			for (var j = 0; j < numberOfBands; j++) { select.append("<option value = " + j + ">" + j + "</option>"); }

			$("#" + x + "GunSelect option[value='" + bands[i] + "']").prop("selected", true); 
		}
	);
	
	$("#brightnessSliderInput").slider("setValue", params.BRIGHTNESS);
	$("#contrastSliderInput").slider("setValue", params.CONTRAST);
	$("#draSelect option[value='" + params.DRA + "']").prop("selected", true);
	$("#draAreaSelect option[value='" + params.DRA_AREA + "']").prop("selected", true);
	$("#draSigmaSelect option[value='" + params.DRA_SIGMA + "']").prop("selected", true);
	$("#interpolationSelect option[value='" + params.INTERPOLATION + "']").prop("selected", true);
	$("#sharpnessSliderInput").slider("setValue", params.SHARPNESS);	
}
