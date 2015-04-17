package fr.epsi.entites;

import java.io.IOException;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;

import fr.epsi.outils.HibernateUtil;
import fr.epsi.outils.Log;


@Entity
@Table(name="question")
public class Question {
	
	//Etats possibles pour une question
	public static final String EN_ATTENTE = "ATTENTE";
	public static final String OK  = "OK";
	public static final String TRAITEMENT = "TRAITEMENT";
	public static final String ERREUR = "ERREUR";
	
	
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Basic  
	private int id;
	
	@Basic
	private String question;
	
	@Basic
	private String reponse;
	
	@Basic
	private String etat;
	
	@Basic
	private String expert; // contient un identifiant permettant d'identifier le systeme expert ayant répondu à la question
	
	@Transient
	private ObjectMapper mapper;
	
	public Question () {
		this.mapper = new ObjectMapper();
	}

	public Question(String question) {
		this.question = question;
		this.etat = EN_ATTENTE;
		this.mapper = new ObjectMapper();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public String getExpert() {
		return expert;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public boolean estEnAttente () {
		return getEtat().equals(EN_ATTENTE);
	}
	
	public boolean estEnTraitement () {
		return getEtat().equals(TRAITEMENT);
	}
	

	/**
	 * <p>Enregistre la question en base de données</p>
	 * @return la question avec l'id généré
	 */
	public Question enregistrer() {
		
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
        Log.ecris("Enregistrement de la question : " + this.question);
		return this;
	}
	
	public static Question trouverQuestionParId (int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Question question = (Question)session.get(Question.class, id);
		session.getTransaction().commit();
		return question;
	}

	public static Question getPremiereQuestionEnAttente() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Question where etat = :etat order by id ASC").setMaxResults(1);
		query.setParameter("etat", Question.EN_ATTENTE);
		Question question = (Question)query.uniqueResult();
		session.getTransaction().commit();
		return question;
	}

	/**
	 * <p>La réponse à la question est enregistrée en bdd et l'état de la question est passé à "OK"</p>
	 * @param reponseStr
	 * @param etat 
	 */
	public void repondre(String reponseStr, String etat) {
		this.reponse = reponseStr;
		this.etat = etat;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
		Log.ecris("Question : " + this.id + " répondu par : " + this.expert + " : " + reponseStr);
	}
	
	/**
	 * <p>Le champ état de la question est passé à "TRAITEMENT" et l'idExpert et renseigné en base de données</p> 
	 * @param idExpert 
	 */
	public void mettreEnTraitement(String idExpert) {
		this.expert = idExpert;
		this.etat = TRAITEMENT;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
		Log.ecris("Mise en traitement de la question " + this.id + " par l'expert " + idExpert);
	}
	
	/**
	 * <p>Récupère une question sans réponse appartenant à l'expert passé en paramètre</p>
	 * @param idExpert
	 * @return une question si existe, sinon null
	 */
	public static Question getQuestionSansReponse (String idExpert) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Question where etat = :etat and expert = :expert and reponse is null  order by id ASC").setMaxResults(1);
		query.setParameter("etat", Question.TRAITEMENT);
		query.setParameter("expert", idExpert);
		Question question = (Question)query.uniqueResult();
		session.getTransaction().commit();
		return question;
	}
	
	@Override
	public String toString () {
		
		String retour = null;
		try {
			retour = mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
			retour = this.question;
		}
		
		return retour;
	}
}
