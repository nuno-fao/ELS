package pt.up.fe.els2022;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		App app;
		if(args.length == 0){
			app = new App("test/pt/up/fe/els2022/configFiles/testFull.txt");
		}else{
			app = new App(args[0]);
		}
		app.run();
		

	}
}

