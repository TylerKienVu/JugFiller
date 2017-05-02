package scripts;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class AutoMule implements Activity{
	private Script script;
	private String id = "AutoMule";
	private java.util.List<Player> listOfPlayers;
	private String mule = "51suqahsice";
	private Player player;
	private RS2Object bank;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	public AutoMule(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void withdrawAll() throws InterruptedException{
		this.script.bank.enableMode(Bank.BankMode.WITHDRAW_NOTE);
		Script.sleep(Script.random(2000,3000));
		script.bank.withdrawAll("Jug of water");
		Script.sleep(Script.random(2000,3000));
		this.script.bank.enableMode(Bank.BankMode.WITHDRAW_ITEM);
		Script.sleep(Script.random(2000,3000));
		script.bank.close();
	}
	public void run() throws InterruptedException{
		this.bank = script.objects.closest("Bank booth");
		if(!this.script.getBank().isOpen() && bank != null && !script.inventory.contains(1938)){ //if bank not open and bank exists
			if(bank.interact("Bank")){
				new ConditionalSleep(5000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.bank.isOpen();
					}
				}.sleep();
			}
		}
		if(script.bank.open()){ 
			withdrawAll();
		}
		if(!script.bank.isOpen() && !script.trade.isCurrentlyTrading() && this.player.exists() && script.inventory.contains(1938)){ //if not trading
			if(this.player.interact("Trade with")){
				new ConditionalSleep(15000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.trade.isCurrentlyTrading();
					}
				}.sleep();
			}
		}
		Script.sleep(Script.random(2000,3000));
		if(script.trade.isCurrentlyTrading()){
			if(script.trade.isFirstInterfaceOpen()){
				if(this.script.inventory.interact("Offer-All","Jug of water")){
					new ConditionalSleep(15000){
						@Override
						public boolean condition() throws InterruptedException {
							return script.trade.getOurOffers().contains("Jug of water");
						}
					}.sleep();
				}
				if(script.trade.acceptTrade()){
					new ConditionalSleep(15000){
						@Override
						public boolean condition() throws InterruptedException {
							return script.trade.isSecondInterfaceOpen();
						}
					}.sleep();
				}
			}
			Script.sleep(Script.random(2000,3000));
			if(script.trade.isSecondInterfaceOpen() ){
				if(this.script.trade.acceptTrade()){
					new ConditionalSleep(15000){
						@Override
						public boolean condition() throws InterruptedException {
							return !script.trade.isCurrentlyTrading();
						}
					}.sleep();
				}
			}
		}
	}
	public boolean validate(){
		if(script.inventory.contains(1938) && this.player != null && this.player.exists()){
			return true;
		}
		this.listOfPlayers = script.players.getAll();
		if(this.bankarea.contains(script.myPosition()) && this.script.bank.isOpen() && this.script.bank.getAmount("Jug of water") > 1000){
			for(Player p : listOfPlayers){
				//script.log(p.getName() + " " + this.mule);
				if(p.getName().equalsIgnoreCase(this.mule)){
					this.player = p;
					script.log("Found Mule: " + p.getName());
					return true;
				}
			}
		}
		return false;
	}
}
