package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import estoque.Estoque;
import estoque.NaoPermitidoSaldoNegativoException;
import estoque.Notificador;
import estoque.Produto;

public class EstoqueTest {

	private Estoque estoque;
	private Produto iPhone;
	private Produto galaxyS2;
	private Notificador notificador;
	
	@Before
	public void setUp() {
		estoque = new Estoque();
		iPhone = new Produto("iPhone");
		galaxyS2 = new Produto("Galaxy S2");
		
		notificador = mock(Notificador.class);
		estoque.setNotificador(notificador);
	}
	
	@Test
	public void deveAtualizarSaldoAoAdicionar() {
		estoque.adicionar(5, iPhone);
		estoque.adicionar(6, iPhone);
		estoque.adicionar(10, galaxyS2);
		assertEquals(11, estoque.getSaldo(iPhone).intValue());
		assertEquals(10, estoque.getSaldo(galaxyS2).intValue());
	}
	
	@Test
	public void deveAtualizarSaldoAoRemocao() throws NaoPermitidoSaldoNegativoException {
		estoque.adicionar(10, iPhone);
		estoque.remover(5, iPhone);
		
		assertEquals(5, estoque.getSaldo(iPhone).intValue());
	}
	
	@Test(expected = NaoPermitidoSaldoNegativoException.class)
	public void deveBloquearSaldoNegativo() throws NaoPermitidoSaldoNegativoException {
		estoque.adicionar(10, iPhone);
		estoque.remover(15, iPhone);
	}
	
	@Test
	public void deveNotificarQuandoSaldoZerado() throws NaoPermitidoSaldoNegativoException {
		estoque.adicionar(10, iPhone);
		estoque.remover(10, iPhone);
		
		verify(notificador).notificarSaldoZerado(iPhone);
	}
	
	@Test
	public void naoDeveNotificarQuandoSaldoDiferenteDeZero() throws NaoPermitidoSaldoNegativoException {
		estoque.adicionar(10, iPhone);
		estoque.remover(5, iPhone);
		
		verify(notificador, never()).notificarSaldoZerado(iPhone);
	}
}
