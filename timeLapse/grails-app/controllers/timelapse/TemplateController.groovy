package timelapse


import javax.imageio.ImageIO


class TemplateController {
	def exportService
	def gradientGeneratorService
	def northArrowService
	def templateExportDefaultService
	def restApiService


	def exportImage() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
                def image = exportService.canvasToImage([imageData: requestMap.imageData])

		requestMap.put("image", image)
		def template = templateExportDefaultService.serviceMethod(requestMap)

	
		response.contentType = "image/png"
		ImageIO.write(template, "png", response.outputStream)
	}

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
