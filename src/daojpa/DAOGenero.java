package daojpa;
import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Genero;

public class DAOGenero extends DAO<Genero>{

	public List<Genero> readAll(Object chave) {
		TypedQuery<Genero> q = manager.createQuery("select * from Genero", Genero.class);
		
		return q.getResultList();
	}
	
	@Override
	public Genero read(Object chave) {
		try{
			String nome = (String) chave;
			TypedQuery<Genero> q = manager.createQuery("select g from Genero g where g.nome=:n", Genero.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}

}
