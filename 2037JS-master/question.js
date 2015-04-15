function getReponse (lien) {
//	Récupération de l'URL de la ressource

	var req = $.ajax({
		url : lien,
		dataType : "text",
		type : 'GET'
	});
	var message;

	req.success(function(req, status, xhr) {

		$("#reponse").empty();

//		$("#reponse").append("<p>"+obj.data+"</p>");

		var headers = xhr.getAllResponseHeaders().toLowerCase();

		if (xhr.status == 202) {
			message = "Votre question n'a pas encore de réponse. Veuillez patienter !";
		} else if (xhr.status == 200) {
			var obj = jQuery.parseJSON( req );
			message = "Votre question à été répondu par un de nos experts : " + obj.reponse;
		}

	});
	req.error(function( req, status, xhr ) {

		if (xhr.status == 500) {
			message = "Erreur lors de la récupération de la réponse à votre question";
		} 
	});

	req.complete(function(req, status, xhr){

		$("#reponse").empty();
		$("#reponse").append("<p>"+message+"</p>");
	});
}


function poserQuestion()

{
	$("#reponse").removeClass();
	var question = $("#question").val();
	var message;
	if (question == "") {
		$("#reponse").empty();
		message = "Veuillez entrer une question";
		$("#reponse").addClass("alert alert-danger col-sm-10");
		$("#reponse").append("<p>"+message+"</p>");
		return;
	}	

	$(".listview_test").empty();

	var req = $.ajax({

		url : "http://localhost:8283/interface2037/client/question/" + question,
		dataType : "text",
		type : "POST"
	});
	

	req.success(function(req, status, xhr) {
		if (xhr.status == 202) {
			message = "Votre question à bien été enregistrée.";
			$("#reponse").addClass("alert alert-success col-sm-10");
			$("#reponse").empty();
			var location = xhr.getResponseHeader("Location");
			var coolVar = location;
			var partsArray = coolVar.split('/');
			partsArray[6]
			$("#reponse").append("<p>Votre question à bien été enregistrée.</p>");
			$("#reponse").append("<p>L'url de la ressource est : "+location+"</p>");
			$("#reponse").append("<p>L'ID de votre question est le : "+partsArray[6]+", vous en aurez besoin pour consulter les réponses</p>");
			$("#reponse").append("<p>Vous pouvez aussi cliquer sur le lien ci-dessous :</p>");
			$("#reponse").append("<p><a id='lien' href='./ConsulterReponse.html?id="+partsArray[6]+"'>"+xhr.getResponseHeader("Location")+"?id="+partsArray[6]+"</a></p>");
			
		}
	});

	req.error(function( req, status, xhr ) {
		if (xhr.status == 500) {
			message = "Erreur lors de l'enregistrement de la question.";
			$("#reponse").empty();
			$("#reponse").addClass("alert alert-danger col-sm-10");
			$("#reponse").append("<p>"+message+"</p>");
		}
		else
			{
			message = "Une erreur est survenue.";
			$("#reponse").empty();
			$("#reponse").addClass("alert alert-danger col-sm-10");
		$("#reponse").append("<p>"+message+"</p>");
			}
	});

	req.done(function(req, status, xhr){
		
		if (xhr.status == 201) {
			$("#reponse").append("<p><a id='lien' onclick='getReponse()'>"+xhr.getResponseHeader("Location")+"</a></p>");
		}
	});
}

function demanderReponse()

{
	$("#reponse").removeClass();
	var idquestion = $("#idQuestion").val();

	if (idquestion == "") {
		$("#reponse").empty();
		message = "Entrez un ID de question";
		$("#reponse").addClass("alert alert-danger col-sm-10");
		$("#reponse").append("<p>"+message+"</p>");
		return;
	}	

	$(".listview_test").empty();

	var req = $.ajax({

		url : "http://localhost:8283/interface2037/client/question/" + idquestion,
		dataType : "text",
		type : "GET"
	});
	var message;

	req.success(function(req, status, xhr) {
		
		if (xhr.status == 202) {
			message = "Votre question n'a pas encore de réponse";
			$("#reponse").addClass("alert alert-info col-sm-10");
			$("#reponse").empty();
			var location = xhr.getResponseHeader("Location");
			$("#reponse").append("<p>"+message+"</p>");
		}
		
		if (xhr.status == 200) {
			var question= JSON.parse(xhr.responseText);
			message = "Votre question a trouvé une reponse." ;
			$("#reponse").addClass("alert alert-success col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
		
			message = "Question initiale : "+question.question ;
			$("#reponse").append("<p>"+message+"</p>");
			
			message = "Reponse apportée : "+question.reponse ;
			$("#reponse").append("<p>"+message+"</p>");
			
			message = "Expert ayant répondu: "+question.expert ;
			$("#reponse").append("<p>"+message+"</p>");
			
			
		}
		
		if (xhr.status == 404) {
			message = "Votre uestion n'existe pas, entrez un nouvel ID";
			$("#reponse").addClass("alert alert-danger col-sm-10");
			$("#reponse").empty();
			var location = xhr.getResponseHeader("Location");
			$("#reponse").append("<p>"+message+"</p>");
		}
		
	});

	req.error(function( req, status, xhr ) {
		
		if (req.status == 500) {
			message = "Erreur serveur";
			$("#reponse").addClass("alert alert-error col-sm-10");
			$("#reponse").append("<p>"+message+"</p>");
		}
		if (req.status == 404) {
			message = "Votre question n'existe pas, entrez un nouvel ID";
			$("#reponse").addClass("alert alert-danger col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
		}
		else{
			message = "Erreur survenue lors de la prise de contact avec le serveur, il dois etre au toilettes, entrez un numéro de question valide pour le faire venir plus vite.";
			$("#reponse").addClass("alert alert-danger col-sm-10");
			$("#reponse").empty();
			$("#reponse").append("<p>"+message+"</p>");
		}
		
	});

}


function demanderQuestion()

{
	var req = $.ajax({
		url : "http://localhost:8283/interface2037/expert/question/expertName",
		dataType : "text",
		type : "GET"
	});

	req.success(function(req, textStatus, xhr) {
		$(".theQuestion").empty();

		var obj = jQuery.parseJSON( req );

		var aAfficher;

		if (xhr.status == 202) {
			// Pas de questions en attente
			aAfficher = obj.message;

		} else {
			// Au moins 1 question en attente
			aAfficher = obj.question;
			$("#idQuestion").val(obj.id);
		}


		$(".theQuestion").append("<p>" + aAfficher + "</p>");

	});

	req.error(function( req, status, err ) {

	});
}


function EnvoyerReponse(text)
{

	var idQuestion = $("#idQuestion").val();
	var reponse = $("#reponse").val();

	if (idQuestion == "" || reponse == "") {
		return;
	}


	var req = $.ajax({
		url : "http://localhost:8283/expert/reponse/expertName/" + idQuestion + "/" + reponse,
		dataType : "text",
		type : "POST",
		timeout : 10000
	});

	req.success(function(data) {
		$(".theQuestion").empty();
		$(".theQuestion").append("<p>" + data + "</p>");
	});

	req.error(function( req, status, err ) {

	});

}