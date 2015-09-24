package timelapse


import grails.transaction.Transactional


@Transactional
class RestApiService {
	def grailsApplication


	def normalizeRequestParams(params, request) {
		def requestMap
		if (params.size() == 3) { 
			requestMap = request.reader.text.split('&').inject([:]) {
				map, token ->
				token.split('=').with { map[it[0]] = java.net.URLDecoder.decode(it[1]) }
				map
			}
		}
		else { requestMap = params }


		return requestMap
	}

	def serviceMethod(params) {
		params.remove("action")
		params.remove("controller")
		params.remove("format")

		params.availableResources = [:]
		params.availableResources.complete = grailsApplication.config.libraries
		params.availableResources.sensors = grailsApplication.config.libraries.collect({ it.value.sensors }).flatten().unique({ it.name }).sort({ it.name })
		params.availableResources.libraries = grailsApplication.config.libraries.collect({ it.key })	

		
		return params	
	}
}
