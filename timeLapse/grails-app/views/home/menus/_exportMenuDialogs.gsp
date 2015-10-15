<div class = "modal" id = "exportAnimationParamsDialog">
	<div class = "modal-dialog">
		<div class = "modal-content">
			<div class = "modal-header"><h4>Export Animation</h4></div>
			<div class = "modal-body">
				<table class = "table">
					<tr>
						<td align = "right">File Tyoe:</td>
						<td>
							<select id = "exportAnimationFileTypeSelect">
								<g:each in = "${["gif", "pdf"]}">
									<option value = "${it}">${it.toUpperCase()}</option>
								</g:each>
							</select>
						</td>
					</tr>
				</table>
						
			</div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal" onclick = prepareAnimationExport();>Continue</button>
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div class = "modal" id = "exportFrameDialog">
	<div class = "modal-dialog">
 		<div class = "modal-content">
			<div class = "modal-header"><h4>Export Image</h4></div>
			<div class = "modal-body"><g:render template = "menus/templates/default"/></div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-primary" onclick = updateAnimationFrameParams();changeFrame("rewind");prepareExportFrameParams()>
					<span class = "glyphicon glyphicon-arrow-left"></span>
				</button>
				<button type = "button" class = "btn btn-primary" onclick = updateAnimationFrameParams();changeFrame("fastForward");prepareExportFrameParams()>
					<span class = "glyphicon glyphicon-arrow-right"></span>
				</button>
				<button type = "button" class = "btn btn-primary" data-dismiss = "modal">Export</button>
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
 			</div>
		</div>
	</div>
</div>

<div class = "modal" id = "exportLinkDialog">
	<div class = "modal-dialog">
		<div class = "modal-content">
			<div class = "modal-body">
				Your link has been saved, <a id = "exportLinkHref">here</a> you go!
			</div>
			<div class = "modal-footer">
				<button type = "button" class = "btn btn-default" data-dismiss = "modal">Close</button>
			</div>
		</div>
	</div>
</div>
