package my.simple.stock.market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.simple.stock.market.Stock.Type;
import my.simple.stock.market.Trade.TradeSide;

/**
 * https://technologyconversations.com/2013/12/20/test-driven-development-tdd-example-walkthrough/
 * https://github.com/adosaiguas/sssmarket
 * @author andrejs.burenins
 *
 */
public class StockServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
	
	private static Random random;
	private static List<Stock> stocks;
	private IStockService stockService;
	
	@BeforeClass
    public static void setUp() {
		random = new Random();
		
		stocks = new ArrayList<Stock>(){
			private static final long serialVersionUID = 1L;
		{
			add(Stock.ALE);
			add(Stock.GIN);
			add(Stock.JOE);
			add(Stock.POP);
			add(Stock.TEA);
		}};
	}
	
	@Before
    public void before() {
		stockService = new StockService();
	}
	
	@Test
	public final void whenPriceIsNullOr0ThenCalculateDividendYieldReturnValueIsNull() {
		
		BigDecimal dividendYield = stockService.calculateDividendYield(Stock.TEA, null);
		Assert.assertNull(dividendYield);
		
		dividendYield = stockService.calculateDividendYield(Stock.TEA, BigDecimal.ZERO);
		Assert.assertNull(dividendYield);
	}
	
	@Test(expected = NullPointerException.class)
	public final void whenStockIsNullThenCalculateDividendYieldThrowsException() {
		stockService.calculateDividendYield(null, new BigDecimal(15));
	}
	
	@Test
	public final void whenStockIsCommonThenCalculateDividendYieldReturnsLastDividendDividedOnThePrice() {
		Stock stock = Stock.ALE;
		
		BigDecimal price = new BigDecimal(120);
		BigDecimal lastDividend = stock.getLastDividend();
		
		Assert.assertEquals(divide(lastDividend, price), stockService.calculateDividendYield(stock, price));
	}
	
	@Test
	public final void whenStockIsPreferredThenCalculateDividendYieldReturnsFixedDividentMultipledParValueDividedOnThePrice() {
		Stock stock = Stock.GIN;
		Assert.assertEquals(Type.PREFERRED, stock.getType());
		
		BigDecimal price = new BigDecimal(120);
		BigDecimal fixedDividend = stock.getFixedDividend();
		BigDecimal parValue = stock.getParValue();
		BigDecimal expected = divide(fixedDividend.multiply(parValue), price);
		
		Assert.assertEquals(expected, stockService.calculateDividendYield(stock, price));
	}
	
	@Test
	public final void whenDividendIsNullOr0ThenCalculatePERatioReturnValueIsNull() {
		BigDecimal peRatio = stockService.calculatePERatio(Stock.TEA, null);
		Assert.assertNull(peRatio);
		
		peRatio = stockService.calculatePERatio(Stock.TEA, BigDecimal.ZERO);
		Assert.assertNull(peRatio);
	}
	
	@Test(expected = NullPointerException.class)
	public final void whenStockIsNullThenCalculatePERatioThrowsException() {
		stockService.calculatePERatio(null, new BigDecimal(15));
	}
	
	
	@Test
	public final void whenArgumentsAreValideThenCalculatePERatioReturnsPriceDividedOnDividend() {
		Stock stock = Stock.GIN;
		BigDecimal price = new BigDecimal(120);
		BigDecimal dividend = stockService.calculateDividendYield(stock, price);
		Assert.assertEquals(divide(price, dividend), stockService.calculatePERatio(stock, price));
	}
	
	@Test
	public final void whenNoTradesForLast15MinutesExistThenCalculateVolumeWeightedPriceReturnsNull() {
		Assert.assertNull(stockService.calculateVolumeWeightedPrice(Stock.TEA));
	}
	
	@Test
	public final void executeAndCheckRecordedTrades() {
		// trade 10 times for each stock
		int n = 10;
		stocks.forEach(stock -> {
			for (int i = 0; i < n; i++) {
				trade(stock);
			}
		});
		
		stocks.forEach(stock -> {
			Assert.assertEquals(n, stockService.findTrades(stock).size());
		});
	}
	
	@Test
	public final void executeTradesThenCalculateVolumeWeightedPrice() {
		// trade 10 times for each stock
		int n = 10;
		stocks.forEach(stock -> {
			for (int i = 0; i < n; i++) {
				trade(stock);
			}
			BigDecimal calculated = stockService.calculateVolumeWeightedPrice(stock);
			Assert.assertNotNull(calculated);
		});
		
	}
	
	private void trade(final Stock stock) {
		int quantity = random.nextInt(10) + 1; 															// from 1 to 10 (inclusive)
		TradeSide side = random.nextBoolean() ? TradeSide.BUY : TradeSide.SELL;
		BigDecimal price = new BigDecimal((random.nextDouble() + 1)).setScale(2, RoundingMode.HALF_UP); // from 1.00 to 1.99 (inclusive)
		stockService.executeTrade(stock, quantity, side, price);
	}

	private BigDecimal divide(BigDecimal numerator, BigDecimal divisor ) {
		return numerator.divide(divisor, 2, RoundingMode.HALF_EVEN);
	}
}
