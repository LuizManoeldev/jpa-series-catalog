package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Serie {
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	private String ano;
	
	@OneToMany(mappedBy="serie") // Sempre Usar LIST
	private List<Episodio> listaEpisodios = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Genero genero;
	
	private String canal;
	
	
	public Serie() {}
	public Serie(String nome, String ano, Genero genero, String canal) {
		super();
		this.nome = nome;
		this.ano = ano;
		this.genero = genero;
		this.canal = canal;
	}
	
	public void adicionar(Episodio ep){
		listaEpisodios.add(ep);
	}
	public void remover(Episodio ep){
		listaEpisodios.remove(ep);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}
	
	public List<Episodio> getEpisodios() {
		listaEpisodios.removeIf(elemento -> elemento == null);
		return this.listaEpisodios;
	}

	@Override
	public String toString() {
		return "Serie [nome = " + nome + " ] [Genero = " + this.genero.getNome() + 
				"] [ Ano = " + ano + " ] [ Canal = " + canal + " ] [Qtd de Eps = " + this.listaEpisodios.size() + " ]" ;
	}
	
	
	
}
