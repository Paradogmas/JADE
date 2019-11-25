package agents;

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
    private Hashtable catalogoMedicos;


    protected void setup() {
        catalogoMedicos = new Hashtable<String, Integer>();
        Object[] args = getArguments();
        catalogoMedicos = (Hashtable) args[0];

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("marcando-consulta");
        sd.setName("Marcando-Consulta-Medico-Paciente");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new ServerOferece());
        addBehaviour(new ServerMarca());
    }

    private class ServerOferece extends CyclicBehaviour {

        private static final long serialVersionUID = 1L;

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String especialidade = msg.getContent();
                ACLMessage reply = msg.createReply();

                Integer preco = (Integer) catalogoMedicos.get(especialidade);
                if (preco != null) {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(preco.intValue()));
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    private class ServerMarca extends CyclicBehaviour {

        private static final long serialVersionUID = 1L;

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String especialidade = msg.getContent();
                ACLMessage reply = msg.createReply();

                Integer preco = (Integer) catalogoMedicos.remove(especialidade);
                if (preco != null) {
                    reply.setPerformative(ACLMessage.INFORM);
                    System.out.println(especialidade + " sold to agent " + msg.getSender().getName());
                } else {
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    public void atualizarCatalogoMedicos(final String especialidade, final int preco) {
        addBehaviour(new OneShotBehaviour() {

            private static final long serialVersionUID = 1L;

            public void action() {
                catalogoMedicos.put(especialidade, new Integer(preco));
                System.out.println(especialidade + " inserida no catalogo Medicos. Preco de consulta = " + preco);
            }
        });
    }
    
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("Agente de " + getAID().getName() + " finalizando.");
    }
}

