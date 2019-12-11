
import java.io.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator; 




public class Bolsa
{
	private static String file1 = "C:\\Users\\Samsung\\OneDrive\\faculdade\\arquivos\\wp\\arquivo-bolsa-familia\\src\\arquivos-bf\\201901_BolsaFamilia_Pagamentos.csv";
	private static String file2 = "C:\\Users\\Samsung\\OneDrive\\faculdade\\arquivos\\wp\\arquivo-bolsa-familia\\src\\arquivos-bf\\201902_BolsaFamilia_Pagamentos.csv";


	
	private static Map <Long, Obj> map1;
	private static Map <Long, Obj> map2;

	private static List<Obj> usuarios_repetidos;
	private static List<Obj> usuarios_unicos;
	

	public static void main(String[] args) 
	{	
		System.out.println("\r\nIniciando variaveis");

		map1 = new HashMap<Long, Obj>();
		map2 = new HashMap<Long, Obj>();
		usuarios_repetidos = new ArrayList<Obj>();
		usuarios_unicos    = new ArrayList<Obj>();

		System.out.println("\r\nLendo arquivo 1");
		preencherMap(map1, file1);
		System.out.println("Arquivo 1 lido com sucesso");

		System.out.println("\r\nLendo arquivo 2");
		preencherMap(map2, file2);
		System.out.println("Arquivo 2 lido com sucesso");



		System.out.println("\r\nUsuarios no 1o arquivo: " + map1.size());
		System.out.println("Usuarios no 2o arquivo: " + map2.size());





		Iterator<Map.Entry<Long, Obj>> it = map1.entrySet().iterator();

		// procura os itens do 1o arquivo no 2o
		while (it.hasNext()) 
		{
			Map.Entry<Long, Obj> pair = it.next();
			Obj obj = pair.getValue();

			// verifica se o NIS do arquivo 1 está contido no arquivo 2
			if (map2.containsKey(obj.NIS) )
			{
				// usuario recebeu 2 meses
				usuarios_repetidos.add(obj);
				map2.remove(obj.NIS);
			}
			else
			{
				// usuario recebeu apenas 1 mes
				usuarios_unicos.add(obj);
			}

			// remove do MAP para economizar memoria (e tempo)
			it.remove();
		}


		//os usuarios que sobraram no map2, sao unicos tbm
		it = map2.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry<Long, Obj> pair = it.next();
			Obj obj = pair.getValue();

			usuarios_unicos.add(obj);

			it.remove();
		}


		System.out.println("\r\nUsuarios repetidos: " + usuarios_repetidos.size());

		System.out.println("\r\nUsuarios unicos: " + usuarios_unicos.size());



	}

	private static void preencherMap(Map<Long, Obj> map, String file_name)
	{
		try  
		{  
			File f = new File(file_name);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine(); //ignora o cabecaalho
			int i = 0;
			
			while( (line = br.readLine()) != null)
			{  
				line = line.replace("\"", "");
				String[] campos = line.split(";");

				Obj temp = new Obj();

				// COMENTADO PARA ECONOMIZAR MEMORIA ! ! !

				//temp.Mes_referencia = 	campos[0];
				//temp.Mes_competencia = 	campos[1];
				//temp.UF = 				campos[2];
				//temp.Codigo_municipio = campos[3];
				//temp.Nome_muninipio = 	campos[4];
				temp.NIS = 				Long.parseLong(campos[5]);
				//temp.Nome = 			campos[6];
				//temp.Valor = 			campos[7];

				map.put(temp.NIS, temp);

				// PRA TESTES
				i++;/*
				if (i == 13)
					break; // */
			}

			fr.close();
		}
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}
	}

}





    




// set "path=%path%;C:\Program Files\Java\jdk-12.0.2\bin"

// javac Obj.java Bolsa.java & java Bolsa -Xmx2048g


