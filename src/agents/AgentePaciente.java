package agents;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.MessageTemplate;

public class AgentePaciente extends Agent{
	
	private static final long serialVersionUID = -216671578848886786L;
	private AID[] AgentesMedicos;
	private String areaDeAtendimento;
	protected void setup() {

		System.out.println("Olá agente "+getAID().getName()+". Precisa de atendimento em que área?");
		Object[] args = getArguments();
		if(args != null && args.length >0) {
			areaDeAtendimento = (String) args[0];
			System.out.println("Estou procurando por atendimento em " + areaDeAtendimento);

			addBehaviour(new TickerBehaviour(this, 5000) {
				@Override
				protected void onTick() {
					System.out.println("Olá médicos, preciso de atendimento em " + areaDeAtendimento);
					DFAgentDescription template = new DFAgentDescription();
					template.setName(getAID());
					ServiceDescription sd = new ServiceDescription();
					sd.setName(getName());
					sd.setType("Atendimento Médico");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template);
						System.out.println("Os seguintes médicos estão disponveís: ");
						AgentesMedicos = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							AgentesMedicos[i] = result[i].getName();
							System.out.println(AgentesMedicos[i].getName());
						}

					} catch (FIPAException e) {
						e.printStackTrace();
						doDelete();
					}
					myAgent.addBehaviour(new Atendimento());
				}
			});
		}
		else {
			System.out.println("Não há médicos disponíveis.");
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

	private class Atendimento extends Behaviour{
		private AID melhorMedico;
		private int melhorPreco;
		private int repliesCnt = 0;
		private MessageTemplate mt;
		private int step = 0;

		@Override
		public void action() {
			switch (step) {
				case 0:
					ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
					for (int i = 0; i < AgentesMedicos.length; i++) {
						cfp.addReceiver(AgentesMedicos[i]);
					}
					cfp.setContent(areaDeAtendimento);
					cfp.setConversationId("paciente");
					cfp.setReplyWith("cfp" + System.currentTimeMillis());
					myAgent.send(cfp);
					//mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Central de atendimento médico"),
					//		MessageTemplate.MatchReplyTo(cfp.getReplyWith()));
					step = 1;
					break;
				case 1:
					ACLMessage reply = myAgent.receive(mt);
					if (reply != null) {
						if (reply.getPerformative() == ACLMessage.PROPOSE) {
							int preco = Integer.parseInt(reply.getContent());
							if (melhorMedico == null || preco < melhorPreco) {
								melhorPreco = preco;
								melhorMedico = reply.getSender();
							}
						}
						repliesCnt++;
						if (repliesCnt >= AgentesMedicos.length) {
							step = 2;
						}
					}
					else{
						block();
					}
					break;
				case 2:
					ACLMessage atendimento = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
					atendimento.addReceiver(melhorMedico);
					atendimento.setContent(areaDeAtendimento);
					atendimento.setConversationId("paciente");
					atendimento.setReplyWith("Atendimento "+System.currentTimeMillis());
					myAgent.send(atendimento);
					//mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Central de atendimento médico"),
					//		MessageTemplate.MatchReplyTo(atendimento.getReplyWith()));
					step = 3;
					break;
				case 3:
					reply = myAgent.receive(mt);
					if(reply!=null){
						if(reply.getPerformative() == ACLMessage.INFORM){
							System.out.println("Atendimento em "+areaDeAtendimento+" com doutor "+reply.getSender().getName());
							System.out.println("Preco: R$ "+melhorPreco);
							myAgent.doDelete();
						}
						else{
							System.out.println("Descuple mas não há mais atendimento disponível.");
						}
						step = 4;
					}
					else{
						block();
					}
					break;
			}
		}

		@Override
		public boolean done() {
			if(step==2 && melhorMedico == null){
				System.out.println("Descuple mas o atendimento médico em "+areaDeAtendimento+" com o doutor"+melhorMedico.getName()+" não está mais disponível.");
			}
			return ((step == 2 && melhorMedico == null) || step == 4);
		}
	}
}
