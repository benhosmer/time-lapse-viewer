package template


import grails.transaction.Transactional
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import javax.imageio.ImageIO


@Transactional
class ImageScalingService {

	def serviceMethod(params) {
		def originalImage
		if (params.file) { originalImage = ImageIO.read(params.file as File) }
		else if (params.image) { originalImage = params.image }	
		def scaledHeight = params.height as Integer
		def scaledWidth = params.width ? params.width as Integer : null

		def originalHeight = originalImage.getHeight()
		def originalWidth = originalImage.getWidth()

		if (!params.width) { scaledWidth = scaledHeight / originalHeight * originalWidth as Integer }

		def scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH)
		def bufferedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_4BYTE_ABGR)
		def graph = bufferedImage.createGraphics()
		def renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		graph.setRenderingHints(renderingHints)

		graph.drawImage(scaledImage, 0, 0, null)
		graph.dispose()


		return bufferedImage
	}
}
