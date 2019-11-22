package agents;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.MessageTemplate;

import java.util.Hashtable;

public class AgenteMedico extends Agent{
    private static final long serialVersionUID = -216671578848886786L;
    private Hashtable especialidades;
    private GuiAtendimentoMedico myGui;

    @Override
    protected void setup() {

        System.out.println("Olá agente médico, "+getAID().getName());
        especialidades = new Hashtable();
        myGui = new GuiAtendimentoMedico(this);
        myGui.showGui();


        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("medico");
        sd.setName("Atendimento");
        dfd.addServices(sd);

        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException e){
            e.printStackTrace();
        }

        addBehaviour(new DemandaOfertaServidor());
        addBehaviour(new OrdenemAtendimentoServidor());


    }

    protected void takeDown() {
        try {
            System.out.println("Até a próxima!");
            DFService.deregister(this);
        } catch(FIPAException e) {
            e.printStackTrace();
        }
    }

    public void AtualizarCatalogo(final String especialidade, final int preco){
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                especialidades.put(especialidade, new Integer(preco));
                System.out.println("Atendimento em "+especialidade+"está disponível por R$"+preco);
            }
        });
    }
    private class DemandaOfertaServidor extends CyclicBehaviour{
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if(msg != null){
                String especialidade = msg.getContent();
                ACLMessage resposta = msg.createReply();
                Integer preco = (Integer) especialidades.get(especialidade);
                if(preco != null){
                    resposta.setPerformative(ACLMessage.PROPOSE);
                }
                else{
                    resposta.setPerformative(ACLMessage.REFUSE);
                    resposta.setContent("Não disponível");
                }
                myAgent.send(resposta);
            }
            else{
                block();
            }
        }
    }
    private class OrdenemAtendimentoServidor extends CyclicBehaviour{
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if(msg!=null){
                String especialidade = msg.getContent();
                ACLMessage resposta = msg.createReply();
                Integer preco = (Integer) especialidades.remove(especialidade);
                if(preco != null){
                    resposta.setPerformative(ACLMessage.INFORM);
                    System.out.println("O atendimento em "+especialidade+" foi marcado para o paciente "+msg.getSender().getName());
                }
                else{
                    resposta.setPerformative(ACLMessage.FAILURE);
                    resposta.setContent("nao-disponivel");
                }
                myAgent.send(resposta);

            }
            else{
                block();
            }
        }
    }
}