package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class ExportService {
	def logService


	def canvasToImage(params) {
		def imageData = params.imageData
		def bytes = imageData.bytes
		def decoded = bytes.decodeBase64()
		def byteArrayInputStream = new ByteArrayInputStream(decoded)
		def image = ImageIO.read(byteArrayInputStream)

		
		return image
	}

	def convertMetadataToCsv(params) {
		def layers = params as Collection

		// csv headers
		def stringBuffer = layers[0].collect({ it.key }).join(",") + "\n"
		// csv metadata
		layers.each() { stringBuffer += it.collect({ it.value }).join(",") + "\n" }
	

		return stringBuffer
	}

	def saveLink(params, request) {
		def date = new Date()
		def identifier = date.format("yyyyMMddHHmmssSSS")
		new LinkExport(
			browserInfo: logService.getBrowserInfo(request),
			date: date,
			identifier: identifier,
			ipAddress: logService.getIpAddress(request),
			tlvInfo: params.tlvInfo
		).save()

	
		return identifier
	}

	def serviceMethod() {}
}
