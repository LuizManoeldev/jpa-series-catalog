package appconsole;

import regras_negocio.Fachada;

public class DeletarCatalogo {
	public DeletarCatalogo() {
		try {
			Fachada.inicializar();
			System.out.println("\nDeletando serie...");
			Fachada.excluirSerie("Vingadores");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new DeletarCatalogo();
	}
}
