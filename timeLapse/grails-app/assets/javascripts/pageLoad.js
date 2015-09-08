function pageLoad() {
	setupSearchTab();

	$("#tlvTabs a[href='#timeLapseTab']").tab("show");
	setupMap();
	$("#tlvTabs a[href='#searchTab']").tab("show");

	initializeLoadingDialog();
	enableKeyboardShortcuts();
}

$(document).ready(function() { pageLoad(); });
