#! /usr/bin/env groovy


import java.awt.AlphaComposite
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import javax.imageio.ImageIO


def size = 1000
def strokeWidth = 0.05 * size as Integer

// create a blank image
def image = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR)
def graphic = image.createGraphics()

// apply rendering hints
def antialiasing = RenderingHints.KEY_ANTIALIASING
def antialiasOn = RenderingHints.VALUE_ANTIALIAS_ON
def renderingHints = new RenderingHints(antialiasing, antialiasOn)
graphic.setRenderingHints(renderingHints)

// draw the hour dots
graphic.setColor(Color.WHITE)
for (i in 1..12) {
	graphic.drawOval(size / 2 - strokeWidth / 2 as Integer, 0, strokeWidth, strokeWidth)
	graphic.fillOval(size / 2 - strokeWidth / 2 as Integer, 0, strokeWidth, strokeWidth)
	graphic.rotate(30 * Math.PI / 180, size / 2, size / 2)
}

// draw the upper part of the T
graphic.setColor(new Color(0, 255, 255))
graphic.fillArc(0, 0, size, size, 45, 90)
graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT))
graphic.fillArc(strokeWidth, strokeWidth, size - 2 * strokeWidth, size - 2 * strokeWidth, 45, 90)
graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER))

// draw the arrow
graphic.setColor(new Color(0, 255, 255))
graphic.rotate(-45 * Math.PI / 180, size / 2, size / 2)
int[] x = [size / 2, size / 2, size / 2 - 1.5 * strokeWidth]
int[] y = [-1.5 * strokeWidth, 2.5 * strokeWidth, 0.5 * strokeWidth]
graphic.fillPolygon(x, y, 3)
graphic.rotate(45 * Math.PI / 180, size / 2, size / 2)

// draw the vertical leg of the T, L and V
graphic.setColor(new Color(0, 255, 255))
graphic.fillRect(size / 2 - strokeWidth / 2 as Integer, strokeWidth / 2 as Integer, strokeWidth, size / 2 - strokeWidth / 2 as Integer)

// draw the horizontal leg of the L
graphic.setColor(new Color(0, 255, 255))
graphic.fillRect(size / 2 as Integer, size / 2 - strokeWidth / 2 as Integer, size / 2 - 4 * strokeWidth as Integer, strokeWidth)

// draw the second leg of the V
graphic.setColor(Color.WHITE)
graphic.rotate(60 * Math.PI / 180, size / 2, size / 2)
graphic.fillRect(size / 2 - strokeWidth / 4 as Integer, 1.5 * strokeWidth as Integer, strokeWidth / 2 as Integer, size / 2 - 1.5 * strokeWidth as Integer)

// draw the center dot
graphic.setColor(Color.WHITE)
graphic.drawOval(size / 2 - strokeWidth / 2 as Integer, size / 2 - strokeWidth / 2 as Integer, strokeWidth, strokeWidth)
graphic.fillOval(size / 2 - strokeWidth / 2 as Integer, size / 2 - strokeWidth / 2 as Integer, strokeWidth, strokeWidth)

// clean up
graphic.dispose()

ImageIO.write(image, "png", new File("tlv.png"))
