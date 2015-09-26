package template


import grails.transaction.Transactional
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.RenderingHints


@Transactional
class NorthArrowService {

	def serviceMethod(params) {
		def angle = params.angle as Double

		def size = 1000
		def strokeWidth = 50
		def image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR)
		def graphic = image.createGraphics()

		// apply rotation
		graphic.rotate(angle, size / 2, size / 2)

		// apply rendering hints
		def antialiasing = RenderingHints.KEY_ANTIALIASING
		def antialiasOn = RenderingHints.VALUE_ANTIALIAS_ON
		def renderingHints = new RenderingHints(antialiasing, antialiasOn)
		graphic.setRenderingHints(renderingHints)

		// set a transparent background
		graphic.setColor(new Color(0,0,0,0))
		graphic.fillRect(0, 0, size, size)
		
		// draw the outer black circle
		graphic.setColor(Color.BLACK)
		graphic.drawOval(0, 0, size, size)
		graphic.fillOval(0, 0, size, size)

		def buffer = 50

		// draw the white outline circle
		def outerCircleDiameter = (2/3 * size) as Integer
		def outerCircleX = ((size - outerCircleDiameter) / 2) as Integer
		def outerCircleY = size - outerCircleDiameter - buffer
		graphic.setColor(Color.WHITE)
		graphic.fillOval(outerCircleX, outerCircleY, outerCircleDiameter, outerCircleDiameter)

		// draw the inner black circle
		def innerCircleDiameter = outerCircleDiameter - 2 * strokeWidth
		def innerCircleX = outerCircleX + strokeWidth
		def innerCircleY = outerCircleY + strokeWidth
		graphic.setColor(Color.BLACK)
		graphic.fillOval(innerCircleX, innerCircleY, innerCircleDiameter, innerCircleDiameter)

		// draw the pointer
		def rectangleHeight = outerCircleY - 2 * buffer
		def rectangleWidth = rectangleHeight
		int[] triangleX = [size / 2, size / 2 + rectangleWidth / 2, size / 2 - rectangleWidth / 2]
		int[] triangleY = [buffer, buffer + rectangleHeight, buffer + rectangleHeight]
		graphic.setColor(Color.WHITE)
		graphic.fillPolygon(triangleX, triangleY, 3)

		// draw the N
		def nHeight = (0.5 * innerCircleDiameter) as Integer
		def nWidth = (0.35 * innerCircleDiameter) as Integer
		def nBottomY = innerCircleY + innerCircleDiameter / 2 + nHeight / 2
		def nTopY = innerCircleY + innerCircleDiameter / 2 - nHeight / 2 
		def nRightX = innerCircleX + innerCircleDiameter / 2 + strokeWidth / 2
		def nLeftX = nRightX - nWidth
		int[] nX = [
			nRightX, 
			nRightX, 
			nRightX - strokeWidth, 
			nLeftX + strokeWidth, 
			nLeftX + strokeWidth, 
			nLeftX, 
			nLeftX, 
			nLeftX + strokeWidth, 
			nRightX - strokeWidth, 
			nRightX - strokeWidth
		]
		int [] nY = [
			buffer + rectangleHeight, 
			nBottomY, 
			nBottomY, 
			nTopY + 2 * strokeWidth, 
			nBottomY, 
			nBottomY, 
			nTopY, 
			nTopY, 
			nBottomY - 2 * strokeWidth, 
			buffer + rectangleHeight
		]
		graphic.fillPolygon(nX, nY, 10)

		// clean up
		graphic.dispose()


		return image
	}
}
