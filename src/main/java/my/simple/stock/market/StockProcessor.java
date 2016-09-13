package my.simple.stock.market;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import my.simple.stock.market.Stock.Type;
import my.simple.stock.market.Trade.TradeSide;

/**
 * @author andrejs.burenins
 *
 */
public class StockProcessor {
	
	private static Map<Stock.Symbol, Trade> tradesLookup = new HashMap<>();
	
	/**
	 * @param stock {@link Stock} 
	 * @return Calculated Dividend Yield or {@code null} if stock's price is {@code 0}
	 */
	public static BigDecimal calculateDividendYield(final Stock stock) {
		BigDecimal dividentYield = null;
		int price = stock.getPrice();
		if (price != 0) {
			BigDecimal priceBD = new BigDecimal(price);
			if (Type.COMMON == stock.getType()) {
				int lastDividend = stock.getLastDividend();
				dividentYield = new BigDecimal(lastDividend).divide(priceBD);
			} else if (Type.PREFERRED == stock.getType()) {
				int fixedDividend = stock.getFixedDividend();
				int parValue = stock.getParValue();
				dividentYield = new BigDecimal(fixedDividend).multiply(new BigDecimal(parValue));
				dividentYield = dividentYield.divide(priceBD);
			}
		}
		
		return dividentYield;
	}
	
	/**
	 * @param stock {@link Stock} 
	 * @return Calculated P/E Ratio or {@code null} if {@link #calculateDividendYield(Stock)} returns {@code null}
	 */
	public static BigDecimal calculatePERatio(final Stock stock) {
		BigDecimal peRatio = null;
		BigDecimal divident = calculateDividendYield(stock);
		if (divident != null) {
			peRatio = new BigDecimal(stock.getPrice()).divide(divident);
		}
		return peRatio;
	}
	
	public static void recordTrade(int quantity, TradeSide side, int price) {
		
	}
	
}
