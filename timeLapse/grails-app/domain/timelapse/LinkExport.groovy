package timelapse

class LinkExport {
	BrowserInfo browserInfo
	Date date
	String identifier
	IpAddress ipAddress
	String tlvInfo


	static constraints = { identifier unique: true }

	static mapping = { 
		identifier index: "link_export_identifier_idx"
		tlvInfo type: "text"
		version false 
	}
}
