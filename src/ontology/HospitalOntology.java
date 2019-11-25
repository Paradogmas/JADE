package ontology;

import jade.content.onto.*;
import jade.content.schema.*;


public class HospitalOntology extends Ontology implements HospitalVocabulary {

    public static final String ONTOLOGY_NAME = "Hospital-Ontology";
    private static Ontology instance = new HospitalOntology();
    public static Ontology getInstance() { return instance; }

    private HospitalOntology() {

        super(ONTOLOGY_NAME, BasicOntology.getInstance());

        try {

        }
        catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }
}// BankOntology