package agents;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import view.main_screen;
import jade.core.AID;
import jade.core.Agent;

public class Hospital extends Agent{

        private static final long serialVersionUID = 1L;
        private String areaDeAtendimento;
        private AID[] AgentesMedicos;

        @Override
        protected void setup() {
            super.setup();
            main_screen screen = new main_screen();
        }
}
