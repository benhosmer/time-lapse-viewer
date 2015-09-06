package timelapse


import javax.imageio.ImageIO


class ExportController {
	def exportService


	def exportCanvas() {
		def imageData = exportService.getImageData(params, request)
		def image = exportService.canvasToImage([imageData: imageData])


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}
}
