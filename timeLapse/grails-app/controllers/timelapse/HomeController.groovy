package timelapse


import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.imageio.ImageIO


class HomeController {
	def imageProxyService
	def openSearchService
	def restApiService
	def searchLibraryService
	def wmsConversionService
	def grailsApplication

	def index() { 
		def model = restApiService.serviceMethod(params)

		render(view: "index.gsp", model: [logos: grailsApplication.config.logos, tlvParams : JsonOutput.toJson(model)])
	}

	def openSearch() { render openSearchService.serviceMethod() }

	def proxyImage() {
		def image = imageProxyService.serviceMethod(params)


		response.contentType = "image/png"
		ImageIO.write(image, "png", response.outputStream)
	}

	def searchLibrary() {
		def searchParams = new JsonSlurper().parseText(params.searchParams)
		def results = searchLibraryService.serviceMethod(searchParams)


		render JsonOutput.toJson(results)
	}

	def wms() {
		def url = wmsConversionService.serviceMethod(params)

		
		redirect(url: url)
	}
}
