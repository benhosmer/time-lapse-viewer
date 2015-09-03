function convertRadiusToBbox(x, y, radius) {
	/* radius * 1 nautical mile / 1852 meters * 1 minute latitude / 1 nautical mile * 1 deg latitude / 60 minute latitude */
	var deltaLatitude = radius / 1852 / 60;
	var deltaLongitude = radius / 1852 / 60 * Math.cos(Math.abs(y) * Math.PI / 180);


	return { maxLat: y + deltaLatitude, maxLon: x + deltaLongitude, minLat: y - deltaLatitude, minLon: x - deltaLongitude };
}

function enableKeyboardShortcuts() {
	$(document).on(
		"keydown", 
		function(event) {
			var keyCode = event.keyCode;
			
			switch(keyCode) {
				// left arrow key
				case 37: changeFrame("rewind"); break; 
				// right arrow key
				case 39: changeFrame("fastForward"); break;
			}
		}
	);
}
