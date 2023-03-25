
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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Testes unitarios
 * 
 * @author Henrique Almeida
 */
class Tests {

	private String[] excp = { "", " ", "2+", "^2", "2/3)", "(2*3", "(2+3)*4/(5" }; // expressões inválidas

	private String[] expn = { "2", "2+2", "2-2", "2^2", "2/3", "(2*3)",
			"(2+3)*4/(5-2)", "(20-70)*160/(50-20)^2", "4/0" }; // expressões válidas

	private double[] res = { 2, 4, 0, 4, 0.6666666666666666, 6,
			6.666666666666667, -8.88888888888889, Double.POSITIVE_INFINITY }; // resultados esperados para as
																				// expressões válidas

	/**
	 * Verifica se as expressões inválidas geram exceção
	 */
	@Test
	void invalid_input() {
		for (String i : excp)
			assertThrows(Throwable.class, () -> Calculadora.resultado(i));
	}

	/**
	 * Verifica se as expressões válidas geram o resultado esperado
	 */
	@Test
	void valid_input() {
		for (int i = 0; i < expn.length; i++)
			assertTrue(res[i] == Calculadora.resultado(expn[i])); // compara o resultado com o esperado
	}

}
