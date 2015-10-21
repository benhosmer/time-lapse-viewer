package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class AnimationExportService {
	def exportService
	def grailsApplication
	def templateExportDefaultService


	def exportGif(params) {
		def tempFilesDirectory = grailsApplication.config.tempFilesDirectory

		def inputFiles = []
		params.each() {
			def image = exportService.canvasToImage([imageData: it.imageData])
			it.put("image", image)
			def template = templateExportDefaultService.serviceMethod(it)

			def filename = new Date().format("yyyyMMddHHmmssSSS")
			def inputFile = new File("${tempFilesDirectory}${filename}.png")
			ImageIO.write(template, "png", inputFile)
		
			inputFiles.push(inputFile)
		}

		def date = new Date().format("yyyyMMddHHmmssSSS")
		def gifFile = new File("${tempFilesDirectory}${date}.gif")
		def command = "convert -delay 100 ${inputFiles.collect({ it.absolutePath }).join(' ')} ${gifFile.absolutePath}"
		def process = command.execute()
		process.waitFor()


		if (gifFile.exists()) { return gifFile }
		else { return null }		
	}

	def exportPdf(params) {
		def tempFilesDirectory = grailsApplication.config.tempFilesDirectory

		def inputFiles = []
		params.each() {
			def image = exportService.canvasToImage([imageData: it.imageData])
			it.put("image", image)
			def template = templateExportDefaultService.serviceMethod(it)

			def filename = new Date().format("yyyyMMddHHmmssSSS")
			def inputFile = new File("${tempFilesDirectory}${filename}.png")
			ImageIO.write(template, "png", inputFile)

			inputFiles.push(inputFile)
		}

		def date = new Date().format("yyyyMMddHHmmssSSS")
		def pdfFile = new File("${tempFilesDirectory}${date}.pdf")
		def command = "convert ${inputFiles.collect({ it.absolutePath }).join(' ')} ${pdfFile.absolutePath}"
		def process = command.execute()
		process.waitFor()


		if (pdfFile.exists()) { return pdfFile }
		else { return null }
	}
}
