package httpbuilder


import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET


@Transactional
class HttpDownloadService {

	def serviceMethod(params) {
		def http = new HTTPBuilder(params.url)

		http.request(GET) { req ->
			response.success = { resp, reader ->


				return reader
			}
		}
	}
}
