package my.simple.stock.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import my.simple.stock.market.Stock.Symbol;
import my.simple.stock.market.Stock.Type;

/**
 * @author andrejs.burenins
 *
 */
public class Application {
	
	private static List<Stock> stocks = new ArrayList<>();
	static {
		Stock tea = new Stock(Symbol.TEA, Type.COMMON);
		tea.setLastDividend(0);
		tea.setParValue(100);
		
		Stock pop = new Stock(Symbol.POP, Type.COMMON);
		pop.setLastDividend(8);
		pop.setParValue(100);

		
		Stock ale = new Stock(Symbol.ALE, Type.COMMON);
		ale.setLastDividend(23);
		ale.setParValue(60);
		
		Stock gin = new Stock(Symbol.POP, Type.PREFERRED);
		gin.setLastDividend(8);
		gin.setFixedDividend(2);
		gin.setParValue(100);
		
		Stock joe = new Stock(Symbol.JOE, Type.COMMON);
		joe.setLastDividend(13);
		joe.setParValue(250);
		
		stocks.add(tea);
		stocks.add(pop);
		stocks.add(ale);
		stocks.add(gin);
		stocks.add(joe);
		

		
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)){
			for (Stock stock : stocks) {
				System.out.format("Enter the price for %s stock (in pennies) :\t", stock.getType());
				int price = scanner.nextInt();
				stock.setPrice(price);
			}
		}
		
	}

}
