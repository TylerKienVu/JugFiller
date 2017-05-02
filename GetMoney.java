package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class GetMoney implements Activity{
	private Script script;
	private String id = "GetMoney";
	private String mule = "51suqahsice";
	private java.util.List<Player> listOfPlayers;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	private Player player;

	public GetMoney(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void withdrawCoin() throws InterruptedException{
		script.bank.withdraw("Coins", 1);
		Script.sleep(Script.random(2000,3000));
		script.bank.close();
	}
	public void run() throws InterruptedException{
		if(!bankarea.contains(script.myPosition())){
			script.walking.webWalk(new Position(3253,3421,0));
		}
		if(this.script.getBank().isOpen() && this.player != null && this.player.exists()){ //if bank open
			this.withdrawCoin();
		}
		if(!script.trade.isCurrentlyTrading() && this.player.exists() && script.inventory.contains("Coins")){
			if(this.player.interact("Trade with")){
				new ConditionalSleep(15000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.trade.isCurrentlyTrading();
					}
				}.sleep();
			}
		}
		if(script.trade.isCurrentlyTrading()){
			if(script.trade.isFirstInterfaceOpen()){
				if(this.script.inventory.interact("Offer","Coins")){
					new ConditionalSleep(15000){
						@Override
						public boolean condition() throws InterruptedException {
							return script.trade.didOtherAcceptTrade();
						}
					}.sleep();
				}
				if(script.trade.didOtherAcceptTrade()){
					if(script.trade.acceptTrade()){
						new ConditionalSleep(15000){
							@Override
							public boolean condition() throws InterruptedException {
								return script.trade.isSecondInterfaceOpen();
							}
						}.sleep();
					}
				}
			}
			Script.sleep(Script.random(2000,3000));
			if(script.trade.isSecondInterfaceOpen()){
				if(script.trade.acceptTrade()){
					new ConditionalSleep(15000){
						@Override
						public boolean condition() throws InterruptedException {
							return !script.trade.isCurrentlyTrading();
						}
					}.sleep();
				}
				Script.sleep(Script.random(3000,5000));
			}
		}
	}
	public boolean validate(){
		if(this.player == null){
			this.listOfPlayers = script.players.getAll();
			for(Player p : listOfPlayers){
				if(p.getName().equalsIgnoreCase(this.mule)){
					this.player = p;
					script.log("Found Mule: " + p.getName());
				}
			}
		}
		if(script.trade.isCurrentlyTrading() && (script.inventory.getAmount("Coins") == 1 || script.trade.getOurOffers().contains("Coins"))){
			return true;
		}
		else if(script.inventory.getAmount("Coins") == 1 && this.player.exists()){
			return true;
		}
		else if(this.bankarea.contains(script.myPosition()) && this.script.bank.isOpen() && this.script.bank.getAmount("Coins") <= 10000 && this.player.exists()){
			return true;
		}
		return false;
	}
}
