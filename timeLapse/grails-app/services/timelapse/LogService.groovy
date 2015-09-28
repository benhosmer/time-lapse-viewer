package timelapse


import grails.transaction.Transactional


@Transactional
class LogService {

	def getBrowserInfo(request) { 
		def userAgent = request.getHeader("user-agent")


		return BrowserInfo.findOrSaveByUserAgent(userAgent) 
	}
		
	def getIpAddress(request) { 
		def ip = request.getHeader("client-ip")


		return IpAddress.findOrSaveByIp(ip ?: 0)
	}
}
