package template


import grails.transaction.Transactional
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.image.BufferedImage
import java.awt.RenderingHints


@Transactional
class TextGeneratorService {

	def serviceMethod(params) {
		def alignment = params.alignment ?: "left"
		def color = params.color ? Color.decode("#${params.color}") : Color.BLUE
		def height = params.height as Integer
		def text = params.text as String
		def width = params.width as Integer

		// create blank image
		def image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
		def graphic = image.createGraphics()

		// set rendering hints and color		
		def renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		graphic.setRenderingHints(renderingHints)
		graphic.setColor(color)

		// cycle through font sizes until the text fills the specified ractangle
		def font = graphic.getFont().deriveFont(Font.BOLD, 10)
		def fontMetrics
		def fontSize = 0
		def stringHeight = 0
		def stringWidth = 0
		while (stringHeight < height && stringWidth < width) {
			fontSize++
			font = font.deriveFont(Font.BOLD, fontSize)
			graphic.setFont(font)
			fontMetrics = graphic.getFontMetrics()
			stringHeight = fontMetrics.getHeight()
			stringWidth = fontMetrics.stringWidth("${text}")
		}

		fontSize--
		font = font.deriveFont(Font.BOLD, fontSize)
		graphic.setFont(font)
		fontMetrics = graphic.getFontMetrics()

		// determine the offset for the appropriate alignment
		def textOffsetX = 0
		def textOffsetY = height - fontMetrics.getDescent() as Integer
		if (alignment == "center") { 
			stringWidth = fontMetrics.stringWidth("${text}")
			textOffsetX = (width - stringWidth) / 2 as Integer
		}
		else if (alignment == "right") {
			stringWidth = fontMetrics.stringWidth("${text}")
			textOffsetX = width - stringWidth as Integer
		}

		// draw the text
		graphic.drawString("${text}", textOffsetX, textOffsetY)

		// clean up
		graphic.dispose()


		return image
	}
}
