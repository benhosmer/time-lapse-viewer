<table class = "table">
	<g:each in = "${(1..6)}">
		<tr>
			<td align = "right">Line ${it}:</td>
			<td><input id = "exportFrameLine${it}Input" type = "text"></td>
		</tr>
	</g:each>
	<tr>
		<td align = "right">Logo:</td>
		<td>
			<select id = "exportFrameLogoSelect">
				<g:each in = "${["cia", "dia", "jcc", "nga", "nro", "nsa", "odni", "tlv", "usmc"]}">
					<option value = "${it}">${it.toUpperCase()}</option>
				</g:each>
			</select>
		</td>
	</tr>
</table>
