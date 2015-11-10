$(function() {
	var $json = $('textarea.json') 
	if($json.length > 0) {

	    var json = JSON.parse($json.val());

		var $div = $('<div>').css('width', $json.css('width')).css('height', $json.css('height'));

		$div.insertBefore( $json );
		$json.css('display', 'none');
		
		var container = $div[0];
	    var editor = new JSONEditor(container, {
	    	change: function() {
		    	$json.val( editor.getText() );
		    }
	    }, json);
	    editor.expandAll()
	}
});