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
    private String areaDeAtendimento;
    private AID[] AgentesMedicos;

    protected void setup() {
        System.out.println("Olá agente " + getAID().getName() + ". Precisa de atendimento em que área?");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            areaDeAtendimento = (String) args[0];
            System.out.println("Estou procurando por atendimento em " + areaDeAtendimento);

            addBehaviour(new TickerBehaviour(this, 10000) {

                private static final long serialVersionUID = 1L;

                protected void onTick() {
                    System.out.println("Olá médicos, preciso de atendimento em " + areaDeAtendimento);
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("Atendimento-Medico");
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Os seguintes médicos estão disponveís: ");
                        AgentesMedicos = new AID[result.length];
                        for (int i = 0; i < result.length; ++i) {
                            AgentesMedicos[i] = result[i].getName();
                            System.out.println(AgentesMedicos[i].getName());
                        }
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }

                    myAgent.addBehaviour(new RequestPerformer());
                }
            });
        } else {
            System.out.println("Não há médicos disponíveis.");
            doDelete();
        }
    }

    private class RequestPerformer extends Behaviour {

        private static final long serialVersionUID = 1L;
        private AID melhorMedico;
        private int melhorPreco;
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;

        public void action() {
            switch (step) {
                case 0:
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < AgentesMedicos.length; ++i) {
                        cfp.addReceiver(AgentesMedicos[i]);
                    }
                    cfp.setContent(areaDeAtendimento);
                    cfp.setConversationId("paciente");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis());
                    myAgent.send(cfp);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("atendimento"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;
                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            int price = Integer.parseInt(reply.getContent());
                            if (melhorMedico == null || price < melhorPreco) {
                                melhorPreco = price;
                                melhorMedico = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= AgentesMedicos.length) {
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;
                case 2:
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(melhorMedico);
                    order.setContent(areaDeAtendimento);
                    order.setConversationId("atendimento");
                    order.setReplyWith("order" + System.currentTimeMillis());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("atendimento"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    break;
                case 3:
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("Atendimento em " + areaDeAtendimento + " com doutor " + reply.getSender().getName());
                            System.out.println("Preco: R$ " + melhorPreco);
                            myAgent.doDelete();
                        } else {
                            System.out.println("Descuple mas não há mais atendimento disponível.");
                        }

                        step = 4;
                    } else {
                        block();
                    }
                    break;
            }
        }

        public boolean done() {

            if (step == 2 && melhorMedico == null) {
                System.out.println("Descuple mas o atendimento médico em " + areaDeAtendimento + " com o doutor" + melhorMedico.getName() + " não está mais disponível.");
            }

            boolean medicoNaoDisponivel = (step == 2 && melhorMedico == null);
            boolean consultaMarcada = (step == 4);

            boolean terminou = false;
            if (medicoNaoDisponivel || consultaMarcada) {
                terminou = true;
            } else {
                terminou = false;
            }

            return terminou;
        }
    }

    protected void takeDown() {
        System.out.println("Até mais!");
    }
}

