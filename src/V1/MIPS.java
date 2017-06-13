package V1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MIPS {

	public static void main(String[] args) {
		Queue<Instrucao> FilaDeInstrucoes = new LinkedList<Instrucao>();
		Registrador[] Registradores = new Registrador[32];
		Registradores[0].valor=0;
		String nome = "Teste1";
		
		//Leitura do arquivo de entrada e preenchimento da fila.
	    
		try {
	    	FileReader arq = new FileReader(nome);
	    	BufferedReader lerArq = new BufferedReader(arq);
	 
	    	String instAux = lerArq.readLine(); // lê a primeira instrução
	    	while (instAux != null) {
	    		Instrucao in = new Instrucao(instAux.split(" ")[0]);
	    		FilaDeInstrucoes.add(in);
	    		System.out.printf("%s\n", in.getInstrucao());
	    		instAux = lerArq.readLine(); // lê da segunda até a última linha
	    	} 
	    	arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	    }
		
		
	}

}
