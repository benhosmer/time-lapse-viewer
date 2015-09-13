package timelapse


import groovy.json.JsonSlurper
import javax.imageio.ImageIO


class ExportController {
	def exportService


	def exportCanvas() {
		def imageData = exportService.getImageData(params, request)
		def image = exportService.canvasToImage([imageData: imageData])


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}

	def exportMetadata() {
		def metadataLayers = new JsonSlurper().parseText(params.metadata)
		def csv = exportService.convertMetadataToCsv(metadataLayers)


		response.contentType = "text/csv"
		response.setHeader("Content-disposition", "attachment;filename=${new Date().format("yyyyMMddHHmmssSSS")}metadata.csv")
		response.outputStream << csv.bytes
	}
}
