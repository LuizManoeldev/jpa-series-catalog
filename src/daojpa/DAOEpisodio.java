package daojpa;
import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Episodio;

public class DAOEpisodio extends DAO<Episodio> {
	
	public List<Episodio> readAll(Object chave) {
		TypedQuery<Episodio> q = manager.createQuery("select * from Episodio", Episodio.class);
		
		return q.getResultList();
	}
	
	@Override
	public Episodio read(Object chave) {
		try{
			String nome = (String) chave;
			TypedQuery<Episodio> q = manager.createQuery("select e from Episodio e where e.nome=:n", Episodio.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}
}
