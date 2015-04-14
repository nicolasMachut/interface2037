function demanderQuestion()
{
	var nomExpert = $("#expertName").val();
	if(nomExpert==""){
		alert("Renseignez votre nom d'expert");
		return;
	}
	
	
	$("#reponse").removeClass();
	$("#reponse").empty();

	var req = $.ajax({
		url : "http://localhost:8283/interface2037/expert/question/"+nomExpert,
		dataType : "text",
		type : "GET"
	});
	
	var message;

	req.success(function(req, status, xhr) {
		if (xhr.status == 202) {
			var question = JSON.parse(xhr.responseText) 
			message = "Voici la question : ";
			$("#reponse").addClass("alert alert-success col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
			
			message = "";
//vars			
			$("#reponse").append("<p>"+question.question+"</p>");
			$("#idQuestion").val(question.id);
//			alert("id Q : "+ $("#idQuestion").val()+" id reel :"+question.id);
		}
		
		if (xhr.status == 204) {
			message = "Pas de question en attente.";
			$("#reponse").addClass("alert alert-danger col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
			
		}
	});

	req.error(function( req, status, xhr ) {
			message = "une erreur est survenue.";
			$("#reponse").addClass("alert alert-error col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
	});

}

function EnvoyerReponse()
{
	var idQuestion = $("#idQuestion").val();
	var reponse = $("#reponseText").val();
	var nameOfExpert = $("#expertName").val();
	alert("Id question : "+idQuestion +"/ reponse : "+reponse+"/ nomExpert : "+nameOfExpert);
	if (idQuestion == "" || reponse == "") {
		return;
	}
	
	var req = $.ajax({
		url : "http://localhost:8283/interface2037/expert/question/" + nameOfExpert + "/" + idQuestion + "/" + reponse,
		dataType : "text",
		type : "PUT",
		crossOrigin : false,
		timeout : 10000
	});

	req.success(function(data) {
		$(".theQuestion").empty();
		$(".theQuestion").append("<p>" + data + "</p>");
	});

	req.error(function( req, status, err ) {
alert("test");
	});

}