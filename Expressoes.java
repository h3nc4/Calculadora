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

package calculadora;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Calcula expressoes matematicas
 * 
 * @author Henrique Almeida
 */
public class Expressoes {

	/**
	 * Calcula uma expressao matematica em notacao polonesa
	 * 
	 * @param in Expressao em notacao polonesa
	 * @return Resultado da expressao
	 * @throws Exception Erro na pilha
	 */
	private double calculaPolones(String[] in) throws Exception {
		Pilha<Double> p = new Pilha<Double>();
		for (String strIn : in)
			if (strIn.equals("+"))
				p.empilhar(p.desempilhar() + p.desempilhar()); // desempilha os dois ultimos itens e empilha
																// a soma
			else if (strIn.equals("-"))
				p.empilhar(-p.desempilhar() + p.desempilhar()); // desempilha os dois ultimos itens e
																// empilha a subtracao
			else if (strIn.equals("*"))
				p.empilhar(p.desempilhar() * p.desempilhar()); // desempilha os dois ultimos itens e empilha
																// a multiplicacao
			else if (strIn.equals("/")) {
				double div = p.desempilhar();
				p.empilhar(p.desempilhar() / div); // desempilha os dois ultimos itens e
													// empilha a divisao
			} else if (strIn.equals("^")) {
				double pow = p.desempilhar();
				p.empilhar(Math.pow(p.desempilhar(), pow)); // desempilha os dois ultimos itens e empilha a
															// potencia
			} else
				p.empilhar(Double.parseDouble(strIn)); // empilha o numero
		return p.desempilhar();
	}

	/**
	 * Retorna o peso de um operador
	 * 
	 * @param i Operador
	 * @return Peso do operador
	 */
	private int peso(char i) {
		return (i == '(') ? 0 : (i == '+' || i == '-') ? 1 : (i == '*' || i == '/') ? 2 : 3;
	}

	/**
	 * Converte uma expressao matematica para notacao polonesa
	 * 
	 * @param in Expressao matematica
	 * @return Expressao em notacao polonesa
	 * @throws Exception Erro na pilha
	 */
	public String paraPolones(String[] in) throws Exception {
		StringBuilder saida = new StringBuilder();
		Pilha<String> p = new Pilha<String>();
		for (String i : in) // para cada item da expressao
			if (i.equals("(")) // se for um parentese aberto, empilha
				p.empilhar(i);
			else if (i.equals(")")) { // se for um parentese fechado, desempilha ate encontrar o parentese aberto
				while (!p.topo().equals("("))
					saida.append(p.desempilhar()).append(" ");
				p.desempilhar();
			} else if (i.matches("[0-9]")) // se for um numero, adiciona a saida
				saida.append(i + " ");
			else { // se for um operador, desempilha ate encontrar um operador de menor peso
				while (!p.estaVazia() && peso(p.topo().charAt(0)) >= peso(i.charAt(0)))
					saida.append(p.desempilhar()).append(" ");
				p.empilhar(i); // empilha o operador
			}
		while (!p.estaVazia()) // desempilha o restante da pilha
			saida.append(p.desempilhar()).append(" ");
		return new String(saida); // retorna a saida
	}

	/**
	 * Verifica se a expressao esta bem formada (parenteses abertos e fechados
	 * corretamente)
	 * 
	 * @param str Expressao a ser verificada
	 * @return true se a expressao estiver bem formada, false caso contrario
	 */
	private boolean bemFormada(String str) {
		Pilha<Character> p = new Pilha<Character>();
		for (char c : str.toCharArray())
			if (c == '(') // Sendo parenteses, empilha
				p.empilhar(c);
			else if (c == ')') { // Sendo parenteses, desempilha
				try {
					p.desempilhar(); // Desempilha o ultimo parenteses
				} catch (Exception e) { // Se a pilha estiver vazia, retorna falso
					return false;
				}
			}
		return p.estaVazia(); // Se a pilha estiver vazia, a expressao esta bem formada
	}

	/**
	 * Calcula uma expressao matematica
	 * 
	 * @param in Expressao matematica
	 * @return Resultado da expressao
	 * @throws Exception Erro na pilha
	 */
	public double resultado(String in) throws Exception {
		if (!bemFormada(in)) // Verifica se a expressao esta bem formada
			throw new Exception(" Expressao mal formada");
		return calculaPolones( //
				paraPolones( //
						in.replaceAll("[^\\d\\-+/*()^]", "") // Mantem apenas os numeros e operadores
								.split("(?=[-+*/=()^])|(?<=[-+*/=()^])") // Separa os numeros e operadores
																			// mantendo-os em Strings separadas
				).split(" ") // Separa os numeros e operadores
		);
	}

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Expressoes calc = new Expressoes();
		while (true)
			try {
				System.out.println(calc.resultado(input.readLine()));
			} catch (Exception e) {
				System.out.print(" Expressao invalida: ");
				System.out.println(e.getMessage());
			}
	}

}
