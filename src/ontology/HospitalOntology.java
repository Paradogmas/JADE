package ontology;

import jade.content.onto.*;
import jade.content.schema.*;


public class HospitalOntology extends Ontology implements HospitalVocabulary {

    public static final String ONTOLOGY_NAME = "Hospital-Ontology";
    private static Ontology instance = new HospitalOntology();

    public static Ontology getInstance() {
        return instance;
    }

    private HospitalOntology() {

        super(ONTOLOGY_NAME, BasicOntology.getInstance());

        try {
            // MEDICO
            ConceptSchema cs = new ConceptSchema(MEDICO);
            add(cs, Medico.class);
            cs.add(MEDICO_NOME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(MEDICO_CRM, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add(MEDICO_ESPECIALIDADE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);

            // PACIENTE
            add(cs = new ConceptSchema(PACIENTE), Paciente.class);
            cs.add(PACIENTE_CPF, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
            cs.add(PACIENTE_NOME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);

        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }
}// BankOntology