package my.simple.stock.market;

import java.math.BigDecimal;
import java.util.List;

import my.simple.stock.market.Trade.TradeSide;

/**
 * @author andrejs.burenins
 *
 */
public interface IStockService {
	
	/**
	 * @param stock {@link Stock} 
	 * @param price (in pennies)
	 * @return Calculated Dividend Yield or {@code null} if stock's price is {@code 0}
	 */
	BigDecimal calculateDividendYield(final Stock stock, BigDecimal price);
	
	/**
	 * @param stock {@link Stock} 
	 * @param price (in pennies)
	 * @return Calculated P/E Ratio or {@code null} if {@link #calculateDividendYield(Stock)} returns {@code null}
	 */
	BigDecimal calculatePERatio(final Stock stock, BigDecimal price);
	
	
	/**
	 * @param stock {@link Stock} 
	 * @return calculated Volume Weighted Stock Price based on trades in past 15 minutes
	 */
	BigDecimal calculateVolumeWeightedPrice(final Stock stock);
	
	/**
	 * @return GBCE All Share Index using the geometric mean of prices for all stocks, or {@code null} if no trade available
	 */
	BigDecimal calculateGBCEAllShareIndex();
	
	/**
	 * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price 
	 * @param stock {@link Stock} 
	 * @param quantity quantity of shares
	 * @param side buy or sell {@link TradeSide indicator} 
	 * @param price (in pennies)
	 * @return {@link Trade recorded trade}
	 */
	Trade executeTrade(final Stock stock, int quantity, TradeSide side, BigDecimal price);
	
	/**
	 * @param stock {@link Stock} 
	 * @return List of {@link Trade}s for specified {@link Stock}. Never {@code null}, empty list if there are no one
	 */
	List<Trade> findTrades(final Stock stock);
	
	
}
