package AnalisadorSemantico;

import java.util.LinkedList;

public class Erro_Semantico {

	private String mensagem_erro;

	private LinkedList<String> erros;

	public Erro_Semantico() {
		erros = new LinkedList<String>();
	}

	public void guardarErros(int linha, String esperado) {
		if (esperado.equals("SUCESSO"))
			mensagem_erro = "Seu programa foi finalizado com: " + esperado;
		else if (esperado.equals("SEMSUCESSO"))
			mensagem_erro = "\n\n PROGRAMA NAO FOI FINALIZADO COM SUCESSO";
		else
			mensagem_erro = "ERROR! Linha: " + linha + " - " + esperado;

		erros.add(mensagem_erro);
		System.out.println(mensagem_erro);
	}

	public LinkedList<String> getErrosList() {
		return erros;
	}

}
