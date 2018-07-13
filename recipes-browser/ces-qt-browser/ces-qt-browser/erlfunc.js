function checkURLerrs(){
	var el_errordomain = document.getElementById("errordomain");
	var el_errorcode = document.getElementById("errorcode");
	var string_url = window.location.href;
	var url = new URL(string_url);
	var edomain = url.searchParams.get("edomain");
	if(emptyString(edomain)){
		edomain=null;
	}
	if(null !== edomain){
		var isText= isNaN(parseInt(edomain));

		if(isText){
			el_errordomain.innerHTML = edomain
		}else{
			el_errordomain.innerHTML = "An error occured"
		}
	}else{
		el_errordomain.innerHTML = "An error occured"
	}

	var ecode= url.searchParams.get("ecode");
	if(isNaN(parseInt(ecode))){
		el_errorcode.innerHTML = "unknown";
	}else{
		console.log(ecode+"ecode");
		el_errorcode.innerHTML = parseInt(ecode);
	}
}

function emptyString(str){
	if(str === null)
		return true;

	return (str.length === 0 || !str.trim());
}
