package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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
}
