/**********************************
 *	Catalogo
 *	Luiz Manoel 
 *	Maicon Felipe

 **********************************/

package regras_negocio;

import java.util.List;

import daojpa.DAO;
import daojpa.DAOSerie;
import daojpa.DAOGenero;
import daojpa.DAOEpisodio;
import daojpa.DAOUsuario;

import modelo.Serie;
import modelo.Episodio;
import modelo.Genero;
import modelo.Usuario;

public class Fachada {
	private Fachada () {}

	private static DAOUsuario daousuario = new DAOUsuario();
	private static DAOSerie daoserie = new DAOSerie();
	private static DAOGenero daogenero = new DAOGenero();
	private static DAOEpisodio daoepisodio = new DAOEpisodio();

	public static Usuario logado; // Objeto da Interface Grafica

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}


	//Insert
	public static Genero cadastrarGenero(String nome) throws Exception {
		DAO.open();
		Genero genero = daogenero.read(nome);
		if(genero != null) {
			throw new Exception("Genero ja cadastrado: " + nome);
		}

		genero = new Genero(nome);
		daogenero.create(genero);
		DAO.commit();

		return genero;
	}

	public static Episodio cadastrarEpisodio(int numero_ep, String nome) throws Exception {
		DAO.open();
		Episodio episodio = daoepisodio.read(nome);
		
		
		if(episodio != null) {
			throw new Exception("Episodio ja cadastrado: " + nome);
		}
		

		episodio = new Episodio(numero_ep, nome);
		daoepisodio.create(episodio);
		
		DAO.commit();

		return episodio;

	}

	public static Serie cadastrarSerie(String nome, String ano, String genero, String canal) throws Exception{
		DAO.begin();
		Serie serie = daoserie.read(nome);
		Genero generoObj = daogenero.read(genero);
		
		if(serie != null) {
			throw new Exception("Serie ja cadastrada: " + nome);
		}

		if(generoObj == null) {
			throw new Exception("Genero invalido: " + genero);
		}

		serie = new Serie(nome, ano, generoObj, canal);
		daoserie.create(serie);
		generoObj.adicionar(serie);
		daogenero.update(generoObj);
		DAO.commit();

		return serie;
	}

	public static void adicionarEpisodioNaSerie(String nomeEpisodio, String nomeSerie) throws Exception{

		DAO.begin();
		Serie serie = daoserie.read(nomeSerie);
		Episodio episodio = daoepisodio.read(nomeEpisodio);
		
		if(serie == null) {
			throw new Exception("Serie digitada: "+nomeSerie+" Serie Obtida: " + serie.getNome());
		}

		if(episodio == null) {
			throw new Exception("Serie digitada: "+nomeEpisodio + " Episodio obtida: " + episodio.getNome());
		}

		serie.adicionar(episodio);
		episodio.setSerie(serie);
		daoserie.update(serie);
		daoepisodio.update(episodio);

		DAO.commit();
	}

	//Update
	@SuppressWarnings("unused")
	public static void atualizarGeneroDeSerie(String nomeGenero, String nomeSerie) throws Exception {
		DAO.begin();

		Genero novoGenero = daogenero.read(nomeGenero);
		Serie serie = daoserie.read(nomeSerie);
		Genero antigoGenero = serie.getGenero();

		if(serie == null) { 
			throw new Exception("Serie não encontrada: " + nomeSerie);
		}

		if(novoGenero == null) { 
			throw new Exception("Novo Genero não encontrado: " + nomeGenero);
		}

		if(antigoGenero == null) {
			serie.setGenero(novoGenero);
			novoGenero.adicionar(serie);
			daogenero.update(novoGenero);
			daoserie.update(serie);
			DAO.commit();

		} else {
			//Removendo Serie do antigo genero
			antigoGenero.remover(serie);


			//Removendo antigo genero da serie e adicionando o novo
			serie.setGenero(novoGenero);

			// Adicionando Serie no genero
			novoGenero.adicionar(serie);

			daogenero.update(antigoGenero);
			daogenero.update(novoGenero);
			daoserie.update(serie);
			DAO.commit();
		}

	}


	// Delete
	public static void excluirGenero(String nome) throws Exception {
		DAO.begin();
		Genero genero = daogenero.read(nome);

		if(genero == null) {
			throw new Exception("Genero inexistente");
		}

		daogenero.delete(genero);
		DAO.commit();

	}

	public static void excluirEpisodio(String nome) throws Exception{
		DAO.begin();
		Episodio episodio = daoepisodio.read(nome);

		if( episodio == null) {
			throw new Exception("Episodio inexistente");
		}

		List<Serie> series = listarSerie();
		if(series != null) {
			for(Serie serie : series) {
				if((serie.getEpisodios()).contains(episodio)) {
					serie.remover(episodio);
				}
			}
		}

		daoepisodio.delete(episodio);

		DAO.commit();
	}

	public static void excluirSerie(String nome) throws Exception {
		DAO.begin();
		Serie serie = daoserie.read(nome);

		if( serie == null) {
			throw new Exception("Serie inexistente");
		}
		//Removendo serie da lista de series do genero
		if(serie.getGenero() != null) {
			serie.getGenero().remover(serie);
			daogenero.update(serie.getGenero());	
		}
		
		serie.setGenero(null);
		
		
		daoserie.delete(serie);

		DAO.commit();

	}



	// Listagem
	public static List<Genero> listarGenero() {
		DAO.begin();
		List<Genero> resultados =  daogenero.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Episodio> listarEpisodio() {
		DAO.begin();
		List<Episodio> resultados =  daoepisodio.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Serie> listarSerie() {
		DAO.begin();
		List<Serie> resultados =  daoserie.listarSerie();
		
		// Carrega as propriedades lazy-loaded (por exemplo, genero) antes de retornar
        for (Serie serie : resultados) {
            Genero genero = serie.getGenero(); // Isso deve inicializar o proxy lazy-loaded
            // Outras operações, se necessário
        }
		
        DAO.commit();
		return resultados;
	}

	public static Serie localizarSerie(String nomeDaSerie) {
		DAO.begin();
		Serie serie =  daoserie.read(nomeDaSerie);
		DAO.commit();
		return serie;
	}

	public static Episodio localizarEpisodio(String nomeDoEpisodio) {
		DAO.begin();
		Episodio episodio =  daoepisodio.read(nomeDoEpisodio);
		DAO.commit();
		return episodio;
	}


	//consultas especificas
	public static List<Serie> seriesDoAno(String ano) {
		DAO.begin();
		List<Serie> resultados = daoserie.seriesDoAno(ano);
		DAO.commit();
		return resultados;
	}

	public static List<Serie> seriesDoGenero(String nomeDoGenero){
		DAO.begin();
		List<Serie> resultados = daoserie.seriesDoAno(nomeDoGenero);
		DAO.commit();
		return resultados;
	}

	public static List<Serie> seriesComMaisDeXEpisodios(int numeroDeEps) throws Exception{
		DAO.begin();
		List<Serie> resultados = daoserie.seriesComMaisDeXEpisodios(numeroDeEps);
		if (resultados == null) {
			throw new Exception("falha na leitura do banco" + resultados);
		}
		DAO.commit();
		return resultados;
	}


	//Usuario
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usu = daousuario.read(nome);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + nome);
		usu = new Usuario(nome, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario localizarUsuario(String nome, String senha) throws Exception{
		Usuario usu = daousuario.read(nome);
		if (usu == null)
			throw new Exception("Usuario nao encontrado:" + nome);
		if (! usu.getSenha().equals(senha))
			throw new Exception("Senha errada" + senha);
		return usu;
	}

	public static List<Usuario> listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	}

}
