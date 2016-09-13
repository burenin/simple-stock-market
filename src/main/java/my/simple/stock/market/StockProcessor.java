package my.simple.stock.market;

import java.math.BigDecimal;

import my.simple.stock.market.Stock.Type;

/**
 * @author andrejs.burenins
 *
 */
public class StockProcessor {
	
	/**
	 * Calculates the dividend yield
	 * @param stock
	 * @return
	 */
	public static BigDecimal calculateDevidendYield(final Stock stock) {
		BigDecimal result = null;
		int price = stock.getPrice();
		if (price != 0) {
			BigDecimal priceBD = new BigDecimal(price);
			if (Type.COMMON == stock.getType()) {
				int lastDividend = stock.getLastDividend();
				result = new BigDecimal(lastDividend).divide(priceBD);
			} else if (Type.PREFERRED == stock.getType()) {
				int fixedDividend = stock.getFixedDividend();
				int parValue = stock.getParValue();
				result = new BigDecimal(fixedDividend).multiply(new BigDecimal(parValue));
				result = result.divide(priceBD);
			}
		}
		
		return result;
	}
	
}
