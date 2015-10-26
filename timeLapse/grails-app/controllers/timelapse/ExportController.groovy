package timelapse


import groovy.json.JsonSlurper
import javax.imageio.ImageIO


class ExportController {
	def animationExportService
	def exportService
	def restApiService

	
	def exportAvi() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def json = new JsonSlurper().parseText(requestMap.frames)
		def aviFile = animationExportService.exportAvi(json)


		if (aviFile.exists()) {
			def aviBytes = aviFile.getBytes()

			response.contentType = "video/avi"
			response.outputStream << aviBytes
			response.outputStream.flush()
		}
		else { render "There was a problem making your AVI" }
	}

	def exportCanvas() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def image = exportService.canvasToImage([imageData: requestMap.imageData])


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
		response.outputStream.flush()
	}

	def exportGif() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def json = new JsonSlurper().parseText(requestMap.frames)
		def gifFile = animationExportService.exportGif(json)
		
		
		if (gifFile.exists()) {
			def gifBytes = gifFile.getBytes()

			response.contentType = "image/gif"
			response.outputStream << gifBytes
			response.outputStream.flush()
		}
		else { render "There was a problem making your GIF" }
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

	def exportMov() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def json = new JsonSlurper().parseText(requestMap.frames)
		def movFile = animationExportService.exportMov(json)


		if (movFile.exists()) {
			def movBytes = movFile.getBytes()

			response.contentType = "video/quicktime"
			response.outputStream << movBytes
			response.outputStream.flush()
		}
		else { render "There was a problem making your MOV" }
	}

	def exportPdf() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def json = new JsonSlurper().parseText(requestMap.frames)
		def pdfFile = animationExportService.exportPdf(json)

		
		if (pdfFile.exists()) {
			def pdfBytes = pdfFile.getBytes()

			response.contentType = "application/pdf"
			response.outputStream << pdfBytes
			response.outputStream.flush()
		}
		else { render "There was a problem making your PDF" }
	}

	def exportWmv() {
		def requestMap = restApiService.normalizeRequestParams(params, request)
		def json = new JsonSlurper().parseText(requestMap.frames)
		def wmvFile = animationExportService.exportWmv(json)


		if (wmvFile.exists()) {
			def wmvBytes = wmvFile.getBytes()

			response.contentType = "video/x-ms-wmv"
			response.outputStream << wmvBytes
			response.outputStream.flush()
		}
		else { render "There was a problem making your WMV" }
}

}
