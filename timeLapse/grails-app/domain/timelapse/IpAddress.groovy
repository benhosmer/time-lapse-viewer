package timelapse

class IpAddress {
	String ip


	static constraints = { ip unique: true }

	static hasMany = [linkExport: LinkExport]

	static mapping = { 
		ip index: "ip_address_ip_idx"
		version false 
	}
}
