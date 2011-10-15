import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;


public class ControleEstoqueTest {
	
	private Estoque estoque;
	private Produto sabaoEmPo;
	private Produto ipad;
	private NotificadorSetorCompra notificadorMock;
	
	@Before
	public void setUp() {
		estoque = new Estoque();
		sabaoEmPo = new Produto("Sabão em pó");
		ipad  = new Produto("IPad");
		notificadorMock = createMock(NotificadorSetorCompra.class);
	}

	@Test
	public void testarAdicionarOutroProdutoVariasVezes() {
		estoque.adicionar(sabaoEmPo, 100);
		estoque.adicionar(ipad, 1);
		estoque.adicionar(ipad, 1);
		
		assertEquals(100, estoque.getQuantidade(sabaoEmPo));
		assertEquals(2, estoque.getQuantidade(ipad));
	}
	
	@Test
	public void testarAdicionarERemover() throws SaldoNaoSuficienteException {
		estoque.adicionar(sabaoEmPo, 100);
		estoque.remover(sabaoEmPo, 50);
		
		assertEquals(50, estoque.getQuantidade(sabaoEmPo));
	}
	
	@Test(expected = SaldoNaoSuficienteException.class)
	public void testarSaldoNegativo() throws SaldoNaoSuficienteException {
		estoque.adicionar(sabaoEmPo, 100);
		estoque.remover(sabaoEmPo, 150);
	}
	
	@Test
	public void testarNotificacaoCompra() throws SaldoNaoSuficienteException {
		notificadorMock.notificar(sabaoEmPo);
		replay(notificadorMock);
		
		estoque.setNotificadorCompras(notificadorMock);
		
		estoque.adicionar(sabaoEmPo, 100);
		estoque.remover(sabaoEmPo, 100);

		verify(notificadorMock);
	}

}
