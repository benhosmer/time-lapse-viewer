package unclassified


import grails.transaction.Transactional


@Transactional
class SearchLibraryService {
	def grailsApplication
	def httpDownloadService
	def mathConversionService


	def extractMetadata(imageXml) {
		def metadata = [:]
		if (imageXml.azimuth_angle) { metadata.azimuthAngle = imageXml.azimuth_angle.text() as Double }
		if (imageXml.filename) { metadata.filename = imageXml.filename.text() }
		if (imageXml.index_id) { metadata.indexId = imageXml.index_id.text() }
		if (imageXml.number_of_bands) { metadata.numberOfBands = imageXml.number_of_bands.text() as Integer }


		return metadata
	}
	
	def searchOmar(params, library) {
		def queryUrl = grailsApplication.config.libraries["${library}"].baseUrl
		queryUrl += "/omar/wfs"

		def filter
		def filterArray = []
		//filterArray.push("acquisition_date>${params.startYear}-${params.startMonth}-${params.startDay}T${params.startHour}:${params.startHour}:${params.startSecond}")
		//filterArray.push("acquisition_date<${params.endYear}-${params.endMonth}-${params.endDay}T${params.endHour}:${params.endHour}:${params.endSecond}")

		def deltaDegrees = mathConversionService.convertRadiusToDeltaDegrees(params)
		filterArray.push("DWITHIN(ground_geom,POINT(${params.location.join(" ")}),${deltaDegrees},meters)")

		filter = filterArray.join("AND")

		queryUrl += "?filter=" + URLEncoder.encode(filter)
 
		queryUrl += "&maxResults=${params.maxResults}"
		queryUrl += "&request=getFeature"
		queryUrl += "&service=WFS"
		queryUrl += "&typeName=omar:raster_entry"
		queryUrl += "&version=1.0.0"

		def xml = httpDownloadService.serviceMethod([url: queryUrl])
		
		def imageArray = []
		if (xml.featureMember) {
			xml.featureMember.each() {
				def metadata = extractMetadata(it.raster_entry)
				
				def image = [:]
				image.indexId = metadata.indexId
				image.imageId = metadata.imageId ?: metadata.filename
				image.library = "omar"
				image.metadata = metadata
				imageArray.push(image)
			}
		}

		
		return imageArray
	}

	def serviceMethod(params) {
		def resultsMap = [
			layers: [],
			location: params.location.collect({ it as Double })
		]
		
		params.libraries.each() {	
			def library = it	
			resultsMap.layers += searchOmar(params, library)
		}

		return resultsMap
	}
}
