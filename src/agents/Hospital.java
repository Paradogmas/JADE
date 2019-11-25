package agents;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import view.main_screen;
import jade.core.AID;
import jade.core.Agent;

import java.util.concurrent.TimeUnit;

public class Hospital extends Agent{
        public ContainerController Container;
        private static final long serialVersionUID = 1L;
        private String areaDeAtendimento;
        private AID[] AgentesMedicos;

        @Override
        protected void setup() {
            super.setup();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main_screen screen = new main_screen(getContainerController());
        }
}
