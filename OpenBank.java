package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class OpenBank implements Activity{
	private Script script;
	private String id = "OpenBank";
	private RS2Object bank;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	public OpenBank(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run() throws InterruptedException{
		this.bank = script.objects.closest("Bank booth");
		if(!this.script.getBank().isOpen() && bank != null){ //if bank not open and bank exists
			if(bank.interact("Bank")){
				new ConditionalSleep(5000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.bank.isOpen();
					}
				}.sleep();
			}
		}
		if(script.bank.isOpen() && !script.inventory.isEmpty()){
			Script.sleep(Script.random(2000,3000));
			script.bank.depositAll();
		}
	}
	public boolean validate(){
		return this.bankarea.contains(script.myPosition()) && !script.bank.isOpen();
	}
}
