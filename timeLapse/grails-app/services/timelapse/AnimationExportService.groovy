package timelapse


import grails.transaction.Transactional
import javax.imageio.ImageIO


@Transactional
class AnimationExportService {
	def exportService
	def templateExportDefaultService


	def exportGif(params) {
		def inputFiles = []
		params.each() {
			def image = exportService.canvasToImage([imageData: it.imageData])
			it.put("image", image)
			def template = templateExportDefaultService.serviceMethod(it)

			def filename = new Date().format("yyyyMMddHHmmssSSS")
			def inputFile = new File("${filename}.png")
			ImageIO.write(template, "png", inputFile)
		
			inputFiles.push(inputFile)
		}

		def date = new Date().format("yyyyMMddHHmmssSSS")
		def gifFile = new File("${date}.gif")
		def command = "convert -delay 100 ${inputFiles.collect({ it.absolutePath }).join(' ')} ${gifFile.absolutePath}"
		def process = command.execute()
		process.waitFor()

		// delete input files
		inputFiles.each() {
			if (it.exists()) { it.delete() }
		}


		if (gifFile.exists()) { return gifFile }
		else { return null }		
	}

	def exportPdf(params) {
		def inputFiles = []
		params.each() {
			def image = exportService.canvasToImage([imageData: it.imageData])
			it.put("image", image)
			def template = templateExportDefaultService.serviceMethod(it)

			def filename = new Date().format("yyyyMMddHHmmssSSS")
			def inputFile = new File("${filename}.png")
			ImageIO.write(template, "png", inputFile)

			inputFiles.push(inputFile)
		}

		def date = new Date().format("yyyyMMddHHmmssSSS")
		def pdfFile = new File("${date}.pdf")
		def command = "convert ${inputFiles.collect({ it.absolutePath }).join(' ')} ${pdfFile.absolutePath}"
		def process = command.execute()
		process.waitFor()

		// delete input files
		inputFiles.each() {
			if (it.exists()) { it.delete() }
		}


		if (pdfFile.exists()) { return pdfFile }
		else { return null }
	}
}
