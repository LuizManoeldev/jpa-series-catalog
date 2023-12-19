package appconsole;

import regras_negocio.Fachada;

public class AlterarCatalogo {
	public AlterarCatalogo(){
		try {
			Fachada.inicializar();
			System.out.println("Atualizando Genero...");
			Fachada.atualizarGeneroDeSerie("acao", "Casa do Dragao");
	
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new AlterarCatalogo();
	}
}
