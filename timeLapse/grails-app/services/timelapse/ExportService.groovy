package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class ExportService {

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

	def getImageData(params, request) {
		def imageData
		if (params.imageData) { imageData = params.imageData }
		else {
			def requestMap = request.reader.text.split('&').inject([:]) { 
				map, token ->		
				token.split('=').with { map[it[0]] = it[1] }
				map
			}
		
			imageData = java.net.URLDecoder.decode(requestMap.imageData)
		}

		
		return imageData
	}

    def serviceMethod() {

    }
}
