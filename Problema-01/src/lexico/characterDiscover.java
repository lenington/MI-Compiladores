package lexico;

public class characterDiscover {
	
	/*
	 * Retorna verdadeiro se o caractere informado percente ao alfabeto [a-z] [A-Z]
	 * Em lower case ASCII: a = 97, b = 98, ..., z = 122
     * Em upper case ASCII: A = 65, B = 66, ..., Z = 90
	 * */
	public boolean isLetra(char c){
        int ascii = (int) c; //transforma para ascii
        
        if ((ascii >= 65 && ascii <= 90) ||
    	    (ascii >= 97 && ascii <= 122)) {
        	return true;
        } else return false;
    }
	
	/*
	 * Retorna verdadeiro se o caractere informado percente ao conjunto de digitos [0-9]
	 * Em ASCII: 0 = 48, 1 = 49, ..., 9 = 57
	 * */
	public boolean isDigito(char c) {
        int ascii = (int) c; //transforma para ascii
        
        if (ascii >= 48 && ascii <= 57) {
        	return true;
        } else return false;
	}
	
	/*
	 * Retorna verdadeiro se o caractere informado percente ao conjunto de simbolos 
	 * ASCII 34 = " (aspas dupla)
	 * */
	public boolean isSimbolo(Character c){
    	int ascii = (int) c;
    	
    	if (ascii >= 32 && ascii <= 126 && ascii != 34) {
    		return true;
    	} else return false;
    }
	
	/*
	 * Retorna verdadeiro se o caractere atual for um espaco: 
	 * ASCII = 9 equivale a \t
	 * ASCII = 32 equivale a espaco
	 */
	public boolean isEspaco(char c) {
		int ascii = (int) c;
		if(ascii == 9 || ascii == 32) { 
			return true;
		} else return false;
	}
	
	public boolean isDelimitador(char c) {
		if (c == ';' || c == ',' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '.' || c == '/')
			return true;
		return false;
	}

}
