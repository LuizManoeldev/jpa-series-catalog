package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Genero {
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;
	
	@OneToMany(mappedBy="genero")
	private List<Serie> listaSeries = new ArrayList<>();
	
	
	public Genero() {}
	public Genero(String nome) {
		super();
		this.nome = nome;
	}
	
	public void adicionar(Serie ser){
		listaSeries.add(ser);
	}
	public void remover(Serie ser){
		listaSeries.remove(ser);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Serie> getSeries() {
		return this.listaSeries;
	}
	
	@Override
	public String toString() {
		return "Genero [nome = " + nome + " ]" ;
	}
	
}
