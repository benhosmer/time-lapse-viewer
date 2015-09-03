package timelapse


import groovy.json.JsonSlurper
import groovy.json.JsonOutput


class HomeController {
	def restApiService
	def searchLibraryService
	def wmsConversionService


	def index() { 
		def model = restApiService.serviceMethod(params)

	
		render(view: "index.gsp", model: [tlvParams : JsonOutput.toJson(model)])
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
