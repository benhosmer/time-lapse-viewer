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
			image: northArrowService.serviceMethod()
		])
		def northArrowOffsetX = imageWidth - logoOffsetX - northArrowSize as Integer
		def northArrowOffsetY = logoOffsetY as Integer
		graphic.drawImage(northArrowImage, northArrowOffsetX, northArrowOffsetY, null)

		// header security classification
		def headerSecurityClassificationHeight = 0.25 * logoHeight as Integer
		def headerSecurityClassificationWidth = imageWidth - logoOffsetX - logoWidth - logoOffsetX - logoOffsetX - northArrowSize - logoOffsetX as Integer
		def headerSecurityClassificationImage = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: headerSecurityClassificationHeight,
			text: params.headerSecurityClassification,
			width: headerSecurityClassificationWidth
		])
		def headerSecurityClassificationOffsetX = logoOffsetX + logoWidth + logoOffsetX as Integer
		def headerSecurityClassificationOffsetY = logoOffsetY as Integer
		graphic.drawImage(headerSecurityClassificationImage, headerSecurityClassificationOffsetX, headerSecurityClassificationOffsetY, null)

		// title
		def titleHeight = 0.43 * logoHeight as Integer
		def titleWidth = headerSecurityClassificationWidth as Integer
		def titleImage = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: titleHeight,
			text: params.title,
			width: titleWidth
		])
		def titleOffsetX = headerSecurityClassificationOffsetX as Integer
		def titleOffsetY = headerSecurityClassificationOffsetY + headerSecurityClassificationHeight as Integer
		graphic.drawImage(titleImage, titleOffsetX, titleOffsetY, null)

		// description
		def descriptionHeight = 0.25 * logoHeight as Integer	
		def descriptionWidth = titleWidth as Integer
		def descriptionImage = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: descriptionHeight,
			text: params.description,
			width: descriptionWidth
		])
		def descriptionOffsetX = titleOffsetX as Integer
		def descriptionOffsetY = titleOffsetY + titleHeight as Integer
		graphic.drawImage(descriptionImage, descriptionOffsetX, descriptionOffsetY, null)

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

		// footer security classification
		def footerSecurityClassificationHeight = footerHeight as Integer
		def footerSecurityClassificationWidth = footerWidth / 3 as Integer
		def footerSecurityClassificationImage = textGeneratorService.serviceMethod([
			color: "FFFFFF",
			height: footerSecurityClassificationHeight,
			text: params.footerSecurityClassification,
			width: footerSecurityClassificationWidth
		])
		def footerSecurityClassificationOffsetX = 0
		def footerSecurityClassificationOffsetY = imageOffsetY + imageHeight as Integer
		graphic.drawImage(footerSecurityClassificationImage, footerSecurityClassificationOffsetX, footerSecurityClassificationOffsetY, null)

		// location
		def locationHeight = footerHeight as Integer
		def locationWidth = footerSecurityClassificationWidth as Integer
		def locationImage = textGeneratorService.serviceMethod([
			alignment: "center",
			color: "FFFFFF",
			height: locationHeight,
			text: params.location,
			width: locationWidth
		])
		def locationOffsetX = footerSecurityClassificationOffsetX + footerSecurityClassificationWidth as Integer
		def locationOffsetY = footerSecurityClassificationOffsetY as Integer
		graphic.drawImage(locationImage, locationOffsetX, locationOffsetY, null)

		// date
		def dateHeight = footerHeight as Integer
		def dateWidth = locationWidth as Integer
		def dateImage = textGeneratorService.serviceMethod([
			alignment: "right",
			color: "FFFFFF",
			height: dateHeight,
			text: params.date,
			width: dateWidth
		])
		def dateOffsetX = locationOffsetX + locationWidth as Integer
		def dateOffsetY = locationOffsetY as Integer
		graphic.drawImage(dateImage, dateOffsetX, dateOffsetY, null)

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
