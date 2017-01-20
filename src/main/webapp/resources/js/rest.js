function restRequest(url, data, type, response) {
	type(url, data, response);
}

var POST = function(url, jsonObj, response) {
	$.ajax({
		url : url,
		method : "POST",
		data : jsonObj,
		contentType : "application/x-www-form-urlencoded",
		success : function(result) {
			response(result);
		},
		error () {
			console.log('Error');
		}
	});
}

var GET = function(url, jsonObj, response) {
	$.ajax({
		url : url,
		method : "GET",
		contentType : "application/json",
		success : function(result) {
			response(result);
		},
		error () {
			console.log('Error');
		}
	});
}

var PUT = function(url, jsonObj, response) {
	$.ajax({
		url : url,
		method : "PUT",
		data : jsonObj,
		contentType : "application/x-www-form-urlencoded",
		success : function(result) {
			response(result);
		},
		error () {
			console.log('Error');
		}
	});
}

var DELETE = function(url, jsonObj, response) {
	$.ajax({
		url : url,
		method : "DELETE",
		contentType : "application/json",
		success : function(result) {
			response(result);
		},
		error () {
			console.log('Error');
		}
	});
}

function response(result) {
	//alert(result)
	$('body').append("<div>" + result + "</div>")
}
