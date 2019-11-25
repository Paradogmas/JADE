package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgentePaciente extends Agent {

    private static final long serialVersionUID = 1L;
    private String especialidadeMedica;
    private AID[] medicos;

    protected void setup() {
        System.out.println("Ola paciente "+getAID().getName()+".");
        
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            especialidadeMedica = (String) args[0];
            System.out.println("A especialidade requisitada e "+especialidadeMedica);

            addBehaviour(new TickerBehaviour(this, 10000) {

                private static final long serialVersionUID = 1L;

                protected void onTick() {
                    System.out.println("Procurando por medicos com "+especialidadeMedica);

                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("marcando-consulta");
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Encontrado os seguintes medicos:");
                        medicos = new AID[result.length];
                        for (int i = 0; i < result.length; ++i) {
                            medicos[i] = result[i].getName();
                            System.out.println(medicos[i].getName());
                        }
                    }
                    catch (FIPAException fe) {
                        fe.printStackTrace();
                    }

                    // Perform the request
                    myAgent.addBehaviour(new ProcurarMedico());
                }
            } );
        }
        else {
            // Make the agent terminate
            System.out.println("Nao existe medico com essa especializaçao");
            doDelete();
        }
    }

    
    private class ProcurarMedico extends Behaviour {

        private static final long serialVersionUID = 1L;
        private AID melhorMedicoPreco;
        private int menorPreco;
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;

        public void action() {
            switch (step) {
                case 0:
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

                    for (int i = 0; i < medicos.length; ++i) {
                        cfp.addReceiver(medicos[i]);
                    }
                    cfp.setContent(especialidadeMedica);
                    cfp.setConversationId("marcando-consulta");
                    cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);

                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("marcando-consulta"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;
                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            int preco = Integer.parseInt(reply.getContent());
                            if (melhorMedicoPreco == null || preco < menorPreco) {
                                menorPreco = preco;
                                melhorMedicoPreco = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= medicos.length) {
                            step = 2;
                        }
                    }
                    else {
                        block();
                    }
                    break;
                case 2:
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(melhorMedicoPreco);
                    order.setContent(especialidadeMedica);
                    order.setConversationId("marcando-consulta");
                    order.setReplyWith("order"+System.currentTimeMillis());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("marcando-consulta"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    break;
                case 3:
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("A consulta em " + especialidadeMedica + " foi marcado com "+reply.getSender().getName());
                            System.out.println("Preco = "+menorPreco);
                            myAgent.doDelete();
                        }
                        else {
                            System.out.println("Nao ha horarios disponiveis");
                        }

                        step = 4;
                    }
                    else {
                        block();
                    }
                    break;
            }
        }

        public boolean done() {

            if (step == 2 && melhorMedicoPreco == null) {
                System.out.println("Nao possuimos medicos especializados em "+especialidadeMedica+" no momento");
            }

            boolean medicoNaoDisponivel = (step == 2 && melhorMedicoPreco == null);
            boolean consultaMarcada = (step == 4);

            boolean terminou = false;
            if (medicoNaoDisponivel || consultaMarcada) {
                terminou = true;
            }
            else {
                terminou = false;
            }

            return terminou;
            //return ((step == 2 && melhorMedicoPreco == null) || step == 4);
        }
    }  // End of inner class ProcurarMedico



    protected void takeDown() {
        System.out.println("Agente de "+getAID().getName()+" finalizando.");
    }

}

