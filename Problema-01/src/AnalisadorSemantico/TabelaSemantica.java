package AnalisadorSemantico;

import java.util.HashMap;
import java.util.LinkedList;

public class TabelaSemantica {
	
	HashMap<String, LinkedList<String>> tabSem;
	public TabelaSemantica() {
		
		tabSem = new HashMap<String, LinkedList<String>>();
	}
	
	public void inserirTabela(String cadeia, String token, String cat, String tipo) {
		LinkedList<String> e = new LinkedList<String>();
		e.add(token);
		e.add(cat);
		e.add(tipo);
		tabSem.put(cadeia, e);
	}

}
