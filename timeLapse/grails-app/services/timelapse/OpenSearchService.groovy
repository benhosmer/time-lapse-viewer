package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class OpenSearchService {
	def assetResourceLocator
	def grailsApplication
	def imageScalingService


	def serviceMethod() {
		// get a base 64 representation of an icon image
		def icon = imageScalingService.serviceMethod([
			height: 16,
			image: ImageIO.read(assetResourceLocator.findAssetForURI("logos/tlv.png").inputStream)
		])
		def byteArrayOutputStream = new ByteArrayOutputStream()
		ImageIO.write(icon, "png", byteArrayOutputStream)
		def base64 = byteArrayOutputStream.toByteArray().encodeBase64()

		// write the xml
		def xml = new StringBuilder()
		xml.append('<?xml version = "1.0" encoding = "UTF-8"?>\n')
		xml.append('<OpenSearchDescription xmlns = "http://a9.com/-/spec/opensearch/1.1/">\n')
		xml.append('	<ShortName>TLV</ShortName>\n')
		xml.append('	<Description>Time-Lapse Viewer</Description>\n')
		xml.append('	<Image>data:image/x-icon;base64,' + base64 + '</Image>\n')
		xml.append('	<Url template = "http://tlv.com/' + 'timeLapse/home?location={searchTerms}" type = "text/html"/>\n')
		xml.append('</OpenSearchDescription>')

 
		return xml.toString()
	}
}
