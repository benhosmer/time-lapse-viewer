package template


import grails.transaction.Transactional
import java.awt.Color
import java.awt.GradientPaint
import java.awt.image.BufferedImage


@Transactional
class GradientGeneratorService {

	def serviceMethod(params) {
		def bottomColor = params.bottomColor ? Color.decode("#${params.bottomColor}") : Color.BLACK
		def height = params.height ? params.height as Integer : 1
		def topColor = params.topColor ? Color.decode("#${params.topColor}") : Color.DARK_GRAY
		def width = params.width as Integer

		// create a blank buffered image
		def blankBufferedImage = new BufferedImage(1, height, BufferedImage.TYPE_4BYTE_ABGR)

		// set the gradient paint properties
		def gradientPaint = new GradientPaint(0, 0, topColor, 1, height, bottomColor)
		
		// draw the gradient
		def gradientGraphic = blankBufferedImage.createGraphics()
		gradientGraphic.setPaint(gradientPaint)
		gradientGraphic.fillRect(0, 0, 1, height)

		// clean up
		gradientGraphic.dispose()

		// create a blank gradient buffered image
		def bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
	
		// fill the buffered image with the gradient
		def graphic = bufferedImage.createGraphics()
		for (i in 0..width) { graphic.drawImage(blankBufferedImage, i, 0, null) }

		// clean up
		graphic.dispose()


		return bufferedImage
	}
}
