import java.util.ArrayList;
import java.util.Arrays;

public class EncontrarPotenciaDeTresImplementacion implements EncontrarPotenciaDeTres {

	private int maximosParesPosibles;
	private int maximosImparesPosibles;
	private int cantidadPares;
	private int cantidadImpares;
	private long solucionParcial;
	private int[] contador = new int[10];

	@Override
	public ArrayList<Integer> obtenerPotencia(
		ArrayList<Integer> pares, ArrayList<Integer> impares
		) {

		// Juntamos todas las cifras en un único vector
		int[] digitos = new int[pares.size() + impares.size()];
		int digito = 0;
		for (int par : pares) {
			digitos[digito++] = par;
		}
		for (int impar : impares) {
			digitos[digito++] = impar;
		}

		// Inicialización de variables auxiliares
		ArrayList<Long> resultados = new ArrayList<>();
		inicializarVariablesAuxiliares();

		// Llamado inicial al backtracking
		obtenerCombinaciones(digitos, resultados);

		// Formateo de salida
		ArrayList<Integer> resultadoFinal = new ArrayList<>();
		for(Long resultado: resultados){
			resultadoFinal.add(resultado.intValue());
		}

		return resultadoFinal;
	}

	private static void obtenerCombinaciones(
		int[] digitos, ArrayList<Long> resultados
		) {

		// Agregado de resultados correctos al array de salida
		if (cantidadPares == cantidadImpares && esPotenciaDeTres(solucionParcial)) {
			resultados.add(solucionParcial);
		}

		for (int digito : digitos) {
			boolean esPar = (digito % 2 == 0);
			if(solucionParcial == 0 && digito == 0){
				return;
			}
			if (contador[digito] < 2 && esPar && maximosParesPosibles > 0) {
				bajarUnNivel(esPar);

				obtenerCombinaciones(digitos, resultados);

				subirUnNivel(esPar);
			}
			else if (contador[digito] < 2 && !esPar && maximosImparesPosibles > 0) {
				bajarUnNivel(esPar);
				
				obtenerCombinaciones(digitos, resultados);
				
				subirUnNivel(esPar);
			}
		}
	}

	private static boolean esPotenciaDeTres(long numero) {
		double logaritmo = Math.log(numero) / Math.log(3);
		return logaritmo == (int) logaritmo;
	}

	private void inicializarVariablesAuxiliares(){
		
		// Inicialización de variables de poda
		Arrays.fill(contador, 0);
		maximosParesPosibles = maximosImparesPosibles = Math.min(pares.size() * 2, impares.size() * 2);

		// Inicalización de variables de backtracking
		solucionParcial = 0;
		cantidadPares = 0;
		cantidadImpares = 0;
	}

	private void bajarUnNivel(boolean esPar){
		solucionParcial = solucionParcial * 10 + digito;
		contador[digito]++;
		if(esPar){
			cantidadPares++;
			maximosParesPosibles--;
		} else {
			cantidadImpares++;
			maximosImparesPosibles--;
		}				
	}

	private void subirUnNivel(boolean esPar){
		solucionParcial = (solucionParcial - digito) / 10;
		contador[digito]--;
		if(esPar){
			cantidadPares--;
			maximosParesPosibles++;
		} else {
			cantidadImpares--;
			maximosImparesPosibles++;
		}
	}
}
