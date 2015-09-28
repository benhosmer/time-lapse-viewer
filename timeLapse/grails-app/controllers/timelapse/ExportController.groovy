package timelapse


import groovy.json.JsonSlurper
import javax.imageio.ImageIO


class ExportController {
	def exportService
	def restApiService


	def exportCanvas() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def image = exportService.canvasToImage([imageData: requestMap.imageData])


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}

	def exportLink() {
		def identifier = exportService.saveLink(params, request)

		
		render identifier
	}

	def exportMetadata() {
		def metadataLayers = new JsonSlurper().parseText(params.metadata)
		def csv = exportService.convertMetadataToCsv(metadataLayers)


		response.contentType = "text/csv"
		response.setHeader("Content-disposition", "attachment;filename=${new Date().format("yyyyMMddHHmmssSSS")}metadata.csv")
		response.outputStream << csv.bytes
	}
}
