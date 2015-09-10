package unclassified


import grails.transaction.Transactional


@Transactional
class SearchLibraryService {
	def grailsApplication
	def httpDownloadService
	def mathConversionService


	def extractMetadata(imageXml) {
		def metadata = [
			azimuthAngle: imageXml.azimuth_angle?.text() as Double ?: null,
			filename: imageXml.filename.text() ?: null,
			indexId: imageXml.index_id?.text() ?: null,
			numberOfBands: imageXml.number_of_bands?.text() as Integer ?: null
		]


		return metadata
	}
	
	def searchOmar(params, library) {
		def libraryObject = grailsApplication.config.libraries["${library}"]

		def queryUrl = libraryObject.baseUrl
		queryUrl += "/omar/wfs"

		def filter = ""
		
		// acquisition date
		def startDate = "${params.startYear}-${params.startMonth}-${params.startDay}T${params.startHour}:${params.startHour}:${params.startSecond}.000"
		def endDate = "${params.endYear}-${params.endMonth}-${params.endDay}T${params.endHour}:${params.endHour}:${params.endSecond}.000"
		filter += "((acquisition_date > ${startDate} AND acquisition_date < ${endDate}) OR acquisition_date IS NULL)"
		
		filter += " AND "

		// cloud cover
		filter += "(cloud_cover < ${params.maxCloudCover} OR cloud_cover IS NULL)"

		filter += " AND "

		// dwithin
		def deltaDegrees = mathConversionService.convertRadiusToDeltaDegrees(params)
                filter += "DWITHIN(ground_geom,POINT(${params.location.join(" ")}),${deltaDegrees},meters)"

		filter += " AND "

		// niirs
		filter += "(niirs < ${params.minNiirs} OR niirs IS NULL)"

		// sensors
		if (params.sensors.find { it == "all" } != "all") {
			filter += " AND "

			// only search for sensors that are available in the library
			def availableSensors = libraryObject.sensors
			def sensorFilters = []
			params.sensors.each() {
				def sensor = it
				if (sensor == availableSensors.find({ it.name == sensor }).name) { sensorFilters.push("sensor_id ILIKE '%${sensor}%'") }
			}
			sensorFilters.push("sensor_id IS NULL")
			filter += "(${sensorFilters.join(" OR ")})"
		}		

println filter

		queryUrl += "?filter=" + URLEncoder.encode(filter)
 
		queryUrl += "&maxResults=${params.maxResults}"
		queryUrl += "&request=getFeature"
		queryUrl += "&service=WFS"
		queryUrl += "&typeName=omar:raster_entry"
		queryUrl += "&version=1.0.0"
println queryUrl
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
		
		params.libraries.each() { resultsMap.layers += searchOmar(params, it) }

	
		if (resultsMap.layers.size() > params.maxResults) { 
			def howManyToDrop = resultsMap.layers.size() - paams.maxResults
			resultsMap.layers = resultsMap.reverse().drop(howManyToDrop).reverse() 
		}


		return resultsMap
	}
}
