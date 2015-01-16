package fr.epsi.entites;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.Session;

import fr.epsi.outils.HibernateUtil;


@Entity
@Table(name="question")
@XmlRootElement
public class Question {
	
	//Etats possibles pour une question
	public static final String EN_ATTENTE = "ATTENTE";
	public static final String OK  = "OK";
	public static final String TRAITEMENT = "TRAITEMENT";
	
	
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

	public Question () {
		
	}
	
	public Question(String question) {
		this.question = question;
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
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public boolean estEnAttente () {
		return getEtat().equals(EN_ATTENTE);
	}
	
	public String toJSOn() {
		// TODO Auto-generated method stub
		return null;
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
		return question;
	}

	public static Question getPremiereQuestionEnAttente() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void miseEnTraitementDeLaQuestion(int id2) {
		// TODO Auto-generated method stub
		
	}

	public boolean repondre(String reponseStr) {
		// TODO Auto-generated method stub
		return false;
	}
}
