import java.util.ArrayList;
import java.util.Arrays;

public class EncontrarPotenciaDeTresImplementacion implements EncontrarPotenciaDeTres {

	@Override
	public ArrayList<Integer> obtenerPotencia(ArrayList<Integer> nroPares, ArrayList<Integer> nroImpares) {
		ArrayList<Long> resultados = new ArrayList<>();
		int[] digitos = new int[nroPares.size() + nroImpares.size()];
		int i = 0;
		for (int par : nroPares) {
			digitos[i++] = par;
		}
		for (int impar : nroImpares) {
			digitos[i++] = impar;
		}
		int[] contador = new int[10];
		Arrays.fill(contador, 0);
		int maximaCantidadDeCifrasPorTipo = Math.min(nroPares.size() * 2, nroImpares.size() * 2);
		long solucionParcial = 0;
		int cantidadPares = 0;
		int cantidadImpares = 0;
		obtenerCombinaciones(
				digitos, solucionParcial, cantidadPares, cantidadImpares,
				contador, maximaCantidadDeCifrasPorTipo, maximaCantidadDeCifrasPorTipo,
				resultados
		);
		ArrayList<Integer> resultadoFinal = new ArrayList<>();
		for(Long resultado: resultados){
			resultadoFinal.add(resultado.intValue());
		}
		return resultadoFinal;
	}

	private static void obtenerCombinaciones(
			int[] digitos, long solucionParcial, int cantidadPares, int cantidadImpares,
			int[] contador, int maximosParesPosibles, int maximosImparesPosibles,
			ArrayList<Long> resultados
	) {
		if(solucionParcial == 14348907){
			System.out.println("Yes!");
		}
		if (cantidadPares == cantidadImpares && esPotenciaDeTres(solucionParcial)) {
			resultados.add(solucionParcial);
		}
		for (int digito : digitos) {
			boolean esPar = digito % 2 == 0;
			if(solucionParcial == 0 && digito == 0){
				return;
			}
			if (contador[digito] < 2 && esPar && maximosParesPosibles > 0) {
				solucionParcial = solucionParcial * 10 + digito;
				contador[digito]++;
				cantidadPares++;
				maximosParesPosibles--;
				obtenerCombinaciones(
						digitos, solucionParcial, cantidadPares, cantidadImpares,
						contador, maximosParesPosibles, maximosImparesPosibles,
						resultados
				);

				solucionParcial = (solucionParcial - digito) / 10;
				contador[digito]--;
				cantidadPares--;
				maximosParesPosibles++;
			}
			else if (contador[digito] < 2 && !esPar && maximosImparesPosibles > 0) {
				solucionParcial = solucionParcial * 10 + digito;
				contador[digito]++;
				cantidadImpares++;
				maximosImparesPosibles--;
				obtenerCombinaciones(
						digitos, solucionParcial, cantidadPares, cantidadImpares,
						contador, maximosParesPosibles, maximosImparesPosibles,
						resultados
				);
				solucionParcial = (solucionParcial - digito) / 10;
				contador[digito]--;
				cantidadImpares--;
				maximosImparesPosibles++;
			}
		}
	}

	private static boolean esPotenciaDeTres(long numero) {
		double logaritmo = Math.log(numero) / Math.log(3);
		return logaritmo == (int) logaritmo;
	}
}
