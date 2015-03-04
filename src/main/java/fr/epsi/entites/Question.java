package fr.epsi.entites;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;

import fr.epsi.outils.HibernateUtil;


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
	
	public Question () {
		
	}

	public Question(String question) {
		this.question = question;
		this.etat = EN_ATTENTE;
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
	
	@Override
	public String toString () {
		String json = "Question : {"
				+ "id : " + this.id + ", "
				+ "question : " + this.question + ", "
				+ "expert : " + this.expert + ", "
				+ "reponse : " + this.reponse
				+ "}";
		return json;
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
	 */
	public void repondre(String reponseStr) {
		this.reponse = reponseStr;
		this.etat = OK;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
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
	}
}
