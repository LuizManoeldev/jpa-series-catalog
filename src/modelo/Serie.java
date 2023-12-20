package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Serie {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;
	private String ano;
	
	@OneToMany(mappedBy="serie", cascade=CascadeType.ALL) // Sempre Usar LIST
	private List<Episodio> listaEpisodios = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Genero genero;
	
	private String canal;
	
	@Transient
	private Long n_eps;
	
	
	public Serie() {}
	public Serie(String nome, String ano, Genero genero, String canal) {
		super();
		this.nome = nome;
		this.ano = ano;
		this.genero = genero;
		this.canal = canal;
	}
	public Serie(String nome, String ano, String genero, String canal, Long eps) {
		super();
		this.nome = nome;
		this.ano = ano;
		this.genero = new Genero(genero);
		this.canal = canal;
		this.n_eps = eps;
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
	
	public void excluirEpisodios(){
		this.listaEpisodios = null;
	}

	@Override
	public String toString() {
		return "Serie [nome = " + nome + " ] [Genero = " + this.genero.getNome() + 
				"] [ Ano = " + ano + " ] [ Canal = " + canal + " ] [Qtd de Episosdios = " + this.n_eps + " ]" ;
	}
	
	
	
}
