package daojpa;

import modelo.Usuario;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class DAOUsuario extends DAO<Usuario>{
	public List<Usuario> readAll(Object chave) {
		TypedQuery<Usuario> q = manager.createQuery("select * from usuario", Usuario.class);
		return  q.getResultList();
	}

	@Override
	public Usuario read(Object chave) {
		try{
			String nome = (String) chave;
			TypedQuery<Usuario> q = manager.createQuery("select u from Usuario u where u.nome=:n", Usuario.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}

}
