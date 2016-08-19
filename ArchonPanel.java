package archon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ArchonPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * tabs:
	 * factions
	 * archons
	 * contracts
	 * battle logs
	 * 
	 */
	FactionPanel factionpanel=new FactionPanel();
	ChickPanel chickpanel=new ChickPanel();
	ContractPanel contractpanel=new ContractPanel();
	BattleLogPanel battlelogpanel=new BattleLogPanel();
	LogPanel logpanel=new LogPanel();
	World world=new World();
	
	public ArchonPanel (){
		//create a prompt for player name
		setLayout(new BorderLayout());
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Factions", factionpanel);
		jtp.addTab("Archons", chickpanel);
		jtp.addTab("Contracts", contractpanel);
		jtp.addTab("Battle Logs", battlelogpanel);
		
		add(jtp, BorderLayout.CENTER);
		add(logpanel, BorderLayout.EAST);
	}
	public void initWorld(){
		world=new World();
		initFactionPanel();
	}
	public void initFactionPanel(){
		factionpanel.updateFactionJList(world);
	}
	public void initChickPanel(){
		chickpanel.updateArchonJList();
		chickpanel.updatePFSummary();
	}
	
	private class FactionPanel extends JPanel implements ListSelectionListener {
		
		JList factionlist=new JList();
		JList citylist=new JList();
		Faction selectedFaction=null;
		City selectedCity=null;
//		ArchonPanel ap;
		
		public FactionPanel(){
			JPanel jp=new JPanel();
			jp.setLayout(new GridLayout(1,0));
			
			setLayout(new BorderLayout());
			
			factionlist.addListSelectionListener(this);
			JScrollPane jsp1=new JScrollPane(factionlist);
			jsp1.setPreferredSize(new Dimension(200,400));
			jp.add(jsp1);
			
			citylist.addListSelectionListener(this);
			JScrollPane jsp2=new JScrollPane(citylist);
			jsp2.setPreferredSize(new Dimension(200,400));			
			jp.add(jsp2);
			
			add(jp,BorderLayout.CENTER);
			
		}

		public void updateFactionJList(World w){
			factionlist.setListData(w.getFactionString());
			repaint();
		}
		public void updateCityJList(){
			if (selectedFaction!=null){
				citylist.setListData(selectedFaction.cities.toArray());
			}
			repaint();
		}
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource()==factionlist){
				if (factionlist.getSelectedIndex()<world.factions.size() && factionlist.getSelectedIndex()>-1)
					selectedFaction=world.factions.get(factionlist.getSelectedIndex());
				if (selectedFaction!=null){
					logpanel.updateDesc(selectedFaction.getSummary());
					updateCityJList();
				}
			}
			if (e.getSource()==citylist && selectedFaction !=null){
				if (citylist.getSelectedIndex()<selectedFaction.cities.size() && citylist.getSelectedIndex()>-1)
					selectedCity=selectedFaction.cities.get(citylist.getSelectedIndex());
				if (selectedCity!=null){
					logpanel.updateDesc(selectedCity.getSummary());
				}
			}
		}
		
	}
	private class ChickPanel extends JPanel implements ActionListener, ListSelectionListener {
		JList archonlist;
		Archon selectedArchon;
		JButton hireArchon, praiseArchon, rewardArchon, sellArchon;
		JTextArea pfsummary=new JTextArea("");
		
		ChickPanel(){
			setLayout(new BorderLayout());
			JScrollPane jspf=new JScrollPane(pfsummary);
			pfsummary.setEditable(false);
			jspf.setPreferredSize(new Dimension(300,100));
			add(jspf, BorderLayout.NORTH);
			//initialize list
			archonlist=new JList();
			archonlist.addListSelectionListener(this);
			JScrollPane jsp=new JScrollPane(archonlist);
			jsp.setPreferredSize(new Dimension(100,400));
			add (jsp, BorderLayout.CENTER);
			
			hireArchon=new JButton("Hire Archon");
			hireArchon.addActionListener(this);
			praiseArchon=new JButton("Praise Archon");
			praiseArchon.addActionListener(this);
			rewardArchon=new JButton("Reward Archon");
			rewardArchon.addActionListener(this);
			sellArchon=new JButton("Sell Archon");
			sellArchon.addActionListener(this);
			JPanel buttonpane=new JPanel();
			buttonpane.add(hireArchon);
			buttonpane.add(praiseArchon);
			buttonpane.add(rewardArchon);
			buttonpane.add(sellArchon);
			add(buttonpane, BorderLayout.SOUTH);
		}
		public void updateArchonJList(){
			archonlist.setListData(world.player.archons.toArray());
			repaint();
		}
		/**
		 * Update player faction summmary
		 */
		public void updatePFSummary(){
			String s="";
			ArrayList<String>ss=world.player.getSummary();
			for (String st:ss){
				s+=st+"\n";
			}
			pfsummary.setText(s);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==hireArchon){
				world.addPlayerArchon();
			} else if (e.getSource()==praiseArchon){
				if (selectedArchon !=null){
					selectedArchon.gainEXP(1);
					logpanel.updateDesc(selectedArchon.getSummary());
				}
			} else if (e.getSource()==rewardArchon){
				world.addPlayerArchon();
				
			} else if (e.getSource()==sellArchon){
				world.addPlayerArchon();
			}
			updateArchonJList();
			updatePFSummary();
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
//			selectedArchon=world.player.archons.get(archonlist.getSelectedIndex());
			//settext in main panel
			if (e.getSource()==archonlist){
				if (archonlist.getSelectedIndex()<world.player.archons.size() && archonlist.getSelectedIndex()>-1)
					selectedArchon=world.player.archons.get(archonlist.getSelectedIndex());
				if (selectedArchon!=null){
					logpanel.updateDesc(selectedArchon.getSummary());
				}
			}
		}
	}
	private class ContractPanel extends JPanel implements ActionListener{
		Timer t=new Timer(1000,this);
		JList contractlist;
		JButton toggleTimer;
		
		
		ContractPanel(){
			t.setInitialDelay(0);
			toggleTimer=new JButton("Timer");
			toggleTimer.addActionListener(this);
			add (toggleTimer);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==toggleTimer){
				if (t.isRunning()){
					t.stop();
				} else {
					t.start();
				}
				
			}
			if (e.getSource()==t){
				System.out.println("555");
			}
			
		}
	}
	private class BattleLogPanel extends JPanel {
		
		JList logs=new JList();
		JButton delete;
		
		BattleLogPanel(){
			setLayout(new BorderLayout());
			
			logs=new JList();
			JScrollPane jsp=new JScrollPane(logs);
			jsp.setPreferredSize(new Dimension(100,400));
			add (jsp, BorderLayout.CENTER);
			
			delete=new JButton("Delete Selected");
			add(delete, BorderLayout.SOUTH);
		}
	}
	private class LogPanel extends JPanel {
		JTextArea desc=new JTextArea("");
		JTextArea clog=new JTextArea("");
		
		public LogPanel (){
			setLayout(new GridLayout(0,1));
			desc.setEditable(false);
			clog.setEditable(false);
			JScrollPane jsp1=new JScrollPane(desc);
			JScrollPane jsp2=new JScrollPane(clog);
			jsp1.setPreferredSize(new Dimension(300,300));
			jsp2.setPreferredSize(new Dimension(300,300));
			add (jsp1);
			add (jsp2);
		}
		public void updateDesc(String s){
			desc.setText(s);
		}
		public void updateDesc(ArrayList<String> ss){
			String s="";
			for (int i=0;i<ss.size();i++){
				s+=ss.get(i)+"\n";
			}
			desc.setText(s);
		}
		public void updateCLog(String s){
			clog.setText(s);
		}
	}
}
