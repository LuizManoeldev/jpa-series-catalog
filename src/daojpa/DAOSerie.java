package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Genero;
import modelo.Serie;

public class DAOSerie extends DAO<Serie> {
	
	public List<Serie> readAll(Object chave) {
		TypedQuery<Serie> q = manager.createQuery("select * from Serie", Serie.class);
		
		return q.getResultList();
	}
	
	@Override
	public Serie read(Object chave) {
		try{
			String nome = (String) chave;
			TypedQuery<Serie> q = manager.createQuery("select s from Serie s where s.nome=:n", Serie.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}

	public List<Serie> seriesDoAno(String chave) {
		try{
			String ano = (String) chave;
			TypedQuery<Serie> q = manager.createQuery("select s from Serie s where s.ano =: ano", Serie.class);
			q.setParameter("ano", ano);
			return q.getResultList();

		}catch(NoResultException e){
			return null;
		}
	}

	public List<Serie> seriesComMaisDeXEpisodios(Object chave) {
	    try {
	        int numero_eps = (int) chave;
	        TypedQuery<Serie> q = manager.createQuery(
	            "SELECT s FROM Serie s WHERE (SELECT COUNT(e) FROM s.listaEpisodios e) > :numeroDeEpisodios", Serie.class);
	        q.setParameter("numeroDeEpisodios", numero_eps);
	        return q.getResultList();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
	
	public List<Serie> seriesDoGenero(Object chave){
		try{
			Genero nome_genero = (Genero) chave;
			
			TypedQuery<Serie> q = manager.createQuery("select s from Serie s where s.genero =: genero", Serie.class);
			q.setParameter("genero", nome_genero);
			return q.getResultList();

		}catch(NoResultException e){
			return null;
		}
	}
	
	public List<Serie> listarSerie(){
		try {
			TypedQuery<Serie> q = manager.createQuery("SELECT NEW modelo.Serie(s.nome, s.ano, g.nome, s.canal, COUNT(e.id)) FROM Serie s JOIN s.genero g LEFT JOIN s.listaEpisodios e GROUP BY s.id, g.id\r\n" , Serie.class);
			return q.getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
}
