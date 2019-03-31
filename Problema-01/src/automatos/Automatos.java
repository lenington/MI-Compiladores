package automatos;


public class Automatos {
	int count;
	
	public String automatoComentarios(String line) {
		int state = -1;

		if (line.charAt(this.count) == '/')
			state = 0;

		while (true) {
			switch (state) {
			case 0:
				this.count++;
				if (line.charAt(this.count) == '/')
					state = 1;
				else
					state = 3;
				break;
			case 1:
				this.count++;
				if (this.count < line.length())
					state = 1;
				else
					state = 2;
				break;
			case 2:
				return "comentario de linha";
			case 3:
				this.count++;
				if (line.charAt(this.count) == '*')
					state = 4;
				else
					state = 3;
				break;
			case 4:
				this.count++;
				if (line.charAt(this.count) == '/')
					state = 5;
				else if (line.charAt(this.count) == '*')
					state = 4;
				else
					state = 3;
				break;
			case 5:
				return "comentario de bloco";

			}
		}
	}

	public String automatoNumero(String line) {
		int state = -1;
		char char_line;
		int num_asc;
		int line_tamanho = line.length() - 1;
		
		if (line.charAt(this.count) == '-') {
			state = 0;
		}

		while (true) {
			switch (state) {
			case 0:
				this.count++;
				if (line.charAt(this.count) == ' ') state = 1;
				break;
			case 1:
				this.count++;
				if (line.charAt(this.count) == ' ') state = 1;
				else state = 2;
				break;
			case 2:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if (num_asc >= 48 && num_asc <= 57) state = 2;
				else if (line.charAt(this.count) == '.') state = 3;
				
				if (this.count >= line_tamanho) return "so numero";
				break;
			case 3:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if (num_asc >= 48 && num_asc <=57) state = 3;
				else if (line.charAt(this.count) == '.') state = 4;
				else state = 3;
				
				if (this.count >= line_tamanho) return "numero com ponto";
				break;
			case 4: 
				return "error";
			default:
				break;
			}
		}

	}
	
	public String automatoIndentificador(String line) {
		int state = 1;
		char char_line;
		int num_asc;
		int line_tamanho = line.length() - 1;
		
		while(true) {
			switch (state) {
			case 1:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if ((num_asc >= 97 && num_asc <= 122) || 
						(num_asc >= 65 && num_asc <= 90) || 
							(num_asc >= 48 && num_asc <= 57) || char_line == '_') state = 1;
				else if (char_line == ' ' || char_line == '+') return "idetificador";
				
				if (this.count == line_tamanho) return "identificador_fim";
				break;
			
			}
		}
	}
	
	//AUTOMATOS LENINGTON:
	/*
	 * Por enquanto ele verifica se tem operações separadas apenas
	 * Ex: +, ++, -, --, *, /, +1, -1... mas n verifica se tem um número antes
	 */
	public String automatoOperadorAritmetico(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '+') {
					if (line.charAt(1+this.count) == '+') {
						return "operador aritmético de incremento";
					} else {
						//verifica se tem um número...
						return "operador aritmético de adição";
					} 
				} else if (char_line == '-') { 
					if (line.charAt(1+this.count) == '-') {
						return "operador aritmético de decremento";
					} else {
						//verifica se tem um número...
						return "operador aritmético de subtração";
					} 
				} else if (char_line == '*') {
					return "operador aritmético de multiplicação";
				} else if (char_line == '/') {
					return "operador aritmético de divisão";
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}
	
	/*
	 * 
	 */
	public String automatoOperadorRelacional(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '<') {
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional menor ou igual";
					} else {
						return "operador relacional menor";
					} 
				} else if (char_line == '>') {
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional maior ou igual";
					} else {
						return "operador relacional maior";
					} 
				} else if (char_line == '=') { 
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional igual";
					} else {
						return "operador relacional atribuição";
					} 
				} else if (char_line == '!') { 
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional diferente";
					} else {
						return "operador lógico falso";
					} 
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}
	
	/*
	 * 
	 */
	public String automatoOperadorLogico(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '!') {
					return "operador lógico falso";
				} else if (char_line == '&') {
					if (line.charAt(1+this.count) == '&') {
						return "operador lógico 'e'";
					} else {
						//
					} 
				} else if (char_line == '|') { 
					if (line.charAt(1+this.count) == '|') {
						return "operador lógico 'ou'";
					} else {
						//
					} 
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}	
	
	
	
	public String automatoOp_Relacional (String line) {
		
		
		return " ";
	}
	
	public int getCounter() {
		return count;
	}
	
	public void setCounter(int count) {
		this.count = count;
	}

}
