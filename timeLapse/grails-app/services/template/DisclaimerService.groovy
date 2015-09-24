package template


import grails.transaction.Transactional
import java.awt.image.BufferedImage


@Transactional
class DisclaimerService {
	def gradientGeneratorService
	def textGeneratorService


	def serviceMethod(params) {
		def height = params.height as Integer
		def width = params.width as Integer

		// create blank image
		def image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
		def graphic = image.createGraphics()

		// add a yellow background
		def backgroundImage = gradientGeneratorService.serviceMethod([
			bottomColor: "FFFF00",
			height: height,
			topColor: "FFFF00",
			width: width
		])
		graphic.drawImage(backgroundImage, 0, 0, null)

		// add the disclaimer text
		def textImage = textGeneratorService.serviceMethod([
			alignment: "center",
			color: "000000",
			height: height,
			text: "Not an intelligence product // For information use only // Not certified for targeting",
			width: width,
		])
		graphic.drawImage(textImage, 0, 0, null)

		// clean up
		graphic.dispose()


		return image
	}
}
