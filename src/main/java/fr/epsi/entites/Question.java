package fr.epsi.entites;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="question")
@XmlRootElement
public class Question {
	
	@Id
	private int id;
	
	@XmlElement
	private String question;
	
	private String reponse;
	
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
	
	@Override
    public String toString() {
		String anyString = "test";
		String anyNumber = "yo";
        return "MyBean{" +
            "anyString='" + anyString + '\'' +
            ", anyNumber=" + anyNumber +
            '}';
    }
}
