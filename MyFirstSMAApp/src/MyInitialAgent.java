import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class MyInitialAgent extends Agent{
	
	private static final long serialVersionUID = -216671578848886786L;
	
	class makingFriendsBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = 776821786531384442L;
		
		private int n = 0;
		
		public makingFriendsBehaviour(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			System.out.println("Inicializando a action do meu comportamento!");
			
			System.out.println("Recebendo o conteúdo enviado por um terceiro!");
			ACLMessage msg = myAgent.receive();
			if(msg != null) {
				System.out.println("Criando uma resposta!");
				ACLMessage reply = msg.createReply();
				
				if(msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println("REQUEST!");
					String content = msg.getContent();
					System.out.println(++n + " " + getLocalName() + ": recebi " + content);
					
					if(content != null) {
						System.out.println("INFORM!");
						reply.setPerformative(ACLMessage.INFORM);
						reply.setContent("Oi! Claro! Já sou seu amigo!");
						System.out.println("Enviando msg: " + reply.getContent());
					} else {
						System.out.println("REFUSE!");
						reply.setPerformative(ACLMessage.REFUSE);
						reply.setContent("No momento, estou triste e não quero ter amigo");
						System.out.println("Enviando msg: " + reply.getContent());
					}
				} else {
					System.out.println("NOT_UNDERSTOOD!");
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("Não entendi sua mensagem.");
					System.out.println("Enviando msg: " + reply.getContent());
				}
				
				System.out.println("Msg enviada!");
				send(reply);
			}  else {
				block();
			}
		}
		
	}
	
	protected void setup() {
		System.out.println("Eu suo seu primeiro agentee quero conversar com outros agentes!");
		
		System.out.println("Estou me registrando no DF!!");
		DFAgentDescription dfd = new DFAgentDescription(); 
		dfd.setName(getAID()); 
		ServiceDescription sd = new ServiceDescription(); 
		sd.setName(getName()); 
		sd.setType("MyInitialAgent"); 
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			
			System.out.println("Criando e adicionando um comportamento!");
			makingFriendsBehaviour mFB = new makingFriendsBehaviour(this);
			addBehaviour(mFB);
			
		} catch(FIPAException e) {
			e.printStackTrace();
			doDelete();
		}
	}
	
	protected void takeDown() {
		try {
			System.out.println("Até a próxima!");
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
}
