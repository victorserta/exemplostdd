package estoque;

import java.util.HashMap;
import java.util.Map;


public class Estoque {
	
	private Map<Produto, Integer> saldos = new HashMap<Produto, Integer>();
	private Notificador notificador;

	public Integer getSaldo(Produto produto) {
		Integer saldo = saldos.get(produto);
		
		if (saldo == null) {
			return 0;
		}
		
		return saldo;
	}

	public void adicionar(int quantidade, Produto produto) {
		atualizarQuantidade(quantidade, produto);
	}

	public void remover(int quantidade, Produto produto) throws NaoPermitidoSaldoNegativoException {
		atualizarQuantidade(-quantidade, produto);
		
		if (getSaldo(produto) < 0) {
			throw new NaoPermitidoSaldoNegativoException();
		}
		
		if (getSaldo(produto) == 0) {
			notificador.notificarSaldoZerado(produto);
		}
	}
	
	private void atualizarQuantidade(int quantidade, Produto produto) {
		Integer saldo = getSaldo(produto);
		saldo += quantidade;
		
		saldos.put(produto, saldo);
	}

	public Notificador getNotificador() {
		return notificador;
	}

	public void setNotificador(Notificador notificador) {
		this.notificador = notificador;
	}

	
}
