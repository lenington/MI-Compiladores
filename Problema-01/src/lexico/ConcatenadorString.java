package lexico;

/*
 * Classe responsavel por concatenar as string para serem inseridas
 * na Tabela de tokens
 * */
public class ConcatenadorString {
	
	String token;
	
	/*
	 * Construtor
	 * */
	public ConcatenadorString() {
		token = "";
	}
	
	/*
	 * metodo responsavel por concatenar a string
	 * */
	public void concatenar_String (char c) {
		token+= c;
	}
	
	/*
	 * zera a string concatenada para quando quiser gerar
	 * uma nova String
	 * */
	public void zerar_StringConcatenada() {
		token = "";
	}
	
	/*
	 * retorna a string concatenada
	 * */
	public String getStringConcatenada() {
		return token;
	}

}
