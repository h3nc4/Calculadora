/** 
 * MIT License
 *
 * Copyright(c) 2023 Henrique Almeida
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

/**
 * Calcula expressões matematicas
 * 
 * @author Henrique Almeida
 */
public class Calculadora {

	/**
	 * Calcula uma expressão matemática em notacao polonesa
	 * 
	 * @param in Expressão em notacao polonesa
	 * @return Resultado da expressão
	 * @throws IllegalArgumentException Erro na pilha
	 */
	private static double calculaPolones(String[] in) throws IllegalArgumentException {
		Pilha<Double> p = new Pilha<Double>();
		for (String strIn : in)
			if (strIn.equals("+"))
				p.empilhar(p.desempilhar() + p.desempilhar()); // desempilha os dois últimos itens e empilha
																// a soma
			else if (strIn.equals("-"))
				p.empilhar(-p.desempilhar() + p.desempilhar()); // desempilha os dois últimos itens e
																// empilha a subtração
			else if (strIn.equals("*"))
				p.empilhar(p.desempilhar() * p.desempilhar()); // desempilha os dois últimos itens e empilha
																// a multiplicação
			else if (strIn.equals("/")) {
				double div = p.desempilhar();
				p.empilhar(p.desempilhar() / div); // desempilha os dois últimos itens e
													// empilha a divisão
			} else if (strIn.equals("^")) {
				double pow = p.desempilhar();
				p.empilhar(Math.pow(p.desempilhar(), pow)); // desempilha os dois últimos itens e empilha a
															// potência
			} else
				p.empilhar(Double.parseDouble(strIn)); // empilha o número
		return p.desempilhar();
	}

	/**
	 * Retorna o peso de um operador
	 * 
	 * @param i Operador
	 * @return Peso do operador
	 */
	private static int peso(char i) {
		return (i == '(') ? 0 : (i == '+' || i == '-') ? 1 : (i == '*' || i == '/') ? 2 : 3;
	}

	/**
	 * Converte uma expressão matemática para notacao polonesa
	 * 
	 * @param in Expressão matemática
	 * @return Expressão em notacao polonesa
	 * @throws IllegalArgumentException Erro na pilha
	 */
	private static String paraPolones(String[] in) throws IllegalArgumentException {
		StringBuilder saída = new StringBuilder();
		Pilha<String> p = new Pilha<String>();
		for (String i : in) // para cada item da expressão
			if (i.equals("(")) // se for um parêntese aberto, empilha
				p.empilhar(i);
			else if (i.equals(")")) { // se for um parêntese fechado, desempilha até encontrar o parêntese aberto
				while (!p.topo().equals("("))
					saída.append(p.desempilhar()).append(" ");
				p.desempilhar();
			} else if (i.matches("[0-9]")) // se for um número, adiciona a saída
				saída.append(i + " ");
			else { // se for um operador, desempilha até encontrar um operador de menor peso
				while (!p.está_vazia() && peso(p.topo().charAt(0)) >= peso(i.charAt(0)))
					saída.append(p.desempilhar()).append(" ");
				p.empilhar(i); // empilha o operador
			}
		while (!p.está_vazia()) // desempilha o restante da pilha
			saída.append(p.desempilhar()).append(" ");
		return new String(saída); // retorna a saída
	}

	/**
	 * Verifica se a expressão está bem formada (parênteses abertos e fechados
	 * corretamente)
	 * 
	 * @param str Expressão a ser verificada
	 * @return true se a expressão estiver bem formada, false caso contrário
	 */
	private static boolean bemFormada(String str) {
		Pilha<Character> p = new Pilha<Character>();
		for (char c : str.toCharArray())
			if (c == '(') // Sendo parênteses, empilha
				p.empilhar(c);
			else if (c == ')') { // Sendo parênteses, desempilha
				try {
					p.desempilhar(); // Desempilha o último parênteses
				} catch (IllegalArgumentException e) { // Se a pilha estiver vazia, retorna falso
					return false;
				}
			}
		return p.está_vazia(); // Se a pilha estiver vazia, a expressão está bem formada
	}

	/**
	 * Calcula uma expressão matemática
	 * 
	 * @param in Expressão matemática
	 * @return Resultado da expressão
	 * @throws IllegalArgumentException Erro na pilha
	 */
	static double resultado(String in) throws IllegalArgumentException {
		if (in.equals("quit"))
			System.exit(0);
		if (!bemFormada(in)) // Verifica se a expressão está bem formada
			throw new IllegalArgumentException(" Expressão mal formada");
		return calculaPolones( //
				paraPolones( //
						in.replaceAll("[^\\d\\-+/*()^]", "") // Mantem apenas os numeros e operadores
								.split("(?=[-+*/=()^])|(?<=[-+*/=()^])") // Separa os numeros e operadores
																			// mantendo-os em Strings separadas
				).split(" ") // Separa os numeros e operadores
		);
	}

	public static void main(String[] args) {
		System.out.println("Digite uma expressão matemática ou \"quit\" para sair");
		while (true)
			try {
				System.out.println(resultado(System.console().readLine()));
			} catch (IllegalArgumentException e) {
				System.out.print(" Expressão invalida: ");
				System.out.println(e.getMessage());
			}
	}

}
