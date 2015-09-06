package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class ImageProxyService {
	def httpDownloadService


	def serviceMethod(params) {
		params.remove("action")
		params.remove("controller")
		params.remove("format")

		def url = "${params.proxyUrl}?"
		params.remove("proxyUrl")
		def paramsArray = []
		params.each() { paramsArray.push("${it.key}=${it.value}") }
		url += paramsArray.join("&")

		def inputStream = httpDownloadService.serviceMethod([url: url])
		def byteArrayInputStream = new ByteArrayInputStream(inputStream)
		def bufferedImage = ImageIO.read(byteArrayInputStream)


		return bufferedImage
	}
}
