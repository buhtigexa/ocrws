package server;

public class OCRText {

	public String docName;
	public String event;
	public String id;
	public String image;
	
	public OCRText(String docName,String image,String event,String id) {
		// TODO Auto-generated constructor stub
		this.event=event;
		this.id = id;
		this.image=image;
		this.docName=docName;
		
		
	}
	public void clean(){
		
		try{
			
		while (event.endsWith("+")|event.endsWith(" ")){
			event = event.substring(0, event.length()-1);
		}
		
		
			event=event.replace("\\r\\n"," ");
			event = event.replace("\\","");
			event = event.replaceAll("[\\[\\]?¿{}!¡:\\.,-]" ," ");
			event = event.replaceAll("\"", "");
			//event = event.substring(1, event.length()-1);
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		
		
	}
	
	public String toString(){
		
		String print="<"+docName+">"+"\n" +
		"  <id> " + this.id + " </id>\n"+
		"  <image> " + this.image + " </image>\n"+
		"  <event> " + this.event + " </event>\n"+
		"</"+docName+">\n";
		return print;
		
	}

public void trim(){
		
		
		while (event.startsWith(" ")){
			event=event.substring(1, event.length());
		}
		
		while(event.endsWith(" ")){
		
			event=event.substring(0, event.length()-1);
		}
	
		event = event.replace(" ","+");
		event = event.replace("++","+");

		
		
		while (event.startsWith("+")){
			event=event.substring(1, event.length());
		}
		
		while(event.endsWith("+")){
		
			event=event.substring(0, event.length()-1);
		}
	}
}
