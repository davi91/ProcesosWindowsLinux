package pgv.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RunProcess {

	public static void main(String[] args) throws IOException {
		
		// Usaremos el /C para ejecutar el cmd.exe y darle un parámetro, quiero que se termine la ejecución
		// del cmd una vez terminamos
		if( args.length <= 0 ) {
			System.err.println("Se neceista un programa a ejectuar");
			System.exit(-1);
		}
		
		// Construimos el proceso
		ProcessBuilder pb = new ProcessBuilder(args);
		
		try {
			
			// Empezamos
			Process process = pb.start();
			int retorno = process.waitFor(); // Espera la respuesta
			// 0 por defecto funcionó bien
			System.out.println("La ejecución de " + Arrays.toString(args) + " devuelve " + retorno);
			
			// Ahora mostramos la salida con el "inputStream"
			InputStream in = process.getInputStream(); // Usamos esta clase, lo demás es como hemos visto anteriormente
			InputStreamReader rIn = new InputStreamReader(in, StandardCharsets.UTF_8);
			BufferedReader buff = new BufferedReader(rIn); 
			
			String line;
			while( (line = buff.readLine()) != null ) {
				System.out.println(line);
			}
			
			// Posibles problemas
		} catch( IOException e ) {
			System.err.println("Error de E/S");
			System.exit(-1);
			
		} catch( InterruptedException e ) {
			System.err.println("El proceso hijo finalizó de forma incorrecta");
			System.exit(-1);
			
		}
		
	}
}
