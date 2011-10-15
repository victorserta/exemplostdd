import java.util.HashMap;
import java.util.Map;


public class Estoque {
	
	private Map<Produto, Integer> quantidades = new HashMap<Produto, Integer>();
	private NotificadorSetorCompra notificadorCompras;

	public void adicionar(Produto produto, int quantidadeEntrada) {
		atualizarQuantidade(produto, quantidadeEntrada);
	}
	
	public void remover(Produto produto, int quantidadeSaida) throws SaldoNaoSuficienteException {
		if (quantidadeSaida > getQuantidade(produto)) {
			throw new SaldoNaoSuficienteException();
		}
		
		atualizarQuantidade(produto, -quantidadeSaida);
		
		if (getQuantidade(produto) == 0) {
			notificadorCompras.notificar(produto);
		}
	}

	public void atualizarQuantidade(Produto produto, int quantidadeEntrada) {
		Integer quantidadeAtual = quantidades.get(produto);
		
		if (quantidadeAtual == null) {
			quantidadeAtual = 0;
		}
		
		quantidadeAtual += quantidadeEntrada;
		
		quantidades.put(produto, quantidadeAtual);
	}

	public int getQuantidade(Produto produto) {
		return quantidades.get(produto);
	}

	public NotificadorSetorCompra getNotificadorCompras() {
		return notificadorCompras;
	}

	public void setNotificadorCompras(NotificadorSetorCompra notificadorCompras) {
		this.notificadorCompras = notificadorCompras;
	}

	

}
