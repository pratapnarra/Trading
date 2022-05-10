import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class PortfolioScreen extends JFrame {
private JLabel sampleField;
private String username;
public String fname;
public String lname;
	
	
	public PortfolioScreen(String uname, String f, String l) {
		this.username = uname;
		this.fname = f;
		this.lname = l;
		sampleField = new JLabel(this.fname +  "'s Portfolio");
	    add(sampleField, BorderLayout.CENTER);
	    JPanel p =new JPanel();
		p.setLayout(new GridLayout(3, 1));
		
		p.add(companyCard("Tesla", 1000, 867));
		p.add(companyCard("Meta", 450, 380));
		p.add(companyCard("Microsoft", 400, 450));
		
		add(p,BorderLayout.SOUTH);
		
	}
	
	public JPanel companyCard(String c, int buying_price, int current_price) {
		JPanel panel =new JPanel();
		JLabel company = new JLabel(c);
		JLabel boughtat = new JLabel("Bought at: "+buying_price+" $");
		JLabel current = new JLabel("Current: "+ current_price +" $");
		JLabel net = new JLabel("Net: "+ (1.0 - ((float)current_price/buying_price))*100 +" %");
		JButton sell = new JButton("SELL");
		
		panel.add(company,BorderLayout.CENTER);
		panel.add(boughtat);
		panel.add(current);
		panel.add(net);
		panel.add(sell);
		
		
	    return panel;
	}

}
