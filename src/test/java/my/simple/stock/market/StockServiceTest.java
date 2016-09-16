package my.simple.stock.market;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author andrejs.burenins
 */
public class StockServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
	
	private static Random random;
	private IStockService stockService;
	
	@BeforeClass
    public static void setUp() {
		random = new Random();
	}
	
	@Before
    public void before() {
		stockService = new StockService();
	}
	
	@Test
	public final void whenPriceIsNullOr0ThenCalculateDividendYieldReturnValueIsNull() {
		
		BigDecimal dividendYield = stockService.calculateDividendYield(StockService.TEA, null);
		Assert.assertNull(dividendYield);
		
		dividendYield = stockService.calculateDividendYield(StockService.TEA, BigDecimal.ZERO);
		Assert.assertNull(dividendYield);
	}
	
	@Test(expected = NullPointerException.class)
	public final void whenStockIsNullThenCalculateDividendYieldThrowsException() {
		stockService.calculateDividendYield(null, new BigDecimal(15));
	}
	
	@Test
	public final void whenStockIsCommonThenCalculateDividendYieldReturnsLastDividendDividedOnThePrice() {
		Stock stock = StockService.ALE;
		
		BigDecimal price = new BigDecimal(120);
		BigDecimal lastDividend = stock.getLastDividend().setScale(5, RoundingMode.HALF_EVEN);
		
		Assert.assertEquals(divide(lastDividend, price), stockService.calculateDividendYield(stock, price));
	}
	
	@Test
	public final void whenStockIsPreferredThenCalculateDividendYieldReturnsFixedDividentMultipledParValueDividedOnThePrice() {
		Stock stock = StockService.GIN;
		Assert.assertEquals(Type.PREFERRED, stock.getType());
		
		BigDecimal price = new BigDecimal(120);
		BigDecimal fixedDividend = stock.getFixedDividend();
		BigDecimal parValue = stock.getParValue();
		BigDecimal expected = divide(fixedDividend.multiply(parValue), price);
		
		Assert.assertEquals(expected, stockService.calculateDividendYield(stock, price));
	}
	
	@Test
	public final void whenDividendIsNullOr0ThenCalculatePERatioReturnValueIsNull() {
		BigDecimal peRatio = stockService.calculatePERatio(StockService.TEA, null);
		Assert.assertNull(peRatio);
		
		peRatio = stockService.calculatePERatio(StockService.TEA, BigDecimal.ZERO);
		Assert.assertNull(peRatio);
	}
	
	@Test(expected = NullPointerException.class)
	public final void whenStockIsNullThenCalculatePERatioThrowsException() {
		stockService.calculatePERatio(null, new BigDecimal(15));
	}
	
	
	@Test
	public final void whenArgumentsAreValideThenCalculatePERatioReturnsPriceDividedOnDividend() {
		Stock stock = StockService.GIN;
		BigDecimal price = new BigDecimal(120);
		BigDecimal dividend = stockService.calculateDividendYield(stock, price);
		Assert.assertEquals(divide(price, dividend), stockService.calculatePERatio(stock, price));
	}
	
	@Test
	public final void whenNoTradesForLast15MinutesExistThenCalculateVolumeWeightedPriceReturnsNull() {
		Assert.assertNull(stockService.calculateVolumeWeightedPrice(StockService.TEA));
	}
	
	@Test
	public final void executeAndCheckRecordedTrades() {
		// trade 10 times for each stock
		int n = 10;
		
		Collection<Stock> stocks = stockService.getPredefinedSocks().values();
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
		stockService.getPredefinedSocks().values().forEach(stock -> {
			for (int i = 0; i < n; i++) {
				trade(stock);
			}
			BigDecimal calculated = stockService.calculateVolumeWeightedPrice(stock);
			Assert.assertNotNull(calculated);
		});
	}
	
	@Test
	public final void validateCalculationOfGBCEAllShareIndex() {
		
		Stock stock = StockService.ALE;
		stockService.executeTrade(stock, 5, TradeSide.BUY, new BigDecimal(1));
		stockService.executeTrade(stock, 5, TradeSide.BUY, new BigDecimal(3));
		stockService.executeTrade(stock, 5, TradeSide.BUY, new BigDecimal(9));
		
		BigDecimal expected = new BigDecimal(3).setScale(5, RoundingMode.HALF_EVEN);  	// 3-th root of (1*3*9 == 27) is 3
		BigDecimal calculated = stockService.calculateGBCEAllShareIndex();
		assertEquals(expected, calculated);
		
		// add GIN stock trade (price == 5)
		stock = StockService.GIN;
		stockService.executeTrade(stock, 5, TradeSide.BUY, new BigDecimal(5));
		
		expected = new BigDecimal(3.40866).setScale(5, RoundingMode.HALF_EVEN);  		// 4-th root of (1*3*9*5 == 135) is ~ 3.40866
		calculated = stockService.calculateGBCEAllShareIndex();
		assertEquals(expected, calculated);
		
		// add JOE stock trade (price == 1.12345)
		stock = StockService.JOE;
		stockService.executeTrade(stock, 5, TradeSide.BUY, new BigDecimal(1.12345));
		
		expected = new BigDecimal(2.73009).setScale(5, RoundingMode.HALF_EVEN);  		// 5-th root of (1*3*9*5*1.12345 == 151.66575) is ~ 2.73009
		calculated = stockService.calculateGBCEAllShareIndex();
		assertEquals(expected, calculated);
	}
	
	private void trade(final Stock stock) {
		int quantity = random.nextInt(10) + 1; 															// from 1 to 10 (inclusive)
		TradeSide side = random.nextBoolean() ? TradeSide.BUY : TradeSide.SELL;
		BigDecimal price = new BigDecimal((random.nextDouble() + 1)).setScale(2, RoundingMode.HALF_UP); // from 1.00 to 1.99 (inclusive)
		stockService.executeTrade(stock, quantity, side, price);
	}

	private BigDecimal divide(BigDecimal numerator, BigDecimal divisor ) {
		return numerator.divide(divisor, 5, RoundingMode.HALF_EVEN);
	}
}
