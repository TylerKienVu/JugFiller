package scripts;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class WalkToStore implements Activity{
	private Script script;
	private int amount;
	private int coins;
	public String id = "WalkToStore";
	public WalkToStore(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run(){
		if(!script.inventory.contains("Coins")){
			if(script.bank.withdrawAll("Coins")){
				new ConditionalSleep(15000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.inventory.contains("Coins");
					}
				}.sleep();
			}
		}
		if(script.bank.isOpen()){
			script.bank.close();
		}
		script.walking.webWalk(new Position(Script.random(3214,3219),Script.random(3411,3418),0));
	}
	public boolean validate(){
		if(script.bank.isOpen()){
			if(script.bank.contains("Jug")){
				this.amount = script.bank.getItem("Jug").getAmount();
			}
			else{
				this.amount = 0;
			}
			if(script.bank.contains("Coins")){
				this.coins = script.bank.getItem("Coins").getAmount();
				if(this.coins < 3000){
					script.log("Out of coins, stopping script");
					script.stop();
				}
			}
			else{
				this.coins = 0;
			}
		}
		if(script.bank.isOpen() && script.inventory.isEmpty() && this.amount <= 50 && this.coins > 10000){
			return true;
		}
		return false;
	}
}
