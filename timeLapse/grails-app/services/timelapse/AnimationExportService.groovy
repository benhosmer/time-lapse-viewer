package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class AnimationExportService {
	def exportService
	def grailsApplication
	def templateExportDefaultService


	def exportAvi(params) {
		def inputFile = exportGif(params)

		def tempFilesDirectory = grailsApplication.config.tempFilesDirectory
		def date = new Date().format("yyyyMMddHHmmssSSS")
                def aviFile = new File("${tempFilesDirectory}${date}.avi")
		def command = "ffmpeg -r 1 -i ${inputFile.absolutePath} -q:v 1 -r 1 ${aviFile.absolutePath}"
		def process = command.execute()
		process.waitFor()

	
		if (aviFile.exists()) { return aviFile }
		else { return null }
	}

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

	def exportMov(params) {
		def inputFile = exportGif(params)

		def tempFilesDirectory = grailsApplication.config.tempFilesDirectory
		def date = new Date().format("yyyyMMddHHmmssSSS")
		def movFile = new File("${tempFilesDirectory}${date}.mov")
		def command = "ffmpeg -r 1 -i ${inputFile.absolutePath} -q:v 1 -r 1 ${movFile.absolutePath}"
		def process = command.execute()
		process.waitFor()


		if (movFile.exists()) { return movFile }
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

	def exportWmv(params) {
		def inputFile = exportGif(params)

		def tempFilesDirectory = grailsApplication.config.tempFilesDirectory
		def date = new Date().format("yyyyMMddHHmmssSSS")
		def wmvFile = new File("${tempFilesDirectory}${date}.wmv")
		def command = "ffmpeg -r 1 -i ${inputFile.absolutePath} -q:v 1 -r 1 ${wmvFile.absolutePath}"
		def process = command.execute()
		process.waitFor()


		if (wmvFile.exists()) { return wmvFile }
		else { return null }
	}
}
