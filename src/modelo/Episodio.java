package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Episodio {
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int numero_episodio;
	private String nome;
	@ManyToOne(fetch=FetchType.LAZY)
	private Serie serie;
	
	
	
	
	public Episodio() {}
	public Episodio(int numero_episodio, String nome) {
		super();
		this.numero_episodio = numero_episodio;
		this.nome = nome;
	}
	

	public int getN_Episodios() {
		return numero_episodio;
	}

	public void setN_Episodios(int numero_episodio) {
		this.numero_episodio = numero_episodio;
	}

	public String getNome() {
		return nome;
	}

	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	

	@Override
	public String toString() {
		return "Episodio [Numero do Episodio= " + numero_episodio + "] [nome=" + nome + " ]";
	}
	
	
	

}
