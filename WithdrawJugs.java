package scripts;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class WithdrawJugs implements Activity{
	private Script script;
	public String id = "WithdrawJugs";
	public WithdrawJugs(Script script){
		this.script = script;
	}
	public String getID(){
		return id;
	}
	public void run() throws InterruptedException{
		if(!script.inventory.contains("Jug")){
			if(script.bank.withdrawAll("Jug")){
				new ConditionalSleep(5000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.inventory.contains("Jug");
					}
				}.sleep();
			}
		}
		else if(script.bank.isOpen()){
			script.bank.close();
		}
	}
	public boolean validate(){
		return script.bank.isOpen() && script.inventory.isEmpty() && script.bank.getItem("Jug").getAmount() > 50;
	}
}
