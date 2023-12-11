/**********************************
*	Catalogo
*	Luiz Manoel 
*	Maicon Felipe

 **********************************/
package daojpa;



import java.util.Properties;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
	private static EntityManager manager;
	private static EntityManagerFactory factory;

	public static EntityManager conectarBanco(){
		if(manager == null) {
			//processar arquivo persistence.xml
			factory = Persistence.createEntityManagerFactory("hibernate-postgresql");
			//criar instancia do manager
			manager = factory.createEntityManager();
		}
		return manager;
	}

	public static void fecharBanco(){
		if(manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager=null;
		}
	}

}
