package timelapse


import javax.imageio.ImageIO


class TemplateController {
	def gradientGeneratorService
	def northArrowService

	def generateGradient() {
		def image = gradientGeneratorService.serviceMethod(params)


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}

	def generateNorthArrow() {
		def image = northArrowService.serviceMethod(params)

		
		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}

	def index() { }
}
