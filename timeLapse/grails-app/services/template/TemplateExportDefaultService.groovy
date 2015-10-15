package template


import grails.transaction.Transactional
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import javax.imageio.ImageIO


@Transactional
class TemplateExportDefaultService {
	def assetResourceLocator
	def disclaimerService
	def gradientGeneratorService
	def imageScalingService
	def northArrowService
	def textGeneratorService


	def serviceMethod(params) {
		def image = params.image
		def imageHeight = image.getHeight()
		def imageWidth = image.getWidth()
 
		// generate blank template
		def templateHeight = 1.16 * imageHeight as Integer
		def templateWidth = imageWidth as Integer
		def templateImage = new BufferedImage(templateWidth, templateHeight, BufferedImage.TYPE_4BYTE_ABGR)

		def graphic = templateImage.createGraphics()
		def renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		graphic.setRenderingHints(renderingHints)
		
		// header
		def headerHeight = 0.1 * imageHeight as Integer
		def headerWidth = imageWidth as Integer
		def headerImage = gradientGeneratorService.serviceMethod([ 
			height: headerHeight, 
			width: headerWidth 
		]) 
		def headerOffsetX = 0
		def headerOffsetY = 0
		graphic.drawImage(headerImage, headerOffsetX, headerOffsetY, null)
		
		// logo
		def logoHeight = 0.8 * headerHeight as Integer
		def logoWidth = logoHeight as Integer
		def logoImage = imageScalingService.serviceMethod([
			height: logoHeight,
			image: ImageIO.read(assetResourceLocator.findAssetForURI("logos/${params.logo}.png").inputStream)
		])
		def logoOffsetX = (headerHeight - logoHeight) / 2 as Integer
		def logoOffsetY = logoOffsetX as Integer
		graphic.drawImage(logoImage, logoOffsetX, logoOffsetY, null)

		// north arrow
		def northArrowSize = logoHeight as Integer
		def northArrowImage = imageScalingService.serviceMethod([
			height: northArrowSize,
			image: northArrowService.serviceMethod([ angle: params.northAngle ])
		])
		def northArrowOffsetX = imageWidth - logoOffsetX - northArrowSize as Integer
		def northArrowOffsetY = logoOffsetY as Integer
		graphic.drawImage(northArrowImage, northArrowOffsetX, northArrowOffsetY, null)

		// line 1
		def line1Height = 0.25 * logoHeight as Integer
		def line1Width = imageWidth - logoOffsetX - logoWidth - logoOffsetX - logoOffsetX - northArrowSize - logoOffsetX as Integer
		def line1Image = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: line1Height,
			text: params.line1Text,
			width: line1Width
		])
		def line1OffsetX = logoOffsetX + logoWidth + logoOffsetX as Integer
		def line1OffsetY = logoOffsetY as Integer
		graphic.drawImage(line1Image, line1OffsetX, line1OffsetY, null)

		// line 2
		def line2Height = 0.43 * logoHeight as Integer
		def line2Width = line1Width as Integer
		def line2Image = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: line2Height,
			text: params.line2Text,
			width: line2Width
		])
		def line2OffsetX = line1OffsetX as Integer
		def line2OffsetY = line1OffsetY + line1Height as Integer
		graphic.drawImage(line2Image, line2OffsetX, line2OffsetY, null)

		// line 3
		def line3Height = 0.25 * logoHeight as Integer	
		def line3Width = line2Width as Integer
		def line3Image = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: line3Height,
			text: params.line3Text,
			width: line3Width
		])
		def line3OffsetX = line2OffsetX as Integer
		def line3OffsetY = line2OffsetY + line2Height as Integer
		graphic.drawImage(line3Image, line3OffsetX, line3OffsetY, null)

		// image
		def imageOffsetX = 0
		def imageOffsetY = headerHeight as Integer
		graphic.drawImage(image, imageOffsetX, imageOffsetY, null)

		// footer
		def footerHeight = 0.03 * imageHeight as Integer
		def footerWidth = imageWidth as Integer
		def footerImage = gradientGeneratorService.serviceMethod([ 
			height: headerHeight, 
			width: headerWidth 
		])
		def footerOffsetX = 0
		def footerOffsetY = headerHeight + imageHeight as Integer
		graphic.drawImage(footerImage, footerOffsetX, footerOffsetY, null)

		// line 4
		def line4Height = footerHeight as Integer
		def line4Width = footerWidth / 3 as Integer
		def line4Image = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: line4Height,
			text: params.line4Text,
			width: line4Width
		])
		def line4OffsetX = 0
		def line4OffsetY = imageOffsetY + imageHeight as Integer
		graphic.drawImage(line4Image, line4OffsetX, line4OffsetY, null)

		// line 5
		def line5Height = footerHeight as Integer
		def line5Width = line4Width as Integer
		def line5Image = textGeneratorService.serviceMethod([
			alignment: "center",
			color: "FFFFFF",
			height: line5Height,
			text: params.line5Text,
			width: line5Width
		])
		def line5OffsetX = line4OffsetX + line4Width as Integer
		def line5OffsetY = line4OffsetY as Integer
		graphic.drawImage(line5Image, line5OffsetX, line5OffsetY, null)

		// line 6
		def line6Height = footerHeight as Integer
		def line6Width = line5Width as Integer
		def line6Image = textGeneratorService.serviceMethod([
			alignment: "right",
			color: "FFFFFF",
			height: line6Height,
			text: params.line6Text,
			width: line6Width
		])
		def line6OffsetX = line5OffsetX + line5Width as Integer
		def line6OffsetY = line5OffsetY as Integer
		graphic.drawImage(line6Image, line6OffsetX, line6OffsetY, null)

		// disclaimer
		def disclaimerHeight = templateHeight - headerHeight - imageHeight - footerHeight as Integer
		def disclaimerWidth = imageWidth
		def disclaimerImage = disclaimerService.serviceMethod([
			height: disclaimerHeight,
			width: disclaimerWidth
		])
		def disclaimerOffsetX = 0
		def disclaimerOffsetY = templateHeight - disclaimerHeight
		graphic.drawImage(disclaimerImage, disclaimerOffsetX, disclaimerOffsetY, null)
		
		// clean up
		graphic.dispose()

		
		return templateImage
	}
}
