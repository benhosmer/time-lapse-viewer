package httpbuilder


import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.Method.GET


@Transactional
class HttpDownloadService {

	def serviceMethod(params) {
		def http = new HTTPBuilder(params.url)

		http.request(GET) { req ->
			response.success = { 
				response, reader ->

				def contentType = response.allHeaders.find({ it.name =~ /(?i)Content-Type/})
				if (contentType) { contentType = contentType.value }


				if (contentType.contains("image/jpeg") || contentType.contains("image/png")) { return reader.bytes }
				else { return reader }
			}
		}
	}
}
