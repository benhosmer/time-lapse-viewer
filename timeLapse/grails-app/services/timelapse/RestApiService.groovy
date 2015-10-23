package timelapse


import grails.transaction.Transactional
import groovy.json.JsonSlurper


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
		
		// check for a saved link
		if (params.tlv?.isNumber()) {
			def identifier = params.tlv
			def linkExport = LinkExport.findByIdentifier(identifier)
			if (linkExport) {
				def json = new JsonSlurper().parseText(linkExport.tlvInfo)
				json.each() { params[it.key] = it.value }
			}
		}

		params.availableResources = [:]
		params.availableResources.complete = grailsApplication.config.libraries
		params.availableResources.libraries = grailsApplication.config.libraries.collect({ it.key })	
		params.availableResources.sensors = grailsApplication.config.libraries.collect({ it.value.sensors }).flatten().unique({ it.name }).sort({ it.name })
		params.availableResources.tailoredGeoint = grailsApplication.config.libraries.collect({ it.value.tailoredGeoint }).flatten().unique({ it.name }).sort({ it.name })

		
		return params	
	}
}
