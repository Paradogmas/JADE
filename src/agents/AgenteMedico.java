package agents;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import view.cadastro_medico_screen;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

public class AgenteMedico extends Agent {
    private static final long serialVersionUID = 1L;
    private Hashtable<String,Integer> especialidades;
    private cadastro_medico_screen myGui;

    protected void setup() {
        especialidades = new Hashtable<String,Integer>();

        myGui = new cadastro_medico_screen(this);

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Atendimento-Medico");
        sd.setName("Atendimento");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new OfferRequestsServer());

        addBehaviour(new PurchaseOrdersServer());
    }

    private class OfferRequestsServer extends CyclicBehaviour {

        private static final long serialVersionUID = 1L;

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();

                Integer price = (Integer) especialidades.get(title);
                if (price != null) {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(price.intValue()));
                }
                else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }

    private class PurchaseOrdersServer extends CyclicBehaviour {

        private static final long serialVersionUID = 1L;

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // ACCEPT_PROPOSAL Message received. Process it
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();

                Integer price = (Integer) especialidades.remove(title);
                if (price != null) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println(title+" sold to agent "+msg.getSender().getName());
                }
                else {
                    // The requested book has been sold to another buyer in the meanwhile .
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }

    public void AtualizarCatalogo(final String especialidade, final int price) {
        addBehaviour(new OneShotBehaviour() {

            private static final long serialVersionUID = 1L;

            public void action() {
                especialidades.put(especialidade, new Integer(price));
                System.out.println("Atendimento em "+especialidade+" está disponível por R$"+price);
            }
        } );

    }
    public void CreateMedico(String nome){
        ContainerController cc = getContainerController();
        try {
            AgentController ac = cc.createNewAgent(nome, "agents.AgenteMedico", null);
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Até a próxima!");
    }
}
