package timelapse

class BrowserInfo {
	String userAgent


	static constraints = { userAgent unique: true }

	static hasMany = [linkExport: LinkExport]

	static mapping = { 
		userAgent index: "browser_info_user_agent_idx", type: "text"
		version false 
	}
}
