package pgv.windows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RunProcessMulti {

	public static void main(String[] args) throws IOException {
		
		// Aquí usaremos los comandos directamente y ejecutaremos dos procesos de manera
		// que uno lea y escriba según los resultados del procesos anterior, similar al
		// efecto de la "tuberia"
		try {
			
			// Construimos el proceso
			ProcessBuilder pb = new ProcessBuilder("bash.exe", "-c", "ls -al" )
					;
			// Empezamos
			Process process = pb.start();
			
			// Ahora mostramos la salida con el "inputStream"
			InputStream in = process.getInputStream(); // Usamos esta clase, lo demás es como hemos visto anteriormente
			InputStreamReader rIn = new InputStreamReader(in, StandardCharsets.UTF_8);
			BufferedReader buff = new BufferedReader(rIn); 
			
			// Ahora el proceso 2 escribe los datos del primero, y luego hace lo mismo que el proceso 1
			ProcessBuilder pb2 = new ProcessBuilder("bash.exe", "-c", "tr a 0" ); 
			Process process2 = pb2.start();
			OutputStream out = process2.getOutputStream();
			OutputStreamWriter rOut = new OutputStreamWriter(out);
			BufferedWriter buffW = new BufferedWriter(rOut);
			
			String line;
			while( (line = buff.readLine()) != null ) {
				buffW.write(line + "\n");
			}
			
			// Nos tenemos que asegurar de cerrar este flujo
			buffW.close();
			
			in = process2.getInputStream(); // Usamos esta clase, lo demás es como hemos visto anteriormente
			rIn = new InputStreamReader(in, StandardCharsets.UTF_8);
			buff = new BufferedReader(rIn); 
			
			while( (line = buff.readLine()) != null ) {
				System.out.println(line);
			}
			
			// Posibles problemas
		} catch( IOException e ) {
			System.err.println("Error de E/S");
			System.exit(-1);
			
		} 
		
	}
}
