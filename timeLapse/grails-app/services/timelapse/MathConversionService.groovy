package timelapse


import grails.transaction.Transactional


@Transactional
class MathConversionService {

	def convertRadiusToDeltaDegrees(params) {
		def radius = params.radius as Integer

		/* #m * 1Nm / 1852m * 1min / 1Nm * 1deg / 1min */
		def deltaDegrees = radius / 1852 / 60

		
		return deltaDegrees
	}
}
