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
 * Classe Pilha
 * 
 * @author Henrique Almeida
 */
class Pilha<e> {

	private Celula<e> topo;

	/**
	 * Construtor da classe Pilha
	 */
	Pilha() {
		topo = new Celula<e>(null, null); // topo aponta para null
	}

	/**
	 * Verifica se a pilha está vazia
	 * 
	 * @return true se a pilha estiver vazia, false caso contrario
	 */
	public boolean está_vazia() {
		return topo.item == null;
	}

	/**
	 * Insere um item na pilha
	 * 
	 * @param novoItem Item a ser inserido
	 */
	public void empilhar(e novoItem) {
		topo = new Celula<e>(novoItem, topo); // novo item aponta para o topo anterior e se torna o novo topo
	}

	/**
	 * Remove um item da pilha
	 * 
	 * @return Item removido
	 * @throws IllegalArgumentException Pilha vazia
	 */
	public e desempilhar() throws IllegalArgumentException {
		if (está_vazia())
			throw new IllegalArgumentException("Pilha vazia");
		e retorno = topo.item; // guarda o item do topo
		topo = topo.anterior; // atualiza o topo
		return retorno;
	}

	/**
	 * Retorna o item do topo da pilha
	 * 
	 * @return Item do topo da pilha
	 * @throws IllegalArgumentException Pilha vazia
	 */
	public e topo() throws IllegalArgumentException {
		if (está_vazia())
			throw new IllegalArgumentException("Pilha vazia");
		return topo.item;
	}

}
