function getReponse () {

    // Récupération de l'URL de la ressource
	var lien = $("#lien").text();

	var req = $.ajax({
         url : lien,
         dataType : "text",
         type : 'GET'
     });

    var message;

     req.success(function(req, status, xhr) {

     	$("#reponse").empty();

      	//$("#reponse").append("<p>"+obj.data+"</p>");

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
	var question = $("#question").val();

    if (question == "") {
        return;
    }	

    $(".listview_test").empty();

	var req = $.ajax({

         url : "http://localhost:8283/question/" + question,
         dataType : "text",
         type : 'POST'
     });
    
    var message;

     req.success(function(req, status, xhr) {
       if (xhr.status == 201) {
            message = "Votre question à bien été enregistrée.";
       }
     });

     req.error(function( req, status, xhr ) {
        if (xhr.status == 500) {
            message = "Erreur lors de l'enregistrement de la question.";
         }
     });

     req.done(function(req, status, xhr){
        $(".listview_test").append("<p>"+message+"</p>");
        if (xhr.status == 201) {
            $(".listview_test").append("<p><a id='lien' onclick='getReponse()'>"+xhr.getResponseHeader("Location")+"</a></p>");
        }
     });
}




function demanderQuestion()

{
    var req = $.ajax({
        url : "http://localhost:8283/expert/question/expertName",
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