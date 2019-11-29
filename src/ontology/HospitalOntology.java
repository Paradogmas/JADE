/*
package ontology;

import jade.content.onto.*;
import jade.content.schema.*;

/*
public class HospitalOntology extends Ontology implements HospitalVocabulary {

    public static final String ONTOLOGY_NAME = "Hospital-Ontology";
    private static Ontology instance = new HospitalOntology();

    public static Ontology getInstance() {
        return instance;
    }

    private HospitalOntology() {
        /*
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

            // CONSULTA
            add(cs = new ConceptSchema(CONSULTA), Consulta.class);
            cs.add(CONSULTA_SALA, (PrimitiveSchema) getSchema(BasicOntoloy.STRING), ObjectSchema.MANDATORY);
            cs.add(CONSULTA_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
            cs.add(CONSULTA_DATA, (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.MANDATORY);

            // MarcarConsulta
            AgentActionSchema as = new AgentActionSchema(MARCAR_CONSULTA);
            add(as, MarcarConsulta.class);
            as.add(MARCAR_CONSULTA_TIPO, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            as.add(MARCAR_CONSULTA_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);

            // PagarConsulta
            add(as = new AgentActionSchema(PAGAR_CONSULTA), PagarConsulta.class);
            as.add(PAGAR_CONSULTA_PRECO, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
            as.add(PAGAR_CONSULTA_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);

        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }
}*/